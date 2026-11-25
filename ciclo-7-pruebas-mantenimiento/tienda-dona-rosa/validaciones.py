def validar_producto(nombre, precio, cantidad, cantidad_minima=0):
    errores = []

    nombre = (nombre or "").strip()
    if nombre == "":
        errores.append("El nombre del producto no puede estar vacío.")
    elif len(nombre) > 50:
        errores.append("El nombre no puede tener más de 50 caracteres.")

    try:
        if int(precio) <= 0:
            errores.append("El precio debe ser mayor que 0.")
    except (ValueError, TypeError):
        errores.append("El precio debe ser un número entero.")

    try:
        if int(cantidad) < cantidad_minima:
            errores.append(f"La cantidad debe ser {cantidad_minima} o más.")
    except (ValueError, TypeError):
        errores.append("La cantidad debe ser un número entero.")

    return errores