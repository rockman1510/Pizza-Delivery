package com.huylv.data_remote.network.flavor.service

import com.huylv.data_remote.network.flavor.model.FlavorApiModel
import retrofit2.http.GET

interface FlavorService {

    @GET("/mobile/tests/pizzas.json")
    suspend fun getFlavors(): List<FlavorApiModel>
}