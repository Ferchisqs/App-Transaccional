package com.example.movilaplication.views.home.domain


import com.example.movilaplication.views.home.data.HomeRepository
import com.example.movilaplication.core.network.Disco

class HomeUseCase(private val repository: HomeRepository) {
    suspend fun obtenerDiscos(): List<Disco> {
        return repository.obtenerDiscos()
    }

    suspend fun agregarDisco(disco: Disco): Disco {
        return repository.agregarDisco(disco)
    }
}
