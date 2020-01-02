package com.rpl.masukerja.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rpl.masukerja.R
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.utils.RupiahFormatter
import kotlinx.android.synthetic.main.item_job.view.*

class JobAdapter: RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    private var listJob = ArrayList<Job>()
    private var onItemClickCallback: OnItemClickCallback? = null
    private val formatter = RupiahFormatter()

    fun setJobs(listJob: ArrayList<Job>?) {
        if (listJob != null) {
            this.listJob.clear()
            this.listJob.addAll(listJob)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listJob.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listJob[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(job: Job) {
            with(itemView) {
                tv_name.text = job.name
                tv_company.text = job.company
                tv_salary.text = formatSalary(job.min_salary, job.max_salary)
                tv_location.text = job.location
                tv_job_source.text = job.source
                tv_job_date.text = job.posting_date

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(job)
                }
            }
        }
    }

    private fun formatSalary(min: Double?, max: Double?): String {
        if (min == 0.0 && max == 0.0) {
            return "Gaji Dirahasiakan"
        } else {
            return "${min?.let { formatter.format(it) }} - ${max?.let { formatter.format(it) }}"
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(job: Job)
    }
}