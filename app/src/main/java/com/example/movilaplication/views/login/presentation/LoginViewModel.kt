package com.example.movilaplication.views.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilaplication.views.login.data.LoginRepository
import com.example.movilaplication.core.network.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    var email: String = ""
    var password: String = ""

    fun login() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val loginData = LoginRequest(email, password)
                val loginSuccess = repository.login(loginData)
                if (loginSuccess) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de red. Inténtalo de nuevo.")
            }
        }
    }
}
