package com.tpov.shoppinglist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tpov.shoppinglist.api.ApiFactory
import com.tpov.shoppinglist.api.ApiFactory.apiService
import com.tpov.shoppinglist.databinding.RecipeActivityBinding
import com.tpov.shoppinglist.db.MainViewModel
import com.tpov.shoppinglist.entities.TableRecipe
import com.tpov.shoppinglist.fragments.FragmentManager
import com.tpov.shoppinglist.pojo.Responce
import com.tpov.shoppinglist.ui.recipe.RecipeFragment
import com.tpov.shoppinglist.ui.recipe.RecipeViewModel
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@InternalCoroutinesApi
class RecipeActivity : AppCompatActivity() {
    lateinit var binding: RecipeActivityBinding
    private val systemDate = TimeManager.getCurrentTime()
    private val apiService = ApiFactory.apiService

    private var checkDateApi = false
    private var checkNotDateApi = false

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar(true)

        binding.bCheckInfo.setOnClickListener { startFragment() }
            checkRecipeDB()
            Log.d("RecipeActivity", "thread start")

    }
    private fun checkRecipeDB() {
        for (i in 0..1) {
            viewModel.getAllItemsFromList(systemDate).observe(this, { item ->
                item.forEach {
                    checkDateApi = true
                    loadRecipe(it)
                }
                if (!checkDateApi) {
                    viewModel.getAllItemsFromList("").observe(this, { item ->
                        item.forEach {
                            checkNotDateApi = true
                            loadRecipe(it)
                        }
                        if (!checkNotDateApi) {
                            viewModel.loadRecipe()
                        }
                    })
                }

            })
        }

    }



    @SuppressLint("SetTextI18n")
    private fun loadRecipe(tableRecipe: TableRecipe)  {
        Log.d("RecipeActivity", "laodRecipe")
        var id = tableRecipe.id
        lifecycleScope.launch {
            getEntityRecipe(id)
            progressBar(false)
        }



    }

    private fun getEntityRecipe(id: Int?) = with(binding) {
        viewModel.getEntityRecipe(id!!).observe(this@RecipeActivity, {
            it.forEach { item ->

            }
        })

        viewModel.getEntityRecipeAnalyzedInstruction(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeStep(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeEquipment(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeIngredient(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeCuisines(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeDiets(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeDishTypes(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeExtendedIngredient(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeMeta(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeOccasions(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeMeasures(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeMetric(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeUs(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
        viewModel.getEntityRecipeLength(id).observe(this@RecipeActivity, {
            it.forEach { item ->


            }
        })
    }

    private fun loadCheckTVandImB(
        imvCheck: ImageView,
        tvCheck: TextView,
        check: Boolean
    ) {
        if (check) {
            imvCheck.visibility = View.VISIBLE
            tvCheck.visibility = View.VISIBLE
        } else {

            imvCheck.visibility = View.VISIBLE
            tvCheck.visibility = View.VISIBLE
        }
    }

    private fun loadImage(getimage: String?) {

    }

    private fun progressBar(b: Boolean) {
        if (b) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun startFragment() {
        FragmentManager.setFragment(
            RecipeFragment.newInstance(),
            this
        )
    }
}