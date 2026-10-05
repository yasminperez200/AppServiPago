package com.example.app_recibos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert
    suspend fun registrarUsuario(usuario: UsuarioEntity)

    // Permite buscar por correo electrónico O por nombre de usuario
    @Query("SELECT * FROM usuarios WHERE (email = :identifier OR nombre = :identifier) AND password = :password LIMIT 1")
    suspend fun login(identifier: String, password: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun obtenerPorEmail(email: String): UsuarioEntity?
}