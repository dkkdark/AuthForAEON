package com.example.authforaeon.data.repository

import android.util.Log
import com.example.authforaeon.data.mapper.toLoginRequest
import com.example.authforaeon.data.mapper.toPaymentResult
import com.example.authforaeon.data.model.LoginModel
import com.example.authforaeon.data.sources.RetrofitSource
import com.example.authforaeon.domain.model.LoginBody
import com.example.authforaeon.domain.model.LoginResult
import com.example.authforaeon.domain.model.PaymentModel
import com.example.authforaeon.domain.model.PaymentResult
import com.example.authforaeon.domain.model.ResultModel
import com.example.authforaeon.domain.repository.Repository
import com.example.authforaeon.domain.repository.TokenDataStore
import com.example.authforaeon.utils.AmountDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofit: RetrofitSource,
    private val dataStore: TokenDataStore
): Repository {

    override suspend fun login(body: LoginBody): Flow<ResultModel<LoginResult>> = flow {
        emit(ResultModel.Loading())

        val result = retrofit.login(body.toLoginRequest())

        if (result.isSuccessful && result.body() != null) {
            val body = result.body()!!
            if (body.success) {
                body.response?.token?.let {
                    dataStore.saveToken(it)
                    emit(ResultModel.Success(LoginResult("Login is successful")))
                }
            }
            else {
                emit(handleError(result))
            }
        }
        else {
            emit(handleError(result))
        }
    }

    private fun handleError(result: Response<LoginModel>) =
         if (result.body()?.error != null)
            ResultModel.Error<LoginResult>(result.body()!!.error!!.error_msg)
        else
            ResultModel.Error("Error occurred")

    private fun handleError(result: PaymentResult) =
        if (result.error != null)
            ResultModel.Error<List<PaymentModel>>(result.error.error_msg)
        else
            ResultModel.Error("Error occurred")


    override suspend fun getPayments() = flow {
        emit(ResultModel.Loading())
        dataStore.readToken.collect {
            val result = retrofit.getPayments(it)

            if (result.isSuccessful && result.body() != null) {
                val body = result.body()!!.toPaymentResult()
                if (body.success) {
                    body.response?.let {
                        emit(ResultModel.Success(body.response))
                    }
                }
                else {
                    emit(handleError(body))
                }
            }
            else {
                emit(ResultModel.Error<List<PaymentModel>>("Error occurred"))
            }
        }
    }

    override suspend fun getToken(): Flow<String> = flow {
        dataStore.readToken.collect {
            emit(it)
        }
    }

    override suspend fun logout(): Boolean {
        return dataStore.saveToken("")
    }
}