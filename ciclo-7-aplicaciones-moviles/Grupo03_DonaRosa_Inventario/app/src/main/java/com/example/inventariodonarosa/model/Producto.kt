package com.example.inventariodonarosa.model

import com.example.inventariodonarosa.util.validarCantidad
import com.example.inventariodonarosa.util.validarProducto

/**
 * Un producto del inventario de la tienda (punto 3: modelo de datos).
 *
 * Encapsulamiento: los atributos se pueden LEER desde afuera, pero solo se
 * MODIFICAN llamando a los métodos de la clase (`private set`). Así nadie
 * puede dejar un producto con un precio negativo desde otra parte del código.
 *
 * El id no cambia nunca: es el código con el que Doña Rosa identifica el producto.
 */
class Producto(
    val id: Int,
    nombre: String,
    precio: Double,
    cantidadInicial: Int,
    cantidadActual: Int
) {

    var nombre: String = nombre
        private set

    var precio: Double = precio
        private set

    var cantidadInicial: Int = cantidadInicial
        private set

    var cantidadActual: Int = cantidadActual
        private set

    /** Regla del caso: limite = cantidadInicial * 0.10 */
    fun calcularLimite(): Double {
        return cantidadInicial * 0.10
    }

    /** Está próximo a agotarse cuando cantidadActual <= límite (igual también cuenta). */
    fun estaProximoAAgotarse(): Boolean {
        return cantidadActual <= calcularLimite()
    }

    /** Valor de este producto: valorProducto = precio * cantidadActual */
    fun calcularValor(): Double {
        return precio * cantidadActual
    }

    /** Cambia solo la cantidad disponible. Devuelve false si la cantidad no es válida. */
    fun actualizarCantidad(nuevaCantidad: Int): Boolean {
        if (!validarCantidad(nuevaCantidad)) return false

        cantidadActual = nuevaCantidad
        return true
    }

    /** Cambia los datos del producto. Devuelve false si algún dato es inválido. */
    fun actualizarDatos(
        nuevoNombre: String,
        nuevoPrecio: Double,
        nuevaCantidadInicial: Int,
        nuevaCantidadActual: Int
    ): Boolean {
        if (!validarProducto(nuevoNombre, nuevoPrecio, nuevaCantidadInicial, nuevaCantidadActual)) {
            return false
        }

        nombre = nuevoNombre
        precio = nuevoPrecio
        cantidadInicial = nuevaCantidadInicial
        cantidadActual = nuevaCantidadActual
        return true
    }
}
