package com.example.authforaeon.presentation.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authforaeon.domain.model.PaymentModel
import com.example.authforaeon.domain.model.ResultModel
import com.example.authforaeon.domain.usecase.GetPaymentUseCase
import com.example.authforaeon.domain.usecase.LogoutUseCase
import com.example.authforaeon.presentation.login.LoginEvent
import com.example.authforaeon.presentation.login.LoginStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getPaymentUseCase: GetPaymentUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _states = MutableSharedFlow<PaymentStates>()
    val states = _states.asSharedFlow()

    private val _payments = MutableStateFlow<List<PaymentModel>>(listOf())
    val payments = _payments.asStateFlow()

    private fun getPayment() = viewModelScope.launch {
        getPaymentUseCase().collect {
            when(it) {
                is ResultModel.Success -> { _payments.value = it.data!! }
                is ResultModel.Error -> { _states.emit(PaymentStates.Error(it.message!!)) }
                is ResultModel.Loading -> { _states.emit(PaymentStates.Loading) }
            }
        }
    }

    private fun logout() = viewModelScope.launch {
        if (logoutUseCase())
            _states.emit(PaymentStates.NavigateToLogin)
        else
            _states.emit(PaymentStates.Error("You cannot logout now"))
    }

    fun processEvents(event: PaymentEvent) {
        when (event) {
            is PaymentEvent.GetPayment -> { getPayment() }
            is PaymentEvent.Logout -> { logout() }
        }
    }
}

sealed interface PaymentStates {
    object Loading : PaymentStates
    data class Error(val error: String) : PaymentStates

    object NavigateToLogin: PaymentStates
}

sealed interface PaymentEvent {
    object GetPayment : PaymentEvent
    object Logout: PaymentEvent
}