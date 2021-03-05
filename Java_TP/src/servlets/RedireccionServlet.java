package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RedireccionServlet")
public class RedireccionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public RedireccionServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Sirve para ir de un jsp a otro. Redirige al jsp que llega por parametro. */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String redireccion = request.getParameter("destino");
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/" + redireccion);
    	rd.forward(request,response);
		
	}
	
}