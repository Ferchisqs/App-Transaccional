package com.example.movilaplication.views.login.data

import com.example.movilaplication.core.network.ApiService
import com.example.movilaplication.core.network.LoginRequest
import com.example.movilaplication.core.network.LoginResponse
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {

    suspend fun login(loginData: LoginRequest): Boolean {
        val response: Response<LoginResponse> = apiService.loginUser(loginData)
        return if (response.isSuccessful) {
            val token = response.body()?.token
            token != null
        } else {
            false
        }
    }
}
