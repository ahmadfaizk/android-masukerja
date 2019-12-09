package com.rpl.masukerja.view


import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.findNavController
import com.mukesh.OnOtpCompletionListener
import com.rpl.masukerja.R
import com.rpl.masukerja.api.ApiClient
import com.rpl.masukerja.api.Request
import com.rpl.masukerja.api.response.MessageResponse
import kotlinx.android.synthetic.main.fragment_otp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class OtpFragment : Fragment(), View.OnClickListener {

    private var timer: CountDownTimer? = null
    private var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = OtpFragmentArgs.fromBundle(arguments as Bundle).email
        btn_back.setOnClickListener(this)
        btn_send_email.setOnClickListener(this)
        otp_view.setOtpCompletionListener(object : OnOtpCompletionListener {
            override fun onOtpCompleted(otp: String?) {
                requestOtp(Integer.parseInt(otp.toString()))
                showLoading(true)
                hideHeyboard()
            }
        })
        startTimer()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back -> {
                view?.findNavController()?.navigate(R.id.action_otpFragment_to_emailFragment)
            }
            R.id.btn_send_email -> {
                startTimer()
                showLoading(true)
                requestEmail(email.toString())
            }
        }
    }

    private fun startTimer() {
        btn_send_email.isEnabled = false
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tv_timer.text = String.format("00:%d", millisUntilFinished/1000)
            }

            override fun onFinish() {
                btn_send_email.isEnabled = true
                tv_timer.text = "00:30"
            }
        }.start()
    }

    private fun cancelTimer() {
        timer?.cancel()
    }

    override fun onPause() {
        super.onPause()
        cancelTimer()
    }

    private fun requestOtp(otp: Int) {
        val request = ApiClient.retrofit.create(Request::class.java).verifyOTP(email.toString(), otp)
        request.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                val message = response.body()?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                showLoading(false)
                if (response.body()?.code == 200) {
                    val toPasswordFragment = OtpFragmentDirections.actionOtpFragmentToPasswordFragment()
                    toPasswordFragment.email = email.toString()
                    toPasswordFragment.otp = otp
                    view?.findNavController()?.navigate(toPasswordFragment)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                t.printStackTrace()
                showLoading(false)
            }
        })
    }

    private fun requestEmail(email: String) {
        val request = ApiClient.retrofit.create(Request::class.java).forgotPassword(email)
        request.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                showLoading(false)
                val message = response.body()?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                if (response.body()?.code == 200) {
                    startTimer()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
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

    private fun hideHeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
