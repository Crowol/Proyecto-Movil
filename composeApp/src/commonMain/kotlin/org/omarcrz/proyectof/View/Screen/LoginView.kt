package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource


@Composable
fun LoginView(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    errorMessage: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "INICIO DE SESIÓN",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //Aqui va la imagen

        // Campo de correo
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("CORREO") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("CONTRASEÑA") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Mensaje de error
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Botón de ingresar
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "INGRESAR", fontSize = 16.sp)
        }
    }
}
