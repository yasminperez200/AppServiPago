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
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_recibos.ui.componentes.BarraNavegacionInferior
import com.example.app_recibos.ui.componentes.PantallaInferior

/**
 * Pantalla de Ajustes del sistema: notificaciones, seguridad biométrica,
 * recibos por correo y soporte.
 */
@Composable
fun Ajustes(
    modifier: Modifier = Modifier,
    accionInicio: () -> Unit = {},
    accionServicios: () -> Unit = {},
    accionPagos: () -> Unit = {}
) {
    var notificaciones by rememberSaveable { mutableStateOf(true) }
    var biometria by rememberSaveable { mutableStateOf(true) }
    var recibosEmail by rememberSaveable { mutableStateOf(false) }

    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .padding(bottom = 70.dp)
            ) {
                Text(
                    text = "Ajustes del Sistema",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Notificaciones Push", color = Color.White, fontSize = 16.sp)
                        Text(text = "Alertas de vencimiento en pantalla", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(checked = notificaciones, onCheckedChange = { notificaciones = it })
                }

                Divider(color = Color.DarkGray)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Seguridad Biométrica", color = Color.White, fontSize = 16.sp)
                        Text(text = "Usar huella digital para iniciar pagos", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(checked = biometria, onCheckedChange = { biometria = it })
                }

                Divider(color = Color.DarkGray)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Recibos por Correo", color = Color.White, fontSize = 16.sp)
                        Text(text = "Enviar factura digital al pagar", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(checked = recibosEmail, onCheckedChange = { recibosEmail = it })
                }

                Divider(color = Color.DarkGray)

                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Soporte y Ayuda", color = Color(0xFF29D9E8), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "• Centro de Ayuda y Preguntas Frecuentes", color = Color.LightGray, fontSize = 14.sp)
                Text(text = "• Reportar un problema con un pago", color = Color.LightGray, fontSize = 14.sp)

                Spacer(modifier = Modifier.weight(1f))
                Text(text = "ServiPago App - Versión 1.0.0", color = Color.Gray, fontSize = 13.sp)
            }

            BarraNavegacionInferior(
                modifier = Modifier.align(Alignment.BottomCenter),
                pantallaActual = PantallaInferior.AJUSTES,
                accionInicio = accionInicio,
                accionServicios = accionServicios,
                accionPagos = accionPagos,
                accionAjustes = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun AjustesPreview() {
    Ajustes()
}
