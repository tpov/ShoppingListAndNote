package com.tpov.shoppinglist.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.tpov.shoppinglist.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var defPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        setContentView(R.layout.activity_settings)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.placeHolder, SettingsFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedTheme(): Int {
        return when {
            defPref.getString("theme key", "classic") == "classic" -> {
                R.style.Theme_ShoppingList
            }
            defPref.getString("theme key", "light") == "light" -> {
                R.style.Theme_Light
            }
            defPref.getString("theme key", "night") == "night" -> {
                R.style.Theme_Night
            }
            else -> { R.style.Theme_ShoppingList }
        }
    }
}