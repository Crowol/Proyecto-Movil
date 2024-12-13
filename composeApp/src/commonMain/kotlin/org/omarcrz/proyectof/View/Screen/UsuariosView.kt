package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.omarcrz.proyectof.Model.Users
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.omarcrz.proyectof.View.Tab.Usuarios.UserItem

suspend fun getUsers(): List<Users> {
    val firebaseFirestore = Firebase.firestore
    try {
        val userResponse =
            firebaseFirestore.collection("Users").get()
        return userResponse.documents.map {
            it.data()
        }
    } catch (e: Exception) {
        throw e
    }
}

@Composable
fun UsuariosView(
    currentScreen: String,
    onScreenChange: (String) -> Unit,
    nombre: String,
    onNombreChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
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
            "NuevoUsuario" -> NuevoUsuarioContent(nombre, onNombreChange, telefono, onTelefonoChange, correo, onCorreoChange, contraseña, onContraseñaChange, onUsuarioClick)
        }
    }
}
@Composable
fun UsuariosContent() {
    // Estados para manejar la lista de usuarios, carga y errores
    var list by remember { mutableStateOf<List<Users>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Lógica para cargar usuarios en un LaunchedEffect
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            errorMessage = null
            list = getUsers() // Reemplaza con tu función suspendida para obtener usuarios
        } catch (e: Exception) {
            errorMessage = "Error al cargar usuarios: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LISTA DE USUARIOS",
                    color = RedCatalunya,
                    style = MaterialTheme.typography.h6
                )
            }
        }

        // Mostrar diferentes estados dependiendo de la carga o errores
        when {
            isLoading -> {
                item {
                    Text("Cargando usuarios...", style = MaterialTheme.typography.body1)
                }
            }
            errorMessage != null -> {
                item {
                    Text(
                        text = errorMessage ?: "No se encontraron usuarios.",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            list.isEmpty() -> {
                item {
                    Text(
                        text = "No hay usuarios disponibles.",
                        color = MaterialTheme.typography.body1.color,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            else -> {
                items(list) { user ->
                    UserItem(user)
                }
            }
        }
    }
}

suspend fun addUser(user: Users): Boolean {
    val firebaseFirestore = Firebase.firestore
    return try {
        firebaseFirestore.collection("Users").add(user)
        true
    } catch (e: Exception) {
        println("Error al agregar el usuario: ${e.message}")
        false
    }
}

@Composable
fun NuevoUsuarioContent(
    nombre: String,
    onNombreChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
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
            value = telefono,
            onValueChange = { nuevoValor ->
                if (nuevoValor.length <= 10 && nuevoValor.all { it.isDigit() }) {
                    onTelefonoChange(nuevoValor)
                }
            },
            label = { Text("Teléfono", color = RedCatalunya) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
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
            enabled = nombre.isNotBlank() && correo.isNotBlank() && contraseña.isNotBlank(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar Usuario")
        }
    }
}

fun addUserAsync(
    nombre: String,
    telefono: String,
    correo: String,
    contraseña: String,
    onComplete: (Boolean) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val success = addUser(Users(correo, nombre, telefono, contraseña))
            withContext(Dispatchers.Main) {
                onComplete(success)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onComplete(false)
            }
        }
    }
}
