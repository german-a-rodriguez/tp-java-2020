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

@WebServlet("/ConsultaProductosServlet")
public class ConsultaProductosServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ConsultaProductosServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Este servlet sirve de ingreso a index.jsp. Consulta productos filtrando por categoria elegida. Consulta categorias. Si es el primer ingreso consulta 
	 *  cantidad de valuaciones pendientes. Redirige a index.jsp.
	 * */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		
		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		
		//Me fijo qué filtro tiene el listado de productos y de acuerdo al filtro busco lo que necesito en la bd.
		String filtroCategoria = request.getParameter("filtroCategoria");
		if(filtroCategoria==null) {filtroCategoria = (String)sesion.getAttribute("filtroCategoria");}
		
		LinkedList<Producto> productos;
		
		try {
			
			if(filtroCategoria==null) {
				productos = logicController.getAllProductos(usuario);
				filtroCategoria = "sinFiltro"; //Para primera vez que ingresa.
			} else {
				if(filtroCategoria.equals("sinFiltro")) {
					productos = logicController.getAllProductos(usuario);
				} else {
					Categoria categoria = new Categoria(Integer.parseInt(filtroCategoria));
					productos = logicController.getAllProductosFiltroCategoria(categoria);
				}
			}
			sesion.setAttribute("filtroCategoria",filtroCategoria); //Guardo filtro en la sesion para que se mantenga al cambiar de paginas.
			request.setAttribute("productos",productos);
			
			LinkedList<Categoria> categorias = logicController.getAllCategorias();
			request.setAttribute("categorias",categorias);
			
	    	//En primer ingreso, IniciarSesionServlet manda el parametro primerIngreso. En ese caso consulto la cantidad de valuaciones pendientes.
			if(request.getParameter("primerIngreso") != null) {
				
				int cantidadValuacionesPendientes = logicController.getCantidadValuacionesPendientes(usuario);
				if(cantidadValuacionesPendientes==0) {
					request.setAttribute("hayValuacionesPendientes","false");
				} else {
					request.setAttribute("hayValuacionesPendientes","true");
				}
			}
	    	
		} catch(SQLException ex) {
			request.setAttribute("error","errorEnBD");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);
	}
	
}