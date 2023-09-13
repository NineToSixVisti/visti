package com.ssafy.visti.di

import com.ssafy.data.remote.PicsumApi
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.ImageRepositoryImpl
import com.ssafy.data.repository.MemberInformationRepositoryImpl
import com.ssafy.domain.repository.ImageRepository
import com.ssafy.domain.repository.MemberInformationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(api: PicsumApi): ImageRepository {
        return ImageRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserInformationRepository(api: VistiApi): MemberInformationRepository {
        return MemberInformationRepositoryImpl(api)
    }
}