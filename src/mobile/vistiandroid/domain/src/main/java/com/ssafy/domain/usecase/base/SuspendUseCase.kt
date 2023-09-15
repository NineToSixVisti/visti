package com.ssafy.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
    SuspendUseCase는 단일 비동기 작업을 처리하는 데 사용됩니다.
    예를 들어, 네트워크 요청이나 데이터베이스 쿼리와 같은 작업을 처리
 **/
abstract class SuspendUseCase<in Params, out T> constructor(private val coroutineDispatcher: CoroutineDispatcher) {

    protected abstract suspend fun execute(params: Params? = null): T

    suspend operator fun invoke(params: Params? = null): T = withContext(coroutineDispatcher) {
        execute(params)
    }
}