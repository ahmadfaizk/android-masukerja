package com.rpl.masukerja.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import kotlinx.android.synthetic.main.fragment_register.*
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
class RegisterFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_register.setOnClickListener(this)
        tv_login.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> {
                checkInput()
            }
            R.id.tv_login -> {
                this.view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun checkInput() {
        var ready = true
        val name = et_name.text.toString().trim()
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()

        if (name.isEmpty()) {
            tl_name.error = "Nama harus diisi"
            ready = false
        }

        if (email.isEmpty()) {
            tl_email.error = "Email harus diisi"
            ready = false
        }

        if (password.isEmpty()) {
            tl_password.error = "Password harus diisi"
            ready = false
        }
        if (ready) {
            register(name, email, password)
            showLoading(true)
        }
    }

    private fun register(name: String, email: String, password: String) {
        val request = ApiClient.retrofit.create(Request::class.java).register(name, email, password)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val result = JSONObject(response.body()?.string())
                        val token = result.getString("token")
                        TokenPreference(requireContext()).setToken(token)
                        this@RegisterFragment.view?.findNavController()?.navigate(R.id.action_registerFragment_to_homeActivity)
                        this@RegisterFragment.activity?.finish()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
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
