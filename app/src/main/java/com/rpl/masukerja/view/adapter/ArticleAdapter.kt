package com.rpl.masukerja.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rpl.masukerja.R
import com.rpl.masukerja.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var listArticle = ArrayList<Article>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(data: ArrayList<Article>?) {
        if (data != null) {
            listArticle = data
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listArticle.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(article: Article) {
            with(itemView) {
                tv_title.text = article.name
                tv_date.text = article.date
                tv_category.text = article.category
                tv_description.text = article.description

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(article)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: Article)
    }
}