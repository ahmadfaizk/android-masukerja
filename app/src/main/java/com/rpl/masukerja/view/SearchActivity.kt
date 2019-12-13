package com.rpl.masukerja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rpl.masukerja.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
