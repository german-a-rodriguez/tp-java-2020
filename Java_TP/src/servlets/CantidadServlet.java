package servlets;

import entities.ProductoEnCompra;
import entities.Usuario;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CantidadServlet")
public class CantidadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    String redireccion = "";
    
    public CantidadServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
    
    /** Valida usuario. Busca producto en carrito y actualiza la cantidad. Redirige a carrito.jsp. */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	
    	if(esCliente(usuario)) {
    		
    		String operacion = request.getParameter("hiddenOperacion");
    		if(operacion!=null) {
    			
    			int id = Integer.parseInt(request.getParameter("hiddenId"));
        		LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)sesion.getAttribute("carritoProductos");
    			
    			for(ProductoEnCompra pec : carritoProductos) {
    				if(pec.getProducto().getId() == id) {
    					
    					int cantidadActual = pec.getCantidad();
    					
    					if(operacion.equals("cantidadMenosUno")) {
    	    				
    						if(1 < cantidadActual) {
    							cantidadActual--;
    							pec.setCantidad(cantidadActual);
    	    				}
    						
    					} else if(operacion.equals("cantidadMasUno")) {
    	    				
    	    				if(cantidadActual < pec.getProducto().getStock()) {
    	    					cantidadActual++;
    	    					pec.setCantidad(cantidadActual);
    	    				} else {
    	    					request.setAttribute("error","stockMaximoAlcanzado");
    	    				}
    	    			}
    					
        				break;
    				}
    			}
    		}
    		redireccion = "WEB-INF/carrito.jsp";
    	}
    	
    	RequestDispatcher rd = request.getRequestDispatcher(redireccion);
    	rd.forward(request,response);
    }
    
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
