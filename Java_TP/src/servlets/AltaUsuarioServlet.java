package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.ProductoEnCompra;
import entities.Usuario;
import logic.LogicController;

@WebServlet("/RegistrarUsuarioServlet")
public class AltaUsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String redireccion = "";
	
    public AltaUsuarioServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	/** Valida que el usuario no tenga sesion, valida ingreso por url, valida que no exista el email ingresado, registra usuario (cliente), le da un carrito y una sesion, redirige a index.jsp */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		if(usuario == null) {
			
			if(request.getParameter("textNombre") != null) {
				
				String nombre   = request.getParameter("textNombre").trim();
				String apellido = request.getParameter("textApellido").trim();
				String email 	= request.getParameter("textEmail").trim();
				String password = request.getParameter("textPassword").trim();
				String rol 	 	= request.getParameter("hiddenRol");
				
				usuario = new Usuario(nombre,apellido,email,password,rol);
				
				try {
					usuario = logicController.create(usuario);
					
					LinkedList<ProductoEnCompra> carritoProductos = new LinkedList<ProductoEnCompra>();
					sesion.setAttribute("carritoProductos",carritoProductos);
					
					sesion.setAttribute("usuario",usuario);
					
					redireccion = "ConsultaProductosServlet";
					
				} catch(SQLIntegrityConstraintViolationException ex) {
					setearError(request,"emailYaRegistrado",true);
					
				} catch(SQLException ex) {
					setearError(request,"errorEnBD",true);
				}
				
			} else {
				setearError(request,"formularioIncompleto",false);
			}
			
		} else if(usuario.getRol().equals("admin")) {
			redireccion = "WEB-INF/panelAdmin.jsp";
			
		} else if(usuario.getRol().equals("cliente")) {
			redireccion = "ConsultaProductosServlet"; //(index.jsp)
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
	}
	
	/** Redirige a registrarUsuario.jsp. Setea el mensaje de error que llega. Evalua si se devuelve lo que ingreso el usuario. */
	private void setearError(HttpServletRequest request, String mensajeError, boolean cargarIngresos) {
		
		redireccion = "WEB-INF/registrarUsuario.jsp";
		request.setAttribute("error",mensajeError);
		
		if(cargarIngresos==true) {
			request.setAttribute("textNombre"  , request.getParameter("textNombre"));
			request.setAttribute("textApellido", request.getParameter("textApellido"));
			request.setAttribute("textEmail"   , request.getParameter("textEmail"));
			request.setAttribute("textPassword", request.getParameter("textPassword"));
		}
	}
	
}