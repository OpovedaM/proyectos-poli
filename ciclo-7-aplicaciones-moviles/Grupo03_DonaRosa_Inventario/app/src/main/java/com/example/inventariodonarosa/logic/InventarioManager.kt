package com.example.inventariodonarosa.logic

import com.example.inventariodonarosa.model.Producto
import com.example.inventariodonarosa.util.validarId
import com.example.inventariodonarosa.util.validarProducto

/**
 * Lógica de negocio del inventario (puntos 6 y 8).
 *
 * Guarda la lista de productos y ofrece las cuatro operaciones obligatorias
 * —registrar, consultar, actualizar, eliminar— más los cálculos del caso.
 *
 * Es Kotlin puro, sin nada de Android, para poder probarla con JUnit sin emulador.
 * Si se le pasa un repositorio, cada cambio se guarda también en el dispositivo;
 * si no se le pasa ninguno (como en las pruebas), trabaja solo en memoria.
 * Todos los métodos que pueden fallar devuelven Boolean en vez de lanzar
 * excepciones: así la pantalla decide qué mensaje mostrarle a Doña Rosa.
 */
class InventarioManager(private val repositorio: RepositorioProductos? = null) {

    /** La lista es privada: solo se toca a través de los métodos de esta clase. */
    private val productos = mutableListOf<Producto>()

    val totalProductos: Int
        get() = productos.size

    // ----------------------------- REGISTRAR -----------------------------

    /** Agrega un producto ya construido. false si los datos o el código no sirven. */
    fun registrar(producto: Producto): Boolean {
        if (!validarId(producto.id)) return false
        if (existeId(producto.id)) return false
        if (!validarProducto(
                producto.nombre,
                producto.precio,
                producto.cantidadInicial,
                producto.cantidadActual
            )
        ) {
            return false
        }

        productos.add(producto)
        repositorio?.guardar(producto)
        return true
    }

    /** Versión cómoda para la pantalla de registro: recibe los datos sueltos. */
    fun registrar(
        id: Int,
        nombre: String,
        precio: Double,
        cantidadInicial: Int,
        cantidadActual: Int
    ): Boolean {
        return registrar(Producto(id, nombre, precio, cantidadInicial, cantidadActual))
    }

    /**
     * Carga los productos que Doña Rosa tenía anotados en su cuaderno.
     * Solo se usa la primera vez: cuando exista la persistencia, los datos
     * se leerán de lo guardado en el dispositivo.
     */
    fun cargarInventarioInicial() {
        registrar(1, "Peras", 4000.0, 65, 65)
        registrar(2, "Limones", 1500.0, 25, 25)
        registrar(3, "Moras", 2000.0, 30, 30)
        registrar(4, "Piñas", 3000.0, 15, 15)
        registrar(5, "Tomates", 1000.0, 30, 30)
        registrar(6, "Fresas", 3000.0, 12, 12)
        registrar(7, "Frunas", 300.0, 50, 50)
        registrar(8, "Galletas", 500.0, 400, 400)
        registrar(9, "Chocolates", 1200.0, 500, 500)
        registrar(10, "Arroz", 1200.0, 60, 60)
    }

    // ----------------------------- CONSULTAR -----------------------------

    /** Devuelve una copia de la lista, para que nadie agregue productos por fuera. */
    fun listar(): List<Producto> {
        return productos.toList()
    }

    /** Busca por código. null si no existe. */
    fun buscarPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun existeId(id: Int): Boolean {
        return buscarPorId(id) != null
    }

    /** Lista ordenada por nombre, que es como se muestra en pantalla. */
    fun listarOrdenadosPorNombre(): List<Producto> {
        return productos.sortedBy { it.nombre.lowercase() }
    }

    // ----------------------------- ACTUALIZAR ----------------------------

    /** Modifica los datos del producto con ese código. false si no existe o si son inválidos. */
    fun actualizar(
        id: Int,
        nombre: String,
        precio: Double,
        cantidadInicial: Int,
        cantidadActual: Int
    ): Boolean {
        val producto = buscarPorId(id) ?: return false
        if (!producto.actualizarDatos(nombre, precio, cantidadInicial, cantidadActual)) {
            return false
        }

        repositorio?.actualizar(producto)
        return true
    }

    /** Actualiza solo la cantidad disponible (punto 5 de la problemática). */
    fun actualizarCantidad(id: Int, nuevaCantidad: Int): Boolean {
        val producto = buscarPorId(id) ?: return false
        if (!producto.actualizarCantidad(nuevaCantidad)) return false

        repositorio?.actualizar(producto)
        return true
    }

    // ------------------------------ ELIMINAR -----------------------------

    /** Elimina el producto con ese código. false si no existe. */
    fun eliminar(id: Int): Boolean {
        val producto = buscarPorId(id) ?: return false
        productos.remove(producto)
        repositorio?.eliminar(id)
        return true
    }

    /**
     * Trae a memoria lo que esté guardado en el dispositivo. Se llama al abrir
     * la app: por eso los datos siguen ahí después de cerrarla (punto 9).
     */
    fun cargarDesdeRepositorio() {
        val repo = repositorio ?: return

        productos.clear()
        productos.addAll(repo.cargar())
    }

    /** Vacía el inventario en memoria. */
    fun limpiar() {
        productos.clear()
    }

    // ------------------------------ CÁLCULOS -----------------------------

    fun estaProximoAAgotarse(producto: Producto): Boolean {
        return producto.estaProximoAAgotarse()
    }

    /** Productos cuya cantidad actual ya llegó al límite del 10 %. */
    fun obtenerProximosAAgotarse(): List<Producto> {
        return productos.filter { it.estaProximoAAgotarse() }
    }

    /** valorTotal = suma de precio * cantidadActual de todos los productos. */
    fun calcularValorTotal(): Double {
        return productos.sumOf { it.calcularValor() }
    }

    /** Misma suma, pero sobre una lista que se le pase (por ejemplo, un filtro). */
    fun calcularValorTotal(lista: List<Producto>): Double {
        return lista.sumOf { it.calcularValor() }
    }

    /** Total de unidades disponibles en la tienda. */
    fun calcularCantidadTotal(): Int {
        return productos.sumOf { it.cantidadActual }
    }
}
