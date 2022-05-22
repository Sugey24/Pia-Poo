package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Productos;
import modelo.ProductosDAO;

/**
 *
 * @author sugey
 */
@WebServlet(name = "ProductosController", urlPatterns = {"/ProductosController"})
public class ProductosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         ProductosDAO productosDAO = new ProductosDAO();
         String accion;
         RequestDispatcher dispatcher = null;
         
         accion = request.getParameter("accion");
         
         if(accion == null || accion.isEmpty()){
           dispatcher = request.getRequestDispatcher("Productos/index.jsp");
           List<Productos> listaProductos = productosDAO.listarProductos();
           request.setAttribute("lista", listaProductos);
         } else if("nuevo".equals(accion)){
            dispatcher = request.getRequestDispatcher("Productos/nuevo.jsp");
         } else if("insert".equals(accion)){
             String codigo = request.getParameter("codigo");
             String nombre = request.getParameter("nombre");
             Double precio = Double.parseDouble(request.getParameter("precio"));
             int existencia = Integer.parseInt(request.getParameter("existencia"));
             
             Productos productos = new Productos(0, codigo, nombre, precio, existencia);
             productosDAO.insertar(productos);
            dispatcher = request.getRequestDispatcher("Productos/index.jsp");
            List<Productos> listaProductos = productosDAO.listarProductos();
            request.setAttribute("lista", listaProductos);
         }
         
         dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
