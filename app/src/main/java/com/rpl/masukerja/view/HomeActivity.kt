package com.rpl.masukerja.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rpl.masukerja.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_statistic, R.id.navigation_article, R.id.navigation_test, R.id.navigation_profile
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }
}
