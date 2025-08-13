package com.example.challangeset_mar.data.repository

import com.example.challangeset_mar.domain.repository.BaseRepository
import com.example.challangeset_mar.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

open class BaseRepositoryImp : BaseRepository {

    override fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading())

        val response = try {
            apiCall()
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
            return@flow
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
            return@flow
        }

        if (response.isSuccessful) {
            response.body()?.let { body ->
                emit(Resource.Success(body))
            } ?: emit(Resource.Error("Response body is null"))
        } else {
            emit(Resource.Error("HTTP error: ${response.code()} ${response.message()}"))
        }
    }

}