package com.ssafy.data.repository

import android.util.Log
import com.ssafy.data.local.PreferenceDataSource
import com.ssafy.data.local.PreferenceDataSource.Companion.ACCESS_TOKEN
import com.ssafy.data.local.PreferenceDataSource.Companion.MEMBER_TYPE
import com.ssafy.data.local.PreferenceDataSource.Companion.REFRESH_TOKEN
import com.ssafy.data.mapper.toDomain
import com.ssafy.data.remote.VistiApi
import com.ssafy.domain.model.user.UserBody
import com.ssafy.domain.model.user.UserToken
import com.ssafy.domain.repository.MemberRepository
import javax.inject.Inject
import com.navercorp.nid.NaverIdLoginSDK
import com.kakao.sdk.user.UserApiClient

private const val TAG = "MemberRepositoryImpl_kakao"

class MemberRepositoryImpl @Inject constructor(
    private val api: VistiApi,
    private val preferenceDataSource: PreferenceDataSource
) : MemberRepository {
    override suspend fun signIn(userBody: UserBody): UserToken {
        val response = api.signIn(userBody.toDomain()).detail.toDomain()
        preferenceDataSource.putString(MEMBER_TYPE, "visti")
        preferenceDataSource.putString(ACCESS_TOKEN, response.accessToken)
        preferenceDataSource.putString(REFRESH_TOKEN, response.refreshToken)
        return response
    }

    override suspend fun socialSignUp(provider: String, accessToken: String): UserToken {
        val response = api.socialSignIn(provider, accessToken).detail.toDomain()
        preferenceDataSource.putString(MEMBER_TYPE, provider)
        preferenceDataSource.putString(ACCESS_TOKEN, response.accessToken)
        preferenceDataSource.putString(REFRESH_TOKEN, response.refreshToken)
        return response
    }

    override suspend fun deleteMember(): String {
        val response = api.signOut().detail ?: "detail"
        Log.d(TAG, "deleteMember: $response")

        preferenceDataSource.remove(ACCESS_TOKEN)
        preferenceDataSource.remove(REFRESH_TOKEN)

        val memberType = preferenceDataSource.getString(MEMBER_TYPE, "visti")

        Log.d(TAG, "deleteMember: $memberType")

        when(memberType) {
            "kakao" -> {
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e("Unlink Error", "회원탈퇴 실패", error)
                    } else {
                        Log.i("Unlink Success", "회원탈퇴 성공. 다시 회원가입 해야 합니다.")
                    }
                }
            }
            "naver" -> {
                NaverIdLoginSDK.logout()
            }
        }

        return response
    }
}