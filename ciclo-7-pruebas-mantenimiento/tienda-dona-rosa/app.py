from flask import Flask, render_template, request, redirect, url_for, abort
import database
from validaciones import validar_producto

app = Flask(__name__)

@app.template_filter("pesos")
def formato_pesos(valor):
    return "${:,}".format(valor).replace(",", ".")

database.crear_tabla()
database.cargar_datos_iniciales()


def mostrar_inicio(errores=None):
    critico = database.producto_mas_cerca_de_acabarse()
    por_acabarse = (
        critico is not None
        and critico["cantidad"] <= 0.10 * critico["cantidad_inicial"]
    )
    return render_template(
        "index.html",
        productos=database.obtener_productos(),
        critico=critico,
        por_acabarse=por_acabarse,
        total=database.costo_total_inventario(),
        errores=errores,
    )


@app.route("/")
def inicio():
    return mostrar_inicio()


@app.route("/agregar", methods=["POST"])
def agregar():
    nombre = request.form.get("nombre", "")
    precio = request.form.get("precio", "")
    cantidad = request.form.get("cantidad", "")

    errores = validar_producto(nombre, precio, cantidad, cantidad_minima=1)
    if not errores and database.existe_producto(nombre):
        errores.append(
            "Ese producto ya está en el listado. Usa «Editar» para cambiar su cantidad."
        )
    if errores:
        return mostrar_inicio(errores)
    
    database.insertar_producto(nombre.strip(), int(precio), int(cantidad))
    return redirect(url_for("inicio"))


@app.route("/editar/<int:codigo>", methods=["GET", "POST"])
def editar(codigo):
    producto = database.obtener_producto(codigo)
    if producto is None:
        abort(404)

    if request.method == "POST":
        nombre = request.form.get("nombre", "")
        precio = request.form.get("precio", "")
        cantidad = request.form.get("cantidad", "")

        errores = validar_producto(nombre, precio, cantidad)
        if not errores and database.existe_producto(nombre, excepto_codigo=codigo):
            errores.append("Ya existe otro producto con ese nombre.")
        if errores:
            return render_template("editar.html", producto=producto, errores=errores)


        database.actualizar_producto(codigo, nombre.strip(), int(precio), int(cantidad))
        return redirect(url_for("inicio"))

    return render_template("editar.html", producto=producto)


@app.route("/borrar/<int:codigo>", methods=["POST"])
def borrar(codigo):
    if database.obtener_producto(codigo) is None:
        abort(404)

    database.borrar_producto(codigo)
    return redirect(url_for("inicio"))


if __name__ == "__main__":
    app.run(debug=True)

