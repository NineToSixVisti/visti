package com.ssafy.visti.di

import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor {
                it.proceed(it.request().newBuilder().apply {
                    addHeader(
                        "access_token",
                         "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJhdXRoIjoiUk9MRV9BRE1JTiIsInVzZXJfZW1haWwiOiJzc29sbGlkYTk0QGdtYWlsLmNvbSIsImV4cCI6MTY5NTIxOTIyOX0.l1deLeNRsn71MXhYCmwclbmdEO6CzFLlqLzTdhIck6cqtTPcV145Z243wjI2VUxiWv5Pw85NrUBp-QUMQR9-KA"
                    )
                    addHeader(
                        "refresh_token",
                        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiZXhwIjoxNzEzMzYxNDI5fQ.CKFlc_l12RO3dUCBWFoZgkcAYAqWSc1hOPo7yhCTkmFwn8lZnksnIYlwgSPdPliY6_OR0ekrG25_kqinZpWiUA")
                }.build())
            }.build()
    }

    @Provides
    @Singleton
    fun provideVistiApi(okHttpClient: OkHttpClient): VistiApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.VISTI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VistiApi::class.java)
    }
}