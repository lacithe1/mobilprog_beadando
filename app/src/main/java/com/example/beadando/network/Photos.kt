package com.example.beadando.network

import com.squareup.moshi.Json

data class Photos (

    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
)