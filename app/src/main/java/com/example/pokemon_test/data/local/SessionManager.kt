package com.example.pokemon_test.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_EMAIL = "key_email"
    }

    fun saveEmail(email: String) {
        prefs.edit {
            putString(KEY_EMAIL, email)
        }
    }

    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun clearSession() {
        prefs.edit {
            remove(KEY_EMAIL)
        }
    }
}