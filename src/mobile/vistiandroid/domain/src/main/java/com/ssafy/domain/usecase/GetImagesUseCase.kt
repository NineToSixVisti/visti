package com.ssafy.domain.usecase

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.ssafy.domain.model.Image
import com.ssafy.domain.model.Resource
import com.ssafy.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(): Flow<Resource<List<Image>>> = flow {
        try {
            emit(Resource.Loading<List<Image>>())
            val images = repository.getImages().map { it }
            emit(Resource.Success<List<Image>>(images))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Image>>(e.localizedMessage ?: "Error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Image>>("Failed to connect to server \uD83D\uDE22"))
        }
    }
}