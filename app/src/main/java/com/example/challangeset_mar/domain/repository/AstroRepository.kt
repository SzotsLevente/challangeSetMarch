package com.example.challangeset_mar.domain.repository

import com.example.challangeset_mar.data.models.AstroListResult
import com.example.challangeset_mar.util.Resource
import kotlinx.coroutines.flow.Flow

interface AstroRepository {

    suspend fun getAstrosData(): Flow<Resource<AstroListResult>>

}