package com.rpl.masukerja.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.rpl.masukerja.R
import com.rpl.masukerja.model.Article
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var article: Article

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        article = intent.getParcelableExtra(EXTRA_ARTICLE) as Article

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
        supportActionBar?.title = article.name
        tv_title.text = article.name
        tv_category.text = article.category
        tv_date.text = article.date
        tv_description.text = article.description
    }
}
