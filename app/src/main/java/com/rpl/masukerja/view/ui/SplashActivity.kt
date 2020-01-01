package com.rpl.masukerja.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rpl.masukerja.R
import com.rpl.masukerja.api.TokenPreference

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed({
            val token = TokenPreference(this).getToken()
            if (token == null || token.isEmpty()) {
                val intent  = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 3000)
    }
}
