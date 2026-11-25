package com.example.inventariodonarosa

import com.example.inventariodonarosa.logic.InventarioManager
import com.example.inventariodonarosa.util.leerDecimal
import com.example.inventariodonarosa.util.leerEntero
import com.example.inventariodonarosa.util.validarCantidad
import com.example.inventariodonarosa.util.validarNombre
import com.example.inventariodonarosa.util.validarPrecio
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas del punto 10 del caso. Corren en la máquina, sin emulador, porque
 * InventarioManager y Producto no dependen de Android.
 *
 * La Prueba 8 (cerrar y volver a abrir la app) no está aquí: es una prueba
 * manual sobre el dispositivo y se hará cuando esté lista la persistencia.
 */
class InventarioManagerTest {

    // Margen de error para comparar Double: no se comparan exacto
    // por cómo el computador guarda los decimales.
    private val margen = 0.001

    /** Inventario con los tres primeros productos del cuaderno de Doña Rosa. */
    private fun inventarioDePrueba(): InventarioManager {
        val manager = InventarioManager()
        manager.registrar(1, "Peras", 4000.0, 65, 65)
        manager.registrar(2, "Limones", 1500.0, 25, 25)
        manager.registrar(3, "Moras", 2000.0, 30, 30)
        return manager
    }

    // --------- Inventario inicial: los productos del cuaderno --------------

    @Test
    fun cargarInventarioInicial_registraLosDiezProductosDelCuaderno() {
        val manager = InventarioManager()

        manager.cargarInventarioInicial()

        assertEquals(10, manager.totalProductos)
        assertEquals("Peras", manager.buscarPorId(1)!!.nombre)
        assertEquals("Arroz", manager.buscarPorId(10)!!.nombre)
        assertEquals(1187, manager.calcularCantidadTotal())
    }

    @Test
    fun cargarInventarioInicial_daElValorTotalDelCuaderno() {
        val manager = InventarioManager()

        manager.cargarInventarioInicial()

        // Suma de precio x cantidad de los diez productos de la tabla del caso.
        assertEquals(1355500.0, manager.calcularValorTotal(), margen)
    }

    // ------------------- PRUEBA 1: registrar un producto ------------------

    @Test
    fun prueba1_registrar_guardaElProductoConSusDatos() {
        val manager = InventarioManager()

        assertTrue(manager.registrar(1, "Peras", 4000.0, 65, 65))
        assertEquals(1, manager.totalProductos)

        val peras = manager.buscarPorId(1)!!
        assertEquals("Peras", peras.nombre)
        assertEquals(4000.0, peras.precio, margen)
        assertEquals(65, peras.cantidadInicial)
        assertEquals(65, peras.cantidadActual)
    }

    @Test
    fun prueba1_registrar_noPermiteDosProductosConElMismoCodigo() {
        val manager = inventarioDePrueba()

        assertFalse(manager.registrar(1, "Piñas", 3000.0, 15, 15))
        assertEquals(3, manager.totalProductos)
        // El producto original no se cambió.
        assertEquals("Peras", manager.buscarPorId(1)!!.nombre)
    }

    // --------------------- PRUEBA 2: consultar productos ------------------

    @Test
    fun prueba2_consultar_devuelveTodosLosProductosRegistrados() {
        val manager = inventarioDePrueba()

        val lista = manager.listar()

        assertEquals(3, lista.size)
        assertEquals("Peras", lista[0].nombre)
        assertEquals("Moras", lista[2].nombre)
    }

    @Test
    fun prueba2_consultar_porCodigoInexistente_devuelveNull() {
        val manager = inventarioDePrueba()

        assertNull(manager.buscarPorId(99))
        assertFalse(manager.existeId(99))
        assertTrue(manager.existeId(2))
    }

    @Test
    fun prueba2_consultar_ordenadoPorNombre() {
        val manager = inventarioDePrueba()

        val nombres = manager.listarOrdenadosPorNombre().map { it.nombre }

        assertEquals(listOf("Limones", "Moras", "Peras"), nombres)
    }

    // --------------------- PRUEBA 3: actualizar producto ------------------

    @Test
    fun prueba3_actualizar_cambiaLosDatosDelProducto() {
        val manager = inventarioDePrueba()

        assertTrue(manager.actualizar(2, "Limón Tahití", 1800.0, 25, 20))

        val limones = manager.buscarPorId(2)!!
        assertEquals("Limón Tahití", limones.nombre)
        assertEquals(1800.0, limones.precio, margen)
        assertEquals(20, limones.cantidadActual)
        // Sigue siendo el mismo producto: no se duplicó.
        assertEquals(3, manager.totalProductos)
    }

    @Test
    fun prueba3_actualizarCantidad_cambiaSoloLaCantidadDisponible() {
        val manager = inventarioDePrueba()

        assertTrue(manager.actualizarCantidad(1, 40))

        val peras = manager.buscarPorId(1)!!
        assertEquals(40, peras.cantidadActual)
        assertEquals(65, peras.cantidadInicial)
        assertEquals(4000.0, peras.precio, margen)
    }

    @Test
    fun prueba3_actualizar_conCodigoInexistente_devuelveFalse() {
        val manager = inventarioDePrueba()

        assertFalse(manager.actualizar(99, "Fresas", 3000.0, 12, 12))
        assertFalse(manager.actualizarCantidad(99, 5))
    }

    // ---------------------- PRUEBA 4: eliminar producto -------------------

    @Test
    fun prueba4_eliminar_quitaElProductoDelInventario() {
        val manager = inventarioDePrueba()

        assertTrue(manager.eliminar(2))

        assertEquals(2, manager.totalProductos)
        assertNull(manager.buscarPorId(2))
        // Los otros productos siguen ahí.
        assertTrue(manager.existeId(1))
        assertTrue(manager.existeId(3))
    }

    @Test
    fun prueba4_eliminar_conCodigoInexistente_devuelveFalse() {
        val manager = inventarioDePrueba()

        assertFalse(manager.eliminar(99))
        assertEquals(3, manager.totalProductos)
    }

    // ------------------ PRUEBA 5: información inválida ---------------------

    @Test
    fun prueba5_registrar_rechazaNombreVacio() {
        val manager = InventarioManager()

        assertFalse(manager.registrar(1, "", 4000.0, 65, 65))
        assertFalse(manager.registrar(1, "   ", 4000.0, 65, 65))
        assertEquals(0, manager.totalProductos)
    }

    @Test
    fun prueba5_registrar_rechazaPrecioCeroONegativo() {
        val manager = InventarioManager()

        assertFalse(manager.registrar(1, "Peras", 0.0, 65, 65))
        assertFalse(manager.registrar(1, "Peras", -500.0, 65, 65))
        assertEquals(0, manager.totalProductos)
    }

    @Test
    fun prueba5_registrar_rechazaCantidadesNegativas() {
        val manager = InventarioManager()

        assertFalse(manager.registrar(1, "Peras", 4000.0, -1, 65))
        assertFalse(manager.registrar(1, "Peras", 4000.0, 65, -1))
        assertEquals(0, manager.totalProductos)
    }

    @Test
    fun prueba5_registrar_rechazaCodigoMenorOIgualACero() {
        val manager = InventarioManager()

        assertFalse(manager.registrar(0, "Peras", 4000.0, 65, 65))
        assertFalse(manager.registrar(-3, "Peras", 4000.0, 65, 65))
        assertEquals(0, manager.totalProductos)
    }

    @Test
    fun prueba5_actualizarCantidad_rechazaCantidadNegativa() {
        val manager = inventarioDePrueba()

        assertFalse(manager.actualizarCantidad(1, -5))
        // La cantidad anterior se conserva.
        assertEquals(65, manager.buscarPorId(1)!!.cantidadActual)
    }

    @Test
    fun prueba5_valoresNoNumericos_seLeenComoNull() {
        assertNull(leerEntero("abc"))
        assertNull(leerEntero(""))
        assertNull(leerDecimal("mil pesos"))

        assertEquals(65, leerEntero(" 65 "))
        assertEquals(1500.0, leerDecimal("1500")!!, margen)
        // El precio se puede escribir con coma, como se usa acá.
        assertEquals(1500.5, leerDecimal("1500,5")!!, margen)
    }

    @Test
    fun prueba5_lasValidacionesSueltasFuncionan() {
        assertTrue(validarNombre("Peras"))
        assertFalse(validarNombre("   "))

        assertTrue(validarPrecio(1200.0))
        assertFalse(validarPrecio(0.0))

        assertTrue(validarCantidad(0))
        assertFalse(validarCantidad(-1))
    }

    // ---------- PRUEBA 6: producto por debajo del límite del 10 % ---------

    @Test
    fun prueba6_limiteEsElDiezPorCientoDeLaCantidadInicial() {
        val manager = inventarioDePrueba()

        // Peras: 65 x 10% = 6,5
        assertEquals(6.5, manager.buscarPorId(1)!!.calcularLimite(), margen)
    }

    @Test
    fun prueba6_identificaElProductoQueLlegoAlLimite() {
        val manager = inventarioDePrueba()
        val peras = manager.buscarPorId(1)!!

        // Con 65 unidades está lejos del límite.
        assertFalse(manager.estaProximoAAgotarse(peras))

        // Con 7 todavía no (7 > 6,5).
        manager.actualizarCantidad(1, 7)
        assertFalse(manager.estaProximoAAgotarse(peras))

        // Con 6 sí (6 <= 6,5).
        manager.actualizarCantidad(1, 6)
        assertTrue(manager.estaProximoAAgotarse(peras))
    }

    @Test
    fun prueba6_cantidadIgualAlLimiteTambienCuenta() {
        val manager = InventarioManager()
        manager.registrar(7, "Frunas", 300.0, 50, 50)

        // Frunas: 50 x 10% = 5. Con 5 unidades ya está próximo a agotarse.
        manager.actualizarCantidad(7, 5)
        assertTrue(manager.buscarPorId(7)!!.estaProximoAAgotarse())
    }

    @Test
    fun prueba6_listaSoloLosProductosProximosAAgotarse() {
        val manager = inventarioDePrueba()
        manager.actualizarCantidad(1, 6)   // Peras: límite 6,5 -> próxima a agotarse
        manager.actualizarCantidad(3, 2)   // Moras: límite 3,0 -> próxima a agotarse
        // Limones queda en 25, con límite 2,5.

        val proximos = manager.obtenerProximosAAgotarse().map { it.nombre }

        assertEquals(2, proximos.size)
        assertTrue(proximos.contains("Peras"))
        assertTrue(proximos.contains("Moras"))
        assertFalse(proximos.contains("Limones"))
    }

    // ------------- PRUEBA 7: valor total del inventario -------------------

    @Test
    fun prueba7_calculaElValorTotalDelInventario() {
        val manager = inventarioDePrueba()

        // Peras 4000x65 = 260000, Limones 1500x25 = 37500, Moras 2000x30 = 60000
        assertEquals(357500.0, manager.calcularValorTotal(), margen)
    }

    @Test
    fun prueba7_elValorCambiaCuandoCambiaLaCantidad() {
        val manager = inventarioDePrueba()

        manager.actualizarCantidad(1, 10) // Peras: 4000 x 10 = 40000

        assertEquals(40000.0, manager.buscarPorId(1)!!.calcularValor(), margen)
        assertEquals(137500.0, manager.calcularValorTotal(), margen)
    }

    @Test
    fun prueba7_inventarioVacio_valeCero() {
        val manager = InventarioManager()

        assertEquals(0.0, manager.calcularValorTotal(), margen)
        assertEquals(0, manager.calcularCantidadTotal())
    }

    @Test
    fun prueba7_sumaLasUnidadesDisponibles() {
        val manager = inventarioDePrueba()

        // 65 + 25 + 30
        assertEquals(120, manager.calcularCantidadTotal())
    }
}
