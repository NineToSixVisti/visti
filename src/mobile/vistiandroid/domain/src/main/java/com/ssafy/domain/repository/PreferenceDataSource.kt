package com.ssafy.domain.repository

interface PreferenceDataSource {
    fun putString(key: String, data: String?)
    fun getString(key: String, defValue: String? = null): String?
    fun remove(key: String)
}
