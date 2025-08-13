package com.example.challangeset_mar.domain.repository

import com.example.challangeset_mar.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BaseRepository {

    fun <T> safeApiCall(apiCall: suspend() -> Response<T>) : Flow<Resource<T>>

}