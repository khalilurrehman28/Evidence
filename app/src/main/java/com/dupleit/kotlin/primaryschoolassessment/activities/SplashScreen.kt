package com.dupleit.kotlin.primaryschoolassessment.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dupleit.kotlin.primaryschoolassessment.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val customFont1 = Typeface.createFromAsset(assets, "fonts/LatoRegular.ttf")
        splashText.typeface =customFont1

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(i)

            // close this activity
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        // Splash screen timer
        private val SPLASH_TIME_OUT = 3000
    }
}
