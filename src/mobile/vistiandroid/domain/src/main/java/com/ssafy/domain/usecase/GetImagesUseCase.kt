package com.ssafy.domain.usecase

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.ssafy.domain.model.Story
import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(): Flow<Resource<List<Story>>> = flow {
        try {
            emit(Resource.Loading<List<Story>>())
            val images = repository.getImages().map { it }
            emit(Resource.Success<List<Story>>(images))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Story>>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Story>>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}