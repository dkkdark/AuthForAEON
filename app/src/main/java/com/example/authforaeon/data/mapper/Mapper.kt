package com.example.authforaeon.data.mapper

import android.util.Log
import com.example.authforaeon.data.model.LoginRequest
import com.example.authforaeon.domain.model.LoginBody
import com.example.authforaeon.domain.model.PaymentResult
import com.example.authforaeon.utils.AmountDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

fun LoginBody.toLoginRequest() =
    LoginRequest(
        this.login,
        this.password
    )

fun JsonObject.toPaymentResult(): PaymentResult {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(String::class.java, AmountDeserializer())
    val gson = gsonBuilder.create()
    val paymentResult: PaymentResult = gson.fromJson(this, PaymentResult::class.java)
    return paymentResult
}