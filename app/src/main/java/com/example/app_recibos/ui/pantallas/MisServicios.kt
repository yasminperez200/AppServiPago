package com.example.app_recibos.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_recibos.R
import com.example.app_recibos.data.ServicioEntity
import com.example.app_recibos.ui.componentes.BarraNavegacionInferior
import com.example.app_recibos.ui.componentes.DialogoNuevoServicio
import com.example.app_recibos.ui.componentes.PantallaInferior
import com.example.app_recibos.viewmodel.PagosViewModel
import com.example.app_recibos.viewmodel.ServiciosViewModel
import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun MisServicios(
    modifier: Modifier = Modifier,
    accionInicio: () -> Unit = {},
    accionServicios: () -> Unit = {},
    accionPagos: () -> Unit = {},
    accionAjustes: () -> Unit = {},
    serviciosViewModel: ServiciosViewModel = viewModel(),
    pagosViewModel: PagosViewModel = viewModel()
) {
    var mostrarDialogo by rememberSaveable { mutableStateOf(false) }
    val listaServicios by serviciosViewModel.servicios.collectAsState(initial = emptyList())

    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
            maximumFractionDigits = 0
        }
    }

    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 90.dp)
            ) {
                // ---------- Barra de Búsqueda / Título superior ----------
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF1E1E1E))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.search_logo),
                            contentDescription = "Buscar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Buscar servicio o convenio...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ---------- Lista de Servicios ----------
                if (listaServicios.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay servicios registrados.\nToca el botón [+] para agregar uno.",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(listaServicios) { servicio ->
                            // Cálculo de días restantes
                            val diffMillis = servicio.vencimientoMillis - System.currentTimeMillis()
                            val diasRestantes = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()

                            val textoVencimiento = when {
                                diasRestantes < 0 -> "Vencido"
                                diasRestantes == 0 -> "Vence hoy"
                                diasRestantes == 1 -> "Vence mañana"
                                else -> "Vence en $diasRestantes días"
                            }

                            // Lógica de colores del botón según los rangos pedidos:
                            // Menos de 8 días -> Rojo
                            // De 9 a 18 días -> Naranja
                            // 19 días en adelante -> Verde
                            val (colorEstado, textoEstadoBoton) = when {
                                diasRestantes < 8 -> Pair(Color(0xFFE53935), "¡Pagar Ya!")
                                diasRestantes in 8..18 -> Pair(Color(0xFFFF9800), "Próximo")
                                else -> Pair(Color(0xFF3DD62E), "Al día")
                            }

                            // Selección de icono y color de fondo del círculo según el tipo o nombre del servicio
                            val (iconoRes, colorFondoIcono) = when {
                                // Claro (Internet / Telefonía)
                                servicio.nombre.contains("Claro", ignoreCase = true) || servicio.tipo.contains("Claro", ignoreCase = true) ->
                                    Pair(R.drawable.wifi_logo, Color(0xFF7C4DFF)) // Morado para Claro

                                // Luz / Electricidad
                                servicio.tipo.contains("Luz", ignoreCase = true) || servicio.nombre.contains("CEO", ignoreCase = true) ->
                                    Pair(R.drawable.luz_logo, Color(0xFFF6C453)) // Amarillo para electricidad

                                // Agua
                                servicio.tipo.contains("Agua", ignoreCase = true) || servicio.nombre.contains("Agua", ignoreCase = true) || servicio.nombre.contains("Gotica", ignoreCase = true) ->
                                    Pair(R.drawable.agua_logo, Color(0xFF00BCD4)) // Celeste para agua

                                // Gas
                                servicio.tipo.contains("Gas", ignoreCase = true) ->
                                    Pair(R.drawable.fuego_logo, Color(0xFFFF5722)) // Naranja/Rojo para gas

                                // Netflix (Entretenimiento)
                                servicio.nombre.contains("Netflix", ignoreCase = true) ->
                                    Pair(R.drawable.netflix_logo, Color(0xFFE50914)) // Rojo para Netflix

                                // Amazon Prime (Entretenimiento)
                                servicio.nombre.contains("Amazon", ignoreCase = true) || servicio.nombre.contains("Prime", ignoreCase = true) ->
                                    Pair(R.drawable.primevideo_logo, Color(0xFF00A8E1)) // Azul para Amazon Prime

                                // Disney+ (Entretenimiento)
                                servicio.nombre.contains("Disney", ignoreCase = true) ->
                                    Pair(R.drawable.disneyplus_logo, Color(0xFF113CCF)) // Azul oscuro para Disney+

                                // Max (Entretenimiento)
                                servicio.nombre.contains("Max", ignoreCase = true) ->
                                    Pair(R.drawable.hbomax_logo, Color(0xFF002BE7)) // Azul para Max

                                // Spotify (Entretenimiento)
                                servicio.nombre.contains("Spotify", ignoreCase = true) ->
                                    Pair(R.drawable.spotify_logo, Color(0xFF1DB954)) // Verde para Spotify

                                // Crunchyroll (Entretenimiento)
                                servicio.nombre.contains("Crunchyroll", ignoreCase = true) ->
                                    Pair(R.drawable.crunchyroll_logo, Color(0xFFF47521)) // Naranja para Crunchyroll

                                // Por defecto (WiFi genérico)
                                else -> Pair(R.drawable.wifi_logo, Color(0xFF7C4DFF))
                            }

                            ItemServicioCard(
                                icono = iconoRes,
                                colorFondoIcono = colorFondoIcono,
                                nombre = servicio.nombre,
                                vencimientoTexto = textoVencimiento,
                                montoTexto = formatoMoneda.format(servicio.monto),
                                colorEstadoBadge = colorEstado,
                                textoBadge = textoEstadoBoton,
                                onPagarClick = {
                                    // Acción al presionar pagar
                                }
                            )
                        }
                    }
                }
            }

            // ---------- Barra de navegación inferior ----------
            BarraNavegacionInferior(
                modifier = Modifier.align(Alignment.BottomCenter),
                pantallaActual = PantallaInferior.SERVICIOS,
                accionInicio = accionInicio,
                accionServicios = accionServicios,
                accionPagos = accionPagos,
                accionAjustes = accionAjustes
            )

            // ---------- Diálogo para nuevo servicio ----------
            if (mostrarDialogo) {
                DialogoNuevoServicio(
                    onCancelar = { mostrarDialogo = false },
                    onGuardar = { nombre, tipo, monto, dias ->
                        val vencimientoMillis = System.currentTimeMillis() + (dias.toLong() * 86_400_000L)
                        val nuevoServicio = ServicioEntity(
                            nombre = nombre,
                            tipo = tipo.toString(),
                            monto = monto,
                            vencimientoMillis = vencimientoMillis
                        )
                        serviciosViewModel.agregarServicio(nuevoServicio)
                        mostrarDialogo = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ItemServicioCard(
    icono: Int,
    colorFondoIcono: Color,
    nombre: String,
    vencimientoTexto: String,
    montoTexto: String,
    colorEstadoBadge: Color,
    textoBadge: String,
    onPagarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF161616))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(colorFondoIcono), // Fondo dinámico según el servicio
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = icono),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = nombre,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Etiqueta de estado dinámica
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(colorEstadoBadge)
                    .clickable(onClick = onPagarClick)
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = textoBadge,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(text = vencimientoTexto, color = Color.Gray, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Total:", color = Color.Gray, fontSize = 13.sp)
                Text(text = montoTexto, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Botón secundario "Pagar" inferior
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .clickable { }
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pagar",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}