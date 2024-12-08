package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
            // Botón "Ventas"
            Button(
                onClick = { onScreenChange("Ventas") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "Ventas") RedCatalunya else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Ventas",
                    color = if (currentScreen != "Ventas") BackgroundWhite else RedCatalunya
                )
            }

            // Botón "Nuevo"
            Button(
                onClick = { onScreenChange("Nuevo") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen != "Nuevo") RedCatalunya else Color.Transparent
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Nuevo",
                    color = if (currentScreen != "Nuevo") BackgroundWhite else RedCatalunya
                )
            }
        }

        // Contenido dinámico según la pantalla seleccionada
        when (currentScreen) {
            "Ventas" -> VentasContent(onCarritoClick, searchQuery, onSearchQueryChange)
            "Nuevo" -> NuevoContent(nombre, onNombreChange, cantidad, onCantidadChange, precio, onPrecioChange, onAgregarClick)
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
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Buscar", color = RedCatalunya) },
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundWhite, shape = RoundedCornerShape(8.dp))
                .border(1.dp, RedCatalunya, RoundedCornerShape(8.dp))
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón del carrito
        Button(
            onClick = onCarritoClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedCatalunya,
                contentColor = BackgroundWhite
            ),
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AGREGAR PRODUCTO",
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre", color = RedCatalunya) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = onCantidadChange,
            label = { Text("Cantidad", color = RedCatalunya) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        OutlinedTextField(
            value = precio,
            onValueChange = onPrecioChange,
            label = { Text("Precio", color = RedCatalunya) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onAgregarClick()
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = RedCatalunya,
                unfocusedBorderColor = YellowGold,
                textColor = Color.Black
            )
        )

        Button(
            onClick = onAgregarClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedCatalunya,
                contentColor = BackgroundWhite
            ),
            enabled = nombre.isNotEmpty() && cantidad.isNotEmpty() && precio.isNotEmpty(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Agregar producto")
        }
    }
}

@Composable
fun Carrito() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 56.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CARRITO",
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(2.dp, RedCatalunya)
                .padding(16.dp)
        ) {
            Text(text = "NOMBRE", color = RedCatalunya, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "CANTIDAD", color = RedCatalunya, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "PRECIO", color = RedCatalunya)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Acción de pagar */ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedCatalunya,
                contentColor = BackgroundWhite
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Pagar")
        }
    }
}
