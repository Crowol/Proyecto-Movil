package org.omarcrz.proyectof.Model

import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val nombre: String = "",
    val contraseña: String = "",
    val correo: String = "",
    val telefono: String = ""
)