package com.tpov.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tpov.shoppinglist.ui.recipe.RecipeFragment

class RecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecipeFragment.newInstance())
                .commitNow()
        }
    }
}