package com.example.authforaeon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authforaeon.domain.usecase.IsLoginUseCase
import com.example.authforaeon.domain.usecase.LoginUseCase
import com.example.authforaeon.presentation.login.LoginEvent
import com.example.authforaeon.presentation.login.LoginStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase
): ViewModel() {

    private val _states = MutableSharedFlow<IsLoginStates>()
    val states = _states.asSharedFlow()

    private fun isLogin() = viewModelScope.launch {
        isLoginUseCase().collect {
            if (it) _states.emit(IsLoginStates.LoggedIn)
            else _states.emit(IsLoginStates.NotLoggedIn)
        }
    }

    fun processEvents(event: MainEvent) {
        when (event) {
            is MainEvent.IsLogin -> { isLogin() }
        }
    }
}

sealed interface IsLoginStates {
    object LoggedIn : IsLoginStates
    object NotLoggedIn : IsLoginStates
}

sealed interface MainEvent {
    object IsLogin : MainEvent
}