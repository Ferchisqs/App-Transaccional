package com.example.movilaplication.views.home.data

import com.example.movilaplication.core.network.ApiService
import com.example.movilaplication.core.network.Disco
import com.example.movilaplication.core.network.RetrofitInstance

class HomeRepository {
    private val apiService = RetrofitInstance.apiService

    suspend fun obtenerDiscos(): List<Disco> {
        return apiService.obtenerDiscos()
    }

    suspend fun agregarDisco(disco: Disco): Disco {
        return apiService.agregarDisco(disco)
    }
}
