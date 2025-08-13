package com.example.challangeset_mar.data.api

import com.example.challangeset_mar.data.models.AstroListResult
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("astros")
    suspend fun getAstros() : Response<AstroListResult>

}