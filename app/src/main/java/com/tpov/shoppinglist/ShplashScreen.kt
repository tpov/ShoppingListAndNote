package com.tpov.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.window.SplashScreen
import com.tpov.shoppinglist.databinding.ActivityShopListBinding
import com.tpov.shoppinglist.databinding.ActivityShplashScreenBinding
import kotlinx.coroutines.InternalCoroutinesApi
@InternalCoroutinesApi
class ShplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivityShplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shplash_screen)
        binding = ActivityShplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        binding.tvHw.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        binding.tvHw.startAnimation(AnimationUtils.loadAnimation(this, R.anim.close_color_picker))
    }
}