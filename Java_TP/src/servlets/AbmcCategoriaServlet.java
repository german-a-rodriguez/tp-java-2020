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

import entities.Categoria;
import entities.Usuario;
import logic.LogicController;

@WebServlet("/AbmcCategoriaServlet")
public class AbmcCategoriaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private LogicController logicController = new LogicController();
    private String redireccion = "";
    
	public AbmcCategoriaServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	/** Valida sesion y rol del usuario, valida ingreso por url, valida conexion a bd en cada operacion, en alta y modificacion valida que no exista categoria, en baja valida que no 
	 *  existan productos en la categoria.
	 * */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		if (usuario!=null && usuario.getRol().equals("admin")) {
			
			String operacion = request.getParameter("hiddenOperacion");
			if(operacion != null) {
				
				Categoria categoria;
				String nombre;
				int id;
				
				switch (operacion) {
					case "alta":
						
						nombre = request.getParameter("textNombre").trim();
						categoria = new Categoria(nombre);
						
						try {
							logicController.create(categoria);
							recargarCategorias(request);
							
						} catch(SQLIntegrityConstraintViolationException ex) {
							setearError(request,"WEB-INF/agregarCategoria.jsp","categoriaYaExiste",true);
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/agregarCategoria.jsp","errorEnBD",true);
						}
						
						break;
					case "modificacion":
						
						id     = Integer.parseInt(request.getParameter("hiddenId")); //Viene como entero desde la bd.
						nombre = request.getParameter("textNombre").trim();
						categoria = new Categoria(id,nombre);
						
						try {
							logicController.update(categoria);
							recargarCategorias(request);
							
						} catch(SQLIntegrityConstraintViolationException ex) {
							setearError(request,"WEB-INF/editarCategoria.jsp","categoriaYaExiste",true);
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/editarCategoria.jsp","errorEnBD",true);
						}
						
						break;
					case "baja":
						
						id = Integer.parseInt(request.getParameter("hiddenId"));
						categoria = new Categoria(id);
						
						try {
							logicController.delete(categoria);
							recargarCategorias(request);
							
						} catch(SQLIntegrityConstraintViolationException ex) {
							recargarCategorias(request);
							setearError(request,"WEB-INF/panelAdmin.jsp","existenProductosConEstaCategoria",false);
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",false);
						}
						
						break;
					case "consulta":
						
						recargarCategorias(request);
						
						break;
					default:
						break;
				}
			} else {
				redireccion = "WEB-INF/panelAdmin.jsp"; //Por si admin ingresa por url
			}
		} else {
			redireccion = "ConsultaProductosServlet"; //(index.jsp) Si no tiene sesion o ingreso como cliente.
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
		
	}
	
	/** Consulta cateogorias. Redirijo al panel de admin e indico que se cargue el include de categorias. */
	private void recargarCategorias(HttpServletRequest request) {
		
		try {
			LinkedList<Categoria> categorias = logicController.getAllCategorias();
			request.setAttribute("categorias",categorias);
			
			redireccion = "WEB-INF/panelAdmin.jsp";
			request.setAttribute("recarga","recargaCategorias");
			
		} catch(SQLException ex) {
			setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",false);
		}
	}
	
	/** Redirige al jsp que corresponda. Setea el mensaje de error que llega. Evalua si se devuelve lo que ingreso el usuario. */
	private void setearError(HttpServletRequest request, String redireccion, String mensajeError, boolean devolverIngresos) {
		
		this.redireccion = redireccion;
		if(redireccion.equals("WEB-INF/panelAdmin.jsp")) {
			request.setAttribute("recarga","recargaCategorias"); //Para include
		}
		request.setAttribute("error",mensajeError);
		if(devolverIngresos == true) {
			request.setAttribute("hiddenId", request.getParameter("hiddenId"));/*-*/
			request.setAttribute("textNombre", request.getParameter("textNombre"));
		}
	}
	
}
