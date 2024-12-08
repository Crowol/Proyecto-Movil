package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun UsuariosView(
    currentScreen: String,
    onScreenChange: (String) -> Unit,
    nombre: String,
    onNombreChange: (String) -> Unit,
    correo: String,
    onCorreoChange: (String) -> Unit,
    contraseña: String,
    onContraseñaChange: (String) -> Unit,
    onUsuarioClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp)
            .padding(bottom = 40.dp)
    ) {
        // Botones de navegación en la parte superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón "Usuarios"
            Button(
                onClick = { onScreenChange("Usuarios") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "Usuarios") RedCatalunya else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Usuarios",
                    color = if (currentScreen != "Usuarios") BackgroundWhite else RedCatalunya
                )
            }

            // Botón "Nuevo Usuario"
            Button(
                onClick = { onScreenChange("NuevoUsuario") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "NuevoUsuario") RedCatalunya else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Nuevo",
                    color = if (currentScreen != "NuevoUsuario") BackgroundWhite else RedCatalunya
                )
            }
        }

        // Contenido dinámico según la pantalla seleccionada
        when (currentScreen) {
            "Usuarios" -> UsuariosContent()
            "NuevoUsuario" -> NuevoUsuarioContent(nombre, onNombreChange, correo, onCorreoChange, contraseña, onContraseñaChange, onUsuarioClick)
        }
    }
}

@Composable
fun UsuariosContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LISTA DE USUARIOS",
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun NuevoUsuarioContent(
    nombre: String,
    onNombreChange: (String) -> Unit,
    correo: String,
    onCorreoChange: (String) -> Unit,
    contraseña: String,
    onContraseñaChange: (String) -> Unit,
    onUsuarioClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NUEVO USUARIO",
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre", color = RedCatalunya) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        OutlinedTextField(
            value = correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo", color = RedCatalunya) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        OutlinedTextField(
            value = contraseña,
            onValueChange = onContraseñaChange,
            label = { Text("Contraseña", color = RedCatalunya) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onUsuarioClick()
                }
            ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onUsuarioClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedCatalunya,
                contentColor = BackgroundWhite
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar Usuario")
        }
    }
}




