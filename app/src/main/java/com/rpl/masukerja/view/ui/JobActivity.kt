package com.rpl.masukerja.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.SearchResponse
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.view.adapter.JobAdapter
import kotlinx.android.synthetic.main.activity_job.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class JobActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: JobAdapter
    private lateinit var data: SearchResponse

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)

        data = intent.getParcelableExtra(EXTRA_DATA) as SearchResponse

        adapter = JobAdapter()

        rv_jobs.setHasFixedSize(true)
        rv_jobs.layoutManager = LinearLayoutManager(this)
        rv_jobs.adapter = adapter

        btn_next.setOnClickListener(this)
        btn_prev.setOnClickListener(this)

        supportActionBar?.title = "Hasil Pencarian"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback {
            override fun onItemClicked(job: Job) {
                Toast.makeText(this@JobActivity, job.name, Toast.LENGTH_SHORT).show()
                openDetail(job)
            }
        })

        tv_job_count.text = "${data.data?.total} Pekerjaan ditemukan"
        initView()
    }

    private fun openDetail(job: Job) {
        val intent = Intent(this, JobDetailActivity::class.java)
        intent.putExtra(JobDetailActivity.EXTRA_JOB, job)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadMore(page: Int?) {
        val token = TokenPreference(this).getToken()
        val name = data.params?.title
        val salary = data.params?.salary
        val location = data.params?.location
        val field = data.params?.field
        val request = ApiClient.retrofit.create(Request::class.java).searchJob("Bearer $token", page, name, salary, location, field)
        request.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    data = response.body() as SearchResponse
                    initView()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                if (t is SocketTimeoutException) {
                    Toast.makeText(this@JobActivity, "Request Timeout", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initView() {
        adapter.setJobs(data.data?.jobs as ArrayList<Job>)
        //showMessage("Page: ${data.data?.currentPage}, ${data.data?.from} - ${data.data?.to}")
        tv_page.text = "Halaman ${data.data?.currentPage} dari ${data.data?.lastPage}"
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_prev -> {
                if (data.data?.currentPage == 1) {
                    showMessage("Ini Halaman Pertama")
                }
                else {
                    loadMore(data.data?.currentPage?.minus(1))
                }
            }
            R.id.btn_next -> {
                if (data.data?.currentPage == data.data?.lastPage) {
                    showMessage("Ini Halaman Terakhir")
                } else {
                    loadMore(data.data?.currentPage?.plus(1))
                }
            }
        }
    }
}
