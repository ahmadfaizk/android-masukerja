package com.rpl.masukerja.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.rpl.masukerja.R
import com.rpl.masukerja.api.TokenPreference
import kotlinx.android.synthetic.main.dialog_about.view.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings,
                SettingsFragment()
            )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private lateinit var dialog: AlertDialog

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            createAbout()
        }

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            when(preference?.key) {
                getString(R.string.help) -> {
                    showToast("help")
                }
                getString(R.string.about) -> {
                    showToast("About")
                    dialog.show()
                }
                getString(R.string.logout) -> {
                    showToast("You Are Logout")
                    logout()
                }
            }
            return super.onPreferenceTreeClick(preference)
        }

        private fun showToast(message: String) {
            Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        private fun createAbout() {
            val builder = AlertDialog.Builder(this.requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_about, null)
            builder.setView(view)
            dialog = builder.create()
            view.btn_close.setOnClickListener{
                dialog.dismiss()
            }
        }

        private fun logout() {
            TokenPreference(this.requireContext()).removeToken()
            fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            startActivity(Intent(this.requireContext(), MainActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}