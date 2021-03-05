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
import entities.Producto;
import entities.Usuario;
import logic.LogicController;

@WebServlet("/AbmcProductoServlet")
public class AbmcProductoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private LogicController logicController = new LogicController();
	private String redireccion = "";
	
	public AbmcProductoServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Valida sesion y rol del usuario, valida ingreso por url, valida conexion a bd en cada operacion, en alta valida que se seleccione categoria, tipos de datos numericos y que no 
	 *  exista producto con la descripcion ingresada, en modificacion valida tipos de datos numericos.
	 *  En caso de error, se consultan categorias para mostrar en select de agregarProducto.jsp / editarProducto.jsp.
	 * */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
				
		if(usuario!=null && usuario.getRol().equals("admin")) {
			
			String operacion = request.getParameter("hiddenOperacion");
			if(operacion != null) {
				
				int id;
				String descripcion;
				int idCategoria;
				double precio;
				int stock;
				String linkFoto;
				Producto producto = null;
				Categoria categoria;
				
				int contadorNumberException = 0;
				
				switch (operacion) {
					case "alta":
						
						idCategoria	= Integer.parseInt(request.getParameter("selectIdCategoria")); //Viene como entero desde la bd.
						
						if(idCategoria != 0) {
							
							try {
								descripcion	= request.getParameter("textDescripcion").trim();
								precio	   	= Double.parseDouble(request.getParameter("textPrecio").trim());
								contadorNumberException++;
								stock 		= Integer.parseInt(request.getParameter("textStock").trim());
								linkFoto 	= request.getParameter("hiddenLinkFoto").trim();
								
								if(stock >= 0) {
									categoria = new Categoria(idCategoria);
									producto  = new Producto(descripcion,linkFoto,categoria,precio,stock);
									
									logicController.create(producto);
									
									recargarProductos(request,usuario);
									
								} else {
									setearError(request,"WEB-INF/agregarProducto.jsp","stockNegativo",true);
									setearCategorias(request);
								}
								
							} catch(NumberFormatException ex) {
								
								if(contadorNumberException == 0) {
									setearError(request,"WEB-INF/agregarProducto.jsp","NumberFormatExceptionPrecio",true);
								} else if(contadorNumberException == 1) {
									setearError(request,"WEB-INF/agregarProducto.jsp","NumberFormatExceptionStock",true);
								}
								setearCategorias(request);
								
							} catch(SQLIntegrityConstraintViolationException ex) {
								setearError(request,"WEB-INF/agregarProducto.jsp","descripcionYaExiste",true);
								setearCategorias(request);
								
							} catch(SQLException ex) {
								setearError(request,"WEB-INF/agregarProducto.jsp","errorEnBD",true);
								//No puedo setear categorias xq la db esta caida
							}
							
						} else {
							setearError(request,"WEB-INF/agregarProducto.jsp","categoriaNoSeleccionada",true);
							setearCategorias(request);
						}
						
						break;
					case "modificacion":
						
						try {
							id			= Integer.parseInt(request.getParameter("hiddenId"));
							descripcion = request.getParameter("textDescripcion").trim();
							idCategoria	= Integer.parseInt(request.getParameter("selectIdCategoria"));
							precio		= Double.parseDouble(request.getParameter("textPrecio").trim());
							contadorNumberException++;
							stock		= Integer.parseInt(request.getParameter("textStock").trim());
							linkFoto	= request.getParameter("hiddenLinkFoto").trim();
							
							if(stock >= 0) {
								categoria = new Categoria(idCategoria);
								producto  = new Producto(id,descripcion,linkFoto,categoria,precio,stock);
								
								logicController.update(producto);
								
								recargarProductos(request,usuario);
								
							} else {
								setearError(request,"WEB-INF/editarProducto.jsp","stockNegativo",true);
								setearCategorias(request);
							}
							
						} catch(NumberFormatException ex) {
							
							if(contadorNumberException == 0) {
								setearError(request,"WEB-INF/editarProducto.jsp","NumberFormatExceptionPrecio",true);
							} else if(contadorNumberException == 1) {
								setearError(request,"WEB-INF/editarProducto.jsp","NumberFormatExceptionStock",true);
							}
							setearCategorias(request); 
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/editarProducto.jsp","errorEnBD",true);
							//No puedo setear categorias xq la db esta caida
						}
						
						break;
					case "baja":
						
						id = Integer.parseInt(request.getParameter("hiddenId"));
						producto = new Producto(id);
						
						try {
							logicController.delete(producto);
							
							recargarProductos(request,usuario);
							
						} catch(SQLIntegrityConstraintViolationException ex) {
							setearError(request,"abmcProductoServlet?hiddenOperacion=consulta","existenProductosEnCompraConEsteProducto",false);
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",false);
							
						}
						
						break;
					case "consulta":
						
						recargarProductos(request,usuario);
						
						break;
					case "darDeAlta":
						
						id = Integer.parseInt(request.getParameter("hiddenId"));
						producto = new Producto(id,true);
						
						try {
							logicController.darDeAltaProducto(producto);
							recargarProductos(request,usuario);
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",false);
							
						}
						
						break;
					case "darDeBaja":
						
						id = Integer.parseInt(request.getParameter("hiddenId"));
						producto = new Producto(id,false);
						
						try {
							logicController.darDeBajaProducto(producto);
							recargarProductos(request,usuario);
							
						} catch(SQLException ex) {
							setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",false);
							
						}	
						
						break;
					default:
						break;
				}
			} else {
				redireccion = "WEB-INF/panelAdmin.jsp"; //Por si admin entra por la url
			}
		} else {
			redireccion = "ConsultaProductosServlet"; //(index.jsp) Si no tiene sesion o ingreso como cliente.
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
	}
	
	/** Consulta productos. Redirijo al panel de admin e indico que se cargue el include de productos. */
	private HttpServletRequest recargarProductos(HttpServletRequest request, Usuario usuario) {
		
		try {
			LinkedList<Producto> productos = logicController.getAllProductos(usuario);
			request.setAttribute("productos",productos);
			
			redireccion = "WEB-INF/panelAdmin.jsp";
			request.setAttribute("recarga","recargaProductos");
			
		} catch(SQLException ex) {
			setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",false);
		}
		
		return request;
	}
	
	/** Redirige al jsp que corresponda. Setea el mensaje de error que llega. Evalua si se devuelve lo que ingreso el usuario. */
	private void setearError(HttpServletRequest request, String redireccion, String mensajeError, boolean devolverIngresos) {
		
		this.redireccion = redireccion;
		
		if(redireccion.equals("WEB-INF/panelAdmin.jsp")) {
			request.setAttribute("recarga","recargaProductos"); //Para el include
		}
		
		request.setAttribute("error",mensajeError);
		
		if(devolverIngresos == true) {
			request.setAttribute("hiddenId"		    , request.getParameter("hiddenId"));
			request.setAttribute("textDescripcion"  , request.getParameter("textDescripcion"));
			request.setAttribute("selectIdCategoria", request.getParameter("selectIdCategoria"));
			request.setAttribute("textPrecio"       , request.getParameter("textPrecio"));
			request.setAttribute("textStock"	    , request.getParameter("textStock"));
			request.setAttribute("textLinkFoto"     , request.getParameter("hiddenLinkFoto"));
		}
	}
	
	/** Consulta categorias para usarlas en editarProducto.jsp. */
	private void setearCategorias(HttpServletRequest request) {
		
		try {
			LinkedList<Categoria> categorias = logicController.getAllCategorias();
			request.setAttribute("categorias",categorias);
			
		} catch(SQLException e) {
			setearError(request,"WEB-INF/panelAdmin.jsp","errorEnBD",true);
		}
	}
	
}