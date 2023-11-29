package com.example.authforaeon.domain.usecase

import com.example.authforaeon.domain.interfaces.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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