package com.example.app_recibos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) de Room para la tabla de pagos.
 * Expone los datos como [Flow] para que la UI (vía ViewModel) se
 * actualice automáticamente cuando se inserta un nuevo pago.
 */
@Dao
interface PagoDao {

    @Query("SELECT * FROM pagos ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<PagoEntity>>

    @Insert
    suspend fun insertar(pago: PagoEntity)
}
