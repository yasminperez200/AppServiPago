package com.example.app_recibos.data

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Base de datos REMOTA de la app: Firebase Firestore (en la nube).
 *
 * Room sigue siendo la base de datos local del teléfono y es la que usa la
 * interfaz. Cada vez que se guarda algo en Room, los ViewModels también lo
 * suben aquí, a las colecciones "servicios", "pagos" y "usuarios" de Firestore.
 *
 * Si Firebase todavía no está configurado (falta app/google-services.json),
 * estas funciones no hacen nada y la app sigue funcionando solo con Room.
 * Mira Logcat con el filtro "NubeRepositorio" para ver qué pasó.
 *
 * Firestore guarda las escrituras en su caché local si no hay internet y las
 * sube solo cuando vuelve la conexión.
 */
object NubeRepositorio {

    private const val TAG = "NubeRepositorio"

    /** Devuelve Firestore, o null si Firebase no está configurado en este build. */
    private fun firestore(context: Context): FirebaseFirestore? = try {
        if (FirebaseApp.getApps(context).isEmpty()) null else FirebaseFirestore.getInstance()
    } catch (e: Exception) {
        Log.e(TAG, "No se pudo obtener Firestore", e)
        null
    }

    /** Sube un servicio a la colección "servicios". */
    fun subirServicio(context: Context, servicio: ServicioEntity) {
        val db = firestore(context) ?: run {
            Log.w(TAG, "Firebase no está configurado (falta app/google-services.json): el servicio solo quedó en Room")
            return
        }
        val datos = hashMapOf(
            "nombre" to servicio.nombre,
            "tipo" to servicio.tipo,
            "monto" to servicio.monto,
            "vencimientoMillis" to servicio.vencimientoMillis,
            "creadoEn" to System.currentTimeMillis()
        )
        db.collection("servicios").add(datos)
            .addOnSuccessListener { Log.d(TAG, "Servicio subido a Firestore: ${it.id}") }
            .addOnFailureListener { Log.e(TAG, "Error subiendo el servicio a Firestore", it) }
    }

    /** Sube un pago a la colección "pagos". */
    fun subirPago(context: Context, pago: PagoEntity) {
        val db = firestore(context) ?: run {
            Log.w(TAG, "Firebase no está configurado (falta app/google-services.json): el pago solo quedó en Room")
            return
        }
        val datos = hashMapOf(
            "servicio" to pago.servicio,
            "monto" to pago.monto,
            "fecha" to pago.fecha,
            "estado" to pago.estado,
            "creadoEn" to System.currentTimeMillis()
        )
        db.collection("pagos").add(datos)
            .addOnSuccessListener { Log.d(TAG, "Pago subido a Firestore: ${it.id}") }
            .addOnFailureListener { Log.e(TAG, "Error subiendo el pago a Firestore", it) }
    }

    /** Sube o actualiza un usuario en la colección "usuarios". */
    fun subirUsuario(context: Context, usuarioId: String, datosUsuario: Map<String, Any>) {
        val db = firestore(context) ?: run {
            Log.w(TAG, "Firebase no está configurado: el usuario solo quedó en Room")
            return
        }
        db.collection("usuarios").document(usuarioId).set(datosUsuario)
            .addOnSuccessListener { Log.d(TAG, "Usuario subido a Firestore correctamente: $usuarioId") }
            .addOnFailureListener { Log.e(TAG, "Error subiendo el usuario a Firestore", it) }
    }
}