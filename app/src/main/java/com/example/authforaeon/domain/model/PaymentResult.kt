package com.example.authforaeon.domain.model

import com.example.authforaeon.data.model.ErrorBody

data class PaymentResult(
    val success: Boolean,
    val response: List<PaymentModel>?,
    val error: ErrorBody?
)