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

@WebServlet("/ConsultaComprasServlet")
public class ConsultaComprasServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    String redireccion = "";
    
    public ConsultaComprasServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Valida sesion y rol del usuario. Consulta compras de un usuario. Consulta cantidad de valuaciones pendientes. Redirige a comprasCliente.jsp. */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		
		HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	
    	if(usuario!=null) {
    		if(usuario.getRol().equals("cliente")) {
    			
    			try {
        			LinkedList<Compra> comprasUnUsuario = logicController.getAllComprasUnUsuario(usuario);
        			Collections.reverse(comprasUnUsuario); //Para que muestre primero las últimas compras
        			request.setAttribute("comprasUnUsuario", comprasUnUsuario);
        			
        			int cantidadValuacionesPendientes = logicController.getCantidadValuacionesPendientes(usuario);
    				request.setAttribute("cantidadValuacionesPendientes", String.valueOf(cantidadValuacionesPendientes));
    				
        		} catch(SQLException ex) {
        			request.setAttribute("error","errorEnBD");
        		}
    			
    			redireccion = "WEB-INF/comprasCliente.jsp";
    		
    		} else if(usuario.getRol().equals("admin")) {
    			redireccion = "WEB-INF/panelAdmin.jsp";
    		}
    	} else {
    		redireccion = "ConsultaProductosServlet";
    	}
    	
    	RequestDispatcher rd = request.getRequestDispatcher(redireccion);
    	rd.forward(request,response);
    	
	}

}