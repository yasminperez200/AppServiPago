package com.example.app_recibos.`data`

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _pagoDao: Lazy<PagoDao> = lazy {
    PagoDao_Impl(this)
  }

  private val _servicioDao: Lazy<ServicioDao> = lazy {
    ServicioDao_Impl(this)
  }

  private val _usuarioDao: Lazy<UsuarioDao> = lazy {
    UsuarioDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(3, "c25d8b7b5b1e4cd0d8fc3edbcff2be81", "f439be4227ab095a5b60c97fe3c93d72") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `pagos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `servicio` TEXT NOT NULL, `monto` TEXT NOT NULL, `fecha` TEXT NOT NULL, `estado` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `servicios` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `tipo` TEXT NOT NULL, `monto` INTEGER NOT NULL, `vencimientoMillis` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `usuarios` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `email` TEXT NOT NULL, `password` TEXT NOT NULL, `telefono` TEXT NOT NULL, `ciudad` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c25d8b7b5b1e4cd0d8fc3edbcff2be81')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `pagos`")
        connection.execSQL("DROP TABLE IF EXISTS `servicios`")
        connection.execSQL("DROP TABLE IF EXISTS `usuarios`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsPagos: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPagos.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPagos.put("servicio", TableInfo.Column("servicio", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPagos.put("monto", TableInfo.Column("monto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPagos.put("fecha", TableInfo.Column("fecha", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPagos.put("estado", TableInfo.Column("estado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPagos: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPagos: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPagos: TableInfo = TableInfo("pagos", _columnsPagos, _foreignKeysPagos, _indicesPagos)
        val _existingPagos: TableInfo = read(connection, "pagos")
        if (!_infoPagos.equals(_existingPagos)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |pagos(com.example.app_recibos.data.PagoEntity).
              | Expected:
              |""".trimMargin() + _infoPagos + """
              |
              | Found:
              |""".trimMargin() + _existingPagos)
        }
        val _columnsServicios: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsServicios.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicios.put("nombre", TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicios.put("tipo", TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicios.put("monto", TableInfo.Column("monto", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsServicios.put("vencimientoMillis", TableInfo.Column("vencimientoMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysServicios: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesServicios: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoServicios: TableInfo = TableInfo("servicios", _columnsServicios, _foreignKeysServicios, _indicesServicios)
        val _existingServicios: TableInfo = read(connection, "servicios")
        if (!_infoServicios.equals(_existingServicios)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |servicios(com.example.app_recibos.data.ServicioEntity).
              | Expected:
              |""".trimMargin() + _infoServicios + """
              |
              | Found:
              |""".trimMargin() + _existingServicios)
        }
        val _columnsUsuarios: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUsuarios.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsuarios.put("nombre", TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsuarios.put("email", TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsuarios.put("password", TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsuarios.put("telefono", TableInfo.Column("telefono", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsuarios.put("ciudad", TableInfo.Column("ciudad", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUsuarios: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUsuarios: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUsuarios: TableInfo = TableInfo("usuarios", _columnsUsuarios, _foreignKeysUsuarios, _indicesUsuarios)
        val _existingUsuarios: TableInfo = read(connection, "usuarios")
        if (!_infoUsuarios.equals(_existingUsuarios)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |usuarios(com.example.app_recibos.data.UsuarioEntity).
              | Expected:
              |""".trimMargin() + _infoUsuarios + """
              |
              | Found:
              |""".trimMargin() + _existingUsuarios)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "pagos", "servicios", "usuarios")
  }

  public override fun clearAllTables() {
    super.performClear(false, "pagos", "servicios", "usuarios")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PagoDao::class, PagoDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ServicioDao::class, ServicioDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UsuarioDao::class, UsuarioDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun pagoDao(): PagoDao = _pagoDao.value

  public override fun servicioDao(): ServicioDao = _servicioDao.value

  public override fun usuarioDao(): UsuarioDao = _usuarioDao.value
}
