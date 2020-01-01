package com.rpl.masukerja.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.JobDataResponse
import com.rpl.masukerja.api.response.ListJobResponse
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.utils.RupiahFormatter
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.util.ArrayList

class SearchActivity : AppCompatActivity(), View.OnClickListener {

    private var salary: Int? = null
    private lateinit var formatter: RupiahFormatter

    companion object {
        internal val TAG = SearchActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Search Jobs"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_search.setOnClickListener(this)
        formatter = RupiahFormatter()

        slide_salary.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var gaji = progress*100000.toDouble()
                tv_salary.text =formatter.format(gaji)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                salary = seekBar?.progress?.times(100000)!!
                Toast.makeText(this@SearchActivity, "Salary $salary", Toast.LENGTH_SHORT).show()
            }
        })

        loadJobData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_search -> {
                checkInput()
            }
        }
    }

    private fun checkInput() {
        var name: String?
        var location: String?
        var field: String?

        name = et_job_name.text.toString()
        location = et_job_location.text.toString()
        field = et_job_field.text.toString()

        if(name.isEmpty()) {
            name = null
        }
        if(location.isEmpty()) {
            location = null
        }
        if (field.isEmpty()) {
            field = null
        }
        if (salary == 0) {
            salary = null
        }

        search(name, location, field)
    }

    private fun search(name: String?, location: String?, field: String?) {
        showLoading(true)
        val token = TokenPreference(this).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).searchJob("Bearer $token", name, salary, location, field)
        request.enqueue(object : Callback<ListJobResponse> {
            override fun onResponse(call: Call<ListJobResponse>, responseList: Response<ListJobResponse>) {
                if (responseList.isSuccessful) {
                    val jobs = responseList.body()?.jobs
                    val intent = Intent(this@SearchActivity, JobActivity::class.java)
                    intent.putParcelableArrayListExtra(JobActivity.EXTRA_JOBS, jobs as ArrayList<Job>)
                    startActivity(intent)
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<ListJobResponse>, t: Throwable) {
                t.printStackTrace()
                if (t is SocketTimeoutException) {
                    Toast.makeText(this@SearchActivity, "Request Timeout", Toast.LENGTH_SHORT).show()
                }
                showLoading(false)
            }
        })
    }

    private fun loadJobData() {
        val token = TokenPreference(this).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getJobData("Bearer $token")
        request.enqueue(object : Callback<JobDataResponse> {
            override fun onResponse(call: Call<JobDataResponse>, response: Response<JobDataResponse>) {
                if (response.isSuccessful) {
                    var fields = response.body()?.field as ArrayList<String>
                    var locations = response.body()?.location as ArrayList<String>

                    var fieldAdapter = ArrayAdapter(this@SearchActivity, android.R.layout.simple_list_item_1, fields)
                    var locationAdapter = ArrayAdapter(this@SearchActivity, android.R.layout.simple_list_item_1, locations)

                    et_job_field.setAdapter(fieldAdapter)
                    et_job_location.setAdapter(locationAdapter)
                }
            }

            override fun onFailure(call: Call<JobDataResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loading.visibility = View.VISIBLE
        } else {
            loading.visibility = View.GONE
        }
    }
}
