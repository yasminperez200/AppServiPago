package com.example.app_recibos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [PagoEntity::class, ServicioEntity::class, UsuarioEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pagoDao(): PagoDao
    abstract fun servicioDao(): ServicioDao
    abstract fun usuarioDao(): UsuarioDao //
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `servicios` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`nombre` TEXT NOT NULL, " +
                            "`tipo` TEXT NOT NULL, " +
                            "`monto` INTEGER NOT NULL, " +
                            "`vencimientoMillis` INTEGER NOT NULL)"
                )
            }
        }

        // 💡 Migración para pasar de la versión 2 a la 3 creando la tabla "usuarios"
        private val MIGRACION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `usuarios` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`nombre` TEXT NOT NULL, " +
                            "`email` TEXT NOT NULL, " +
                            "`password` TEXT NOT NULL, " +
                            "`telefono` TEXT NOT NULL, " +
                            "`ciudad` TEXT NOT NULL)"
                )
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "servipago_db"
                )
                    .addMigrations(MIGRACION_1_2, MIGRACION_2_3) // 👈 Agregamos la nueva migración
                    .build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}