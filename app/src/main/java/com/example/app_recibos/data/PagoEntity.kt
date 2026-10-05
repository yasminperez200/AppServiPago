package com.example.app_recibos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Registro de un pago realizado. Cada vez que el usuario paga un
 * servicio desde "Mis Servicios", se guarda una fila aquí usando Room
 * (base de datos local), tal como lo pide el microproyecto.
 */
@Entity(tableName = "pagos")
data class PagoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val servicio: String,
    val monto: String,
    val fecha: String,
    val estado: String = "Pagado"
)
