package com.example.app_recibos.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_recibos.data.SesionUsuario

@Composable
fun Perfil(
    modifier: Modifier = Modifier,
    onCerrarSesion: () -> Unit = {},
    accionCerrarSesion: () -> Unit = onCerrarSesion,
    onIrACreditos: () -> Unit = {},
    accionAcercaDe: () -> Unit = onIrACreditos // Soporta el parámetro que busca AppNavegacion.kt
) {
    val nombreUsuario = if (SesionUsuario.nombre.isNullOrBlank()) "Usuario" else SesionUsuario.nombre
    val emailUsuario = if (SesionUsuario.email.isNullOrBlank()) "correo@ejemplo.com" else SesionUsuario.email
    val inicial = nombreUsuario.take(1).uppercase()

    Surface(
        color = Color.Black,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Mi Perfil",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Avatar con inicial
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = inicial,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = nombreUsuario,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = emailUsuario,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjeta de información de la cuenta
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1E1E1E))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Información de la Cuenta",
                    color = Color(0xFF29D9E8),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Teléfono: +57 310 123 4567",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ciudad: Popayán, Cauca",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Método de Pago: Tarjeta **** 4321",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN: Acerca de la App y Créditos
            OutlinedButton(
                onClick = accionAcercaDe, // Usa el parámetro esperado por la navegación
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF29D9E8)
                ),
                border = BorderStroke(1.dp, Color(0xFF444444))
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF29D9E8),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Acerca de la App y Créditos",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón: Cerrar Sesión
            Button(
                onClick = {
                    SesionUsuario.nombre = ""
                    SesionUsuario.email = ""
                    accionCerrarSesion()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}