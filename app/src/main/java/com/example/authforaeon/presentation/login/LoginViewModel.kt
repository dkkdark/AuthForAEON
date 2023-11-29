package com.example.authforaeon.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authforaeon.domain.model.ResultModel
import com.example.authforaeon.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
): ViewModel() {

    private val _states = MutableSharedFlow<LoginStates>()
    val states = _states.asSharedFlow()

    private fun login(name: String, password: String) = viewModelScope.launch {
        loginUseCase(name, password).collect {
            when(it) {
                is ResultModel.Success -> { _states.emit(LoginStates.Success(it.data!!.result)) }
                is ResultModel.Error -> { _states.emit(LoginStates.Error(it.message!!)) }
                is ResultModel.Loading -> { _states.emit(LoginStates.Loading) }
            }
        }

    }

    fun processEvents(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> { login(event.name, event.pass) }
        }
    }

}

sealed interface LoginStates {
    object Loading : LoginStates
    data class Success(val success: String) : LoginStates
    data class Error(val error: String) : LoginStates
}

sealed interface LoginEvent {
    data class Login(val name: String, val pass: String) : LoginEvent
}