package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import proyectofinal.composeapp.generated.resources.Logo
import proyectofinal.composeapp.generated.resources.Res
import proyectofinal.composeapp.generated.resources.compose_multiplatform


// Definimos los colores basados en la paleta del logo
val RedCatalunya = Color(0xFFC21807) // Rojo predominante
val YellowGold = Color(0xFFFFC107) // Amarillo dorado
val BackgroundWhite = Color(0xFFFFFFFF) // Blanco de fondo

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
            .padding(16.dp)
            .background(BackgroundWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painterResource(Res.drawable.Logo),null,modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(50.dp))

        // Título
        Text(
            text = "INICIO DE SESIÓN",
            fontSize = 20.sp,
            color = RedCatalunya, // Cambiado al rojo del logo
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Imagen (placeholder)

        // Campo de correo
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("CORREO", color = RedCatalunya) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("CONTRASEÑA", color = RedCatalunya) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLoginClick()
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
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
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedCatalunya, // Botón rojo
                contentColor = BackgroundWhite // Texto blanco
            ),
            enabled = (email.isNotBlank() && password.isNotBlank()),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "INGRESAR", fontSize = 16.sp)
        }
    }
}
