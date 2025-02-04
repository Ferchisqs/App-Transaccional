package com.example.movilaplication.views.register.domain

import com.example.movilaplication.views.register.data.RegisterRepository

class RegisterUseCase(private val repository: RegisterRepository) {
    suspend fun execute(email: String, password: String, confirmPassword: String): Boolean {
        return repository.registerUser(email, password, confirmPassword)
    }
}
