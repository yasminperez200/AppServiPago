package com.example.app_recibos.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_recibos.R
import com.example.app_recibos.data.ServicioEntity
import com.example.app_recibos.data.TipoServicio
import com.example.app_recibos.ui.componentes.BarraNavegacionInferior
import com.example.app_recibos.ui.componentes.PantallaInferior
import com.example.app_recibos.viewmodel.PagosViewModel
import com.example.app_recibos.viewmodel.ServiciosViewModel
import java.util.Locale
import kotlin.math.ceil

@Composable
fun MisServicios(
    modifier: Modifier = Modifier,
    accionInicio: () -> Unit = {},
    accionPagos: () -> Unit = {},
    accionAjustes: () -> Unit = {},
    pagosViewModel: PagosViewModel = viewModel(),
    serviciosViewModel: ServiciosViewModel = viewModel()
) {
    val serviciosGuardados by serviciosViewModel.servicios.collectAsState()
    var busqueda by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // Listas de control para ocultar inmediatamente los servicios pagados en esta sesión
    var serviciosPagadosEjemplo by remember { mutableStateOf(setOf<String>()) }

    fun pagarYVerHistorialEjemplo(idServicio: String, servicio: String, monto: String) {
        serviciosPagadosEjemplo = serviciosPagadosEjemplo + idServicio
        pagosViewModel.registrarPago(servicio = servicio, monto = monto)
        accionPagos()
    }

    // 👈 Modificado para buscar el objeto y eliminarlo permanentemente de Room
    fun pagarYVerHistorialRoom(idServicio: Long, servicioNombre: String, monto: String) {
        pagosViewModel.registrarPago(servicio = servicioNombre, monto = monto)

        // Buscamos el servicio original en la lista y lo borramos de la base de datos
        val servicioA_Borrar = serviciosGuardados.find { it.id == idServicio }
        if (servicioA_Borrar != null) {
            serviciosViewModel.eliminarServicio(servicioA_Borrar)
        }

        accionPagos()
    }

    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
                    .padding(bottom = 70.dp)
            ) {

                // ---------- Barra de búsqueda ----------
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    placeholder = { Text("Buscar servicio o convenio...", color = Color.LightGray) },
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    leadingIcon = {
                        Text(text = "\uD83D\uDD0D", fontSize = 16.sp)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF3A3A3A),
                        focusedContainerColor = Color(0xFF3A3A3A),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // ---------- Lista de servicios de ejemplo ----------
                if (!serviciosPagadosEjemplo.contains("CEO")) {
                    TarjetaServicioLista(
                        nombre = "CEO",
                        dias = 5,
                        total = "\$130.000 COP",
                        alDia = false,
                        colorIcono = Color(0xFFE8B923),
                        accion = { pagarYVerHistorialEjemplo("CEO", "Electricidad CEO", "\$130.000 COP") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.luz_logo),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                if (!serviciosPagadosEjemplo.contains("Efigas")) {
                    TarjetaServicioLista(
                        nombre = "Efigas",
                        dias = 3,
                        total = "\$145.000 COP",
                        alDia = false,
                        colorIcono = Color(0xFFE4633A),
                        accion = { pagarYVerHistorialEjemplo("Efigas", "Efigas Gas", "\$145.000 COP") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fuego_logo),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                if (!serviciosPagadosEjemplo.contains("Claro")) {
                    TarjetaServicioLista(
                        nombre = "Claro",
                        dias = 2,
                        total = "\$65.000 COP",
                        alDia = false,
                        colorIcono = Color(0xFFE30613),
                        iconoEsCuadrado = true,
                        accion = { pagarYVerHistorialEjemplo("Claro", "Claro Hogar", "\$65.000 COP") }
                    ) {
                        Text(
                            text = "Claro",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Acueducto (Ejemplo al día)
                TarjetaServicioLista(
                    nombre = "Acueducto",
                    dias = 1,
                    total = "\$23.000 COP",
                    alDia = true,
                    colorIcono = Color(0xFF2E9BE0)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.agua_logo),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // ---------- Servicios agregados por el usuario (Room) ----------
                serviciosGuardados.forEach { servicio ->
                    Spacer(modifier = Modifier.height(14.dp))

                    val tipo = servicio.tipoServicio()
                    val total = formatearMonto(servicio.monto)
                    val dias = diasRestantes(servicio)

                    TarjetaServicioLista(
                        nombre = servicio.nombre,
                        dias = dias,
                        textoVencimiento = textoVencimiento(dias),
                        total = total,
                        alDia = false,
                        colorIcono = colorDeTipo(tipo),
                        iconoEsCuadrado = tipo == TipoServicio.INTERNET,
                        accion = { pagarYVerHistorialRoom(servicio.id, servicio.nombre, total) }
                    ) {
                        IconoDeTipo(tipo = tipo, nombre = servicio.nombre)
                    }
                }
            }

            BarraNavegacionInferior(
                modifier = Modifier.align(Alignment.BottomCenter),
                pantallaActual = PantallaInferior.SERVICIOS,
                accionInicio = accionInicio,
                accionServicios = {},
                accionPagos = accionPagos,
                accionAjustes = accionAjustes
            )
        }
    }
}

@Composable
private fun TarjetaServicioLista(
    nombre: String,
    dias: Int,
    total: String,
    alDia: Boolean,
    colorIcono: Color,
    iconoEsCuadrado: Boolean = false,
    textoVencimiento: String? = null,
    accion: () -> Unit = {},
    icono: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF161616))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(if (iconoEsCuadrado) RoundedCornerShape(8.dp) else CircleShape)
                    .background(colorIcono),
                contentAlignment = Alignment.Center
            ) {
                icono()
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = nombre,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (alDia) Color(0xFF1E8E4C) else Color(0xFFC62828))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (alDia) "Al día" else "¡Pagar Ya!",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(textoVencimiento ?: "Vence en $dias dias", color = Color.LightGray, fontSize = 12.sp)
                Text("Total:", color = Color.LightGray, fontSize = 12.sp)
                Text(total, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(total, color = Color.LightGray, fontSize = 12.sp)
                if (!alDia) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                            .clickable { accion() }
                            .padding(horizontal = 22.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Pagar",
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IconoDeTipo(tipo: TipoServicio, nombre: String) {
    when (tipo) {
        TipoServicio.AGUA -> Image(
            painter = painterResource(id = R.drawable.agua_logo),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        TipoServicio.LUZ -> Image(
            painter = painterResource(id = R.drawable.luz_logo),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        TipoServicio.GAS -> Image(
            painter = painterResource(id = R.drawable.fuego_logo),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        TipoServicio.INTERNET -> Text(
            text = "WiFi",
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
        TipoServicio.OTRO -> Text(
            text = nombre.take(1).uppercase(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun colorDeTipo(tipo: TipoServicio): Color = when (tipo) {
    TipoServicio.AGUA -> Color(0xFF2E9BE0)
    TipoServicio.LUZ -> Color(0xFFE8B923)
    TipoServicio.GAS -> Color(0xFFE4633A)
    TipoServicio.INTERNET -> Color(0xFF7B52E0)
    TipoServicio.OTRO -> Color(0xFF607D8B)
}

private fun formatearMonto(valor: Long): String =
    "\$" + String.format(Locale.forLanguageTag("es-CO"), "%,d", valor) + " COP"

private fun diasRestantes(servicio: ServicioEntity): Int {
    val milisPorDia = 86_400_000.0
    val faltan = servicio.vencimientoMillis - System.currentTimeMillis()
    return ceil(faltan / milisPorDia).toInt()
}

private fun textoVencimiento(dias: Int): String = when {
    dias > 1 -> "Vence en $dias días"
    dias == 1 -> "Vence mañana"
    dias == 0 -> "Vence hoy"
    else -> "Vencido hace ${-dias} días"
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MisServiciosPreview() {
    MisServicios()
}