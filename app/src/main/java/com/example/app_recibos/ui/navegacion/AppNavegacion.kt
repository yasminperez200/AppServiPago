package com.example.app_recibos.ui.navegacion

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_recibos.ui.pantallas.Ajustes
import com.example.app_recibos.ui.pantallas.Bienvenida
import com.example.app_recibos.ui.pantallas.CrearCuenta
import com.example.app_recibos.ui.pantallas.Creditos
import com.example.app_recibos.ui.pantallas.IniciarSesion
import com.example.app_recibos.ui.pantallas.Inicio
import com.example.app_recibos.ui.pantallas.MisServicios
import com.example.app_recibos.ui.pantallas.OlvidoPassword
import com.example.app_recibos.ui.pantallas.Pagos
import com.example.app_recibos.ui.pantallas.Perfil
import kotlinx.coroutines.launch

/**
 * Grafo de navegación de toda la app (Navigation Component para Compose).
 * Cada pantalla vive en su propio archivo dentro de ui.pantallas; aquí
 * solo se define cómo se conectan entre sí.
 */
@Composable
fun AppNavegacion(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Pantallas.Bienvenida.name,
        modifier = modifier
    ) {

        composable(route = Pantallas.Bienvenida.name) {
            Bienvenida(
                accion = { navController.navigate(Pantallas.IniciarSesion.name) },
                accionCrear = { navController.navigate(Pantallas.CrearCuenta.name) }
            )
        }

        composable(route = Pantallas.IniciarSesion.name) {
            IniciarSesion(
                onLoginExitoso = {
                    navController.navigate(Pantallas.Inicio.name) {
                        popUpTo(Pantallas.Bienvenida.name) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Pantallas.CrearCuenta.name) }
            )
        }

        composable(route = Pantallas.OlvidoPassword.name) {
            OlvidoPassword(
                accionVolverLogin = { navController.popBackStack() }
            )
        }

        composable(route = Pantallas.CrearCuenta.name) {
            CrearCuenta(
                accionIniciarSesion = { navController.navigate(Pantallas.IniciarSesion.name) },
                accionRegistroExitoso = {
                    navController.navigate(Pantallas.Bienvenida.name) {
                        popUpTo(Pantallas.Bienvenida.name) { inclusive = true }
                    }
                    scope.launch {
                        snackbarHostState.showSnackbar("Cuenta creada exitosamente")
                    }
                }
            )
        }

        composable(route = Pantallas.Inicio.name) {
            Inicio(
                accionServicioAgregado = { nombre: String ->
                    scope.launch {
                        snackbarHostState.showSnackbar("Servicio \"$nombre\" agregado")
                    }
                },
                accionInicio = {},
                accionServicios = { navController.navigate(Pantallas.MisServicios.name) },
                accionPagos = { navController.navigate(Pantallas.Pagos.name) },
                accionAjustes = { navController.navigate(Pantallas.Ajustes.name) },
                accionPerfil = { navController.navigate(Pantallas.Perfil.name) }
            )
        }

        composable(route = Pantallas.MisServicios.name) {
            MisServicios(
                accionInicio = { navController.navigate(Pantallas.Inicio.name) },
                accionPagos = { navController.navigate(Pantallas.Pagos.name) },
                accionAjustes = { navController.navigate(Pantallas.Ajustes.name) }
            )
        }

        composable(route = Pantallas.Pagos.name) {
            Pagos(
                accionInicio = { navController.navigate(Pantallas.Inicio.name) },
                accionServicios = { navController.navigate(Pantallas.MisServicios.name) },
                accionAjustes = { navController.navigate(Pantallas.Ajustes.name) }
            )
        }

        composable(route = Pantallas.Ajustes.name) {
            Ajustes(
                accionInicio = { navController.navigate(Pantallas.Inicio.name) },
                accionServicios = { navController.navigate(Pantallas.MisServicios.name) },
                accionPagos = { navController.navigate(Pantallas.Pagos.name) }
            )
        }

        composable(route = Pantallas.Perfil.name) {
            Perfil(
                onCerrarSesion = {
                    navController.navigate(Pantallas.Bienvenida.name) {
                        popUpTo(0)
                    }
                },
                accionAcercaDe = { navController.navigate(Pantallas.Creditos.name) }
            )
        }

        composable(route = Pantallas.Creditos.name) {
            Creditos(
                accionVolver = { navController.popBackStack() }
            )
        }
    }
}