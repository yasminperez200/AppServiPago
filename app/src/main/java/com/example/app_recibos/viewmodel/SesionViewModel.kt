package com.example.app_recibos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel que guarda el estado de la sesión del usuario (nombre, email,
 * teléfono, método de pago). Sobrevive a los cambios de configuración
 * (por ejemplo, rotar la pantalla) mientras se navega entre Inicio,
 * Mis Servicios, Pagos, Ajustes y Perfil.
 */
class SesionViewModel : ViewModel() {
    var nombreUsuario by mutableStateOf("Sofia Daza")
        private set
    var emailUsuario by mutableStateOf("sofia.daza@unicauca.edu.co")
        private set
    var telefonoUsuario by mutableStateOf("+57 310 123 4567")
        private set
    var tarjetaUsuario by mutableStateOf("**** 4321")
        private set

    fun actualizarPerfil(nombre: String, email: String) {
        nombreUsuario = nombre
        emailUsuario = email
    }
}
