package com.example.authforaeon.domain.usecase

import android.util.Log
import com.example.authforaeon.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<Boolean> = flow {
        repository.getToken().collect {
            if (it == "") {
                emit(true)
            }
            else
                emit(false)
        }
    }
}