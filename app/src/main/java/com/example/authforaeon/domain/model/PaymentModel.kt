package com.example.authforaeon.domain.model

data class PaymentModel(
    val id: Int,
    val title: String,
    val amount: String?,
    val created: Long?
)