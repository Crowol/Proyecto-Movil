package org.omarcrz.proyectof.View.Tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.omarcrz.proyectof.View.Screen.VentasView

object Ventas : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.ShoppingCart)
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Ventas",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        var currentScreen by remember { mutableStateOf("Ventas") }
        var searchQuery by remember { mutableStateOf("") }
        var nombre by remember { mutableStateOf("") }
        var cantidad by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }

        VentasView(
            currentScreen = currentScreen,
            onScreenChange = { currentScreen = it },
            onCarritoClick = { currentScreen = "Carrito" },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            nombre = nombre,
            onNombreChange = { nombre = it },
            cantidad = cantidad,
            onCantidadChange = { cantidad = it },
            precio = precio,
            onPrecioChange = { precio = it },
            onAgregarClick = { /* Insert en la BD */ }
        )
    }
}
