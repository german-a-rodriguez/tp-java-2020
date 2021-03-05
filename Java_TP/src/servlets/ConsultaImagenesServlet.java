package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Usuario;
import logic.LogicController;

@WebServlet("/ConsultaImagenesServlet")
public class ConsultaImagenesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public ConsultaImagenesServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	LogicController logicController = new LogicController();
		String redireccion = "";
		
    	if(usuario!=null && usuario.getRol().equals("admin")) {
    		
    		String[] listadoImagenes = logicController.getListadoImagenes();
    		request.setAttribute("listadoImagenes",listadoImagenes);
    		
    		//Mensaje que viene de SubirImagenAlServidorServlet con el resultado de la operacion de subir/borrar imagenes.
    		if(request.getParameter("mensaje")!=null) {
    			request.setAttribute("mensaje",request.getParameter("mensaje"));
    		}
    		
    		redireccion = "WEB-INF/subirImagenAlServidor.jsp";
    		
    	} else {
    		redireccion = "ConsultaProductosServlet";
    	}
    	
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
		
	}
	
}
