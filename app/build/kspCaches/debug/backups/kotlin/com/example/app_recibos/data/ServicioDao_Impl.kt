package com.example.app_recibos.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ServicioDao_Impl(
  __db: RoomDatabase,
) : ServicioDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfServicioEntity: EntityInsertAdapter<ServicioEntity>

  private val __deleteAdapterOfServicioEntity: EntityDeleteOrUpdateAdapter<ServicioEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfServicioEntity = object : EntityInsertAdapter<ServicioEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `servicios` (`id`,`nombre`,`tipo`,`monto`,`vencimientoMillis`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ServicioEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nombre)
        statement.bindText(3, entity.tipo)
        statement.bindLong(4, entity.monto)
        statement.bindLong(5, entity.vencimientoMillis)
      }
    }
    this.__deleteAdapterOfServicioEntity = object : EntityDeleteOrUpdateAdapter<ServicioEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `servicios` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ServicioEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertar(servicio: ServicioEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfServicioEntity.insert(_connection, servicio)
  }

  public override suspend fun eliminar(servicio: ServicioEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfServicioEntity.handle(_connection, servicio)
  }

  public override fun obtenerTodos(): Flow<List<ServicioEntity>> {
    val _sql: String = "SELECT * FROM servicios ORDER BY vencimientoMillis ASC"
    return createFlow(__db, false, arrayOf("servicios")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombre: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfTipo: Int = getColumnIndexOrThrow(_stmt, "tipo")
        val _columnIndexOfMonto: Int = getColumnIndexOrThrow(_stmt, "monto")
        val _columnIndexOfVencimientoMillis: Int = getColumnIndexOrThrow(_stmt, "vencimientoMillis")
        val _result: MutableList<ServicioEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ServicioEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombre: String
          _tmpNombre = _stmt.getText(_columnIndexOfNombre)
          val _tmpTipo: String
          _tmpTipo = _stmt.getText(_columnIndexOfTipo)
          val _tmpMonto: Long
          _tmpMonto = _stmt.getLong(_columnIndexOfMonto)
          val _tmpVencimientoMillis: Long
          _tmpVencimientoMillis = _stmt.getLong(_columnIndexOfVencimientoMillis)
          _item = ServicioEntity(_tmpId,_tmpNombre,_tmpTipo,_tmpMonto,_tmpVencimientoMillis)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
