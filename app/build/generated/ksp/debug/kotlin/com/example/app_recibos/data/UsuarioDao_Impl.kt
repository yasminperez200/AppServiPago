package com.example.app_recibos.`data`

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
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
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UsuarioDao_Impl(
  __db: RoomDatabase,
) : UsuarioDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUsuarioEntity: EntityInsertAdapter<UsuarioEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfUsuarioEntity = object : EntityInsertAdapter<UsuarioEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `usuarios` (`id`,`nombre`,`email`,`password`,`telefono`,`ciudad`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UsuarioEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nombre)
        statement.bindText(3, entity.email)
        statement.bindText(4, entity.password)
        statement.bindText(5, entity.telefono)
        statement.bindText(6, entity.ciudad)
      }
    }
  }

  public override suspend fun registrarUsuario(usuario: UsuarioEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfUsuarioEntity.insert(_connection, usuario)
  }

  public override suspend fun login(identifier: String, password: String): UsuarioEntity? {
    val _sql: String = "SELECT * FROM usuarios WHERE (email = ? OR nombre = ?) AND password = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, identifier)
        _argIndex = 2
        _stmt.bindText(_argIndex, identifier)
        _argIndex = 3
        _stmt.bindText(_argIndex, password)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombre: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfPassword: Int = getColumnIndexOrThrow(_stmt, "password")
        val _columnIndexOfTelefono: Int = getColumnIndexOrThrow(_stmt, "telefono")
        val _columnIndexOfCiudad: Int = getColumnIndexOrThrow(_stmt, "ciudad")
        val _result: UsuarioEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombre: String
          _tmpNombre = _stmt.getText(_columnIndexOfNombre)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpPassword: String
          _tmpPassword = _stmt.getText(_columnIndexOfPassword)
          val _tmpTelefono: String
          _tmpTelefono = _stmt.getText(_columnIndexOfTelefono)
          val _tmpCiudad: String
          _tmpCiudad = _stmt.getText(_columnIndexOfCiudad)
          _result = UsuarioEntity(_tmpId,_tmpNombre,_tmpEmail,_tmpPassword,_tmpTelefono,_tmpCiudad)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun obtenerPorEmail(email: String): UsuarioEntity? {
    val _sql: String = "SELECT * FROM usuarios WHERE email = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNombre: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfPassword: Int = getColumnIndexOrThrow(_stmt, "password")
        val _columnIndexOfTelefono: Int = getColumnIndexOrThrow(_stmt, "telefono")
        val _columnIndexOfCiudad: Int = getColumnIndexOrThrow(_stmt, "ciudad")
        val _result: UsuarioEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNombre: String
          _tmpNombre = _stmt.getText(_columnIndexOfNombre)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpPassword: String
          _tmpPassword = _stmt.getText(_columnIndexOfPassword)
          val _tmpTelefono: String
          _tmpTelefono = _stmt.getText(_columnIndexOfTelefono)
          val _tmpCiudad: String
          _tmpCiudad = _stmt.getText(_columnIndexOfCiudad)
          _result = UsuarioEntity(_tmpId,_tmpNombre,_tmpEmail,_tmpPassword,_tmpTelefono,_tmpCiudad)
        } else {
          _result = null
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
