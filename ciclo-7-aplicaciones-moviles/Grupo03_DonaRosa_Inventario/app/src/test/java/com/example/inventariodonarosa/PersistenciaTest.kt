package com.example.inventariodonarosa

import com.example.inventariodonarosa.logic.InventarioManager
import com.example.inventariodonarosa.logic.RepositorioProductos
import com.example.inventariodonarosa.model.Producto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Prueba 8 del caso: los datos no dependen de variables temporales en memoria.
 *
 * Se usa un repositorio falso en vez de SQLite para que corra sin emulador.
 * Vale igual: los dos cumplen el mismo contrato RepositorioProductos, así que
 * si el manager guarda bien contra el falso, guarda bien contra la base real.
 */
class PersistenciaTest {

    /**
     * Repositorio de mentiras. Guarda COPIAS, igual que una base de datos:
     * una fila guardada no cambia sola porque cambie el objeto en memoria.
     */
    private class RepositorioFalso : RepositorioProductos {

        val filas = mutableListOf<Producto>()
        val eliminados = mutableListOf<Int>()
        var vecesActualizado = 0

        override fun cargar(): List<Producto> = filas.map { copiar(it) }

        override fun guardar(producto: Producto) {
            filas.add(copiar(producto))
        }

        override fun actualizar(producto: Producto) {
            vecesActualizado++
            val posicion = filas.indexOfFirst { it.id == producto.id }
            if (posicion >= 0) filas[posicion] = copiar(producto)
        }

        override fun eliminar(id: Int) {
            eliminados.add(id)
            filas.removeAll { it.id == id }
        }

        private fun copiar(p: Producto): Producto {
            return Producto(p.id, p.nombre, p.precio, p.cantidadInicial, p.cantidadActual)
        }
    }

    @Test
    fun registrar_guardaTambienEnElAlmacenamiento() {
        val repositorio = RepositorioFalso()
        val manager = InventarioManager(repositorio)

        manager.registrar(1, "Peras", 4000.0, 65, 65)

        assertEquals(1, repositorio.filas.size)
        assertEquals("Peras", repositorio.filas[0].nombre)
    }

    @Test
    fun registrar_siLosDatosSonInvalidos_noGuardaNada() {
        val repositorio = RepositorioFalso()
        val manager = InventarioManager(repositorio)

        manager.registrar(1, "", 4000.0, 65, 65)
        manager.registrar(2, "Moras", -100.0, 30, 30)

        assertTrue(repositorio.filas.isEmpty())
    }

    @Test
    fun actualizar_guardaLosCambios() {
        val repositorio = RepositorioFalso()
        val manager = InventarioManager(repositorio)
        manager.registrar(1, "Peras", 4000.0, 65, 65)

        manager.actualizarCantidad(1, 40)

        assertEquals(1, repositorio.vecesActualizado)
        assertEquals(40, repositorio.filas[0].cantidadActual)
    }

    @Test
    fun eliminar_borraTambienDelAlmacenamiento() {
        val repositorio = RepositorioFalso()
        val manager = InventarioManager(repositorio)
        manager.registrar(1, "Peras", 4000.0, 65, 65)
        manager.registrar(2, "Limones", 1500.0, 25, 25)

        manager.eliminar(1)

        assertEquals(listOf(1), repositorio.eliminados)
        assertEquals(1, repositorio.filas.size)
        assertEquals("Limones", repositorio.filas[0].nombre)
    }

    @Test
    fun prueba8_alCerrarYVolverAAbrir_losDatosSiguenAhi() {
        val almacenamiento = RepositorioFalso()

        // --- Primera vez que se abre la app ---
        val primeraApertura = InventarioManager(almacenamiento)
        primeraApertura.cargarDesdeRepositorio()
        primeraApertura.cargarInventarioInicial()
        primeraApertura.actualizarCantidad(1, 6)      // se venden peras
        primeraApertura.eliminar(4)                   // ya no vende piñas

        // --- Se cierra la app: el manager anterior se pierde por completo ---
        val segundaApertura = InventarioManager(almacenamiento)
        segundaApertura.cargarDesdeRepositorio()

        assertEquals(9, segundaApertura.totalProductos)
        assertEquals(6, segundaApertura.buscarPorId(1)!!.cantidadActual)
        assertNull(segundaApertura.buscarPorId(4))
        // Y el cálculo sigue dando lo mismo que antes de cerrar.
        assertEquals(
            primeraApertura.calcularValorTotal(),
            segundaApertura.calcularValorTotal(),
            0.001
        )
    }

    @Test
    fun alAbrirSegundaVez_noSeVuelveASembrarElCuaderno() {
        val almacenamiento = RepositorioFalso()

        val primeraApertura = InventarioManager(almacenamiento)
        primeraApertura.cargarDesdeRepositorio()
        primeraApertura.cargarInventarioInicial()

        // Así lo hace MainActivity: solo siembra si no hay nada guardado.
        val segundaApertura = InventarioManager(almacenamiento)
        segundaApertura.cargarDesdeRepositorio()
        if (segundaApertura.totalProductos == 0) {
            segundaApertura.cargarInventarioInicial()
        }

        assertEquals(10, segundaApertura.totalProductos)
    }
}
