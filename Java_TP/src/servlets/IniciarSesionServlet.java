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

import entities.Usuario;
import entities.ProductoEnCompra;
import logic.LogicController;

@WebServlet("/IniciarSesionServlet")
public class IniciarSesionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String redireccion = "";
	
    public IniciarSesionServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	/** Valida que el usuario no tenga sesión, valida ingreso por url, valida usuario para iniciar sesión, le da una sesion, si el usuario es cliente se le asigna un carrito.
	 *  Redirige a jsp de bienvenida dependiendo rol del usuario.
	 *  */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		if(usuario==null) {
			
			if(allFieldsComplete(request)) {
				
				String email    = request.getParameter("textEmail");
				String password = request.getParameter("textPassword");
				
				usuario = new Usuario(email,password);
				
				try {
					usuario = logicController.validar(usuario);
					
					if (usuario != null) { //Cuenta ingresada es valida
						
						sesion.setAttribute("usuario",usuario);
						
						if(usuario.getRol().equals("cliente")) {
							
							LinkedList<ProductoEnCompra> carritoProductos = new LinkedList<ProductoEnCompra>();
							sesion.setAttribute("carritoProductos",carritoProductos);
							
							redireccion = "ConsultaProductosServlet?primerIngreso=true";
							
						} else if(usuario.getRol().equals("admin")) {
							
							redireccion = "abmcProductoServlet?hiddenOperacion=consulta";
							
						}
						
					} else { //Cuenta ingresada no es valida
						setError(request,"cuentaNoValida",true);
					}
				} catch (SQLException ex) {
					setError(request,"errorEnBD",true);
				}
			} else {
				setError(request,"formularioIncompleto",false);
			}
			
		} else if(usuario.getRol().equals("admin")) {
			redireccion = "WEB-INF/panelAdmin.jsp";
		
		} else if(usuario.getRol().equals("cliente")) {
			redireccion = "ConsultaProductosServlet";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
	}
	
	/** Valida que todos los campos ingresados esten completos. Cuando se ingresa por la url vienen en null. */
	private boolean allFieldsComplete(HttpServletRequest request) {
		if(request.getParameter("textEmail") != null && request.getParameter("textPassword") != null) {
			return true;
		} else {
			request.setAttribute("textEmail"   ,"");
			request.setAttribute("textPassword","");
			return false;
		}
	}
	
	/** Redirige a iniciarSesion.jsp. Setea el mensaje de error que llega. Evalua si se devuelve lo que ingreso el usuario. */
	private void setError(HttpServletRequest request, String mensajeError, boolean cargarIngresos) {
		
		redireccion = "WEB-INF/iniciarSesion.jsp";
		request.setAttribute("error",mensajeError);
		if(cargarIngresos == true) {
			request.setAttribute("textEmail"   ,request.getParameter("textEmail"));
			request.setAttribute("textPassword",request.getParameter("textPassword"));
		}
	}
	
}