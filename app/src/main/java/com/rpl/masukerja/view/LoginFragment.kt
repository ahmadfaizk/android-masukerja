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
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.LoginResponse
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_forgot_password.setOnClickListener(this)
        btn_skip.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                checkInput()
            }
            R.id.tv_register -> {
                this.view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
            }
            R.id.tv_forgot_password -> {
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_emailFragment)
            }
            R.id.btn_skip -> {
                TokenPreference(requireContext()).setToken("xxx")
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
                this@LoginFragment.activity?.finish()
            }
        }
    }

    private fun checkInput() {
        var ready = true
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()

        if (email.isEmpty()) {
            tl_email.error = "Email belum diisi"
            ready = false
        }
        if (password.isEmpty()) {
            tl_password.error = "Password belum diisi"
            ready = false
        }

        if (ready) {
            login(email, password)
            showLoading(true)
        }
    }

    private fun login(email: String, password: String) {
        val request = ApiClient.retrofit.create(Request::class.java).login(email, password)
        request.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val message = response.body()?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                if (response.body()?.code == 200) {
                    val token = response.body()?.token.toString()
                    TokenPreference(requireContext()).setToken(token)
                    view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
                    this@LoginFragment.activity?.finish()
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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
