package org.omarcrz.proyectof.View.Tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.omarcrz.proyectof.View.Screen.UsuariosView

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
        UsuariosView(title = "Usuarios")
    }
}
