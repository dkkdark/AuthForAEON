package com.example.authforaeon.data.sources

import com.example.authforaeon.data.model.LoginRequest
import com.example.authforaeon.data.model.LoginModel
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitSource {

    // url: https://easypay.world/api-test/

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginModel>

    @Headers("app-key: 12345", "v: 1")
    @GET("payments")
    suspend fun getPayments(@Header("token") token: String): Response<JsonObject>
}