package com.rpl.masukerja.view.ui


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.rpl.masukerja.R
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * A simple [Fragment] subclass.
 */
class TestFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start.setOnClickListener(this)
        btn_history.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_start -> {
                showGuide()
            }
            R.id.btn_history -> {
                val intent = Intent(this.requireContext(), TestHistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showGuide() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Test Guide")
        val dialogView = layoutInflater.inflate(R.layout.dialog_test_guide, null)
        builder.setView(dialogView)
        builder.setPositiveButton("Start", DialogInterface.OnClickListener{dialog, which ->
            showToast("Start")
            startActivity(Intent(this.requireContext(), TestActivity::class.java))
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, which ->
            dialog.cancel()
            showToast("Cancel")
        })
        builder.create().show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
