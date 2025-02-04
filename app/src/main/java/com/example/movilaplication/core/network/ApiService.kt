package com.example.movilaplication.core.network

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response

data class Disco(val nombre: String, val artista: String, val anio_lanzamiento: Int)

data class RegisterRequest(val email: String, val password: String, val confirmPassword: String)
data class RegisterResponse(val id: Int, val email: String)

interface ApiService {
    @GET("discos")
    suspend fun obtenerDiscos(): List<Disco>

    @POST("discos")
    suspend fun agregarDisco(@Body disco: Disco): Disco

    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}
