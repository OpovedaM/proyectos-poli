from validaciones import validar_producto


def test_producto_valido_no_tiene_errores():
    assert validar_producto("Mangos", "2500", "20") == []


def test_nombre_vacio_da_error():
    errores = validar_producto("", "100", "5")
    assert "El nombre del producto no puede estar vacío." in errores


def test_nombre_solo_con_espacios_da_error():
    errores = validar_producto("   ", "100", "5")
    assert "El nombre del producto no puede estar vacío." in errores


def test_nombre_muy_largo_da_error():
    errores = validar_producto("a" * 51, "100", "5")
    assert len(errores) == 1


def test_precio_cero_da_error():
    errores = validar_producto("Mangos", "0", "5")
    assert "El precio debe ser mayor que 0." in errores


def test_precio_negativo_da_error():
    errores = validar_producto("Mangos", "-100", "5")
    assert "El precio debe ser mayor que 0." in errores


def test_precio_con_letras_da_error():
    errores = validar_producto("Mangos", "abc", "5")
    assert "El precio debe ser un número entero." in errores


def test_precio_con_decimales_da_error():
    errores = validar_producto("Mangos", "2.5", "5")
    assert "El precio debe ser un número entero." in errores


def test_cantidad_negativa_da_error():
    errores = validar_producto("Mangos", "100", "-1")
    assert len(errores) == 1


def test_cantidad_con_letras_da_error():
    errores = validar_producto("Mangos", "100", "abc")
    assert "La cantidad debe ser un número entero." in errores


def test_cantidad_cero_se_permite_al_actualizar():
    assert validar_producto("Mangos", "100", "0") == []


def test_cantidad_cero_no_se_permite_al_insertar():
    errores = validar_producto("Mangos", "100", "0", cantidad_minima=1)
    assert errores == ["La cantidad debe ser 1 o más."]


def test_varios_errores_a_la_vez():
    errores = validar_producto("", "0", "abc")
    assert len(errores) == 3