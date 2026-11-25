/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author enidsolenyguarinquintero
 */
@WebServlet(name = "CalcularNota", urlPatterns = {"/CalcularNota"})
public class CalcularNota extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Obtener los datos del formulario
            String nombreEstudiante = request.getParameter("estudiante");
            String nombreMateria = request.getParameter("materia");
            float notaCorte1 = Float.parseFloat(request.getParameter("corte1"));
            float notaCorte2 = Float.parseFloat(request.getParameter("corte2"));
            float notaCorte3 = Float.parseFloat(request.getParameter("corte3"));
            // Calcular nota final
            float notaFinal = (notaCorte1 * 0.35f) + (notaCorte2 * 0.35f) + (notaCorte3 * 0.30f);

           // Determinar si aprobó o no
            boolean aprobo = notaFinal >= 3.0f;
           
            // Generar la respuesta HTML
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Resultado - Nota Final</title>");
            out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/styles.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Resultado de la Nota Final 🧮</h2>");
            
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Estudiante</th>");
            out.println("<th>Materia</th>");
            out.println("<th>Corte 1 (35%)</th>");
            out.println("<th>Corte 2 (35%)</th>");
            out.println("<th>Corte 3 (30%)</th>");
            out.println("<th>Definitiva</th>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td>" + nombreEstudiante + "</td>");
            out.println("<td>" + nombreMateria + "</td>");
            out.println("<td>" + notaCorte1 + "</td>");
            out.println("<td>" + notaCorte2 + "</td>");
            out.println("<td>" + notaCorte3 + "</td>");
            out.println("<td><strong>" + String.format("%.2f", notaFinal) + "</strong></td>");
            out.println("</tr>");
            out.println("</table>");
            
            // Mensaje de mensaje (aprobado o no aprobado)
            out.println("<div class='resultado'>");
            if (aprobo) {
                out.println("<p class='aprobado'>✅ El estudiante " 
                            + nombreEstudiante + " aprobó la materia " + nombreMateria 
                            + " con una nota definitiva de " + String.format("%.2f", notaFinal) + ".</p>");
            } else {
                out.println("<p class='reprobado'>❌ El estudiante " 
                            + nombreEstudiante + " no aprobó la materia " + nombreMateria 
                            + " (nota definitiva: " + String.format("%.2f", notaFinal) + ").</p>");
            }
            out.println("</div>");

            
            out.println("<br><a href='index.html'>🔙 Volver al formulario</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
