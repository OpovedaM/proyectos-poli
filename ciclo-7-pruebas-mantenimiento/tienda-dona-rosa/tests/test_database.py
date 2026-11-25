import sqlite3

import pytest

import database


def test_se_cargan_los_10_productos_iniciales(bd_temporal):
    assert len(database.obtener_productos()) == 10


def test_los_datos_iniciales_no_se_duplican(bd_temporal):
    database.cargar_datos_iniciales()
    assert len(database.obtener_productos()) == 10


def test_insertar_producto(bd_temporal):
    database.insertar_producto("Mangos", 2500, 20)
    productos = database.obtener_productos()
    assert len(productos) == 11
    nuevo = productos[-1]
    assert nuevo["producto"] == "Mangos"
    assert nuevo["precio"] == 2500
    assert nuevo["cantidad"] == 20
    assert nuevo["cantidad_inicial"] == 20


def test_actualizar_producto_cambia_los_datos(bd_temporal):
    database.actualizar_producto(1, "Peras", 4500, 50)
    peras = database.obtener_producto(1)
    assert peras["precio"] == 4500
    assert peras["cantidad"] == 50


def test_una_venta_no_cambia_la_cantidad_inicial(bd_temporal):
    database.actualizar_producto(1, "Peras", 4000, 50)
    assert database.obtener_producto(1)["cantidad_inicial"] == 65


def test_llegada_de_mercancia_sube_la_cantidad_inicial(bd_temporal):
    database.actualizar_producto(1, "Peras", 4000, 100)
    assert database.obtener_producto(1)["cantidad_inicial"] == 100


def test_actualizar_un_producto_no_toca_a_los_demas(bd_temporal):
    database.actualizar_producto(1, "Peras", 4500, 50)
    assert database.obtener_producto(2)["cantidad"] == 25


def test_borrar_producto(bd_temporal):
    database.borrar_producto(1)
    assert database.obtener_producto(1) is None
    assert len(database.obtener_productos()) == 9


def test_producto_inexistente_devuelve_none(bd_temporal):
    assert database.obtener_producto(999) is None


def test_costo_total_inicial(bd_temporal):
    assert database.costo_total_inventario() == 1355500


def test_costo_total_cambia_al_vender(bd_temporal):
    # Fresas pasa de 12 a 1 unidad: bajan 11 x 3000 = 33000
    database.actualizar_producto(6, "Fresas", 3000, 1)
    assert database.costo_total_inventario() == 1322500


def test_producto_mas_cerca_de_acabarse_al_inicio_es_fresas(bd_temporal):
    assert database.producto_mas_cerca_de_acabarse()["producto"] == "Fresas"


def test_producto_mas_cerca_de_acabarse_cambia_con_las_ventas(bd_temporal):
    database.actualizar_producto(2, "Limones", 1500, 1)
    assert database.producto_mas_cerca_de_acabarse()["producto"] == "Limones"


def test_sin_productos_el_total_es_cero_y_no_hay_producto_critico(bd_temporal):
    for producto in database.obtener_productos():
        database.borrar_producto(producto["codigo"])
    assert database.costo_total_inventario() == 0
    assert database.producto_mas_cerca_de_acabarse() is None


def test_la_base_de_datos_rechaza_precios_negativos(bd_temporal):
    with pytest.raises(sqlite3.IntegrityError):
        database.insertar_producto("Malo", -5, 1)


def test_existe_producto_detecta_un_nombre_repetido(bd_temporal):
    assert database.existe_producto("Peras") is True


def test_existe_producto_ignora_mayusculas_y_espacios(bd_temporal):
    assert database.existe_producto("  peras  ") is True
    assert database.existe_producto("PIÑAS") is True


def test_existe_producto_con_nombre_nuevo(bd_temporal):
    assert database.existe_producto("Mangos") is False


def test_existe_producto_puede_ignorar_el_producto_que_se_edita(bd_temporal):
    assert database.existe_producto("Peras", excepto_codigo=1) is False
    assert database.existe_producto("Peras", excepto_codigo=2) is True