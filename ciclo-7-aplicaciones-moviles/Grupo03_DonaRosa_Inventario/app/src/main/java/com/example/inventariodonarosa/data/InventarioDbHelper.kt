package com.example.inventariodonarosa.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Crea y mantiene la base de datos SQLite de la aplicación.
 *
 * SQLite es una base de datos embebida: toda la información queda en UN archivo
 * dentro del dispositivo (inventario.db), así que sobrevive a cerrar la app.
 *
 * Esta clase solo se encarga de la estructura (crear la tabla). El SQL de
 * consultar, insertar, actualizar y borrar está en ProductoRepositorio.
 */
class InventarioDbHelper(context: Context) :
    SQLiteOpenHelper(context, NOMBRE_BD, null, VERSION_BD) {

    /** Se ejecuta UNA sola vez: la primera vez que se abre la app. */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLA (" +
                    "$COL_ID INTEGER PRIMARY KEY, " +
                    "$COL_NOMBRE TEXT NOT NULL, " +
                    "$COL_PRECIO REAL NOT NULL, " +
                    "$COL_CANTIDAD_INICIAL INTEGER NOT NULL, " +
                    "$COL_CANTIDAD_ACTUAL INTEGER NOT NULL)"
        )
    }

    /** Se ejecuta si se cambia VERSION_BD, por ejemplo al agregar una columna. */
    override fun onUpgrade(db: SQLiteDatabase, versionAnterior: Int, versionNueva: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA")
        onCreate(db)
    }

    companion object {
        const val NOMBRE_BD = "inventario.db"
        const val VERSION_BD = 1

        const val TABLA = "productos"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_PRECIO = "precio"
        const val COL_CANTIDAD_INICIAL = "cantidad_inicial"
        const val COL_CANTIDAD_ACTUAL = "cantidad_actual"
    }
}
