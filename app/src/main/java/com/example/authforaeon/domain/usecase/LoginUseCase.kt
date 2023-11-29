package com.example.authforaeon.domain.usecase

import com.example.authforaeon.domain.model.LoginBody
import com.example.authforaeon.domain.repository.Repository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(name: String, password: String) =
        repository.login(LoginBody(name, password))
}