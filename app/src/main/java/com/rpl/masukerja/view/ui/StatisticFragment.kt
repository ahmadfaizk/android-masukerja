package com.rpl.masukerja.view.ui


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.StatisticResponse
import com.rpl.masukerja.model.StatisticData
import kotlinx.android.synthetic.main.fragment_statistic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class StatisticFragment : Fragment() {

    private var listData = ArrayList<StatisticData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
    }

    private fun loadData() {
        val token = TokenPreference(this.requireContext()).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getStatisticField("Bearer $token")
        request.enqueue(object : Callback<StatisticResponse> {
            override fun onResponse(call: Call<StatisticResponse>, response: Response<StatisticResponse>) {
                if (response.isSuccessful) {
                    listData = response.body()?.data!!
                    initChart()
                    initTable()
                }
            }

            override fun onFailure(call: Call<StatisticResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun initChart() {
        val entries = ArrayList<PieEntry>()
        for (data in listData) {
            val entry = data.count?.toFloat()?.let { PieEntry(it, data.name) }
            if (entry != null) {
                entries.add(entry)
            }
        }
        val dataSet = PieDataSet(entries, "Data Field")

        dataSet.colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f

        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter(data_chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)

        data_chart.data = data
        data_chart.invalidate()
    }

    private fun initTable() {
        for (data in listData) {
            val row = TableRow(this.requireContext())
            val layout = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            row.layoutParams = layout
            val name = TextView(requireContext())
            val count = TextView(requireContext())
            name.text = data.name
            count.text = data.count.toString()
            row.addView(name)
            row.addView(count)
            data_table.addView(row)
        }
    }
}
