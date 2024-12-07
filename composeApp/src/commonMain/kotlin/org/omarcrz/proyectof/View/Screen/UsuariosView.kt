package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onContraseñaChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                    backgroundColor = if (currentScreen != "Usuarios") MaterialTheme.colors.primary else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Usuarios",
                    color = if (currentScreen != "Usuarios") Color.White else MaterialTheme.colors.primary
                )
            }

            // Botón "Nuevo Usuario"
            Button(
                onClick = { onScreenChange("NuevoUsuario") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "NuevoUsuario") MaterialTheme.colors.primary else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Nuevo",
                    color = if (currentScreen != "NuevoUsuario") Color.White else MaterialTheme.colors.primary
                )
            }
        }

        // Contenido dinámico según la pantalla seleccionada
        when (currentScreen) {
            "Usuarios" -> UsuariosContent()
            "NuevoUsuario" -> NuevoUsuarioContent(
                nombre = nombre,
                onNombreChange = onNombreChange,
                correo = correo,
                onCorreoChange = onCorreoChange,
                contraseña =  contraseña,
                onContraseñaChange = onContraseñaChange
            )
        }
    }
}

@Composable
fun UsuariosContent() {
    // Aquí puedes agregar el contenido de la pantalla "Usuarios"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LISTA DE USUARIOS",
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
    onContraseñaChange: (String) -> Unit
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
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contraseña,
            onValueChange = onContraseñaChange,
            label = { Text("contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Acción de agregar usuario */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar Usuario")
        }
    }
}



