package com.huylv.data_remote.network.flavor.model

import com.squareup.moshi.Json

data class FlavorApiModel(
    @Json(name = "name") val name: String,
    @Json(name = "price") val price: Float,
)