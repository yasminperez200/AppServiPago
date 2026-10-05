package com.example.app_recibos.data

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Objeto global para mantener en memoria los datos
 * del usuario que acaba de iniciar sesión.
 */
object SesionUsuario {
    var nombre by mutableStateOf("Usuario")
    var email by mutableStateOf("correo@ejemplo.com")
    var telefono by mutableStateOf("+57 310 123 4567")
    var ciudad by mutableStateOf("Popayán, Cauca")

    // Agregamos el estado reactivo para la foto de perfil
    var fotoPerfil by mutableStateOf<Bitmap?>(null)

    fun iniciar(usuario: UsuarioEntity) {
        nombre = usuario.nombre
        email = usuario.email
        telefono = usuario.telefono
        ciudad = usuario.ciudad
        // Opcional: si la entidad del usuario guardara una foto, la asignarías aquí
    }

    fun cerrar() {
        nombre = "Usuario"
        email = "correo@ejemplo.com"
        telefono = "+57 310 123 4567"
        ciudad = "Popayán, Cauca"
        fotoPerfil = null // Limpiamos la foto al cerrar sesión
    }
}