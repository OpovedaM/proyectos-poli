import sqlite3

NOMBRE_BD = "tienda.db"


def conectar():
    conexion = sqlite3.connect(NOMBRE_BD)
    conexion.row_factory = sqlite3.Row
    return conexion


def crear_tabla():
    conexion = conectar()
    conexion.execute("""
        CREATE TABLE IF NOT EXISTS productos (
            codigo INTEGER PRIMARY KEY AUTOINCREMENT,
            producto TEXT NOT NULL,
            precio INTEGER NOT NULL CHECK (precio > 0),
            cantidad INTEGER NOT NULL CHECK (cantidad >= 0),
            cantidad_inicial INTEGER NOT NULL CHECK (cantidad_inicial > 0)
        )
    """)
    conexion.commit()
    conexion.close()
PRODUCTOS_INICIALES = [
    ("Peras", 4000, 65),
    ("Limones", 1500, 25),
    ("Moras", 2000, 30),
    ("Piñas", 3000, 15),
    ("Tomates", 1000, 30),
    ("Fresas", 3000, 12),
    ("Frunas", 300, 50),
    ("Galletas", 500, 400),
    ("Chocolates", 1200, 500),
    ("Arroz", 1200, 60),
]


def cargar_datos_iniciales():
    conexion = conectar()
    total = conexion.execute("SELECT COUNT(*) FROM productos").fetchone()[0]
    if total == 0:
        for nombre, precio, cantidad in PRODUCTOS_INICIALES:
            conexion.execute(
                "INSERT INTO productos (producto, precio, cantidad, cantidad_inicial) "
                "VALUES (?, ?, ?, ?)",
                (nombre, precio, cantidad, cantidad),
            )
        conexion.commit()
    conexion.close()
def obtener_productos():
    conexion = conectar()
    filas = conexion.execute("SELECT * FROM productos ORDER BY codigo").fetchall()
    conexion.close()
    return filas
def insertar_producto(nombre, precio, cantidad):
    conexion = conectar()
    conexion.execute(
        "INSERT INTO productos (producto, precio, cantidad, cantidad_inicial) "
        "VALUES (?, ?, ?, ?)",
        (nombre, precio, cantidad, cantidad),
    )
    conexion.commit()
    conexion.close()
def obtener_producto(codigo):
    conexion = conectar()
    fila = conexion.execute(
        "SELECT * FROM productos WHERE codigo = ?", (codigo,)
    ).fetchone()
    conexion.close()
    return fila


def actualizar_producto(codigo, nombre, precio, cantidad):
    conexion = conectar()
    conexion.execute(
        "UPDATE productos SET producto = ?, precio = ?, cantidad = ?, "
        "cantidad_inicial = MAX(cantidad_inicial, ?) WHERE codigo = ?",
        (nombre, precio, cantidad, cantidad, codigo),
    )
    conexion.commit()
    conexion.close()

def borrar_producto(codigo):
    conexion = conectar()
    conexion.execute("DELETE FROM productos WHERE codigo = ?", (codigo,))
    conexion.commit()
    conexion.close()

def producto_mas_cerca_de_acabarse():
    conexion = conectar()
    fila = conexion.execute(
        "SELECT * FROM productos "
        "ORDER BY cantidad - 0.10 * cantidad_inicial ASC, codigo ASC LIMIT 1"
    ).fetchone()
    conexion.close()
    return fila


def costo_total_inventario():
    conexion = conectar()
    total = conexion.execute(
        "SELECT SUM(precio * cantidad) FROM productos"
    ).fetchone()[0]
    conexion.close()
    return total or 0

def existe_producto(nombre, excepto_codigo=None):
    nombre = nombre.strip().casefold()
    for producto in obtener_productos():
        if producto["codigo"] == excepto_codigo:
            continue
        if producto["producto"].strip().casefold() == nombre:
            return True
    return False

if __name__ == "__main__":
    crear_tabla()
    cargar_datos_iniciales()
    conexion = conectar()
    for fila in conexion.execute("SELECT * FROM productos"):
        print(fila["codigo"], fila["producto"], fila["precio"], fila["cantidad"])
    conexion.close()

