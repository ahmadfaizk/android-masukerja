package com.rpl.masukerja.view.ui

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.TestResponse
import com.rpl.masukerja.api.response.TestResultResponse
import com.rpl.masukerja.db.DatabaseContract
import com.rpl.masukerja.db.TestHelper
import com.rpl.masukerja.model.Answer
import com.rpl.masukerja.model.Question
import com.rpl.masukerja.model.Test
import com.rpl.masukerja.utils.MappingHelper
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestActivity : AppCompatActivity(), View.OnClickListener {

    private var test : ArrayList<Question>? = null
    private var index = 0
    private val filled = false
    private lateinit var testHelper: TestHelper
    private lateinit var dataTest: ArrayList<Test>
    private var introvert = 0
    private var extrovert = 0
    private var sensing = 0
    private var intuiting = 0
    private var thingking = 0
    private var feeling = 0
    private var judging = 0
    private var perceiving = 0

    companion object {
        internal val TAG = TestActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        testHelper = TestHelper.getInstance(this)
        testHelper.open()

        btn_answer_1.setOnClickListener(this)
        btn_answer_2.setOnClickListener(this)
        btn_next.setOnClickListener(this)
        btn_back.setOnClickListener(this)

        loadTest()
        loadData()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_answer_1 -> {
                val answer = test?.get(index)?.answers?.get(0)
                selectAnswer(answer)
                next()
            }
            R.id.btn_answer_2 -> {
                val answer = test?.get(index)?.answers?.get(1)
                selectAnswer(answer)
                next()
            }
            R.id.btn_next -> {
                next()
            }
            R.id.btn_back -> {
                back()
            }
        }
    }

    private fun loadTest() {
        val token = TokenPreference(this).getToken()
        val request = ApiClient.retrofit.create(Request::class.java).getTest("Bearer $token")
        request.enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                if (response.isSuccessful) {
                    test = response.body()?.questions
                    showMessage("Load Test Succes")
                    initView()
                }
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        val question = test?.get(index)?.name
        val answer1 = test?.get(index)?.answers?.get(0)?.name
        val answer2 = test?.get(index)?.answers?.get(1)?.name

        tv_number.text = "${index+1}."
        tv_question.text = question
        btn_answer_1.text = answer1
        btn_answer_2.text = answer2

    }

    private fun next() {
        if (index == test?.size?.minus(1)) {
            showMessage("Ini Soal Terakhir")
            storeTest()
        } else {
            index += 1
            initView()
        }
    }

    private fun back() {
        if (index == 0) {
            showMessage("Ini Soal Pertama")
        } else {
            index -= 1
            initView()
        }
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            val defferedTest = async(Dispatchers.IO) {
                val cursor = testHelper.queryAll()
                MappingHelper.mapCursorToList(cursor)
            }
            dataTest = defferedTest.await()
            showMessage("Load DB Succes, Size: ${dataTest.size}")
            if (dataTest.size > 0) {
                showDialog()
            }
        }
    }

    private fun selectAnswer(answer: Answer?) {
        val code = answer?.code
        val idQuestion = test?.get(index)?.id
        val values = ContentValues()
        values.put(DatabaseContract.TestColumns.ID_QUESTION, idQuestion)
        values.put(DatabaseContract.TestColumns.CODE_ANSWER, code)
        val result = testHelper.insert(values)
        if (result > 0) {
            Log.d(TAG, "Sukses Menyimpan data")
        } else {
            val update = testHelper.update(idQuestion.toString(), values)
            if (update > 0) {
                showMessage("Sukses menupdate data")
            } else {
                Log.d(TAG, "Gagal Menyimpan data, Q: $idQuestion, A: $code")
            }
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Peringatan")
        builder.setMessage("Anda pernah mengerjakan tes sebelumnya.\nApakah ingin memulai baru?")
        builder.setPositiveButton("Ya", DialogInterface.OnClickListener{dialog, which ->
            val result = testHelper.deleteAll()
            if (result > 0) {
                showMessage("Berhasil menghapus data")
            } else {
                showMessage("Gagal menghapus data")
            }
            dialog.dismiss()
        })
        builder.setNegativeButton("Tidak", DialogInterface.OnClickListener{dialog, which ->
            dialog.dismiss()
            index = dataTest.size
            initView()
        })
        builder.create().show()
    }

    private fun storeTest() {
        GlobalScope.launch(Dispatchers.Main) {
            val defferedTest = async(Dispatchers.IO) {
                val cursor = testHelper.queryAll()
                MappingHelper.mapCursorToList(cursor)
            }
            dataTest = defferedTest.await()
            showMessage("Load DB Succes, Size: ${dataTest.size}")
            if (dataTest.size == 70) {
                testHelper.deleteAll()
                calculateTest()
                showMessage("Caculate Test")
            }
        }
    }

    private fun calculateTest() {
        val iterator = dataTest.iterator()
        while (iterator.hasNext()) {
            updateData(iterator.next().code_answer)
        }
        storeData()
    }

    private fun updateData(code: String?) {
        when(code) {
            "I" -> introvert++
            "E" -> extrovert++
            "S" -> sensing++
            "N" -> intuiting++
            "T" -> thingking++
            "F" -> feeling++
            "J" -> judging++
            "P" -> perceiving++
        }
    }

    private fun storeData() {
        showMessage("Store Data")
        val token = TokenPreference(this).getToken()
        val request = ApiClient.retrofit.create(Request::class.java)
            .storeTest("Bearer $token", introvert, extrovert, sensing, intuiting, thingking, feeling, judging, perceiving)
        request.enqueue(object : Callback<TestResultResponse> {
            override fun onResponse(call: Call<TestResultResponse>, response: Response<TestResultResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.data
                    val intent = Intent(this@TestActivity, TestResultActivity::class.java)
                    intent.putExtra(TestResultActivity.EXTRA_RESULT, result)
                    startActivity(intent)
                }
                showMessage("${response.code()} ${response.message()}")
            }

            override fun onFailure(call: Call<TestResultResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
