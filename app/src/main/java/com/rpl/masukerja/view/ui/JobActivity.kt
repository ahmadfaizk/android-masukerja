package com.rpl.masukerja.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpl.masukerja.R
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.view.adapter.JobAdapter
import kotlinx.android.synthetic.main.activity_job.*

class JobActivity : AppCompatActivity() {

    private lateinit var adapter: JobAdapter
    private lateinit var listJob: ArrayList<Job>

    companion object {
        const val EXTRA_JOBS = "extra_jobs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)

        listJob = intent.getParcelableArrayListExtra<Job>(EXTRA_JOBS) as ArrayList<Job>

        adapter = JobAdapter()

        rv_jobs.setHasFixedSize(true)
        rv_jobs.layoutManager = LinearLayoutManager(this)
        rv_jobs.adapter = adapter

        adapter.setJobs(listJob)
        supportActionBar?.title = "Hasil Pencarian"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback {
            override fun onItemClicked(job: Job) {
                Toast.makeText(this@JobActivity, job.name, Toast.LENGTH_SHORT).show()
                openDetail(job)
            }
        })

        tv_job_count.text = "${listJob.size} Pekerjaan ditemukan"
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
}
