package org.omarcrz.proyectof.View.Tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.omarcrz.proyectof.Model.Users
import org.omarcrz.proyectof.View.Screen.ReportesView
import org.omarcrz.proyectof.View.Screen.UsuariosView
import org.omarcrz.proyectof.View.Screen.addUserAsync


object Usuarios : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.AccountCircle)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Usuarios",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        var currentScreen by remember { mutableStateOf("Usuarios") }
        var nombre by remember { mutableStateOf("") }
        var telefono by remember { mutableStateOf("") }
        var correo by remember { mutableStateOf("") }
        var contraseña by remember { mutableStateOf("") }

        UsuariosView(
            currentScreen = currentScreen,
            onScreenChange = { currentScreen = it },
            nombre = nombre,
            onNombreChange = { nombre = it },
            telefono = telefono,
            onTelefonoChange ={ telefono = it },
            correo = correo,
            onCorreoChange = { correo = it },
            contraseña = contraseña,
            onContraseñaChange = {contraseña = it},
            onUsuarioClick = {
                addUserAsync(contraseña, correo, nombre, telefono) { success ->
                    if (success) {
                        currentScreen = "Usuarios"
                    }
                }
            }
        )
    }
    //Funcion que registra la fecha de registro del usuario para ingresar el dato a la DB
    private fun fechaReg(): String {

        val currentMoment = Clock.System.now()

        val localDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

        return "${localDateTime.date} ${localDateTime.time}"
    }

    @Composable
    fun UserItem(user: Users) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)  // Para el espacio vertical entre items
                .background(Color(0xFFF2F2F2)) // Fondo personalizado similar al ejemplo
                .padding(16.dp)  // Relleno dentro del item
        ) {
            Text(text = "Nombre: ${user.nombre}", style = MaterialTheme.typography.body1)
            Text(text = "Correo: ${user.correo}", style = MaterialTheme.typography.body2)
            Text(text = "Teléfono: ${user.telefono}", style = MaterialTheme.typography.body2)
        }
    }
}
