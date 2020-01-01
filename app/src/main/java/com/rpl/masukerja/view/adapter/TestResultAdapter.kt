package com.rpl.masukerja.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rpl.masukerja.R
import com.rpl.masukerja.model.TestResult
import kotlinx.android.synthetic.main.item_test_result.view.*

class TestResultAdapter: RecyclerView.Adapter<TestResultAdapter.ViewHolder>() {

    private var listTest = ArrayList<TestResult>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(listTest: ArrayList<TestResult>?) {
        if (listTest != null) {
            this.listTest = listTest
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test_result, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTest.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTest[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(test: TestResult) {
            with(itemView) {
                tv_name.text = test.result?.name
                tv_date.text = test.date

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(test)
                }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(test: TestResult)
    }
}