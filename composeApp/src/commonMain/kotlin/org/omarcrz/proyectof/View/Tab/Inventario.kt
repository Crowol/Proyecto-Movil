package org.omarcrz.proyectof.View.Tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import org.omarcrz.proyectof.Model.Producto
import org.omarcrz.proyectof.View.Screen.InventarioView
import org.omarcrz.proyectof.View.Screen.addProductAsync

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

        var currentScreen by remember { mutableStateOf("Inventario") }
        var searchQuery by remember { mutableStateOf("") }
        var nombre by remember { mutableStateOf("") }
        var cantidad by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }
        var costo by remember { mutableStateOf("") }

        InventarioView(
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
    fun InventarioItem(producto: Producto, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF2F2F2))
                .padding(16.dp)
                .clickable(onClick = onClick)
        ) {
            Text(text = "Nombre: ${producto.Descripcion}", style = MaterialTheme.typography.body1)
            Text(text = "Cantidad: ${producto.Inventario}", style = MaterialTheme.typography.body2)
            Text(text = "Costo de compra: ${producto.PrecioCompra}", style = MaterialTheme.typography.body2)
            Text(text = "Precio de venta: ${producto.PrecioVenta}", style = MaterialTheme.typography.body1)
        }
    }

}

