<%-- 
    Document   : ingresar
    Created on : 6/11/2025, 4:44:04 p. m.
    Author     : Jeff Paladines
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Calcular Nota</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body>
  <h2>Calcular nota definitiva</h2>

  <form action="<%=request.getContextPath()%>/CalcularNota" method="post">
    <label>Estudiante:
      <input type="text" name="estudiante" required>
    </label><br>

    <label>Materia:
      <input type="text" name="materia" required>
    </label><br>

    <label>Corte 1 (35%):
      <input type="number" name="corte1" step="0.1" min="0" max="5" required>
    </label><br>

    <label>Corte 2 (35%):
      <input type="number" name="corte2" step="0.1" min="0" max="5" required>
    </label><br>

    <label>Corte 3 (30%):
      <input type="number" name="corte3" step="0.1" min="0" max="5" required>
    </label><br>

    <button type="submit">Calcular</button>
  </form>

  <p style="margin-top:12px;">
    <!-- Si quieres que esta JSP sea la de bienvenida -->
    <a href="<%=request.getContextPath()%>/ingresar.jsp">Recargar</a>
  </p>
</body>
</html>
