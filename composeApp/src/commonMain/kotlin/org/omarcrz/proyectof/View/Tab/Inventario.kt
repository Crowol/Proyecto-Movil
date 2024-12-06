package org.omarcrz.proyectof.View.Tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.omarcrz.proyectof.View.Screen.InventarioView

object Inventario : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.Info)
            return remember {
                TabOptions(
                    index = 3u,
                    title = "Inventario",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        InventarioView(title = "Inventario")
    }
}
