package com.example.authforaeon.domain.usecase

import com.example.authforaeon.domain.interfaces.Repository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke() =
        repository.logout()
}