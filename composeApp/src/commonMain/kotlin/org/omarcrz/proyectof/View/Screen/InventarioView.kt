package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InventarioView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp)
            .padding(bottom = 40.dp)
    ) {
        // Contenido del inventario
        InventarioContent()
    }
}

@Composable
fun InventarioContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la lista de inventario
        Text(
            text = "Lista de Inventario",
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Aquí puedes agregar los artículos de inventario
        // Ejemplo de artículo individual
        InventarioItem(nombre = "Producto A", cantidad = 50, precio = 19.99)
        InventarioItem(nombre = "Producto B", cantidad = 30, precio = 9.99)
        InventarioItem(nombre = "Producto C", cantidad = 20, precio = 5.49)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun InventarioItem(nombre: String, cantidad: Int, precio: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF2F2F2))
            .padding(16.dp)
    ) {
        Text(text = "Producto: $nombre", color = Color.Black)
        Text(text = "Cantidad: $cantidad", color = Color.Black)
       /* Text(text = "Precio: \$${"%.2f".format(precio)}", color = Color.Black)*/
    }
}
