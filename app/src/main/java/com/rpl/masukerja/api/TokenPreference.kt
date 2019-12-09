package com.rpl.masukerja.api

import android.content.Context

internal class TokenPreference(context: Context) {
    companion object {
        private const val PREFS = "mk"
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        val token = preferences.getString(TOKEN, "")
        return token
    }

    fun removeToken() {
        setToken("")
    }
}