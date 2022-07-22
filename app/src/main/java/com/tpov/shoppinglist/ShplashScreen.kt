package com.tpov.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
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
        visibleTPOV(true)
    }

    private fun visibleTPOV(visible: Boolean) = with(binding) {
        Log.d("WorkManager", "Видимость ТПОВ.")
        if (visible) {
            tvT.visibility = View.VISIBLE
            tvP.visibility = View.VISIBLE
            tvO.visibility = View.VISIBLE
            tvV.visibility = View.VISIBLE
        } else {
            tvT.visibility = View.GONE
            tvP.visibility = View.GONE
            tvO.visibility = View.GONE
            tvV.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        createAnimation()
    }

    private fun createAnimation() = with(binding) {

        tvT.startAnimation(AnimationUtils.loadAnimation(this@ShplashScreen, R.anim.anim_splash_t))
        tvP.startAnimation(AnimationUtils.loadAnimation(this@ShplashScreen, R.anim.anim_splash_p))
        tvO.startAnimation(AnimationUtils.loadAnimation(this@ShplashScreen, R.anim.anim_splash_o))
        var anim3 = AnimationUtils.loadAnimation(this@ShplashScreen, R.anim.anim_splash_v)
        animationListener(anim3, 3)

        tvV.startAnimation(anim3)
    }

    private fun animationListener(anim: Animation, numAnim: Int) {

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                visibleTPOV(true)
            }

            override fun onAnimationEnd(p0: Animation?) {
                startActivity()


            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
    }

    private fun startActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        visibleTPOV(false)
        finish()
    }
}