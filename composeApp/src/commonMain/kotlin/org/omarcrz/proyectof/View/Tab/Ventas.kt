package org.omarcrz.proyectof.View.Tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import org.omarcrz.proyectof.Model.Producto
import org.omarcrz.proyectof.View.Screen.VentasView
import org.omarcrz.proyectof.View.Screen.addProductAsync


object Ventas : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.ShoppingCart)
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Compras",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        var currentScreen by remember { mutableStateOf("Compras") }
        var searchQuery by remember { mutableStateOf("") }
        var nombre by remember { mutableStateOf("") }
        var cantidad by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }
        var costo by remember { mutableStateOf("") }

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
            costo = costo,
            onCostoChange = { costo = it },
            precio = precio,
            onPrecioChange = { precio = it },
            onAgregarClick = {
                addProductAsync(nombre, cantidad, costo, precio) { success ->
                    if (success) {
                        currentScreen = "Inventario"
                        println("Producto agregado correctamente")
                    }
                }
            }
        )
    }

    @Composable
    fun VentasItem(producto: Producto, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF2F2F2))
                .padding(16.dp)
                .clickable(onClick = onClick)
        ) {
            Text(text = "Nombre: ${producto.Descripcion}", style = MaterialTheme.typography.body1)
            Text(text = "Cantidad: ${producto.Inventario}", style = MaterialTheme.typography.body1)
            Text(text = "Precio: ${producto.PrecioVenta}", style = MaterialTheme.typography.body1)
        }
    }

}
