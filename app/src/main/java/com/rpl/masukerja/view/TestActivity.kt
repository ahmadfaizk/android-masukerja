package com.rpl.masukerja.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rpl.masukerja.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity(), View.OnClickListener {

    private var isLargeLayout: Boolean = false

    companion object {
        internal val TAG = TestActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        isLargeLayout = resources.getBoolean(R.bool.large_layout)
    }

    override fun onClick(v: View) {

    }
}
