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
import com.rpl.masukerja.api.response.ArticleResponse
import com.rpl.masukerja.api.response.ListJobResponse
import com.rpl.masukerja.api.response.SearchResponse
import com.rpl.masukerja.model.Article
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.view.adapter.ArticleAdapter
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
    private lateinit var articleAdapter: ArticleAdapter

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
        articleAdapter = ArticleAdapter()

        btn_search.setOnClickListener(this)
        rv_job_favorite.setHasFixedSize(true)
        rv_job_favorite.layoutManager = LinearLayoutManager(this.requireContext())
        rv_job_favorite.adapter = jobAdapter

        rv_article_now.setHasFixedSize(true)
        rv_article_now.layoutManager = LinearLayoutManager(this.requireContext())
        rv_article_now.adapter = articleAdapter

        jobAdapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback {
            override fun onItemClicked(job: Job) {
                openDetail(job)
            }
        })

        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                openArticle(article)
            }
        })
        loadFavorite()
        loadArticle()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_search -> {
                val intent = Intent(this.context, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadFavorite() {
        val token = TokenPreference(this.requireContext()).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getFavorite("Bearer $token")
        request.enqueue(object : Callback<ListJobResponse> {
            override fun onResponse(call: Call<ListJobResponse>, response: Response<ListJobResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.jobs as ArrayList<Job>
                    if (data.isEmpty()) {
                        tv_job_saved.text = "Anda Belum Menyimpan Pekerjaan"
                    } else {
                        tv_job_saved.text = "Pekerjaan Yang Disimpan"
                        jobAdapter.setJobs(data)
                    }
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

    private fun loadArticle() {
        val token = TokenPreference(this.requireContext()).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getArticle("Bearer $token")
        request.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    articleAdapter.setData(data)
                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun openArticle(article: Article) {
        val intent = Intent(this.requireContext(), ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, article)
        startActivity(intent)
    }
}
