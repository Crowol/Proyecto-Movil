package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VentasView(
    currentScreen: String,
    onScreenChange: (String) -> Unit,
    onCarritoClick: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    nombre: String,
    onNombreChange: (String) -> Unit,
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    precio: String,
    onPrecioChange: (String) -> Unit,
    onAgregarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botones de navegación en la parte superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón "Ventas"
            Button(
                onClick = { onScreenChange("Ventas") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "Ventas") MaterialTheme.colors.primary else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Ventas",
                    color = if (currentScreen != "Ventas") Color.White else MaterialTheme.colors.primary
                )
            }

            // Botón "Nuevo"
            Button(
                onClick = { onScreenChange("Nuevo") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "Nuevo") MaterialTheme.colors.primary else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Nuevo",
                    color = if (currentScreen != "Nuevo") Color.White else MaterialTheme.colors.primary
                )
            }
        }

        // Contenido dinámico según la pantalla seleccionada
        when (currentScreen) {
            "Ventas" -> VentasContent(onCarritoClick = onCarritoClick, searchQuery = searchQuery, onSearchQueryChange = onSearchQueryChange)
            "Nuevo" -> NuevoContent(nombre = nombre, onNombreChange = onNombreChange, cantidad = cantidad, onCantidadChange = onCantidadChange, precio = precio, onPrecioChange = onPrecioChange, onAgregarClick = onAgregarClick)
            "Carrito" -> Carrito()
        }
    }
}

@Composable
fun VentasContent(onCarritoClick: () -> Unit, searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección de productos
        Text(
            text = "PRODUCTOS",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón del carrito
        Button(
            onClick = onCarritoClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Carrito")
        }
    }
}

@Composable
fun NuevoContent(
    nombre: String,
    onNombreChange: (String) -> Unit,
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    precio: String,
    onPrecioChange: (String) -> Unit,
    onAgregarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AGREGAR PRODUCTO",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = onCantidadChange,
            label = { Text("Cantidad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = precio,
            onValueChange = onPrecioChange,
            label = { Text("Precio") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Button(
            onClick = onAgregarClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "AGREGAR")
        }
    }
}

@Composable
fun Carrito() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Habilitar scroll
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "CARRITO",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Contenedor para los detalles del carrito
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Permite que este bloque ocupe todo el espacio restante
                .border(2.dp, Color.Black)
                .padding(16.dp)
        ) {
            Text(
                text = "NOMBRE",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "CANTIDAD",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "PRECIO",
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Pagar
        Button(
            onClick = { /* Acción de pagar */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "PAGAR")
        }
    }
}