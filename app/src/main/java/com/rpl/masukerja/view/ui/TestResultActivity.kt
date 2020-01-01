package com.rpl.masukerja.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.rpl.masukerja.R
import com.rpl.masukerja.model.TestResult
import kotlinx.android.synthetic.main.activity_test_result.*

class TestResultActivity : AppCompatActivity() {

    private lateinit var test: TestResult

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        test = intent.getParcelableExtra(EXTRA_RESULT) as TestResult
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        supportActionBar?.title = test.result?.name
        tv_name.text = test.result?.name
        tv_nickname.text = test.result?.nickname
        tv_date.text = test.date

        val ie = test.introvert?.times(100)?.div(test.introvert!! + test.extrovert!!)!!
        val sn = test.sensing?.times(100)?.div(test.sensing!! + test.intuiting!!)!!
        val tf = test.thingking?.times(100)?.div(test.thingking!! + test.feeling!!)!!
        val jp = test.judging?.times(100)?.div(test.judging!! + test.perceiving!!)!!

        bar_ie.progress = ie
        bar_sn.progress = sn
        bar_tf.progress = tf
        bar_jp.progress = jp

        tv_description.text = test.result?.description
        tv_characteristics.text = test.result?.characteristic
        tv_suggestion.text = test.result?.suggestion
        tv_job.text = test.result?.job
        tv_partner.text = test.result?.partner
    }
}
