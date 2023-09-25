package com.ssafy.visti.di

import com.ssafy.data.local.PreferenceDataSource
import com.ssafy.data.remote.VistiApi
import com.ssafy.data.repository.LikedStoryRepositoryImpl
import com.ssafy.data.repository.MemberInformationRepositoryImpl
import com.ssafy.data.repository.TokenRepositoryImpl
import com.ssafy.data.repository.MemberRepositoryImpl
import com.ssafy.domain.repository.LikedStoryRepository
import com.ssafy.domain.repository.MemberInformationRepository
import com.ssafy.domain.repository.TokenRepository
import com.ssafy.domain.repository.MemberRepository
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
    fun provideUserInformationRepository(api: VistiApi): MemberInformationRepository {
        return MemberInformationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLikedStoryRepository(api: VistiApi): LikedStoryRepository {
        return LikedStoryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(
        preferenceDataSource: PreferenceDataSource
    ): TokenRepository {
        return TokenRepositoryImpl(preferenceDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        api: VistiApi,
        preferenceDataSource: PreferenceDataSource
    ): MemberRepository {
        return MemberRepositoryImpl(api, preferenceDataSource)
    }
}