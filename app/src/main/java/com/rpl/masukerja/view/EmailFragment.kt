package com.rpl.masukerja.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController

import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.response.MessageResponse
import kotlinx.android.synthetic.main.fragment_email.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class EmailFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_send.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_send -> {
                checkInput()
            }
        }
    }

    private fun checkInput() {
        val email = et_email.text.toString().trim()
        if (email.isEmpty()) {
            tl_email.error = "Email masih kosong"
        } else {
            requestEmail(email)
            showLoading(true)
        }
    }

    private fun requestEmail(email: String) {
        val request = ApiClient.retrofit.create(Request::class.java).forgotPassword(email)
        request.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                showLoading(false)
                val message = response.body()?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                if (response.body()?.code == 200) {
                    val toOtpFragment = EmailFragmentDirections.actionEmailFragmentToOtpFragment()
                    toOtpFragment.email = email
                    view?.findNavController()?.navigate(toOtpFragment)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                showLoading(false)
                t.printStackTrace()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loading.visibility = View.VISIBLE
        } else {
            loading.visibility = View.GONE
        }
    }
}
