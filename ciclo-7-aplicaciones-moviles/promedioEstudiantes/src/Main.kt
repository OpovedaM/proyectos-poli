fun main() {
    val cantidad = pedirCantidadEstudiantes()
    val nombres = Array(cantidad) { "" }
    val notasFinales = DoubleArray(cantidad)

    for (i in 0 until cantidad) {
        println("\n--- Estudiante ${i + 1} de $cantidad ---")
        nombres[i] = pedirNombre()
        val notas = pedirTresNotas()
        notasFinales[i] = calcularPromedio(notas)
        println("Nota final de ${nombres[i]}: ${"%.2f".format(notasFinales[i])}")
    }

    ordenarDescendentePorNota(nombres, notasFinales)
    mostrarListadoFinal(nombres, notasFinales)
}

fun pedirCantidadEstudiantes(): Int {
    var cantidad: Int
    while (true) {
        print("Ingrese la cantidad de estudiantes del aula: ")
        cantidad = readLine()?.toIntOrNull() ?: -1
        if (cantidad > 0) break
        println("Por favor ingrese un numero entero mayor a cero.")
    }
    return cantidad
}

fun pedirNombre(): String {
    var nombre: String
    while (true) {
        print("Ingrese el nombre del estudiante: ")
        nombre = readLine()?.trim() ?: ""
        if (nombre.isNotEmpty()) break
        println("El nombre no puede estar vacio.")
    }
    return nombre
}

fun pedirTresNotas(): DoubleArray {
    val notas = DoubleArray(3)
    for (i in 0 until 3) {
        notas[i] = pedirNota(i + 1)
    }
    return notas
}

fun pedirNota(numero: Int): Double {
    var nota: Double
    while (true) {
        print("  Ingrese la nota $numero (0.0 a 5.0): ")
        nota = readLine()?.replace(",", ".")?.toDoubleOrNull() ?: -1.0
        if (nota in 0.0..5.0) break
        println("  Nota invalida. Debe ser un numero entre 0.0 y 5.0.")
    }
    return nota
}

fun calcularPromedio(notas: DoubleArray): Double {
    var suma = 0.0
    for (nota in notas) {
        suma += nota
    }
    return suma / notas.size
}

fun ordenarDescendentePorNota(nombres: Array<String>, notas: DoubleArray) {
    val n = nombres.size
    for (i in 0 until n - 1) {
        var indiceMayor = i
        for (j in i + 1 until n) {
            if (notas[j] > notas[indiceMayor]) {
                indiceMayor = j
            }
        }
        if (indiceMayor != i) {
            val notaTemp = notas[i]
            notas[i] = notas[indiceMayor]
            notas[indiceMayor] = notaTemp

            val nombreTemp = nombres[i]
            nombres[i] = nombres[indiceMayor]
            nombres[indiceMayor] = nombreTemp
        }
    }
}

fun mostrarListadoFinal(nombres: Array<String>, notas: DoubleArray) {
    println("\n=== Listado de estudiantes ordenado de mayor a menor nota final ===")
    for (i in nombres.indices) {
        println("${i + 1}. ${nombres[i]} - ${"%.2f".format(notas[i])}")
    }
}
