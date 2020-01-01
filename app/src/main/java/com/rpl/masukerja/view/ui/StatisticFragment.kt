package com.rpl.masukerja.view.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rpl.masukerja.R
import kotlinx.android.synthetic.main.fragment_statistic.*

/**
 * A simple [Fragment] subclass.
 */
class StatisticFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_search.setOnClickListener {
            val intent = Intent(this.requireContext(), TestResultActivity::class.java)
            startActivity(intent)
        }
    }
}
