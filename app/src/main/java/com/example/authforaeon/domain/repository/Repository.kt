package com.example.authforaeon.domain.repository

import com.example.authforaeon.domain.model.LoginBody
import com.example.authforaeon.domain.model.LoginResult
import com.example.authforaeon.domain.model.PaymentModel
import com.example.authforaeon.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun login(body: LoginBody): Flow<ResultModel<LoginResult>>
    suspend fun getToken(): Flow<String>
    suspend fun getPayments() : Flow<ResultModel<List<PaymentModel>>>
    suspend fun logout(): Boolean

}