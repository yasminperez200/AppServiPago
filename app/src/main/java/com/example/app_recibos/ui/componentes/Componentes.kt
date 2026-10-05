package com.example.app_recibos.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_recibos.R

/**
 * Componentes de UI reutilizados por varias pantallas de la app.
 * Se centralizan aquí para no repetir el mismo código de la barra de
 * navegación inferior en Inicio, MisServicios, Pagos, Ajustes y Perfil.
 */

/** Identifica qué pestaña de la barra inferior está activa. */
enum class PantallaInferior {
    INICIO, SERVICIOS, PAGOS, AJUSTES
}

@Composable
fun BarraNavegacionInferior(
    modifier: Modifier = Modifier,
    pantallaActual: PantallaInferior,
    accionInicio: () -> Unit,
    accionServicios: () -> Unit,
    accionPagos: () -> Unit,
    accionAjustes: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF3A3A3A))
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ItemNavegacion(
            icono = R.drawable.casa_logo,
            etiqueta = "Inicio",
            activo = pantallaActual == PantallaInferior.INICIO,
            accion = accionInicio
        )
        ItemNavegacion(
            icono = R.drawable.servicios_logo,
            etiqueta = "Mis Servicios",
            activo = pantallaActual == PantallaInferior.SERVICIOS,
            accion = accionServicios
        )
        ItemNavegacion(
            icono = R.drawable.billetera_logo,
            etiqueta = "Pagos",
            activo = pantallaActual == PantallaInferior.PAGOS,
            accion = accionPagos
        )
        ItemNavegacion(
            icono = R.drawable.configuracion_logo,
            etiqueta = "Ajustes",
            activo = pantallaActual == PantallaInferior.AJUSTES,
            accion = accionAjustes
        )
    }
}

@Composable
private fun ItemNavegacion(icono: Int, etiqueta: String, activo: Boolean, accion: () -> Unit) {
    val color = if (activo) Color(0xFF29D9E8) else Color.LightGray
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = accion)
            .padding(horizontal = 6.dp)
    ) {
        Image(
            painter = painterResource(id = icono),
            contentDescription = etiqueta,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(etiqueta, color = color, fontSize = 10.sp)
    }
}
