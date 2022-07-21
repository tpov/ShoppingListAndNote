package com.tpov.shoppinglist

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import com.tpov.shoppinglist.api.ApiFactory
import com.tpov.shoppinglist.databinding.RecipeActivityBinding
import com.tpov.shoppinglist.entities.ShopingListItem
import com.tpov.shoppinglist.entities.TableRecipe
import com.tpov.shoppinglist.fragments.FragmentManager
import com.tpov.shoppinglist.fragments.NoteFragment
import com.tpov.shoppinglist.ui.recipe.RecipeFragment
import com.tpov.shoppinglist.ui.recipe.RecipeViewModel
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.*
import java.lang.System.load
import kotlin.concurrent.thread

/** LiveData
 *                                              wait children
 *  +-----+ start  +--------+ complete   +-------------+  finish  +-----------+
 *  | New | -----> | Active | ---------> | Completing  | -------> | Completed |
 *  +-----+        +--------+            +-------------+          +-----------+
 *  |  cancel / fail       |
 *  |     +----------------+
 *  |     |
 *  V     V
 *  +------------+                           finish  +-----------+
 *  | Cancelling | --------------------------------> | Cancelled |
 *  +------------+                                   +-----------+
 */

@DelicateCoroutinesApi
@InternalCoroutinesApi
class RecipeActivity : AppCompatActivity() {
    lateinit var binding: RecipeActivityBinding
    private val systemDate = TimeManager.getCurrentDate()
    private val apiService = ApiFactory.apiService
    private var valNameRecipe: String? = ""
    private var products: MutableMap<String, String> = mutableMapOf()
    private lateinit var getAllItems: Job
    private lateinit var getAllItemsNoDate: Job
    private lateinit var getLoadApi: Job
    private var textWV: String? = ""
    private var mapProduct: MutableMap<Int, String> = mutableMapOf()
    private var network = true
    private var imageURL = ""


    private var i = 0
    private var a1 = true
    private var a2 = true
    private var a3 = true
    private var a4 = true

    private var foundRecipe = false
    private var checkLoadApi = false

    private var idRecipe = 0

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("RecipeActivity", "onCreate.")

        lifecycleScope.launch {
            progressBar(true)
            loadPage()
            Log.d("RecipeActivity", "1.")
            checkRecipeDB1().invokeOnCompletion {
                Log.d("RecipeActivity", "2.")
                viewModel.getAllItemsFromList(systemDate)
            }
        }

        binding.bCheckInfo.setOnClickListener { startFragment() }
        binding.bAddProduct.setOnClickListener { addProducts() }
        binding.imbAddFavorite.setOnClickListener { setFavorite(binding.imbAddFavorite.isClickable) }
    }

    private fun loadPage() {

    }

    private fun setFavorite(clickable: Boolean) {
        if (clickable) {
            var tableRecipe = TableRecipe(idRecipe, !clickable, systemDate)
            viewModel.updateTableRecipe(tableRecipe)
            binding.imbAddFavorite.isClickable = false
        } else {
            var tableRecipe = TableRecipe(idRecipe, !clickable, systemDate)
            viewModel.updateTableRecipe(tableRecipe)
            binding.imbAddFavorite.isClickable = true
        }
    }

    private fun addProducts() {
        viewModel.getShopItemRroducts().observe(this, {
            it.forEach { item ->
                if (item.name == valNameRecipe) {
                    mapProduct.forEach { itItem ->
                        Log.d("RecipeViewModel", "insert ShopItemList")
                        viewModel.insertListItem(ShopingListItem(null, itItem.value, "", false, item.id!!, 0, ""))
                    }
                    Toast.makeText(this, "${R.string.add_recipe_item}", Toast.LENGTH_LONG).show()
                }

            }

        })
        viewModel.addShopListProducts(valNameRecipe, products, systemDate)
        binding.bAddProduct.isClickable = false
        binding.bAddProduct.isEnabled = false
    }

    private fun checkRecipeDB1() = lifecycleScope.launch {

        Log.d("RecipeActivity", "Читаем базу данных")
        viewModel.allItemsFromList.observe(this@RecipeActivity, { item ->
            Log.d("RecipeActivity", "обсервер дата")
            if (!foundRecipe) {
                item.forEach {
                    if (!foundRecipe) {
                        Log.d("RecipeActivity", "Нашел рецепт с датой")
                        foundRecipe = true
                        var tableRecipe = TableRecipe(it.id, it.favorite, systemDate)
                        viewModel.updateTableRecipe(tableRecipe)
                        loadRecipe(it)
                    }
                }

                checkLoadApi = if (!foundRecipe && !checkLoadApi) {
                   viewModel.getAllItemsFromList("")
                    foundRecipe = false
                    true
                } else {
                    false
                }
            }
            checkRecipeDB2().invokeOnCompletion {
            }
        })
    }

    private fun checkRecipeDB2() = lifecycleScope.launch {
        if (!foundRecipe && !checkLoadApi) {
            checkLoadApi = true
            foundRecipe = true
            Log.d(
                "RecipeActivity",
                "Ничего не нашел ( Будем загружать с иннета"
            )
            try {
                apiService.getFullPriceList()
            } catch (e: Exception) {
                network = false
            }
            if (network) {
                viewModel.loadRecipe().invokeOnCompletion {
                    if (checkLoadApi) foundRecipe = false
                    viewModel.getAllItemsFromList("").invokeOnCompletion {

                    }
                }
            } else {
                Toast.makeText(this@RecipeActivity, "${getString(R.string.error_load_api_recipe)}", Toast.LENGTH_LONG).show()

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadRecipe(tableRecipe: TableRecipe) {
        Log.d("RecipeActivity", "laodRecipe.")
        idRecipe = tableRecipe.id!!
        binding.tvProduct.text = ""
        binding.tvUse.text = ""
        binding.wvTitleInfo.loadData(textWV!!, "text/html", "UTF-8")
        lifecycleScope.launch {
            getEntityRecipe(idRecipe)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun getEntityRecipe(id: Int?) = with(binding) {
        Log.d("RecipeActivity", "getEntityRecipe.")
        viewModel.getEntityRecipe(id!!).observe(this@RecipeActivity, {
            i = 0
            it.forEach { item ->
                if (a1) {

                    i++
                    Log.d("getEntityRecipe", "getEntityRecipe: $i, $item")

                    nameRecipe.text = item.title2
                    valNameRecipe = item.title2



                    loadCheckTVandImB(imvCheck1, tvCheck1, item.vegetarian.toString(), "vegetarian")
                    loadCheckTVandImB(imvCheck2, tvCheck2, item.vegan.toString(), "vegan")
                    loadCheckTVandImB(imvCheck3, tvCheck3, item.glutenFree.toString(), "glutenFree")
                    loadCheckTVandImB(imvCheck4, tvCheck4, item.dairyFree.toString(), "dairyFree")
                    loadCheckTVandImB(imvCheck5, tvCheck5, item.veryHealthy.toString(),"veryHealthy")
                    loadCheckTVandImB(imvCheck6, tvCheck6, item.cheap.toString(), "cheap")
                    loadCheckTVandImB(
                        imvCheck7,
                        tvCheck7,
                        item.veryPopular.toString(),
                        "veryPopular"
                    )
                    loadCheckTVandImB(
                        imvCheck8,
                        tvCheck8,
                        item.sustainable.toString(),
                        "sustainable"
                    )
                    loadCheckTVandImB(imvCheck9, tvCheck9, item.lowFodmap.toString(), "lowFodmap")

                    tvInfoFavorite.text = item.aggregateLikes.toString()
                    tvInfoPrice.text = "${item.pricePerServing.toString()}, per serving"
                    tvInfoRating.text = "${item.healthScore.toString()}% Score"
                    tvInfoTime.text = "Ready in ${item.readyInMinutes.toString()} minutes"

                    textWV = item.summary
                    wvTitleInfo.loadData(textWV!!, "text/html", "UTF-8")

                    if (i == 1) {
                        imageURL = "${item.image}"
                        Picasso.get()
                            .load(imageURL)
                            .error(R.drawable.ic_remove)
                            .into(binding.imvRecipe)
                        progressBar(false)
                    }
                }
            }

        })
        var q = 0
        viewModel.getEntityRecipeStep(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                q++
                Log.d("getEntityRecipe", "getEntityRecipeStep: $q, $item")

            }
        })
        var w = 0
        viewModel.getEntityRecipeEquipment(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                if (a2) {
                    w++
                    Log.d("getEntityRecipe", "getEntityRecipeEquipment: $w, $item")
                    tvUse.append("${item.localizedName}, ")
                }
            }
        })
        var e = 0
        viewModel.getEntityRecipeIngredient(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                e++
                Log.d("getEntityRecipe", "getEntityRecipeIngredient: $e, $item")


            }
        })
         var r = 0
        viewModel.getEntityRecipeCuisines(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                r++
                Log.d("getEntityRecipe", "getEntityRecipeCuisines: $r, $item")


            }
        })
        var t = 0
        viewModel.getEntityRecipeDiets(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                t++
                Log.d("getEntityRecipe", "getEntityRecipeDiets: $t, $item")


            }
        })
        var l = 0
        viewModel.getEntityRecipeDishTypes(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                l++
                Log.d("getEntityRecipe", "getEntityRecipeDishTypes: $l, $item")


            }
        })
        var u = 0
        viewModel.getEntityRecipeExtendedIngredient(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                if (a3) {
                    u++
                    Log.d("getEntityRecipe", "getEntityRecipeExtendedIngredient: $u, $item")
                    //products[item.nameClean.toString()] = item
                    var oneMapProduct = mapProduct[u]
                    mapProduct[u] = "${item.nameClean}${oneMapProduct}"
                    showProduct()

                }
            }
        })
         var z = 0
        viewModel.getEntityRecipeMeta(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                z++
                Log.d("getEntityRecipe", "getEntityRecipeMeta: $z, $item")


            }
        })
         var o = 0
        viewModel.getEntityRecipeOccasions(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                o++
                Log.d("getEntityRecipe", "getEntityRecipeOccasions: $o, $item")


            }
        })
        var p = 0
        viewModel.getEntityRecipeMeasures(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                p++
                Log.d("getEntityRecipe", "getEntityRecipeMeasures: $p, $item")


            }
        })
        var a = 0
        viewModel.getEntityRecipeMetric(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                if (a4) {
                    a++
                    Log.d("getEntityRecipe", "getEntityRecipeMetric: $a, $item")
                    var oneMapProduct = mapProduct[a]
                    mapProduct[a] = "$oneMapProduct ${item.amount}(${item.unitShort})"
                    showProduct()
                }
            }
        })
         var s = 0
        viewModel.getEntityRecipeUs(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                s++
                Log.d("getEntityRecipe", "getEntityRecipeUs: $s, $item")


            }
        })
         var d = 0
        viewModel.getEntityRecipeLength(id).observe(this@RecipeActivity, {
            it.forEach { item ->
                d++
                Log.d("getEntityRecipe", "getEntityRecipeLength  : $d, $item")


            }
        })
    }

    private fun showProduct() = with(binding) {
        tvProduct.text = ""
        mapProduct.forEach {
            tvProduct.append("${it.value}, ")
        }

    }

    private fun loadCheckTVandImB(
        imvCheck: ImageView,
        tvCheck: TextView,
        check: String,
        text: String
    ) {
        Log.d("RecipeActivity", "loadCheckTVandImB: $check, $text")
        if (check == "false") {

            imvCheck.visibility = View.GONE
            tvCheck.visibility = View.GONE

        } else {

            imvCheck.visibility = View.VISIBLE
            tvCheck.visibility = View.VISIBLE
            tvCheck.text = text
        }
    }

    private fun progressBar(b: Boolean) {
        Log.d("RecipeActivity", "Progress bar: $b")
        if (b) binding.progressBar.visibility = View.VISIBLE
        else {
            binding.progressBar.visibility = View.GONE
            a1 = true
                a2 = true
                a3 = true
                a4 = true
        }
    }

    private fun startFragment() {
        FragmentManager.setFragment2(RecipeFragment.newInstance(), this)
    }
}