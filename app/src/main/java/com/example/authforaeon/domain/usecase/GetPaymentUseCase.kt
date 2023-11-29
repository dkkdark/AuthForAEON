package com.example.authforaeon.domain.usecase

import android.util.Log
import com.example.authforaeon.domain.repository.Repository
import javax.inject.Inject

class GetPaymentUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() =
        repository.getPayments()

}