package com.ssafy.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
    @ApplicationContext context: Context,
) {

    companion object {
        private const val PREFERENCE_NAME = "visti"
        const val COMPLETED_OX_QUIZ_TIME = "ox"
        const val COMPLETED_FOUR_QUIZ_TIME = "four"
        const val COMPLETED_ATTENDANCE_TIME = "attendance"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }

    fun putString(key: String, data: String?) {
        editor.putString(key, data)
        editor.apply()
    }

    fun getString(key: String, defValue: String? = null): String? {
        return prefs.getString(key, defValue)
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }
}