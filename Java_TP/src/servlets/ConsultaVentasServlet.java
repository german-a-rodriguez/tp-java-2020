package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Compra;
import entities.Usuario;
import logic.LogicController;

@WebServlet("/ConsultaVentasServlet")
public class ConsultaVentasServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public ConsultaVentasServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Valida sesion y rol del usuario. Consulta todas las compras realizadas por los usuarios. Redirige a ventasAdmin.jsp. */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		String redireccion = "";
		
		HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	
    	if(usuario!=null) {
    		if(usuario.getRol().equals("admin")) {
    			
    			try {
    				LinkedList<Compra> todasLasVentas = logicController.getAllCompras();
    				Collections.reverse(todasLasVentas); //Para que muestre primero las ?ltimas ventas
    				request.setAttribute("todasLasVentas",todasLasVentas);
    				
    			} catch(SQLException ex) {
    				request.setAttribute("error","errorEnBD");
    			}
    			
    			redireccion = "WEB-INF/ventasAdmin.jsp";
    			
    		} else if(usuario.getRol().equals("cliente")) {
    			redireccion = "ConsultaProductosServlet";
    		}
    	} else {
    		redireccion = "ConsultaProductosServlet";
    	}
    	
    	RequestDispatcher rd = request.getRequestDispatcher(redireccion);
    	rd.forward(request,response);
    	
	}
	
}
