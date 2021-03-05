package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Compra;
import entities.Direccion;
import entities.Producto;
import entities.ProductoEnCompra;
import entities.Tarjeta;
import entities.Usuario;
import entities.Valuacion;
import logic.LogicController;

@WebServlet("/RegistrarCompraServlet")
public class RegistrarCompraServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	LogicController logicController = new LogicController();
    String redireccion = "";
    
    public RegistrarCompraServlet() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}
	
	/** Valida sesion y rol del usuario. Valida ingreso por formulario. Valida mes y anio ingresado. Valida fecha de vencimiento de la tarjeta. Valida stock de los productos.
	 *  Registra compra. Redirige a detalleCompra.jsp.
	 *  
	 *  Al registrar la compra se hace el alta de direccion, tarjeta(antes valida si ya existe), valuacion(en estado pendiente), compra, productos en compra. Tambien se actualiza
	 *  stock de los productos y se vacia el carrito. */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	
    	if(esCliente(usuario)) {
    		
    		if(request.getParameter("textProvincia") != null) {
    			
    			double hiddenImporteTotal = Double.parseDouble(request.getParameter("hiddenImporteTotal")); //Viene como double desde la bd.
    			
    			String textProvincia		= request.getParameter("textProvincia");
    			String textLocalidad		= request.getParameter("textLocalidad");
    			String textDireccion		= request.getParameter("textDireccion");
    			String textPiso				= request.getParameter("textPiso");
    			String textDepartamento		= request.getParameter("textDepartamento");
    			String textCodigoAreaTelef	= request.getParameter("textCodigoAreaTelef");
    			String textNroTelef			= request.getParameter("textNroTelef");
    			
    			String textDniTitularTarj	= request.getParameter("textDniTitularTarj");
    			String textNyATitularTarj	= request.getParameter("textNyATitularTarj");
    			String textNumeroTarj		= request.getParameter("textNumeroTarj");
    			String textEntidadEmisora	= request.getParameter("textEntidadEmisora");
    			String textCodigoSegTarj	= request.getParameter("textCodigoSegTarj");
    			
    			try {
        			int    textMesVtoTarj		= Integer.parseInt(request.getParameter("textMesVtoTarj"));
    				int    textAnioVtoTarj		= Integer.parseInt(request.getParameter("textAnioVtoTarj"));
    				
    				if(textMesVtoTarj > 0 && textMesVtoTarj < 13) { //Controlo que ingrese un mes valido
    					
    					SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
    					SimpleDateFormat sdfAnio = new SimpleDateFormat("yyyy");
    					Date fecha = new Date();
    					int mesActual = Integer.parseInt(sdfMes.format(fecha));
    					int anioActual = Integer.parseInt(sdfAnio.format(fecha));
    					
    					if(textAnioVtoTarj > anioActual-1 && textAnioVtoTarj < anioActual+10) { //Controlo que ingrese un anio dentro del rango valido
    						
    						if(fechaVencimientoEsMayorQueActual(textAnioVtoTarj,textMesVtoTarj,anioActual,mesActual)) {
    							
    							LinkedList<ProductoEnCompra> carritoProductos = (LinkedList<ProductoEnCompra>)sesion.getAttribute("carritoProductos");
    							
    							if(stocksSuficientes(request,carritoProductos)) {
    								if(sistemaTarjetaValidaCompra(textCodigoSegTarj)) {
    									
    									Direccion direccion = new Direccion(textProvincia, textLocalidad, textDireccion, textPiso, textDepartamento, textCodigoAreaTelef, textNroTelef);
    									direccion = logicController.create(direccion);
    									
    									//Intento traer tarjeta ingresada. Si no existe la doy de alta.
    									Tarjeta tarjeta = new Tarjeta(textNumeroTarj);
    									tarjeta = logicController.getTarjeta(tarjeta);
    									if(tarjeta==null) {
    										tarjeta = new Tarjeta(textNumeroTarj, textEntidadEmisora, textMesVtoTarj, textAnioVtoTarj, textDniTitularTarj, textNyATitularTarj);
    										tarjeta = logicController.create(tarjeta);
    									}
    									
    									Valuacion valuacion = new Valuacion("Pendiente");
    									
    									Compra compra = new Compra(usuario,direccion,tarjeta,valuacion,hiddenImporteTotal);
    									compra = logicController.create(compra);
    									
    									logicController.create(carritoProductos,compra);
    									logicController.updateStockProductos(carritoProductos);
    									
    									carritoProductos.clear();
    									
    									redireccion = "WEB-INF/detalleCompra.jsp";
    									
    								} else {
    									setError(request,"tarjetaRechazaCompra");
    								}
    							}
    						} else {
    							setError(request,"mesDebeSerMayorAlActual");
    						}
    					} else {
    	    				setError(request,"anioIncorrecto");
    	    				request.setAttribute("anioActual",anioActual);
    					}
    				} else {
    					setError(request,"mesIncorrecto");
    				}
    			} catch(NumberFormatException ex) {
    				setError(request,"NumberFormatExceptionMesAnio");
    				
    			} catch(SQLException ex) {
    				setError(request,"errorEnDB");
    				
    			}
    		} else {
    			redireccion = "WEB-INF/realizarCompra.jsp";
    		}
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
	
	private boolean sistemaTarjetaValidaCompra(String textCodigoSegTarj) {
		return true;
	}
	
	/** Devuelve true si la fecha de vencimiento es mayor que la actual y false en caso contrario. */
	private boolean fechaVencimientoEsMayorQueActual(int textAnioVtoTarj, int textMesVtoTarj, int anioActual, int mesActual) {
		boolean bandera = true;
		if(textAnioVtoTarj == anioActual) {
			if(textMesVtoTarj <= mesActual) {
				bandera = false;
			}
		}
		return bandera;
	}
	
	/** Antes de dar de alta la compra revisa en la bd si hay stock suficiente de cada producto. */
	private boolean stocksSuficientes(HttpServletRequest request, LinkedList<ProductoEnCompra> carritoProductos) throws SQLException {
		
		boolean stocksSuficientes = true;
		for(ProductoEnCompra pec: carritoProductos) {
			
			Producto p = logicController.getProductoById(pec.getProducto());
			
			if(pec.getCantidad() > p.getStock()) {
				request.setAttribute("error","stockModificadoMientrasClienteRealizabaLaCompra");
				request.setAttribute("productoErrorStock", p);
				pec.setCantidad(1); //Si no hay stock suficiente de un producto le seteo la cantidad por defecto en 1 nuevamente para que vuelva a elegir cantidad.
				redireccion = "WEB-INF/carrito.jsp";
				stocksSuficientes = false;
				break;
			}
		}
		return stocksSuficientes;
	}
	
	/** Redirige a realizarCompra.jsp. Setea el mensaje de error que llega. Evalua si se devuelve lo que ingreso el usuario. */
	private void setError(HttpServletRequest request, String mensajeError) {
		request.setAttribute("error",mensajeError);
		redireccion = "WEB-INF/realizarCompra.jsp";
		cargarIngresos(request);
	}
	
	/** Setea en request los ingresos del usuario para volver a cargarlos en inputs. */
	private void cargarIngresos(HttpServletRequest request) {
		request.setAttribute("textProvincia", 		request.getParameter("textProvincia"));
		request.setAttribute("textLocalidad", 		request.getParameter("textLocalidad"));
		request.setAttribute("textDireccion", 		request.getParameter("textDireccion"));
		request.setAttribute("textPiso",			request.getParameter("textPiso"));
		request.setAttribute("textDepartamento", 	request.getParameter("textDepartamento"));
		request.setAttribute("textCodigoAreaTelef", request.getParameter("textCodigoAreaTelef"));
		request.setAttribute("textNroTelef", 		request.getParameter("textNroTelef"));
		
		request.setAttribute("textDniTitularTarj", 	request.getParameter("textDniTitularTarj"));
		request.setAttribute("textNyATitularTarj", 	request.getParameter("textNyATitularTarj"));
		request.setAttribute("textNumeroTarj", 		request.getParameter("textNumeroTarj"));
		request.setAttribute("textEntidadEmisora", 	request.getParameter("textEntidadEmisora"));
		request.setAttribute("textMesVtoTarj", 		request.getParameter("textMesVtoTarj"));
		request.setAttribute("textAnioVtoTarj", 	request.getParameter("textAnioVtoTarj"));
		request.setAttribute("textCodigoSegTarj", 	request.getParameter("textCodigoSegTarj"));
	}
	
}
