package com.example.app_recibos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla de usuarios para el registro y el inicio de sesión local.
 */
@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nombre: String,
    val email: String,
    val password: String,
    val telefono: String = "+57 310 123 4567",
    val ciudad: String = "Popayán, Cauca"
)