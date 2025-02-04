package com.example.movilaplication.views.register.data

import com.example.movilaplication.core.network.RetrofitInstance
import com.example.movilaplication.core.network.RegisterRequest

class RegisterRepository {
    suspend fun registerUser(email: String, password: String, confirmPassword: String): Boolean {
        val request = RegisterRequest(email, password, confirmPassword)
        val response = RetrofitInstance.apiService.registerUser(request)
        return response.isSuccessful
    }
}
