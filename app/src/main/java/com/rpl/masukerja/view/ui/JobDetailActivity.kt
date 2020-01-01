package com.rpl.masukerja.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.MessageResponse
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.utils.RupiahFormatter
import kotlinx.android.synthetic.main.activity_job_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var job: Job
    private val formatter = RupiahFormatter()

    companion object {
        const val EXTRA_JOB = "extra_job"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        job = intent.getParcelableExtra(EXTRA_JOB) as Job

        btn_view.setOnClickListener(this)
        btn_favorite.setOnClickListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initialize()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialize() {
        supportActionBar?.title = job.name

        Glide.with(this)
            .load(job.image)
            .into(img_company)

        tv_job_name.text = job.name
        tv_job_field.text = job.field
        tv_company.text = job.company
        tv_location.text = job.location
        tv_salary.text = formatSalary(job.min_salary, job.max_salary)
        tv_job_description.text = job.description
        tv_job_source.text = job.source
        tv_posting_date.text = job.posting_date
        tv_closing_date.text = job.closing_date
    }

    private fun formatSalary(min: Double?, max: Double?): String {
        if (min == 0.0 && max == 0.0) {
            return "Gaji Dirahasiakan"
        } else {
            return "${min?.let { formatter.format(it) }} - ${max?.let { formatter.format(it) }}"
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_view -> {
                val web = Intent(this, JobWebViewActivity::class.java)
                web.putExtra(JobWebViewActivity.EXTRA_URL, job.url)
                startActivity(web)
            }
            R.id.btn_favorite -> {
                favorite()
            }
        }
    }

    private fun favorite() {
        val token = TokenPreference(this).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).setFavorite("Bearer $token", job.id)
        request.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@JobDetailActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}
