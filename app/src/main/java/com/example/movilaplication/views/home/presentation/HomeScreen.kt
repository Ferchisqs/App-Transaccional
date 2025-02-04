package com.example.movilaplication.views.home.presentation

import android.view.MotionEvent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movilaplication.core.network.Disco

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val discos by viewModel.discos.collectAsState()
    val focusManager = LocalFocusManager.current

    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var artista by remember { mutableStateOf(TextFieldValue("")) }
    var anioLanzamiento by remember { mutableStateOf(TextFieldValue("")) }
    var errorMensaje by remember { mutableStateOf<String?>(null) }

    var mostrarModal by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.obtenerDiscos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4C1C97), Color(0xFF1A1A1A))
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a la lista de música",
                fontSize = 24.sp,
                color = Color.White,
                style = TextStyle(fontSize = 24.sp, color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Cerrar sesión", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    mostrarModal = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Agregar disco", color = Color.Black)
            }

            if (mostrarModal) {
                Dialog(onDismissRequest = { mostrarModal = false }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(16.dp)
                    ) {
                        Text(text = "Agregar disco", fontSize = 20.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre del disco") },
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = artista,
                            onValueChange = { artista = it },
                            label = { Text("Artista") },
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = anioLanzamiento,
                            onValueChange = { anioLanzamiento = it },
                            label = { Text("Año de lanzamiento") },
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        errorMensaje?.let {
                            Text(it, color = Color.Red)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Button(
                            onClick = {
                                val anio = anioLanzamiento.text.toIntOrNull()
                                if (anio == null) {
                                    errorMensaje = "Año inválido. Debe ser un número."
                                    return@Button
                                }

                                if (nombre.text.isNotEmpty() && artista.text.isNotEmpty()) {
                                    val nuevoDisco = Disco(
                                        nombre = nombre.text,
                                        artista = artista.text,
                                        anio_lanzamiento = anio
                                    )
                                    viewModel.agregarDisco(nuevoDisco)

                                    nombre = TextFieldValue("")
                                    artista = TextFieldValue("")
                                    anioLanzamiento = TextFieldValue("")
                                    errorMensaje = null
                                    mostrarModal = false // Cerrar el modal
                                } else {
                                    errorMensaje = "Todos los campos son obligatorios."
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text("Agregar disco", color = Color.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Lista de discos", fontSize = 20.sp, color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(discos.size) { index ->
                    val disco = discos[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Nombre: ${disco.nombre}", style = TextStyle(fontSize = 16.sp, color = Color.Black))
                            Text("Artista: ${disco.artista}", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
                            Text("Año de lanzamiento: ${disco.anio_lanzamiento}", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
