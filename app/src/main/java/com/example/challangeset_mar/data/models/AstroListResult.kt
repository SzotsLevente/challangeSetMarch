package com.example.challangeset_mar.data.models


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AstroListResult(
    @JsonProperty("message")
    val message: String? = null,
    @JsonProperty("number")
    val number: Int? = null,
    @JsonProperty("people")
    val people: List<People?>? = null
)