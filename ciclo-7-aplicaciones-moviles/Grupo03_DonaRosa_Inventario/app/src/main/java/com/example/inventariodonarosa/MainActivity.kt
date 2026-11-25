package com.example.inventariodonarosa

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inventariodonarosa.data.InventarioDbHelper
import com.example.inventariodonarosa.data.ProductoRepositorio
import com.example.inventariodonarosa.logic.InventarioManager
import com.example.inventariodonarosa.model.Producto
import com.example.inventariodonarosa.util.leerDecimal
import com.example.inventariodonarosa.util.leerEntero
import com.example.inventariodonarosa.util.validarCantidad
import com.example.inventariodonarosa.util.validarId
import com.example.inventariodonarosa.util.validarNombre
import com.example.inventariodonarosa.util.validarPrecio
import java.util.Locale

/**
 * Capa de interfaz (punto 5). Captura lo que escribe Doña Rosa, se lo entrega
 * al InventarioManager y muestra el resultado. Ningún cálculo se hace aquí.
 *
 * Todo vive en una sola pantalla: cada operación es una sección que se muestra
 * u oculta con View.VISIBLE / View.GONE.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: InventarioDbHelper
    private lateinit var manager: InventarioManager

    /** Código del producto que se está editando o que se va a eliminar. */
    private var idSeleccionado: Int? = null

    private lateinit var seccionMenu: LinearLayout
    private lateinit var seccionRegistrar: LinearLayout
    private lateinit var seccionConsultar: LinearLayout
    private lateinit var seccionActualizar: LinearLayout
    private lateinit var seccionEliminar: LinearLayout
    private lateinit var seccionInventario: LinearLayout
    private lateinit var formActualizar: LinearLayout

    private lateinit var etCodigo: EditText
    private lateinit var etNombre: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etCantidadInicial: EditText
    private lateinit var etCantidadActual: EditText

    private lateinit var etCodigoBuscar: EditText
    private lateinit var etNombreEditar: EditText
    private lateinit var etPrecioEditar: EditText
    private lateinit var etCantidadInicialEditar: EditText
    private lateinit var etCantidadActualEditar: EditText

    private lateinit var etCodigoEliminar: EditText
    private lateinit var btnConfirmarEliminar: Button

    private lateinit var tvListaProductos: TextView
    private lateinit var tvProductoEliminar: TextView
    private lateinit var tvResumenInventario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        enlazarVistas()
        configurarBotones()

        // La base SQLite del dispositivo: aquí viven los datos entre una
        // apertura de la app y la siguiente.
        dbHelper = InventarioDbHelper(this)
        manager = InventarioManager(ProductoRepositorio(dbHelper))

        manager.cargarDesdeRepositorio()
        if (manager.totalProductos == 0) {
            // Primera vez que se abre la app: se siembran los productos que
            // Doña Rosa tenía en el cuaderno. De ahí en adelante se lee lo guardado.
            manager.cargarInventarioInicial()
        }

        mostrar(seccionMenu)
    }

    /** Cierra la conexión con la base cuando la pantalla se destruye. */
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun enlazarVistas() {
        seccionMenu = findViewById(R.id.seccionMenu)
        seccionRegistrar = findViewById(R.id.seccionRegistrar)
        seccionConsultar = findViewById(R.id.seccionConsultar)
        seccionActualizar = findViewById(R.id.seccionActualizar)
        seccionEliminar = findViewById(R.id.seccionEliminar)
        seccionInventario = findViewById(R.id.seccionInventario)
        formActualizar = findViewById(R.id.formActualizar)

        etCodigo = findViewById(R.id.etCodigo)
        etNombre = findViewById(R.id.etNombre)
        etPrecio = findViewById(R.id.etPrecio)
        etCantidadInicial = findViewById(R.id.etCantidadInicial)
        etCantidadActual = findViewById(R.id.etCantidadActual)

        etCodigoBuscar = findViewById(R.id.etCodigoBuscar)
        etNombreEditar = findViewById(R.id.etNombreEditar)
        etPrecioEditar = findViewById(R.id.etPrecioEditar)
        etCantidadInicialEditar = findViewById(R.id.etCantidadInicialEditar)
        etCantidadActualEditar = findViewById(R.id.etCantidadActualEditar)

        etCodigoEliminar = findViewById(R.id.etCodigoEliminar)
        btnConfirmarEliminar = findViewById(R.id.btnConfirmarEliminar)

        tvListaProductos = findViewById(R.id.tvListaProductos)
        tvProductoEliminar = findViewById(R.id.tvProductoEliminar)
        tvResumenInventario = findViewById(R.id.tvResumenInventario)
    }

    private fun configurarBotones() {
        findViewById<Button>(R.id.btnIrRegistrar).setOnClickListener { abrirRegistrar() }
        findViewById<Button>(R.id.btnIrConsultar).setOnClickListener { abrirConsultar() }
        findViewById<Button>(R.id.btnIrActualizar).setOnClickListener { abrirActualizar() }
        findViewById<Button>(R.id.btnIrEliminar).setOnClickListener { abrirEliminar() }
        findViewById<Button>(R.id.btnIrInventario).setOnClickListener { abrirInventario() }

        findViewById<Button>(R.id.btnGuardarProducto).setOnClickListener { registrarProducto() }
        findViewById<Button>(R.id.btnBuscarActualizar).setOnClickListener { buscarParaActualizar() }
        findViewById<Button>(R.id.btnGuardarCambios).setOnClickListener { guardarCambios() }
        findViewById<Button>(R.id.btnBuscarEliminar).setOnClickListener { buscarParaEliminar() }
        btnConfirmarEliminar.setOnClickListener { eliminarProducto() }

        findViewById<Button>(R.id.btnVolverRegistrar).setOnClickListener { mostrar(seccionMenu) }
        findViewById<Button>(R.id.btnVolverConsultar).setOnClickListener { mostrar(seccionMenu) }
        findViewById<Button>(R.id.btnVolverActualizar).setOnClickListener { mostrar(seccionMenu) }
        findViewById<Button>(R.id.btnVolverEliminar).setOnClickListener { mostrar(seccionMenu) }
        findViewById<Button>(R.id.btnVolverInventario).setOnClickListener { mostrar(seccionMenu) }
    }

    // ----------------------------- REGISTRAR -----------------------------

    private fun abrirRegistrar() {
        limpiarFormularioRegistro()
        mostrar(seccionRegistrar)
    }

    /**
     * Valida campo por campo para poder decir EXACTAMENTE qué está mal,
     * y solo al final le pide al manager que registre.
     */
    private fun registrarProducto() {
        val codigo = leerEntero(etCodigo.text.toString())
        if (codigo == null) {
            avisar(R.string.error_codigo_numero)
            return
        }
        if (!validarId(codigo)) {
            avisar(R.string.error_codigo_mayor_cero)
            return
        }
        if (manager.existeId(codigo)) {
            avisar(R.string.error_codigo_repetido)
            return
        }

        val nombre = etNombre.text.toString().trim()
        if (!validarNombre(nombre)) {
            avisar(R.string.error_nombre)
            return
        }

        val precio = leerDecimal(etPrecio.text.toString())
        if (precio == null) {
            avisar(R.string.error_precio_numero)
            return
        }
        if (!validarPrecio(precio)) {
            avisar(R.string.error_precio_mayor_cero)
            return
        }

        val cantidadInicial = leerEntero(etCantidadInicial.text.toString())
        val cantidadActual = leerEntero(etCantidadActual.text.toString())
        if (cantidadInicial == null || cantidadActual == null) {
            avisar(R.string.error_cantidad_numero)
            return
        }
        if (!validarCantidad(cantidadInicial) || !validarCantidad(cantidadActual)) {
            avisar(R.string.error_cantidad_negativa)
            return
        }

        manager.registrar(codigo, nombre, precio, cantidadInicial, cantidadActual)
        avisar(R.string.ok_registrado)
        limpiarFormularioRegistro()
    }

    // ----------------------------- CONSULTAR -----------------------------

    private fun abrirConsultar() {
        tvListaProductos.text = construirListaProductos()
        mostrar(seccionConsultar)
    }

    private fun construirListaProductos(): String {
        val productos = manager.listarOrdenadosPorNombre()
        if (productos.isEmpty()) return getString(R.string.inventario_vacio)

        val texto = StringBuilder()
        for (producto in productos) {
            texto.append(getString(R.string.producto_linea, producto.id, producto.nombre))
            texto.append("\n")
            texto.append(
                getString(
                    R.string.producto_detalle,
                    formatearPesos(producto.precio),
                    producto.cantidadActual,
                    producto.cantidadInicial
                )
            )
            texto.append("\n")
            if (producto.estaProximoAAgotarse()) {
                texto.append(
                    getString(R.string.aviso_agotandose, formatearDecimal(producto.calcularLimite()))
                )
                texto.append("\n")
            }
            texto.append("\n")
        }
        return texto.toString().trimEnd()
    }

    // ----------------------------- ACTUALIZAR ----------------------------

    private fun abrirActualizar() {
        idSeleccionado = null
        etCodigoBuscar.setText("")
        formActualizar.visibility = View.GONE
        mostrar(seccionActualizar)
    }

    /** Busca el producto y llena el formulario con sus datos actuales. */
    private fun buscarParaActualizar() {
        val producto = buscarProducto(etCodigoBuscar) ?: return

        idSeleccionado = producto.id
        etNombreEditar.setText(producto.nombre)
        etPrecioEditar.setText(formatearNumero(producto.precio))
        etCantidadInicialEditar.setText(producto.cantidadInicial.toString())
        etCantidadActualEditar.setText(producto.cantidadActual.toString())
        formActualizar.visibility = View.VISIBLE
    }

    private fun guardarCambios() {
        val id = idSeleccionado ?: return

        val nombre = etNombreEditar.text.toString().trim()
        if (!validarNombre(nombre)) {
            avisar(R.string.error_nombre)
            return
        }

        val precio = leerDecimal(etPrecioEditar.text.toString())
        if (precio == null) {
            avisar(R.string.error_precio_numero)
            return
        }
        if (!validarPrecio(precio)) {
            avisar(R.string.error_precio_mayor_cero)
            return
        }

        val cantidadInicial = leerEntero(etCantidadInicialEditar.text.toString())
        val cantidadActual = leerEntero(etCantidadActualEditar.text.toString())
        if (cantidadInicial == null || cantidadActual == null) {
            avisar(R.string.error_cantidad_numero)
            return
        }
        if (!validarCantidad(cantidadInicial) || !validarCantidad(cantidadActual)) {
            avisar(R.string.error_cantidad_negativa)
            return
        }

        manager.actualizar(id, nombre, precio, cantidadInicial, cantidadActual)
        avisar(R.string.ok_actualizado)
        formActualizar.visibility = View.GONE
        etCodigoBuscar.setText("")
        idSeleccionado = null
    }

    // ------------------------------ ELIMINAR -----------------------------

    private fun abrirEliminar() {
        idSeleccionado = null
        etCodigoEliminar.setText("")
        tvProductoEliminar.text = ""
        btnConfirmarEliminar.visibility = View.GONE
        mostrar(seccionEliminar)
    }

    /** Primero muestra qué producto se va a borrar; eliminar es el segundo paso. */
    private fun buscarParaEliminar() {
        val producto = buscarProducto(etCodigoEliminar) ?: return

        idSeleccionado = producto.id
        tvProductoEliminar.text = getString(R.string.producto_linea, producto.id, producto.nombre) +
                "\n" +
                getString(
                    R.string.producto_detalle,
                    formatearPesos(producto.precio),
                    producto.cantidadActual,
                    producto.cantidadInicial
                )
        btnConfirmarEliminar.visibility = View.VISIBLE
    }

    private fun eliminarProducto() {
        val id = idSeleccionado ?: return

        manager.eliminar(id)
        avisar(R.string.ok_eliminado)

        idSeleccionado = null
        etCodigoEliminar.setText("")
        tvProductoEliminar.text = ""
        btnConfirmarEliminar.visibility = View.GONE
    }

    // ----------------------------- INVENTARIO ----------------------------

    private fun abrirInventario() {
        tvResumenInventario.text = construirResumenInventario()
        mostrar(seccionInventario)
    }

    private fun construirResumenInventario(): String {
        val texto = StringBuilder()
        texto.append(getString(R.string.resumen_productos, manager.totalProductos))
        texto.append("\n")
        texto.append(getString(R.string.resumen_unidades, manager.calcularCantidadTotal()))
        texto.append("\n")
        texto.append(getString(R.string.resumen_valor, formatearPesos(manager.calcularValorTotal())))
        texto.append("\n\n")

        val proximos = manager.obtenerProximosAAgotarse()
        if (proximos.isEmpty()) {
            texto.append(getString(R.string.sin_proximos))
        } else {
            texto.append(getString(R.string.resumen_proximos))
            texto.append("\n")
            for (producto in proximos) {
                texto.append(
                    getString(R.string.producto_linea, producto.id, producto.nombre)
                )
                texto.append("  —  ")
                texto.append(producto.cantidadActual)
                texto.append("\n")
            }
        }
        return texto.toString().trimEnd()
    }

    // ------------------------------ APOYO --------------------------------

    /** Lee el código de un campo, valida y busca. Avisa y devuelve null si algo falla. */
    private fun buscarProducto(campo: EditText): Producto? {
        val codigo = leerEntero(campo.text.toString())
        if (codigo == null) {
            avisar(R.string.error_codigo_numero)
            return null
        }

        val producto = manager.buscarPorId(codigo)
        if (producto == null) {
            avisar(R.string.error_no_existe)
            return null
        }
        return producto
    }

    /** Deja visible solo la sección indicada. */
    private fun mostrar(seccion: LinearLayout) {
        seccionMenu.visibility = View.GONE
        seccionRegistrar.visibility = View.GONE
        seccionConsultar.visibility = View.GONE
        seccionActualizar.visibility = View.GONE
        seccionEliminar.visibility = View.GONE
        seccionInventario.visibility = View.GONE

        seccion.visibility = View.VISIBLE
    }

    private fun limpiarFormularioRegistro() {
        etCodigo.setText("")
        etNombre.setText("")
        etPrecio.setText("")
        etCantidadInicial.setText("")
        etCantidadActual.setText("")
        etCodigo.requestFocus()
    }

    /**
     * 260000.0 -> "$ 260.000"
     *
     * Se fija el formato colombiano a propósito: si se deja el idioma del
     * dispositivo, un celular en inglés mostraría "260,000" con coma.
     */
    private fun formatearPesos(valor: Double): String {
        return "\$ " + String.format(COLOMBIA, "%,.0f", valor)
    }

    /**
     * 4000.0 -> "4000" y 1500.5 -> "1500.5"
     * Se usa para precargar el campo del precio: ahí no van separadores de
     * miles, porque el texto se vuelve a leer como número al guardar.
     */
    private fun formatearNumero(valor: Double): String {
        return if (valor == valor.toLong().toDouble()) {
            valor.toLong().toString()
        } else {
            valor.toString()
        }
    }

    /** 6.5 -> "6,5" */
    private fun formatearDecimal(valor: Double): String {
        return String.format(COLOMBIA, "%.1f", valor)
    }

    companion object {
        private val COLOMBIA: Locale = Locale.forLanguageTag("es-CO")
    }

    private fun avisar(mensaje: Int) {
        Toast.makeText(this, getString(mensaje), Toast.LENGTH_LONG).show()
    }
}
