package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Categoria;
import entities.Usuario;
import logic.LogicController;

@WebServlet("/AgregarProductoServlet")
public class AgregarProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AgregarProductoServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Valida usuario. Consulta categorias para mostrar en select. Redirige a agregarProducto.jsp */
	/** Importante: si se ingresa desde agregarProducto.jsp se esta recargando la pagina y es para cargar la imagen desde el input file */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		String redireccion = "";
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		if(usuario!=null && usuario.getRol().equals("admin")) {
			
			try {
				LinkedList<Categoria> categorias = logicController.getAllCategorias();
				request.setAttribute("categorias",categorias);
				
				//Para devolver lo ingresado cuando cambio la foto (cuando ingreso por primera vez no se ejecuta)
				if(request.getParameter("linkFoto") != null) {
					recargarIngresosAlCargarFoto(request);
				}
				
				redireccion = "WEB-INF/agregarProducto.jsp";
				
			} catch(SQLException ex) {
				redireccion = "WEB-INF/panelAdmin.jsp";
				request.setAttribute("recarga","recargaProductos");
				request.setAttribute("error","errorEnBD");
			}
			
		} else {
			 //(index.jsp) Si no tiene sesion o ingreso como cliente.
			redireccion = "ConsultaProductosServlet";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
		
	}
	
	/** Para cuando se carga la imagen en agregarProducto.jsp (se recarga la pagina y le paso lo que se ingreso hasta ahora) */
	private void recargarIngresosAlCargarFoto(HttpServletRequest request) {
		request.setAttribute("descripcion", request.getParameter("descripcion"));
		request.setAttribute("idCategoria", request.getParameter("idCategoria"));
		request.setAttribute("precio"	  , request.getParameter("precio"));
		request.setAttribute("stock"	  , request.getParameter("stock"));
		request.setAttribute("linkFoto"	  , request.getParameter("linkFoto"));
	}
	
}
