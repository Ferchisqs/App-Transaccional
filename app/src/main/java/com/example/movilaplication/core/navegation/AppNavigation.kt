package com.example.movilaplication.core.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movilaplication.views.login.presentation.LoginScreen
import com.example.movilaplication.views.home.presentation.HomeScreen
import com.example.movilaplication.views.register.presentation.RegisterScreen
import com.example.movilaplication.views.home.presentation.HomeViewModel
import com.example.movilaplication.views.register.presentation.RegisterViewModel

sealed class Screens(val route: String) {
    object Login : Screens("login_screen")
    object Register : Screens("register_screen")
    object Home : Screens("home_screen")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    registerViewModel: RegisterViewModel
) {
    NavHost(navController = navController, startDestination = Screens.Login.route) {
        composable(Screens.Login.route) {
            LoginScreen(onNavigateToRegister = {
                navController.navigate(Screens.Register.route)
            }, onLoginSuccess = {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Login.route) { inclusive = true }
                }
            })
        }

        composable(Screens.Register.route) {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Register.route) { inclusive = true }
                    }
                }
            )
        }


        composable(Screens.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onLogout = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Home.route) { inclusive = true }
                    }
                }
            )
        }

    }}
