package com.rpl.masukerja.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.rpl.masukerja.R
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.dialog_question.view.*

class TestActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        internal val TAG = TestActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btn_question.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_question -> {
                showQueston()
            }
            R.id.btn_question_1 -> {
                Log.d(TAG, "Question 1")
            }
        }
    }

    private fun showQueston() {
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_question, null)
        dialog.setView(view)
        dialog.setCancelable(true)
        view.btn_question_1.setOnClickListener(this)
        view.btn_question_2.setOnClickListener(this)
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        dialog.show()
    }
}
