package com.example.movilaplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.movilaplication.views.home.presentation.HomeViewModel
import com.example.movilaplication.views.home.domain.HomeUseCase
import com.example.movilaplication.views.home.data.HomeRepository
import com.example.movilaplication.ui.theme.MovilAplicationTheme
import com.example.movilaplication.core.navegation.AppNavigation
import com.example.movilaplication.core.network.RetrofitInstance
import com.example.movilaplication.views.login.data.LoginRepository
import com.example.movilaplication.views.login.presentation.LoginViewModel
import com.example.movilaplication.views.register.presentation.RegisterViewModel
import com.example.movilaplication.views.register.domain.RegisterUseCase
import com.example.movilaplication.views.register.data.RegisterRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val loginRepository = LoginRepository(RetrofitInstance.apiService)
            val loginViewModel = LoginViewModel(loginRepository)

            val homeRepository = HomeRepository()
            val homeUseCase = HomeUseCase(homeRepository)
            val homeViewModel = HomeViewModel(homeUseCase)

            val registerRepository = RegisterRepository()
            val registerUseCase = RegisterUseCase(registerRepository)
            val registerViewModel = RegisterViewModel(registerUseCase)

            MovilAplicationTheme {
                AppNavigation(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    registerViewModel = registerViewModel,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}
