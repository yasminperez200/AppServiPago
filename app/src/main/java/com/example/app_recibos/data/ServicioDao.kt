package com.example.app_recibos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {

    @Query("SELECT * FROM servicios ORDER BY vencimientoMillis ASC")
    fun obtenerTodos(): Flow<List<ServicioEntity>>

    @Insert
    suspend fun insertar(servicio: ServicioEntity)

    // Agrega esta función para permitir el borrado en la base de datos
    @Delete
    suspend fun eliminar(servicio: ServicioEntity)
}