package com.rpl.masukerja.view.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.ArticleResponse
import com.rpl.masukerja.model.Article
import com.rpl.masukerja.view.adapter.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment() {

    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleAdapter()
        rv_article.setHasFixedSize(true)
        rv_article.layoutManager = LinearLayoutManager(this.requireContext())
        rv_article.adapter = adapter

        adapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: Article) {
                val detail = Intent(this@ArticleFragment.requireContext(), ArticleDetailActivity::class.java)
                detail.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, article)
                startActivity(detail)
            }
        })

        loadData()
    }

    private fun loadData() {
        val token = TokenPreference(this.requireContext()).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getArticle("Bearer $token")
        request.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    tv_article_count.text = "${data?.size} Artikel"
                    adapter.setData(data)
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
