package com.rpl.masukerja.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rpl.masukerja.R
import com.rpl.masukerja.api.TokenPreference
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_logout) {
            logout()
            this.activity?.finish()
        }
    }

    private fun logout() {
        this.context?.let { TokenPreference(it).removeToken() }
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }
}
