package com.example.movilaplication.views.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Black, Color(0xFF6A1B9A)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Registro", fontSize = 24.sp, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))




            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email", tint = Color.White) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White), // Texto blanco
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Visibility, contentDescription = "Contraseña", tint = Color.White) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color.White
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White), // Texto blanco
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Visibility, contentDescription = "Confirmar Contraseña", tint = Color.White) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color.White
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Comprobar que las contraseñas coinciden antes de hacer el registro
                    if (password == confirmPassword) {
                        viewModel.register(email, password, confirmPassword) { success ->
                            if (success) {
                                onRegisterSuccess()
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión aquí", color = Color.White)
            }
        }
    }
}
