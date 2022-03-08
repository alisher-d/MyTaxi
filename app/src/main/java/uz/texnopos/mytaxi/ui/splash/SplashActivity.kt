package uz.texnopos.mytaxi.ui.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import uz.texnopos.mytaxi.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    private lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler(mainLooper)
        handler.postDelayed(startActivity(), 1000)
    }

    private fun startActivity(): Runnable {
        return Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}