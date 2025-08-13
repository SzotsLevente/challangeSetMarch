package com.example.challangeset_mar.data.repository

import com.example.challangeset_mar.data.api.ApiService
import com.example.challangeset_mar.data.models.AstroListResult
import com.example.challangeset_mar.domain.repository.AstroRepository
import com.example.challangeset_mar.data.repository.BaseRepositoryImp
import com.example.challangeset_mar.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AstroRepositoryImp @Inject constructor(
    private val apiService: ApiService
) : BaseRepositoryImp(), AstroRepository {

    override suspend fun getAstrosData(): Flow<Resource<AstroListResult>> {
        return safeApiCall { apiService.getAstros() }
    }
}