package com.example.e_commerce_kot.splash
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        startHomeScreen()
    }
    private fun startHomeScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(/* enterAnim = */ R.navigation.navigate_to, /* exitAnim = */
                R.navigation.navigation_from)
            finish()
        }
            ,2000)
    }
}
