package com.ssafy.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
    FlowUseCase는 주로 연속적인 데이터 스트림이 필요한 경우에 사용됩니다.
    예를 들어, 실시간 데이터 업데이트나 연속적인 이벤트 스트림을 처리
 **/
abstract class FlowUseCase<in Params, out T> constructor(private val coroutineDispatcher: CoroutineDispatcher) {
    protected abstract fun execute(params: Params? = null): Flow<T>

    operator fun invoke(params: Params? = null): Flow<T> = execute(params).flowOn(coroutineDispatcher)
}
