package com.rpl.masukerja.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.ListTestResponse
import com.rpl.masukerja.model.TestResult
import com.rpl.masukerja.view.adapter.TestResultAdapter
import kotlinx.android.synthetic.main.activity_test_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestHistoryActivity : AppCompatActivity() {

    private lateinit var adapter: TestResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_history)

        adapter = TestResultAdapter()
        rv_test_result.setHasFixedSize(true)
        rv_test_result.layoutManager = LinearLayoutManager(this)
        rv_test_result.adapter = adapter

        adapter.setOnItemClickCallback(object : TestResultAdapter.OnItemClickCallback {
            override fun onItemClicked(test: TestResult) {
                val intent = Intent(this@TestHistoryActivity, TestResultActivity::class.java)
                intent.putExtra(TestResultActivity.EXTRA_RESULT, test)
                startActivity(intent)
            }
        })

        loadData()
    }

    private fun loadData() {
        val token = TokenPreference(this).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getTestHistory("Bearer $token")
        request.enqueue(object : Callback<ListTestResponse> {
            override fun onResponse(call: Call<ListTestResponse>, response: Response<ListTestResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    tv_test_count.text = "${data?.size} Hasil Tes"
                    adapter.setData(data)
                }
            }

            override fun onFailure(call: Call<ListTestResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
