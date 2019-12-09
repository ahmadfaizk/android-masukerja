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
import kotlinx.android.synthetic.main.fragment_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PasswordFragment : Fragment(), View.OnClickListener {

    private var email: String? = null
    private var otp: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = PasswordFragmentArgs.fromBundle(arguments as Bundle).email
        otp = PasswordFragmentArgs.fromBundle(arguments as Bundle).otp
        btn_save.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_save -> {
                checkInput()
            }
        }
    }

    private fun checkInput() {
        val password = et_password.text.toString().trim()
        if (password.isEmpty()) {
            tl_password.error = "Password masih kosong"
        } else {
            requestPassword(password)
        }
    }

    private fun requestPassword(password: String) {
        val request = otp?.let {
            ApiClient.retrofit.create(Request::class.java).changePassword(email.toString(), password,
                it
            )
        }
        request?.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                val message = response.body()?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                showLoading(false)
                if (response.body()?.code == 200) {
                    view?.findNavController()?.navigate(R.id.action_passwordFragment_to_loginFragment)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                t.printStackTrace()
                showLoading(false)
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
