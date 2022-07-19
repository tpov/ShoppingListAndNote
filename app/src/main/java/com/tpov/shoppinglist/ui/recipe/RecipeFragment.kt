package com.tpov.shoppinglist.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tpov.shoppinglist.MainApp
import com.tpov.shoppinglist.R
import com.tpov.shoppinglist.databinding.FragmentNoteBinding
import com.tpov.shoppinglist.databinding.MainFragmentBinding
import com.tpov.shoppinglist.db.MainViewModel
import com.tpov.shoppinglist.entities.TableRecipe
import com.tpov.shoppinglist.fragments.BaseFragment
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class RecipeFragment : BaseFragment() {

    private lateinit var binding: MainFragmentBinding
    private val systemDate = TimeManager.getCurrentDate()
    private var textWeb = ""
    private var idRecipe = -1
    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkRecipeDB1()
        viewModel.getAllItemsFromList(systemDate)
    }

    private fun checkRecipeDB1() {

        viewModel.allItemsFromList.observe(viewLifecycleOwner, { item ->
            item.forEach {
                idRecipe = it.id!!
                if (id != -1) loadRecipe(it)
            }

        })
    }

    private fun loadRecipe(tableRecipe: TableRecipe) {
        viewModel.getEntityRecipe(tableRecipe.id!!).observe(viewLifecycleOwner, {
            it.forEach { item ->
                textWeb = item.instructions!!
                binding.wvRecipe.loadData(textWeb!!, "text/html", "UTF-8")
            }
        })

    }

    companion object {
        fun newInstance() = RecipeFragment()
    }
}