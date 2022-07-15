package com.tpov.shoppinglist.ui.recipe

import android.os.BaseBundle
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tpov.shoppinglist.R
import com.tpov.shoppinglist.fragments.BaseFragment
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class RecipeFragment : BaseFragment() {


    private lateinit var viewModel: RecipeViewModel
    override fun onClickNew() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = RecipeFragment()
    }

}