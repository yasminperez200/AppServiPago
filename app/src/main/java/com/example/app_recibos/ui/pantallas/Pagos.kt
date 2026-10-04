package com.example.app_recibos.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_recibos.data.PagoEntity
import com.example.app_recibos.ui.componentes.BarraNavegacionInferior
import com.example.app_recibos.ui.componentes.PantallaInferior
import com.example.app_recibos.viewmodel.PagosViewModel

/**
 * Pantalla de Historial de Pagos.
 * Lee la lista de pagos realizados desde la base de datos local (Room)
 * a través de [PagosViewModel]. Cuando "Mis Servicios" registra un pago
 * nuevo, aparece aquí automáticamente (el Flow de Room notifica a la UI).
 */
@Composable
fun Pagos(
    modifier: Modifier = Modifier,
    accionInicio: () -> Unit = {},
    accionServicios: () -> Unit = {},
    accionAjustes: () -> Unit = {},
    pagosViewModel: PagosViewModel = viewModel()
) {
    val historial by pagosViewModel.historialPagos.collectAsState()

    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 70.dp)
            ) {
                Text(
                    text = "Historial de Pagos",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (historial.isEmpty()) {
                    Text(
                        text = "Todavía no has realizado ningún pago.\nVe a \"Mis Servicios\" y paga una factura para verla aquí.",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(historial) { pago ->
                            ItemHistorialPago(pago)
                        }
                    }
                }
            }

            BarraNavegacionInferior(
                modifier = Modifier.align(Alignment.BottomCenter),
                pantallaActual = PantallaInferior.PAGOS,
                accionInicio = accionInicio,
                accionServicios = accionServicios,
                accionPagos = {},
                accionAjustes = accionAjustes
            )
        }
    }
}

@Composable
private fun ItemHistorialPago(pago: PagoEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF161616))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = pago.servicio, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Fecha: ${pago.fecha}", color = Color.LightGray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = pago.monto, color = Color(0xFF3DD62E), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF1E8E4C))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = pago.estado,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PagosPreview() {
    Pagos()
}
