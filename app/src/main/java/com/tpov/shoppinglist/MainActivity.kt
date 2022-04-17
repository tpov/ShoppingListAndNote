package com.tpov.shoppinglist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.tpov.shoppinglist.billing.BillingManager
import com.tpov.shoppinglist.databinding.ActivityMainBinding
import com.tpov.shoppinglist.databinding.NewListDialogBinding
import com.tpov.shoppinglist.dialog.NewListDialog
import com.tpov.shoppinglist.fragments.FragmentManager
import com.tpov.shoppinglist.fragments.NoteFragment
import com.tpov.shoppinglist.fragments.NoteFragment.Companion.newInstance
import com.tpov.shoppinglist.fragments.ShopListNamesFragment
import com.tpov.shoppinglist.settings.SettingsActivity
import com.tpov.shoppinglist.settings.SettingsFragment
import kotlinx.coroutines.InternalCoroutinesApi
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback as InterstitialAdLoadCallback

@InternalCoroutinesApi
class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    lateinit var binding: ActivityMainBinding
    lateinit var defPref: SharedPreferences
    private var currentMenuItemId = R.id.shop_list
    private var currentTheme = ""
    private var iAd: InterstitialAd? = null
    private var currentShowAd = 0
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        MobileAds.initialize(this)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences(BillingManager.MAIN_PREF, MODE_PRIVATE)

        currentTheme = defPref.getString("theme_key", "ShoppingList").toString()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        Log.d("MainActivity_bNav", "onCrate")
        setButtonNavListListener()
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        if (!pref.getBoolean(BillingManager.REMOVE_ADS_KEY, false)) {
            loadInterAd()
        }
    }

    private fun showInterAd(adListener :AdListener) {
        if (iAd != null) {
            Log.d("MainActivity_bNav", "iAd != null")

            iAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAd = null
                    loadInterAd()
                }
                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    Toast.makeText(this@MainActivity, R.string.massage_show_ads, Toast.LENGTH_LONG).show()
                    Log.d("MainActivity_bNav", "onAdShowedFullScreenContent")

                    loadInterAd()
                }
            }
            iAd?.show(this)
        } else {
            Log.d("MainActivity_bNav", "iAd = null")
            adListener.onFinish()
        }
    }

    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(this,
                getString(R.string.inter_ad_id), request, object : InterstitialAdLoadCallback() {

            override fun onAdLoaded(ad: InterstitialAd) {
                Log.d("MainActivity_bNav", "onAdLoaded")

                iAd = ad
            }
            override fun onAdFailedToLoad(ad: LoadAdError) {
                Log.d("MainActivity_bNav", "onAdFailedToLoad")

                iAd = null
            }
        } )
    }

    private fun setButtonNavListListener() {
        Log.d("MainActivity_bNav", "setButtonNavListListener")

        binding.bNav.setOnItemSelectedListener {
            Log.d("MainActivity_bNav", "setOnClickListener")

            when(it.itemId) {
                R.id.setting -> {
                    showInterAd(object: AdListener {
                        override fun onFinish() {
                            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        }
                    })
                    Log.d("MainActivity_bNav", "Setting")
                }
                R.id.notes -> {
                    Log.d("MainActivity_bNav", "Notes")

                    currentMenuItemId = R.id.notes
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    Log.d("MainActivity_bNav", "shop_list")
                    currentMenuItemId = R.id.shop_list
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.newItem -> {
                    if (currentShowAd == 3) {
                        showInterAd(object: AdListener {
                            override fun onFinish() {
                                FragmentManager.currentFrag?.onClickNew()
                            }
                        })
                        currentShowAd = 0
                    } else {
                        FragmentManager.currentFrag?.onClickNew()
                        currentShowAd++
                    }
                    Log.d("MainActivity_bNav", "NewItem")
                }
            }
            true
        }
    }

    private fun getSelectedTheme(): Int {
        return when {
            defPref.getString("theme_key", "classic") == "classic" -> {
                R.style.Theme_ShoppingList
            }
            defPref.getString("theme_key", "light") == "light" -> {
                R.style.Theme_Light
            }
            defPref.getString("theme_key", "night") == "night" -> {
                R.style.Theme_Night
            }
            else -> {
                R.style.Theme_ShoppingList
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.bNav.selectedItemId = currentMenuItemId
        if(defPref.getString("theme_key", "ShoppingList") != currentTheme) recreate()
    }

    override fun onClick(name: String) {
    }

    interface AdListener {
        fun onFinish()
    }
}