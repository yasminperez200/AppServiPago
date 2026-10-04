package com.example.app_recibos.`data`

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
public class PagoDao_Impl(
  __db: RoomDatabase,
) : PagoDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPagoEntity: EntityInsertAdapter<PagoEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfPagoEntity = object : EntityInsertAdapter<PagoEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `pagos` (`id`,`servicio`,`monto`,`fecha`,`estado`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PagoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.servicio)
        statement.bindText(3, entity.monto)
        statement.bindText(4, entity.fecha)
        statement.bindText(5, entity.estado)
      }
    }
  }

  public override suspend fun insertar(pago: PagoEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPagoEntity.insert(_connection, pago)
  }

  public override fun obtenerTodos(): Flow<List<PagoEntity>> {
    val _sql: String = "SELECT * FROM pagos ORDER BY id DESC"
    return createFlow(__db, false, arrayOf("pagos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfServicio: Int = getColumnIndexOrThrow(_stmt, "servicio")
        val _columnIndexOfMonto: Int = getColumnIndexOrThrow(_stmt, "monto")
        val _columnIndexOfFecha: Int = getColumnIndexOrThrow(_stmt, "fecha")
        val _columnIndexOfEstado: Int = getColumnIndexOrThrow(_stmt, "estado")
        val _result: MutableList<PagoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PagoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpServicio: String
          _tmpServicio = _stmt.getText(_columnIndexOfServicio)
          val _tmpMonto: String
          _tmpMonto = _stmt.getText(_columnIndexOfMonto)
          val _tmpFecha: String
          _tmpFecha = _stmt.getText(_columnIndexOfFecha)
          val _tmpEstado: String
          _tmpEstado = _stmt.getText(_columnIndexOfEstado)
          _item = PagoEntity(_tmpId,_tmpServicio,_tmpMonto,_tmpFecha,_tmpEstado)
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
