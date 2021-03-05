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
import logic.LogicController;
import entities.Producto;
import entities.ProductoEnCompra;

@WebServlet("/CarritoServlet")
public class CarritoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    String redireccion = "";
	
    public CarritoServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Valida sesion y rol del usuario. Valida ingreso por url. Evalua si hay que agregar o eliminar el producto. Redirije adonde corresponda (carrito.jsp / index.jsp) */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		if(usuario!=null) {
			
			if(usuario.getRol().equals("cliente")) {
				
				String operacion = request.getParameter("hiddenOperacion");
				if(operacion!=null) {
					
					//Evalua a que pagina tiene que volver al salir. Esto pasa porque se puede agregar y eliminar productos del carrito en dos paginas (index.jsp y carrito.jsp)
					String volverACarrito = request.getParameter("volverACarrito"); //Si se ingreso desde carrito.jsp viene en true
					if(volverACarrito !=null && volverACarrito.equals("true")) {
						redireccion = "WEB-INF/carrito.jsp";
					} else {
						redireccion = "ConsultaProductosServlet"; //index.jsp
					}
					
					int id = Integer.parseInt(request.getParameter("hiddenId"));
					LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)sesion.getAttribute("carritoProductos");
					
					if(operacion.equals("agregar")) {
						
						try {
							Producto producto = new Producto(id);
							producto = logicController.getProductoById(producto);
							
							ProductoEnCompra productoEnCompra = new ProductoEnCompra(producto,1); //1 es la cantidad inicial por defecto
							carritoProductos.add(productoEnCompra);
							
						} catch(SQLException ex) {
							setearError("ConsultaProductosServlet","errorEnBD",request);
						}
						
					} else if (operacion.equals("eliminar")) {
						
						ProductoEnCompra productoEnCompraAEliminar = null;
						for(ProductoEnCompra p: carritoProductos) { 
							if (p.getProducto().getId() == id) {
								productoEnCompraAEliminar = p;
								break;
							}
						}
						carritoProductos.remove(productoEnCompraAEliminar);
						
					}
				} else {
					setearError("ConsultaProductosServlet","formularioIncompleto",request);
				}
			} else {
				redireccion = "WEB-INF/panelAdmin.jsp";
			}
		} else {
			setearError("ConsultaProductosServlet","inicieSesionParaComprar",request);
		}
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
	}
	
	/** Redirige al jsp que corresponda. Setea el mensaje de error que llega. */
	private void setearError(String redireccion, String mensajeError, HttpServletRequest request) {
		
		this.redireccion = redireccion;
		request.setAttribute("error",mensajeError);
	}
	
}
