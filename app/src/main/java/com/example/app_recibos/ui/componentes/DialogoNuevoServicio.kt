package com.example.app_recibos.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_recibos.data.TipoServicio

private val ColorAcento = Color(0xFF29D9E8)
private val ColorFondoDialogo = Color(0xFF161616)

/**
 * Pantalla emergente para añadir un servicio nuevo.
 * Se abre desde el botón (+) de Inicio. Valida los campos y, si todo está
 * bien, llama a [onGuardar] con nombre, tipo, monto en pesos y días que
 * faltan para el vencimiento.
 */
@Composable
fun DialogoNuevoServicio(
    onCancelar: () -> Unit,
    onGuardar: (nombre: String, tipo: TipoServicio, monto: Long, dias: Int) -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var tipo by rememberSaveable { mutableStateOf(TipoServicio.AGUA) }
    var monto by rememberSaveable { mutableStateOf("") }
    var dias by rememberSaveable { mutableStateOf("") }
    // Los errores solo se muestran después del primer intento de guardar.
    var intentoGuardar by rememberSaveable { mutableStateOf(false) }

    val montoValor = monto.toLongOrNull()
    val diasValor = dias.toIntOrNull()
    val nombreValido = nombre.isNotBlank()
    val montoValido = montoValor != null && montoValor > 0
    val diasValido = diasValor != null && diasValor in 0..365

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = ColorFondoDialogo,
        title = {
            Text(
                text = "Nuevo servicio",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it.take(30) },
                    label = { Text("Nombre del servicio") },
                    placeholder = { Text("Ej: Acueducto") },
                    singleLine = true,
                    isError = intentoGuardar && !nombreValido,
                    supportingText = {
                        if (intentoGuardar && !nombreValido) Text("Escribe un nombre")
                    },
                    colors = coloresCampo(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Tipo", color = Color.LightGray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TipoServicio.entries.forEach { opcion ->
                        FichaTipo(
                            texto = opcion.etiqueta,
                            seleccionada = opcion == tipo,
                            onClick = { tipo = opcion }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it.filter(Char::isDigit).take(9) },
                    label = { Text("Monto (COP)") },
                    placeholder = { Text("Ej: 130000") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = intentoGuardar && !montoValido,
                    supportingText = {
                        if (intentoGuardar && !montoValido) Text("Escribe un monto mayor a 0")
                    },
                    colors = coloresCampo(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dias,
                    onValueChange = { dias = it.filter(Char::isDigit).take(3) },
                    label = { Text("Vence en (días)") },
                    placeholder = { Text("Ej: 5") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = intentoGuardar && !diasValido,
                    supportingText = {
                        if (intentoGuardar && !diasValido) Text("Escribe un número entre 0 y 365")
                    },
                    colors = coloresCampo(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    intentoGuardar = true
                    if (nombreValido && montoValido && diasValido) {
                        onGuardar(nombre.trim(), tipo, montoValor!!, diasValor!!)
                    }
                }
            ) {
                Text("Guardar", color = ColorAcento, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar", color = Color.LightGray)
            }
        }
    )
}

/** Ficha redonda para elegir el tipo de servicio (misma estética que los botones de la app). */
@Composable
private fun FichaTipo(texto: String, seleccionada: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (seleccionada) ColorAcento else Color(0xFF3A3A3A))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = if (seleccionada) Color.Black else Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    errorTextColor = Color.White,
    focusedBorderColor = ColorAcento,
    unfocusedBorderColor = Color.Gray,
    focusedLabelColor = ColorAcento,
    unfocusedLabelColor = Color.LightGray,
    cursorColor = ColorAcento,
    focusedPlaceholderColor = Color.Gray,
    unfocusedPlaceholderColor = Color.Gray,
    focusedSupportingTextColor = Color.LightGray,
    unfocusedSupportingTextColor = Color.LightGray
)
