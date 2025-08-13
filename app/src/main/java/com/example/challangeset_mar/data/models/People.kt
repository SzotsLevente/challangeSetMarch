package com.example.challangeset_mar.data.models


import com.fasterxml.jackson.annotation.JsonProperty

data class People(
    @JsonProperty("craft")
    val craft: String? = null,
    @JsonProperty("name")
    val name: String? = null
)