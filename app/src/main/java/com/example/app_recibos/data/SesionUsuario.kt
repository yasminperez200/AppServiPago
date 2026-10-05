package com.example.app_recibos.data

/**
 * Objeto global para mantener en memoria los datos
 * del usuario que acaba de iniciar sesión.
 */
object SesionUsuario {
    var nombre: String = "Usuario"
    var email: String = "correo@ejemplo.com"
    var telefono: String = "+57 310 123 4567"
    var ciudad: String = "Popayán, Cauca"

    fun iniciar(usuario: UsuarioEntity) {
        nombre = usuario.nombre
        email = usuario.email
        // Si tu UsuarioEntity tiene teléfono o ciudad, los puedes asignar aquí también:
        // telefono = usuario.telefono
        // ciudad = usuario.ciudad
    }
}