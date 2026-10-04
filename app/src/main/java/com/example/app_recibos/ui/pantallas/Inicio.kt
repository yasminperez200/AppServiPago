package com.example.app_recibos.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_recibos.R
import com.example.app_recibos.data.ServicioEntity
import com.example.app_recibos.data.SesionUsuario
import com.example.app_recibos.ui.componentes.BarraNavegacionInferior
import com.example.app_recibos.ui.componentes.DialogoNuevoServicio
import com.example.app_recibos.ui.componentes.PantallaInferior
import com.example.app_recibos.viewmodel.ServiciosViewModel

@Composable
fun Inicio(
    modifier: Modifier = Modifier,
    nombreUsuario: String = SesionUsuario.nombre,
    accionServicioAgregado: (nombre: String) -> Unit = {},
    accionInicio: () -> Unit = {},
    accionServicios: () -> Unit = {},
    accionPagos: () -> Unit = {},
    accionAjustes: () -> Unit = {},
    accionPerfil: () -> Unit = {},
    serviciosViewModel: ServiciosViewModel = viewModel()
) {
    var mostrarDialogo by rememberSaveable { mutableStateOf(false) }

    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 90.dp)
            ) {

                // ---------- Encabezado ----------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hola, $nombreUsuario",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.perfil_logo),
                        contentDescription = "Perfil",
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .clickable(onClick = accionPerfil)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ---------- Tarjeta: Resumen del Mes ----------
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF161616))
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Resumen del Mes",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Pendiente:", color = Color.LightGray, fontSize = 12.sp)
                            Text("\$145.000", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Pagos Realizados :", color = Color.LightGray, fontSize = 12.sp)
                            Text("35", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Gráfico de barras
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.height(112.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("80", color = Color.Gray, fontSize = 9.sp)
                            Text("60", color = Color.Gray, fontSize = 9.sp)
                            Text("40", color = Color.Gray, fontSize = 9.sp)
                            Text("20", color = Color.Gray, fontSize = 9.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(148.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BarraResumen("Invierno", 88.dp, Color(0xFFE53935))
                            BarraResumen("Primavera", 55.dp, Color(0xFFF6C453))
                            BarraResumen("Verano", 112.dp, Color(0xFF3DD62E))
                            BarraResumen("Otoño", 42.dp, Color(0xFF4E8DF5))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "PROXIMOS VENCIMIENTOS",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ---------- Tarjetas de servicios ----------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TarjetaServicio(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        icono = R.drawable.fuego_logo,
                        titulo = "Gas Natural",
                        estado = "Vence mañana",
                        colorFondo = Color(0xFFE4483A),
                        textoBoton = "¡Pagar Ya!",
                        colorBoton = Color(0xFFB71C1C),
                        colorTextoBoton = Color.White
                    )
                    TarjetaServicio(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        icono = R.drawable.luz_logo,
                        titulo = "Electricidad CEO",
                        estado = "Vence en 3 dias",
                        colorFondo = Color(0xFFD6A419),
                        textoBoton = "Próximo",
                        colorBoton = Color(0xFFB98A12),
                        colorTextoBoton = Color.White
                    )
                    TarjetaServicio(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        icono = R.drawable.agua_logo,
                        titulo = "AGUA Acueducto",
                        estado = "Vence en 12 dias",
                        colorFondo = Color(0xFF16A67B),
                        textoBoton = "Al día",
                        colorBoton = Color(0xFF0E7A5B),
                        colorTextoBoton = Color.White
                    )
                }
            }

            // ---------- Botón flotante (+) ----------
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 86.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF29D9E8))
                    .clickable { mostrarDialogo = true },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.agregar_logo),
                    contentDescription = "Agregar",
                    modifier = Modifier.size(26.dp)
                )
            }

            // ---------- Barra de navegación inferior ----------
            BarraNavegacionInferior(
                modifier = Modifier.align(Alignment.BottomCenter),
                pantallaActual = PantallaInferior.INICIO,
                accionInicio = accionInicio,
                accionServicios = accionServicios,
                accionPagos = accionPagos,
                accionAjustes = accionAjustes
            )

            // ---------- Pantalla emergente: nuevo servicio ----------
            if (mostrarDialogo) {
                DialogoNuevoServicio(
                    onCancelar = { mostrarDialogo = false },
                    onGuardar = { nombre, tipo, monto, dias ->
                        val tipoString = tipo.toString()
                        val vencimientoMillis = System.currentTimeMillis() + (dias.toLong() * 86_400_000L)

                        val nuevoServicio = ServicioEntity(
                            nombre = nombre,
                            tipo = tipoString,
                            monto = monto,
                            vencimientoMillis = vencimientoMillis
                        )
                        serviciosViewModel.agregarServicio(nuevoServicio)
                        mostrarDialogo = false
                        accionServicioAgregado(nombre)
                    }
                )
            }
        }
    }
}

@Composable
private fun BarraResumen(etiqueta: String, altura: Dp, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(altura)
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(etiqueta, color = Color.LightGray, fontSize = 8.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun TarjetaServicio(
    modifier: Modifier = Modifier,
    icono: Int,
    titulo: String,
    estado: String,
    colorFondo: Color,
    textoBoton: String,
    colorBoton: Color,
    colorTextoBoton: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(colorFondo)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(colorBoton),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icono),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = titulo, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 16.sp)

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = estado, color = Color.White, fontSize = 11.sp, lineHeight = 13.sp)

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(colorBoton)
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = textoBoton, color = colorTextoBoton, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}