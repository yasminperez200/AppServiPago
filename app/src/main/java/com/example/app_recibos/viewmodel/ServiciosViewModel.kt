package com.example.app_recibos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_recibos.data.AppDatabase
import com.example.app_recibos.data.NubeRepositorio
import com.example.app_recibos.data.ServicioEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ServiciosViewModel(application: Application) : AndroidViewModel(application) {

    private val servicioDao = AppDatabase.getInstance(application).servicioDao()
    private val context = application

    // Flujo observable con la lista de servicios guardados en Room
    val servicios: StateFlow<List<ServicioEntity>> = servicioDao.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Inserta un nuevo servicio en la base de datos local y lo sube a Firebase
    fun agregarServicio(servicio: ServicioEntity) {
        viewModelScope.launch {
            servicioDao.insertar(servicio)                                  // local (Room)
            NubeRepositorio.subirServicio(context, servicio)                // remoto (Firestore)
        }
    }

    // Elimina el servicio de la base de datos de forma permanente al pagarlo
    fun eliminarServicio(servicio: ServicioEntity) {
        viewModelScope.launch {
            servicioDao.eliminar(servicio)
        }
    }
}