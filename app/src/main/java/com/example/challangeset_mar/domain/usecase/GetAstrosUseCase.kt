package com.example.challangeset_mar.domain.usecase

import com.example.challangeset_mar.data.models.AstroListResult
import com.example.challangeset_mar.domain.repository.AstroRepository
import com.example.challangeset_mar.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAstrosUseCase @Inject constructor(
    private val astroRepository: AstroRepository
) {
    suspend operator fun invoke(): Flow<Resource<AstroListResult>> {
        return astroRepository.getAstrosData()
    }
}