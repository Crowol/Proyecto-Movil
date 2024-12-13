package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import proyectofinal.composeapp.generated.resources.Res
import proyectofinal.composeapp.generated.resources.compose_multiplatform
import proyectofinal.composeapp.generated.resources.img

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
    MaterialTheme {
        val scope = rememberCoroutineScope()
        val auth = remember { Firebase.auth }
        var firebaseUser: FirebaseUser? by remember { mutableStateOf(null) }

        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }

        if (firebaseUser == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painterResource(Res.drawable.img),null,modifier = Modifier.size(250.dp))
                //Spacer(modifier = Modifier.height(10.dp))

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

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                auth.signInWithEmailAndPassword(email, password)
                                firebaseUser = auth.currentUser
                                println("Conectado C:")
                                onLoginClick()
                            } catch (e: Exception) {
                                (e.message ?: "Error desconocido").let {
                                    println(it)
                                }
                            }
                        }
                    },
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
        } else {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = firebaseUser?.uid ?: "No user")
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        scope.launch {
                            auth.signOut()
                            firebaseUser = auth.currentUser
                        }
                    }
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
        }
    }
}

