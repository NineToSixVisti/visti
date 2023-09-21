package com.ssafy.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.domain.repository.PreferenceDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceDataSource {
    companion object {
        private const val PREFERENCE_NAME = "visti"
        const val COMPLETED_OX_QUIZ_TIME = "ox"
        const val COMPLETED_FOUR_QUIZ_TIME = "four"
        const val COMPLETED_ATTENDANCE_TIME = "attendance"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    override fun putString(key: String, data: String?) {
        editor.putString(key, data)
        editor.apply()
    }

    override fun getString(key: String, defValue: String?): String? {
        return prefs.getString(key, defValue)
    }

    override fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }
}
