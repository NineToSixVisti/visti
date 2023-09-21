package com.ssafy.visti.di

import com.ssafy.data.remote.VistiApi
import com.ssafy.data.remote.XAccessTokenInterceptor
import com.ssafy.data.repository.PreferenceDataSourceImpl
import com.ssafy.domain.model.Constants
import com.ssafy.domain.repository.PreferenceDataSource
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
    fun provideOkHttpClient(xAccessTokenInterceptor: XAccessTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(xAccessTokenInterceptor)
            .build()
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

    @Provides
    @Singleton
    fun providePreferenceDataSource(preferenceDataSourceImpl: PreferenceDataSourceImpl): PreferenceDataSource {
        return preferenceDataSourceImpl
    }
}