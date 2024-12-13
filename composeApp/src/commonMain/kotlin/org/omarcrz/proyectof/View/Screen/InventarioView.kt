package org.omarcrz.proyectof.View.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.omarcrz.proyectof.Model.Producto
import org.omarcrz.proyectof.Model.Users
import org.omarcrz.proyectof.View.Tab.Inventario.InventarioItem
import org.omarcrz.proyectof.View.Tab.Usuarios.UserItem


suspend fun getInventarios(): List<Producto> {
    val firebaseFirestore = Firebase.firestore
    return try {
        val productResponse = firebaseFirestore.collection("Productos").get()
        productResponse.documents.map{
            it.data<Producto>() // Mapear directamente a Producto usando serialization
        }
    } catch (e: Exception) {
        throw e
    }
}

@Composable
fun InventarioView(
    currentScreen: String,
    onScreenChange: (String) -> Unit,
    onCarritoClick: () -> Unit,  // Asegúrate de recibir la función para cambiar la pantalla
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp)
            .padding(bottom = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite)
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
                    onClick = { onScreenChange("Inventario") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (currentScreen != "Inventario") RedCatalunya else Color.Transparent
                    ),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Text(
                        text = "Inventario",
                        color = if (currentScreen != "Inventario") BackgroundWhite else RedCatalunya
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
                "Inventario" -> InventarioContent()
                "Nuevo" -> NuevoContent(nombre, onNombreChange, cantidad, onCantidadChange, costo, onCostoChange, precio, onPrecioChange, onAgregarClick)
            }
        }
    }
}

@Composable
fun InventarioContent() {
    var list by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Estados para el modal
    var isModalVisible by remember { mutableStateOf(false) }
    var selectedProducto by remember { mutableStateOf<Producto?>(null) }

    // Lógica para cargar productos en un LaunchedEffect
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            errorMessage = null
            list = getInventarios()
        } catch (e: Exception) {
            errorMessage = "Error al cargar productos: ${e.message}"
        } finally {
            isLoading = false
        }
    }

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
                    text = "INVENTARIO",
                    color = RedCatalunya,
                    style = MaterialTheme.typography.h6
                )
            }
        }

        when {
            isLoading -> {
                item {
                    Text(
                        text = "Cargando productos...",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black
                    )
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
                        color = Color.Gray,
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            else -> {
                items(list) { producto ->
                    InventarioItem(
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

    if (isModalVisible && selectedProducto != null) {
        EditProductoModal(
            producto = selectedProducto!!,
            onDismiss = { isModalVisible = false },
            onSave = { updatedProducto ->
                list = list.map {
                    if (it.Descripcion == updatedProducto.Descripcion) updatedProducto else it
                }
                isModalVisible = false
            }
        )
    }
}

@Composable
fun EditProductoModal(
    producto: Producto,
    onDismiss: () -> Unit,
    onSave: (Producto) -> Unit
) {
    var descripcion by remember { mutableStateOf(producto.Descripcion) }
    var inventario by remember { mutableStateOf(producto.Inventario) }
    var precioCompra by remember { mutableStateOf(producto.PrecioCompra) }
    var precioVenta by remember { mutableStateOf(producto.PrecioVenta) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Editar Producto",
                color = RedCatalunya,
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción", color = RedCatalunya) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = RedCatalunya,
                        unfocusedBorderColor = YellowGold,
                        textColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de cantidad con teclado numérico
                OutlinedTextField(
                    value = inventario,
                    onValueChange = { inventario = it },
                    label = { Text("Cantidad", color = RedCatalunya) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = RedCatalunya,
                        unfocusedBorderColor = YellowGold,
                        textColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de costo de compra con teclado numérico
                OutlinedTextField(
                    value = precioCompra,
                    onValueChange = { precioCompra = it },
                    label = { Text("Costo de Compra", color = RedCatalunya) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = RedCatalunya,
                        unfocusedBorderColor = YellowGold,
                        textColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de precio de venta con teclado numérico
                OutlinedTextField(
                    value = precioVenta,
                    onValueChange = { precioVenta = it },
                    label = { Text("Precio de Venta", color = RedCatalunya) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = RedCatalunya,
                        unfocusedBorderColor = YellowGold,
                        textColor = Color.Black
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedProducto = producto.copy(
                        Descripcion = descripcion,
                        Inventario = inventario,
                        PrecioCompra = precioCompra,
                        PrecioVenta = precioVenta
                    )

                    // Llamada a Firestore para actualizar el producto
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val firestore = Firebase.firestore
                            println("Código del producto a actualizar: ${updatedProducto.codigo}") // Verificar el ID del documento
                            firestore.collection("Productos")
                                .document(updatedProducto.codigo)
                                .update(
                                    mapOf(
                                        "Descripcion" to updatedProducto.Descripcion,
                                        "Inventario" to updatedProducto.Inventario,
                                        "PrecioCompra" to updatedProducto.PrecioCompra,
                                        "PrecioVenta" to updatedProducto.PrecioVenta
                                    )
                                )
                            println("Producto actualizado correctamente en Firestore")

                            // Si es exitoso, actualiza la lista y cierra el modal
                            withContext(Dispatchers.Main) {
                                onSave(updatedProducto) // Actualiza el estado en la lista
                                onDismiss() // Cierra el modal
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                println("Error al actualizar producto: ${e.message}")
                                onDismiss() // Cierra el modal
                            }
                        }

                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = RedCatalunya,
                    contentColor = BackgroundWhite
                )
            ) {
                Text("Guardar")
            }

        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = YellowGold,
                    contentColor = BackgroundWhite
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun NuevoContent(
    nombre: String,
    onNombreChange: (String) -> Unit,
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    costo: String,
    onCostoChange: (String) -> Unit,
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
            value = costo,
            onValueChange = onCostoChange,
            label = { Text("Costo", color = RedCatalunya) },
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
            label = { Text("Precio venta", color = RedCatalunya) },
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
            enabled = nombre.isNotBlank() && cantidad.isNotBlank() && precio.isNotBlank() && costo.isNotBlank(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Agregar producto")
        }
    }
}

suspend fun addProduct(product: Producto): Boolean {
    val firebaseFirestore = Firebase.firestore
    return try {
        firebaseFirestore.collection("Productos").add(product)
        true
    } catch (e: Exception) {
        println("Error al agregar el producto: ${e.message}")
        false
    }
}

fun addProductAsync(
    Descripcion: String,
    Inventario: String,
    PrecioVenta: String,
    PrecioCompra: String,
    onComplete: (Boolean) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            println("Intentando agregar producto...")
            val success = addProduct(Producto(Descripcion, Inventario, PrecioVenta, PrecioCompra))
            withContext(Dispatchers.Main) {
                onComplete(success)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onComplete(false)
            }
        }
    }
}

