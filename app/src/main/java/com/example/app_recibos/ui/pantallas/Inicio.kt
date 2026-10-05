package com.example.app_recibos.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_recibos.R
import com.example.app_recibos.data.PagoEntity
import com.example.app_recibos.data.ServicioEntity
import com.example.app_recibos.data.SesionUsuario
import com.example.app_recibos.ui.componentes.BarraNavegacionInferior
import com.example.app_recibos.ui.componentes.PantallaInferior
import com.example.app_recibos.viewmodel.PagosViewModel
import com.example.app_recibos.viewmodel.ServiciosViewModel
import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.random.Random

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
    serviciosViewModel: ServiciosViewModel = viewModel(),
    pagosViewModel: PagosViewModel = viewModel()
) {
    var mostrarDialogo by rememberSaveable { mutableStateOf(false) }

    val listaServicios by serviciosViewModel.servicios.collectAsState(initial = emptyList())
    val listaPagos by pagosViewModel.historialPagos.collectAsState(initial = emptyList<PagoEntity>())

    val pagosRealizadosCount = listaPagos.size
    val totalPendienteSuma = listaServicios.sumOf { it.monto }

    val serviciosProximos = remember(listaServicios) {
        listaServicios.sortedBy { it.vencimientoMillis }.take(3)
    }

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

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF333333))
                            .clickable(onClick = accionPerfil),
                        contentAlignment = Alignment.Center
                    ) {
                        if (SesionUsuario.fotoPerfil != null) {
                            Image(
                                bitmap = SesionUsuario.fotoPerfil!!.asImageBitmap(),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.perfil_logo),
                                contentDescription = "Perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                        }
                    }
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
                            Text(
                                text = formatoMoneda.format(totalPendienteSuma),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Pagos Realizados :", color = Color.LightGray, fontSize = 12.sp)
                            Text(
                                text = pagosRealizadosCount.toString(),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

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
                            BarraResumen("Julio", 88.dp, Color(0xFFE53935))
                            BarraResumen("Agosto", 55.dp, Color(0xFFF6C453))
                            BarraResumen("Septiembre", 112.dp, Color(0xFF3DD62E))
                            BarraResumen("Octubre", 42.dp, Color(0xFF4E8DF5))
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

                // ---------- Tarjetas de servicios dinámicas (Máximo 3) ----------
                if (serviciosProximos.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF161616))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay servicios próximos. ¡Agrega uno con el botón +!",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (servicio in serviciosProximos) {
                            val diffMillis = servicio.vencimientoMillis - System.currentTimeMillis()
                            val diasRestantes = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()

                            val textoEstado = when {
                                diasRestantes < 0 -> "Vencido"
                                diasRestantes == 0 -> "Vence hoy"
                                diasRestantes == 1 -> "Vence mañana"
                                else -> "Vence en $diasRestantes días"
                            }

                            val iconoRes = when {
                                servicio.tipo.contains("Internet", ignoreCase = true) || servicio.nombre.contains("Claro", ignoreCase = true) -> R.drawable.wifi_logo
                                servicio.tipo.contains("Luz", ignoreCase = true) || servicio.nombre.contains("CEO", ignoreCase = true) -> R.drawable.luz_logo
                                servicio.tipo.contains("Agua", ignoreCase = true) || servicio.nombre.contains("Agua", ignoreCase = true) || servicio.nombre.contains("Gotica", ignoreCase = true) -> R.drawable.agua_logo
                                servicio.tipo.contains("Gas", ignoreCase = true) -> R.drawable.fuego_logo
                                else -> R.drawable.fuego_logo
                            }

                            TarjetaServicio(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                icono = iconoRes,
                                titulo = servicio.nombre,
                                estado = textoEstado,
                                colorFondo = Color(0xFFE4483A),
                                textoBoton = "Ver más",
                                colorBoton = Color(0xFFB71C1C),
                                colorTextoBoton = Color.White
                            )
                        }
                    }
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

            // ---------- Diálogo de Nuevo Servicio Modificado ----------
            if (mostrarDialogo) {
                DialogoNuevoServicioModificado(
                    onCancelar = { mostrarDialogo = false },
                    onGuardarGenerado = { nombre, tipo, monto, dias ->
                        val vencimientoMillis = System.currentTimeMillis() + (dias.toLong() * 86_400_000L)

                        val nuevoServicio = ServicioEntity(
                            nombre = nombre,
                            tipo = tipo,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogoNuevoServicioModificado(
    onCancelar: () -> Unit,
    onGuardarGenerado: (nombre: String, tipo: String, monto: Long, dias: Int) -> Unit
) {
    var categoriaSeleccionada by remember { mutableStateOf("Servicios públicos") }
    var expandidoCategoria by remember { mutableStateOf(false) }

    // Campos para Servicios Públicos
    var numeroContrato by remember { mutableStateOf("") }

    // Campos para Entretenimiento
    var plataformaSeleccionada by remember { mutableStateOf("Netflix") }
    var expandidoPlataforma by remember { mutableStateOf(false) }
    var correoEntretenimiento by remember { mutableStateOf("") }

    // Estado para controlar el error del correo
    var correoError by remember { mutableStateOf(false) }

    val listaCategorias = listOf("Servicios públicos", "Entretenimiento")
    val listaPlataformas = listOf("Netflix", "Amazon Prime", "Disney+", "Max", "Spotify", "Crunchyroll")

    val opcionesEmpresasPublicas = listOf(
        Pair("Claro", "Internet"),
        Pair("CEO", "Luz"),
        Pair("Gotica", "Agua"),
        Pair("Acueducto", "Agua"),
        Pair("Gas", "Gas")
    )

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = Color(0xFF161616),
        title = {
            Text(
                text = "Nuevo servicio",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ---------- Desplegable Principal: Categoría ----------
                ExposedDropdownMenuBox(
                    expanded = expandidoCategoria,
                    onExpandedChange = { expandidoCategoria = !expandidoCategoria }
                ) {
                    OutlinedTextField(
                        value = categoriaSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de servicio", color = Color.Gray) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoCategoria) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF29D9E8),
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandidoCategoria,
                        onDismissRequest = { expandidoCategoria = false }
                    ) {
                        listaCategorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(categoria) },
                                onClick = {
                                    categoriaSeleccionada = categoria
                                    expandidoCategoria = false
                                }
                            )
                        }
                    }
                }

                // ---------- VISTA 1: SI ES SERVICIO PÚBLICO ----------
                if (categoriaSeleccionada == "Servicios públicos") {
                    OutlinedTextField(
                        value = numeroContrato,
                        onValueChange = { numeroContrato = it },
                        label = { Text("Número de contrato", color = Color.Gray) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF29D9E8),
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF29D9E8)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                // ---------- VISTA 2: SI ES ENTRETENIMIENTO ----------
                else {
                    // Desplegable de Plataforma
                    ExposedDropdownMenuBox(
                        expanded = expandidoPlataforma,
                        onExpandedChange = { expandidoPlataforma = !expandidoPlataforma }
                    ) {
                        OutlinedTextField(
                            value = plataformaSeleccionada,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Plataforma", color = Color.Gray) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoPlataforma) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF29D9E8),
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandidoPlataforma,
                            onDismissRequest = { expandidoPlataforma = false }
                        ) {
                            listaPlataformas.forEach { plataforma ->
                                DropdownMenuItem(
                                    text = { Text(plataforma) },
                                    onClick = {
                                        plataformaSeleccionada = plataforma
                                        expandidoPlataforma = false
                                    }
                                )
                            }
                        }
                    }

                    // Campo de Correo con validación visual
                    OutlinedTextField(
                        value = correoEntretenimiento,
                        onValueChange = {
                            correoEntretenimiento = it
                            if (correoError) correoError = false
                        },
                        label = { Text("Correo de la cuenta", color = Color.Gray) },
                        singleLine = true,
                        isError = correoError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (correoError) Color.Red else Color(0xFF29D9E8),
                            unfocusedBorderColor = if (correoError) Color.Red else Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF29D9E8),
                            errorBorderColor = Color.Red
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (correoError) {
                        Text(
                            text = "Ingrese un correo válido (ej: @gmail.com, @unicauca.edu.co)",
                            color = Color.Red,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (categoriaSeleccionada == "Servicios públicos") {
                        if (numeroContrato.isNotBlank()) {
                            val seleccion = opcionesEmpresasPublicas.random()
                            val nombreEmpresa = seleccion.first
                            val tipoServicio = seleccion.second

                            val montoAleatorio = Random.nextLong(30000L, 200001L)
                            val diasAleatorios = Random.nextInt(1, 26)

                            onGuardarGenerado(nombreEmpresa, tipoServicio, montoAleatorio, diasAleatorios)
                        }
                    } else {
                        // Validación de correo: que contenga '@' y un punto después del '@' (ej. @gmail.com, @unicauca.edu.co)
                        val esCorreoValido = android.util.Patterns.EMAIL_ADDRESS.matcher(correoEntretenimiento).matches() ||
                                (correoEntretenimiento.contains("@") && correoEntretenimiento.substringAfter("@").contains("."))

                        if (esCorreoValido) {
                            correoError = false
                            val nombrePlataforma = plataformaSeleccionada
                            val tipoServicio = "Entretenimiento"
                            val montoAleatorio = Random.nextLong(15000L, 60000L)
                            val diasAleatorios = Random.nextInt(1, 26)

                            onGuardarGenerado(nombrePlataforma, tipoServicio, montoAleatorio, diasAleatorios)
                        } else {
                            correoError = true
                        }
                    }
                }
            ) {
                Text(
                    text = "Guardar",
                    color = Color(0xFF29D9E8),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text(
                    text = "Cancelar",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
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