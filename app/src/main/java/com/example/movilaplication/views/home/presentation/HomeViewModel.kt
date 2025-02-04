package com.example.movilaplication.views.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilaplication.views.home.domain.HomeUseCase
import com.example.movilaplication.core.network.Disco
import com.example.movilaplication.core.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _discos = MutableStateFlow<List<Disco>>(emptyList())
    val discos: StateFlow<List<Disco>> = _discos.asStateFlow()

    fun obtenerDiscos() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.obtenerDiscos()
                Log.d("RetrofitTest", "Respuesta: $response")
                _discos.value = response // Asigna la respuesta a la lista de discos
            } catch (e: Exception) {
                Log.e("RetrofitTest", "Error en la solicitud", e)
            }
        }
    }


    fun agregarDisco(disco: Disco) {
        viewModelScope.launch {
            try {
                homeUseCase.agregarDisco(disco)
                obtenerDiscos()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeViewModel", "Error al agregar disco", e)
            }
        }
    }


    fun logout() {
        // Aquí puedes manejar el cierre de sesión si es necesario
    }
}
