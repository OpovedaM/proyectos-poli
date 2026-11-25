import pytest

import database
from app import app, formato_pesos


@pytest.fixture
def cliente(bd_temporal):
    app.config["TESTING"] = True
    return app.test_client()


def test_formato_pesos():
    assert formato_pesos(300) == "$300"
    assert formato_pesos(4000) == "$4.000"
    assert formato_pesos(1355500) == "$1.355.500"


def test_la_pagina_principal_carga_y_muestra_los_datos(cliente):
    respuesta = cliente.get("/")
    html = respuesta.get_data(as_text=True)
    assert respuesta.status_code == 200
    assert "Peras" in html
    assert "$1.355.500" in html


def test_sin_alerta_cuando_hay_suficiente_cantidad(cliente):
    html = cliente.get("/").get_data(as_text=True)
    assert "¡Por acabarse!" not in html


def test_aparece_la_alerta_cuando_queda_el_10_por_ciento_o_menos(cliente):
    database.actualizar_producto(6, "Fresas", 3000, 1)
    html = cliente.get("/").get_data(as_text=True)
    assert "¡Por acabarse!" in html


def test_agregar_producto_valido(cliente):
    respuesta = cliente.post(
        "/agregar", data={"nombre": "Mangos", "precio": "2500", "cantidad": "20"}
    )
    assert respuesta.status_code == 302
    assert len(database.obtener_productos()) == 11


def test_agregar_producto_invalido_no_se_guarda(cliente):
    respuesta = cliente.post(
        "/agregar", data={"nombre": "   ", "precio": "100", "cantidad": "5"}
    )
    assert respuesta.status_code == 200
    assert "no puede estar vacío" in respuesta.get_data(as_text=True)
    assert len(database.obtener_productos()) == 10


def test_editar_producto(cliente):
    respuesta = cliente.post(
        "/editar/1", data={"nombre": "Peras", "precio": "4000", "cantidad": "50"}
    )
    assert respuesta.status_code == 302
    assert database.obtener_producto(1)["cantidad"] == 50


def test_editar_con_datos_invalidos_no_cambia_nada(cliente):
    respuesta = cliente.post(
        "/editar/1", data={"nombre": "Peras", "precio": "-1", "cantidad": "50"}
    )
    assert respuesta.status_code == 200
    assert database.obtener_producto(1)["precio"] == 4000


def test_editar_producto_inexistente_da_404(cliente):
    assert cliente.get("/editar/999").status_code == 404


def test_borrar_producto(cliente):
    respuesta = cliente.post("/borrar/1")
    assert respuesta.status_code == 302
    assert database.obtener_producto(1) is None


def test_borrar_por_enlace_directo_no_esta_permitido(cliente):
    respuesta = cliente.get("/borrar/1")
    assert respuesta.status_code == 405
    assert database.obtener_producto(1) is not None


def test_borrar_producto_inexistente_da_404(cliente):
    assert cliente.post("/borrar/999").status_code == 404

def test_no_se_puede_agregar_un_producto_repetido(cliente):
    respuesta = cliente.post(
        "/agregar", data={"nombre": "peras", "precio": "100", "cantidad": "5"}
    )
    assert respuesta.status_code == 200
    assert "ya está en el listado" in respuesta.get_data(as_text=True)
    assert len(database.obtener_productos()) == 10


def test_no_se_puede_renombrar_a_un_nombre_que_ya_existe(cliente):
    respuesta = cliente.post(
        "/editar/1", data={"nombre": "Limones", "precio": "4000", "cantidad": "65"}
    )
    assert respuesta.status_code == 200
    assert "Ya existe otro producto" in respuesta.get_data(as_text=True)
    assert database.obtener_producto(1)["producto"] == "Peras"


def test_editar_un_producto_conservando_su_nombre_si_se_puede(cliente):
    respuesta = cliente.post(
        "/editar/1", data={"nombre": "Peras", "precio": "4000", "cantidad": "10"}
    )
    assert respuesta.status_code == 302