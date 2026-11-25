package com.example.inventariodonarosa.data

import android.content.ContentValues
import com.example.inventariodonarosa.data.InventarioDbHelper.Companion.COL_CANTIDAD_ACTUAL
import com.example.inventariodonarosa.data.InventarioDbHelper.Companion.COL_CANTIDAD_INICIAL
import com.example.inventariodonarosa.data.InventarioDbHelper.Companion.COL_ID
import com.example.inventariodonarosa.data.InventarioDbHelper.Companion.COL_NOMBRE
import com.example.inventariodonarosa.data.InventarioDbHelper.Companion.COL_PRECIO
import com.example.inventariodonarosa.data.InventarioDbHelper.Companion.TABLA
import com.example.inventariodonarosa.logic.RepositorioProductos
import com.example.inventariodonarosa.model.Producto

/**
 * Guarda y lee los productos en la base SQLite (punto 9).
 *
 * Cumple el contrato RepositorioProductos, así que la lógica lo puede usar
 * sin saber que por debajo hay SQL.
 */
class ProductoRepositorio(private val helper: InventarioDbHelper) : RepositorioProductos {

    /** SELECT de toda la tabla, convertido a objetos Producto. */
    override fun cargar(): List<Producto> {
        val productos = mutableListOf<Producto>()

        val cursor = helper.readableDatabase.query(
            TABLA, null, null, null, null, null, "$COL_ID ASC"
        )

        // use{} cierra el cursor solo, aunque ocurra un error en la mitad.
        cursor.use {
            while (it.moveToNext()) {
                productos.add(
                    Producto(
                        id = it.getInt(it.getColumnIndexOrThrow(COL_ID)),
                        nombre = it.getString(it.getColumnIndexOrThrow(COL_NOMBRE)),
                        precio = it.getDouble(it.getColumnIndexOrThrow(COL_PRECIO)),
                        cantidadInicial = it.getInt(it.getColumnIndexOrThrow(COL_CANTIDAD_INICIAL)),
                        cantidadActual = it.getInt(it.getColumnIndexOrThrow(COL_CANTIDAD_ACTUAL))
                    )
                )
            }
        }
        return productos
    }

    /** INSERT de un producto nuevo. */
    override fun guardar(producto: Producto) {
        helper.writableDatabase.insert(TABLA, null, aValores(producto))
    }

    /** UPDATE ... WHERE id = ? */
    override fun actualizar(producto: Producto) {
        helper.writableDatabase.update(
            TABLA,
            aValores(producto),
            "$COL_ID = ?",
            arrayOf(producto.id.toString())
        )
    }

    /** DELETE ... WHERE id = ? */
    override fun eliminar(id: Int) {
        helper.writableDatabase.delete(TABLA, "$COL_ID = ?", arrayOf(id.toString()))
    }

    /** Pasa un Producto a las columnas de la tabla. */
    private fun aValores(producto: Producto): ContentValues {
        val valores = ContentValues()
        valores.put(COL_ID, producto.id)
        valores.put(COL_NOMBRE, producto.nombre)
        valores.put(COL_PRECIO, producto.precio)
        valores.put(COL_CANTIDAD_INICIAL, producto.cantidadInicial)
        valores.put(COL_CANTIDAD_ACTUAL, producto.cantidadActual)
        return valores
    }
}
