package com.service.codingtest.network

import com.service.codingtest.model.response.JsonData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("users")
    fun getUsers(@Query("p") p: String, @Query("page") page: String): Call<JsonData>
}