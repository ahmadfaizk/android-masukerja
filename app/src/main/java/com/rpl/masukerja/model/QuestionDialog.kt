package com.rpl.masukerja.model

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import com.rpl.masukerja.R
import kotlinx.android.synthetic.main.dialog_question.*
import java.lang.IllegalStateException

class QuestionDialog : DialogFragment(), View.OnClickListener {

    companion object{
        internal val TAG = QuestionDialog::class.java.simpleName
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_question, null))
                .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, which ->
                    Log.d(TAG, "Yes")
                })
                .setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
                    Log.d(TAG, "No")
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_question_1.setOnClickListener(this)
        btn_question_2.setOnClickListener(this)
        btn_question_3.setOnClickListener(this)
        btn_question_4.setOnClickListener(this)
        btn_question_5.setOnClickListener(this)
        btn_question_6.setOnClickListener(this)
        btn_question_7.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_question_1 -> {
                Log.d(TAG, "Question 1")
            }
            R.id.btn_question_2 -> {
                Log.d(TAG, "Question 2")
            }
        }
    }
}