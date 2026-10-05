package com.example.app_recibos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Categorías de servicio que el usuario puede elegir al añadir uno nuevo.
 * En la base de datos se guarda el [name] del enum como texto.
 */
enum class TipoServicio(val etiqueta: String) {
    AGUA("Agua"),
    LUZ("Luz"),
    GAS("Gas"),
    INTERNET("Internet"),
    OTRO("Otro")
}

/**
 * Servicio que el usuario registra desde el botón (+) de Inicio.
 * Se guarda en la tabla "servicios" de Room (base de datos local).
 *
 * - [tipo]: nombre de un [TipoServicio] (AGUA, LUZ, GAS, INTERNET, OTRO).
 * - [monto]: valor de la factura en pesos, sin separadores (130000).
 * - [vencimientoMillis]: fecha de vencimiento en milisegundos. Se guarda la
 *   fecha (y no "faltan N días") para que los días restantes se calculen
 *   siempre contra el día actual y no se queden congelados.
 */
@Entity(tableName = "servicios")
data class ServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val tipo: String,
    val monto: Long,
    val vencimientoMillis: Long
) {
    /** Convierte el texto guardado en [tipo] al enum; si no existe, usa OTRO. */
    fun tipoServicio(): TipoServicio =
        TipoServicio.entries.firstOrNull { it.name == tipo } ?: TipoServicio.OTRO
}
