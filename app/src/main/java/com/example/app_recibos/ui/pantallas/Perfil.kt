package com.example.app_recibos.ui.pantallas

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    accionAcercaDe: () -> Unit = onIrACreditos
) {
    val context = LocalContext.current
    val nombreUsuario = if (SesionUsuario.nombre.isNullOrBlank()) "Usuario" else SesionUsuario.nombre
    val emailUsuario = if (SesionUsuario.email.isNullOrBlank()) "correo@ejemplo.com" else SesionUsuario.email

    val telefonoUsuario = if (SesionUsuario.telefono.isNullOrBlank()) "No registrado" else SesionUsuario.telefono
    val ciudadUsuario = if (SesionUsuario.ciudad.isNullOrBlank()) "Popayán, Cauca" else SesionUsuario.ciudad

    val inicial = nombreUsuario.take(1).uppercase()

    // Estado local para la URI de la imagen seleccionada
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador nativo para seleccionar la imagen de la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imagenUri = uri
            val nuevoBitmap = try {
                if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (e: Exception) {
                null
            }
            // Guardamos directamente en la sesión global para que se actualice en la esquina del Inicio
            SesionUsuario.fotoPerfil = nuevoBitmap
        }
    }

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

            // Avatar clickeable conectado directamente con SesionUsuario.fotoPerfil
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (SesionUsuario.fotoPerfil != null) {
                    Image(
                        bitmap = SesionUsuario.fotoPerfil!!.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = inicial,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
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
                    text = "Teléfono: $telefonoUsuario",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Ciudad: $ciudadUsuario",
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
                onClick = accionAcercaDe,
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
                    SesionUsuario.telefono = ""
                    SesionUsuario.ciudad = ""
                    SesionUsuario.fotoPerfil = null
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