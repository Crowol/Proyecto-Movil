package org.omarcrz.proyectof.Model

import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val Descripcion: String = "",
    val Inventario: String = "",
    val PrecioCompra: String = "",
    val PrecioVenta: String = "",
    val codigo: String = "",
)