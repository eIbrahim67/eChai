package com.eibrahim.chatbot.auth

import android.content.Context
import android.content.SharedPreferences

class AuthPreferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("auth_echai_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String?) {
        preferences.edit().putString("auth_echai_token", token).apply()
    }

    fun getToken(): String? {
        return preferences.getString("auth_echai_token", null)
    }

    fun clearToken() {
        preferences.edit().remove("auth_echai_token").apply()
    }

//    fun isFirstLaunch(): Boolean {
//        val isFirst = preferences.getBoolean("is_first_launch", true)
//        if (isFirst) {
//            preferences.edit().putBoolean("is_first_launch", false).apply()
//        }
//        return isFirst
//    }
}