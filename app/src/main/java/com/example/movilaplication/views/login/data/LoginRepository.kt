package com.example.movilaplication.views.login.data

import com.example.movilaplication.views.login.domain.LoginData
import kotlinx.coroutines.delay

class LoginRepository {
    suspend fun login(user: LoginData): Boolean {
        delay(2000)
        return user.email == "admin@example.com" && user.password == "password"
    }
}