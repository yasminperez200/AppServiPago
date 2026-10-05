package com.example.app_recibos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_recibos.data.AppDatabase
import com.example.app_recibos.data.NubeRepositorio
import com.example.app_recibos.data.PagoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel que guarda el estado de la pantalla de Pagos.
 * Lee y escribe el historial de pagos en la base de datos local (Room)
 * y expone la lista como [StateFlow] para que la UI se recomponga sola
 * cuando hay un pago nuevo. Cada pago nuevo también se sube a Firestore
 * (base de datos remota) mediante [NubeRepositorio].
 */
class PagosViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).pagoDao()

    val historialPagos: StateFlow<List<PagoEntity>> =
        dao.obtenerTodos().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /** Registra un nuevo pago (lo llama MisServicios al pulsar "Pagar"). */
    fun registrarPago(servicio: String, monto: String, fecha: String = "Hoy") {
        viewModelScope.launch {
            val pago = PagoEntity(servicio = servicio, monto = monto, fecha = fecha)
            dao.insertar(pago)                                  // local (Room)
            NubeRepositorio.subirPago(getApplication<Application>(), pago)   // remoto (Firestore)
        }
    }
}
