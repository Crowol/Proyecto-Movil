package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.omarcrz.proyectof.Model.Producto
import org.omarcrz.proyectof.Model.Users
import org.omarcrz.proyectof.View.Tab.Ventas.VentasItem

suspend fun getProductos(): List<Producto> {
    val firebaseFirestore = Firebase.firestore
    return try {
        val productResponse = firebaseFirestore.collection("Productos").get()
        productResponse.documents.mapNotNull {
            it.data<Producto>() // Mapear directamente a Producto usando serialization
        }
    } catch (e: Exception) {
        throw e
    }
}

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
    costo: String,
    onCostoChange: (String) -> Unit,
    onAgregarClick: () -> Unit
) {
    VentasContent();
}

@Composable
fun VentasContent() {
    var list by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Estados para el modal
    var isModalVisible by remember { mutableStateOf(false) }
    var selectedProducto by remember { mutableStateOf<Producto?>(null) }
    var cantidadPedido by remember { mutableStateOf("1") }
    var precioPedido by remember { mutableStateOf("1") }// Estado para la cantidad de pedido

    // Lógica para cargar productos en un LaunchedEffect
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            errorMessage = null
            list = getProductos() // Reemplaza con tu función suspendida para obtener productos
        } catch (e: Exception) {
            errorMessage = "Error al cargar productos: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Modal de confirmación para agregar al carrito
    if (isModalVisible && selectedProducto != null) {
        AlertDialog(
            onDismissRequest = {
                isModalVisible = false
            },
            title = {
                Text(
                    text = "Realizar Pedido",
                    fontSize = 20.sp,
                    color = RedCatalunya // Color del título
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "¿Cuántas piezas de ${selectedProducto?.Descripcion} va a hacer pedido?",
                        color = Color.Black, // Color del texto
                        style = MaterialTheme.typography.body1
                    )
                    // Entrada de datos numérica
                    OutlinedTextField(
                        value = cantidadPedido,
                        onValueChange = { cantidadPedido = it.filter { char -> char.isDigit() } }, // Filtrar solo números
                        label = { Text("Cantidad") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )
                    // Entrada de datos numérica precio
                    OutlinedTextField(
                        value = precioPedido,
                        onValueChange = { precioPedido = it.filter { char -> char.isDigit() } }, // Filtrar solo números
                        label = { Text("Precio") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        hacerPedido(selectedProducto!!, cantidadPedido.toInt())
                        isModalVisible = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = RedCatalunya)
                ) {
                    Text(
                        text = "Aceptar",
                        color = RedCatalunya, // Color del botón
                        fontSize = 16.sp
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isModalVisible = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = YellowGold)
                ) {
                    Text(
                        text = "Atrás",
                        color = YellowGold, // Color del botón
                        fontSize = 16.sp
                    )
                }
            },
            backgroundColor = BackgroundWhite, // Fondo blanco del modal
            contentColor = Color.Black // Color de contenido
        )
    }

    Box(modifier = Modifier.fillMaxSize().padding(bottom = 40.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PRODUCTOS",
                        color = RedCatalunya,
                        style = MaterialTheme.typography.h6
                    )
                }
            }

            // Mostrar diferentes estados dependiendo de la carga o errores
            when {
                isLoading -> {
                    item {
                        Text("Cargando productos...", style = MaterialTheme.typography.body1)
                    }
                }

                errorMessage != null -> {
                    item {
                        Text(
                            text = errorMessage ?: "No se encontraron productos.",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

                list.isEmpty() -> {
                    item {
                        Text(
                            text = "No hay productos disponibles.",
                            color = MaterialTheme.typography.body1.color,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

                else -> {
                    items(list) { producto ->
                        VentasItem(
                            producto = producto,
                            onClick = {
                                selectedProducto = producto
                                isModalVisible = true
                            }
                        )
                    }
                }
            }
        }
    }
}

fun hacerPedido(selectedProducto: Producto, toInt: Int) {
    /*
    Aqui la logica para hacer el pedido xddd
     */
}


