<%-- 
    Document   : Resultado
    Created on : 6/11/2025, 4:45:34 p. m.
    Author     : Jeff Paladines
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Resultado - Nota Final</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
</head>
<body>
  <h2>Resultado de la Nota Final</h2>

  <table>
    <tr>
      <th>Estudiante</th>
      <th>Materia</th>
      <th>Corte 1 (35%)</th>
      <th>Corte 2 (35%)</th>
      <th>Corte 3 (30%)</th>
      <th>Definitiva</th>
    </tr>
    <tr>
      <td><%= request.getAttribute("estudiante") %></td>
      <td><%= request.getAttribute("materia") %></td>
      <td><%= request.getAttribute("corte1") %></td>
      <td><%= request.getAttribute("corte2") %></td>
      <td><%= request.getAttribute("corte3") %></td>
      <td><strong><%= String.format("%.2f", (Float)request.getAttribute("notaFinal")) %></strong></td>
    </tr>
  </table>

  <div class="resultado" style="margin-top:12px;">
    <%
      Boolean aprobo = (Boolean) request.getAttribute("aprobo");
      float nf = (Float) request.getAttribute("notaFinal");
      String est = (String) request.getAttribute("estudiante");
      String mat = (String) request.getAttribute("materia");
      if (aprobo != null && aprobo) {
    %>
      <p class="aprobado">El estudiante <strong><%= est %></strong> aprobó <strong><%= mat %></strong> con <strong><%= String.format("%.2f", nf) %></strong>.</p>
    <% } else { %>
      <p class="reprobado">El estudiante <strong><%= est %></strong> no aprobó <strong><%= mat %></strong> (nota <strong><%= String.format("%.2f", nf) %></strong>).</p>
    <% } %>
  </div>

  <p style="margin-top:12px;">
    <a href="<%=request.getContextPath()%>/ingresar.jsp">Volver al formulario</a>
  </p>
</body>
</html>