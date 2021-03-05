package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Compra;
import entities.Usuario;
import entities.Valuacion;
import logic.LogicController;

@WebServlet("/ValuarServlet")
public class ValuarServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    String redireccion = "";
	
    public ValuarServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	/** Valida sesion y rol del usuario. Valida ingreso por url. Valida si el formulario esta completo. Registra valuacion(actualiza tabla compra). Setea valuacion existosa y redirige a
	 *  realizarValuacion.jsp para mostrar mensaje de exito. */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LogicController logicController = new LogicController();
		
		HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	
    	if(esCliente(usuario)) {
    		
    		if( request.getParameter("hiddenIdCompra") != null ) {
    			
    			if(request.getParameter("radioPuntaje") != null) {
        			
        			int     hiddenIdCompra	= Integer.parseInt(request.getParameter("hiddenIdCompra"));
            		int 	radioPuntaje 	= Integer.parseInt(request.getParameter("radioPuntaje"));
            		String 	textComentario	= request.getParameter("textComentario");
            		
            		try {
            			Valuacion valuacion = new Valuacion(radioPuntaje,textComentario);
            			Compra compra = new Compra(hiddenIdCompra, valuacion);
            			logicController.registrarValuacion(compra);
            			
            			request.setAttribute("valuacion","valuacionExitosa");
            			
            		} catch(SQLException ex) {
            			request.setAttribute("error","errorEnBD");
            		}
            		
        		} else {
        			request.setAttribute("error","formularioIncompletoShowForm");
        			request.setAttribute("hiddenIdCompra",request.getParameter("hiddenIdCompra"));
        			request.setAttribute("textComentario",(request.getParameter("textComentario")==null) ? "" : request.getParameter("textComentario"));
        		}
    			
    		} else {
    			request.setAttribute("error","formularioIncompletoHideForm");
    		}
    		
    		redireccion = "WEB-INF/realizarValuacion.jsp";
    	}
    	
    	RequestDispatcher rd = request.getRequestDispatcher(redireccion);
    	rd.forward(request,response);
    	
	}
	
	/** Valida sesion y rol del usuario. */
	private boolean esCliente(Usuario usuario) {
	   	boolean esCliente = false;
	   	if(usuario!=null) {
	   		if(usuario.getRol().equals("cliente")) {
	   			
	   			esCliente = true;
	   			
	   		} else {
	   			redireccion = "WEB-INF/panelAdmin.jsp";
	   		}
	   	} else {
	   		redireccion = "ConsultaProductosServlet";
	   	}
	   	return esCliente;
	}
	
}
