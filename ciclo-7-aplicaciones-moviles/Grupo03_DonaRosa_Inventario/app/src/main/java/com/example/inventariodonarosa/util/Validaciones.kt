package com.example.inventariodonarosa.util

/**
 * Validaciones del caso (punto 7). Son funciones sueltas de Kotlin puro:
 * devuelven true/false y NO lanzan excepciones, para que la pantalla pueda
 * mostrar un mensaje claro en vez de que la app se cierre.
 */

/** El nombre es obligatorio: no puede estar vacío ni ser solo espacios. */
fun validarNombre(nombre: String): Boolean {
    return nombre.isNotBlank()
}

/** El precio debe ser un número mayor que cero. */
fun validarPrecio(precio: Double): Boolean {
    return precio > 0
}

/** Las cantidades nunca pueden ser negativas. */
fun validarCantidad(cantidad: Int): Boolean {
    return cantidad >= 0
}

/** El código identifica al producto, así que debe ser mayor que cero. */
fun validarId(id: Int): Boolean {
    return id > 0
}

/** Valida de una sola vez todos los datos de un producto. */
fun validarProducto(
    nombre: String,
    precio: Double,
    cantidadInicial: Int,
    cantidadActual: Int
): Boolean {
    return validarNombre(nombre) &&
            validarPrecio(precio) &&
            validarCantidad(cantidadInicial) &&
            validarCantidad(cantidadActual)
}

/**
 * Convierte el texto de un campo a número entero.
 * Devuelve null si el usuario escribió algo que no es un número.
 */
fun leerEntero(texto: String): Int? {
    return texto.trim().toIntOrNull()
}

/**
 * Convierte el texto de un campo a número decimal.
 * Acepta coma o punto, como se escribe el precio en Colombia.
 */
fun leerDecimal(texto: String): Double? {
    return texto.trim().replace(',', '.').toDoubleOrNull()
}
