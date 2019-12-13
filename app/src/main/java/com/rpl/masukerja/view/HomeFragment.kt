package com.rpl.masukerja.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.TokenPreference
import com.rpl.masukerja.api.response.UserResponse
import com.rpl.masukerja.model.User
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_search -> {
                val intent = Intent(this.context, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        internal val TAG = HomeFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_search.setOnClickListener(this)
    }

    private fun generate() {
        val token = this.context?.let { TokenPreference(it).getToken() }
        val request = ApiClient.retrofit.create(Request::class.java).getUser("Bearer " + token)
        request.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
//                    tv_name.text = response.body()?.user?.name
//                    tv_email.text = response.body()?.user?.email
                } else {
                    Log.d(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
