package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet(name = "TipoSalaServlet", urlPatterns = "TipoSala")
public class TipoSalaServlet extends HttpServlet {
    @Inject
    TipoSalaBean tsBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        if (req.getParameter("nombre") != null) {
            TipoSala nuevo = new TipoSala();
            nuevo.setNombre(req.getParameter("nombre"));
            nuevo.setActivo(Boolean.valueOf(req.getParameter("activo")));
            nuevo.setComentarios(req.getParameter("comentarios"));
            nuevo.setExpresionRegular(req.getParameter("expresionRegular"));
            try {
                tsBean.create(nuevo);
                out.println("<html>");
                out.println("<body>");
                out.println("<h1>Tipo sala</h1>");
                out.println("<hr>");
                out.println("Tipo sala guardado correctamente" + nuevo.getIdTipoSala());
                out.println("</body>");
                out.println("</html>");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(500);
            }
        }else{
        resp.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        List<TipoSala> registros = tsBean.findRange(0, 100000000);
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Tipo sala</h1>");
        out.println("<hr>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Id</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Activo</th>");
        out.println("<th>Comentarios</th>");
        out.println("<th>Expresion Regular</th>");
        out.println("</tr>");
        StringWriter sw = new StringWriter();
        for (TipoSala registro : registros) {
            sw.append("<tr>")
                    .append("<td>").append(registro.getIdTipoSala().toString()).append("</td>")
                    .append("<td>").append(registro.getNombre()).append("</td>")
                    .append("<td>").append(registro.getActivo() ? "ACTIVO" : "INACTIVO").append("</td>")
                    .append("<td>").append(registro.getExpresionRegular()).append("</td>")
                    .append("<td>").append(registro.getComentarios() != null ? registro.getComentarios() : "").append("</td>")
                    .append("</tr>");
        }
        out.println(sw.toString());
        out.println("</table>");
        out.println("</hr>");
        out.println("</body>");
        out.println("</html>");
    }
}
