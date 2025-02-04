package com.example.movilaplication.views.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilaplication.views.login.data.LoginRepository
import com.example.movilaplication.views.login.domain.LoginData
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    var email: String = ""
    var password: String = ""
    var isLoading: Boolean = false
    var loginSuccess: Boolean? = null

    fun login() {
        isLoading = true
        viewModelScope.launch {
            loginSuccess = repository.login(LoginData(email, password))
            isLoading = false
        }
    }
}
