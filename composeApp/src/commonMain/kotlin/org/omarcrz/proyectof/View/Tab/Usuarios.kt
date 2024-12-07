package org.omarcrz.proyectof.View.Tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.omarcrz.proyectof.View.Screen.ReportesView
import org.omarcrz.proyectof.View.Screen.UsuariosView

object Usuarios : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.CheckCircle)
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
            var correo by remember { mutableStateOf("") }
            var contraseña by remember { mutableStateOf("") }

            UsuariosView(
                currentScreen = currentScreen,
                onScreenChange = { currentScreen = it },
                nombre = nombre,
                onNombreChange = { nombre = it },
                correo = correo,
                onCorreoChange = { correo = it },
                contraseña = contraseña,
                onContraseñaChange = {contraseña = it},
                onUsuarioClick = { /* Insert en la BD */ }
            )
        }

    }

