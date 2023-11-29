package com.example.authforaeon.data.model

import com.example.authforaeon.data.model.ErrorBody
import com.example.authforaeon.data.model.TokenBody

data class LoginModel(
    val success: Boolean,
    val error: ErrorBody?,
    val response: TokenBody?
)