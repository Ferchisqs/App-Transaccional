package com.example.movilaplication.views.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilaplication.views.register.domain.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    fun register(email: String, password: String, confirmPassword: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (password == confirmPassword) {
                val success = registerUseCase.execute(email, password, confirmPassword)
                onResult(success)
            } else {
                onResult(false)
            }
        }
    }
}
