package com.ssafy.visti.di

import com.ssafy.data.remote.PicsumApi
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.ImageRepositoryImpl
import com.ssafy.data.repository.MemberInformationRepositoryImpl
import com.ssafy.domain.repository.ImageRepository
import com.ssafy.domain.model.Constants
import com.ssafy.domain.repository.MemberInformationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePicsumApi(): PicsumApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicsumApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(api: PicsumApi): ImageRepository {
        return ImageRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideVistiApi(): VistiApi {
        return Retrofit.Builder()
            .baseUrl(Constants.VISTI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VistiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserInformationRepository(api: VistiApi): MemberInformationRepository {
        return MemberInformationRepositoryImpl(api)
    }
}