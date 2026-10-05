package com.example.app_recibos.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_recibos.data.NubeRepositorio
import kotlinx.coroutines.launch

/**
 * ViewModel que guarda el estado de la sesión del usuario (nombre, email,
 * teléfono, método de pago). Sobrevive a los cambios de configuración
 * y ahora también sincroniza los cambios con Firebase Firestore.
 */
class SesionViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

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

        // Sincronizar los datos actualizados a Firebase Firestore
        viewModelScope.launch {
            val datosUsuario = mapOf(
                "nombre" to nombre,
                "correo" to email,
                "telefono" to telefonoUsuario,
                "actualizadoEn" to System.currentTimeMillis()
            )
            // Usamos un ID fijo o el email como identificador del documento del usuario
            NubeRepositorio.subirUsuario(context, "usuario_principal", datosUsuario)
        }
    }
}