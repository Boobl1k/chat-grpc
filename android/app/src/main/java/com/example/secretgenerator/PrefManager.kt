package com.example.secretgenerator

import android.content.SharedPreferences
import android.content.Context

class PrefManager(context: Context?) {
    private val PRIVATE_MODE = 0

    private val PREF_NAME = "SharedPreferences"
    private val IS_LOGGED_IN = "is_logged_in"

    private val pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private val editor: SharedPreferences.Editor? = pref?.edit()

    fun setAuth(isLoggedIn: Boolean) {
        editor?.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor?.commit()
    }

    fun setUsername(username: String) {
        editor?.putString("username", username)
        editor?.commit()
    }

    fun setPassword(password: String) {
        editor?.putString("password", password)
        editor?.commit()
    }

    fun isLoggedIn(): Boolean? {
        return pref?.getBoolean(IS_LOGGED_IN, false)
    }

    fun getUsername(): String? {
        return pref?.getString("username", null)
    }

    fun getPassword(): String? {
        return pref?.getString("password", null)
    }

    fun removeData() {
        editor?.clear()
        editor?.commit()
    }
}