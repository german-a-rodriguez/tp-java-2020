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
import entities.Producto;
import entities.Usuario;
import logic.LogicController;

@WebServlet("/EditarProducto")
public class EditarProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EditarProductoServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	/** Valida usuario. Consulta producto a editar (lo busca por su id). Consulta categorias para mostrar en select. Redirige a editarProducto.jsp */
	/** Importante: si se ingresa desde editarProducto.jsp se esta recargando la pagina y es para cargar la imagen desde el input file */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		String redireccion = "";
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		if(usuario!=null && usuario.getRol().equals("admin")) {
			
			int id = Integer.parseInt(request.getParameter("hiddenId")); //Viene como entero desde la bd.
			Producto producto = new Producto(id);
			
			try {
				//El producto viene desde la bd con su imagen actual.
				producto = logicController.getProductoById(producto);
				
				//Luego si se ingreso al servlet para editar la foto, le seteo la nueva foto (pisa la que viene de la bd) y el contenido de los inputs (los traje como parametros usando javascript).
				if(request.getParameter("linkFoto") != null) {
					recargarIngresosAlCargarFoto(request);
				}
				
				LinkedList<Categoria> categorias = logicController.getAllCategorias();
				
				request.setAttribute("producto", producto);
				request.setAttribute("categorias", categorias);
				
				redireccion = "WEB-INF/editarProducto.jsp";
				
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
	
	/** Para cuando se carga la imagen en editarProducto.jsp (se recarga la pagina y le paso lo que se ingreso hasta ahora) */
	private void recargarIngresosAlCargarFoto(HttpServletRequest request) {
		request.setAttribute("hiddenId"		    , request.getParameter("hiddenId"));
		request.setAttribute("textDescripcion"  , request.getParameter("descripcion"));
		request.setAttribute("selectIdCategoria", request.getParameter("idCategoria"));
		request.setAttribute("textPrecio"       , request.getParameter("precio"));
		request.setAttribute("textStock"	    , request.getParameter("stock"));
		request.setAttribute("textLinkFoto"     , request.getParameter("linkFoto"));
	}
	
}
