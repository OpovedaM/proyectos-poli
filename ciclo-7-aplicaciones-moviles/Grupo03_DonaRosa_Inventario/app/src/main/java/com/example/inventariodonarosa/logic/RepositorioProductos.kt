package com.example.inventariodonarosa.logic

import com.example.inventariodonarosa.model.Producto

/**
 * Contrato de la capa de persistencia (punto 9).
 *
 * La lógica no sabe NI LE IMPORTA si los datos se guardan en SQLite, en un
 * archivo o en ningún lado: solo pide estas cuatro cosas. Gracias a eso
 * InventarioManager sigue siendo Kotlin puro y se puede probar con JUnit,
 * mientras que quien implementa esta interfaz sí usa Android.
 */
interface RepositorioProductos {

    /** Devuelve todos los productos guardados en el dispositivo. */
    fun cargar(): List<Producto>

    /** Guarda un producto nuevo. */
    fun guardar(producto: Producto)

    /** Guarda los cambios de un producto que ya existía. */
    fun actualizar(producto: Producto)

    /** Borra el producto con ese código. */
    fun eliminar(id: Int)
}
