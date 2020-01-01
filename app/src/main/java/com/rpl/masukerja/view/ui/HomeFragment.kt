package com.rpl.masukerja.view.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.ListJobResponse
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.view.adapter.JobAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var jobAdapter: JobAdapter

    companion object {
        internal val TAG = HomeFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobAdapter = JobAdapter()

        btn_search.setOnClickListener(this)
        rv_job_favorite.setHasFixedSize(true)
        rv_job_favorite.layoutManager = LinearLayoutManager(this.requireContext())
        rv_job_favorite.adapter = jobAdapter

        jobAdapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback {
            override fun onItemClicked(job: Job) {
                openDetail(job)
            }
        })
        setFavorite()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_search -> {
                val intent = Intent(this.context, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setFavorite() {
        val token = TokenPreference(this.requireContext()).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getFavorite("Bearer $token")
        request.enqueue(object : Callback<ListJobResponse> {
            override fun onResponse(call: Call<ListJobResponse>, response: Response<ListJobResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.jobs as ArrayList<Job>
                    jobAdapter.setJobs(data)
                    Toast.makeText(this@HomeFragment.requireContext(), "Data Favorite Loaded", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListJobResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    private fun openDetail(job: Job) {
        val intent = Intent(this.requireContext(), JobDetailActivity::class.java)
        intent.putExtra(JobDetailActivity.EXTRA_JOB, job)
        startActivity(intent)
    }
}
