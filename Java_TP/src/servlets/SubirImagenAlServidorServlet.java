package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entities.Usuario;

@WebServlet("/SubirImagenAlServidorServlet")
public class SubirImagenAlServidorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/** Localhost */
	String rutaDirectorio = "C:" + File.separator + "Users" 
								 + File.separator + "Usuario" 
								 + File.separator + "Desarrollo" 
								 + File.separator + "eclipse-workspace-utn-web" 
								 + File.separator + "Java_TP" 
								 + File.separator + "WebContent" 
								 + File.separator + "images" 
								 + File.separator + "productos";
	
	/** Jelastic (En jelastic: config > webapp > busco carpeta productos > click derecho > copiar ruta) */
//	String rutaDirectorio = File.separator + "opt" + 
//							File.separator + "tomcat" + 
//							File.separator + "webapps" + 
//							File.separator + "ROOT" + 
//							File.separator + "images" + 
//							File.separator + "productos";
	
    public SubirImagenAlServidorServlet() {super();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request, response);}
	
	/** Recibe accion y evalua si se va a subir o borrar archivos. Redirige a subirImagenAlServidor.jsp. */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
    	Usuario usuario = (Usuario)sesion.getAttribute("usuario");
    	String redireccion = "";
    	
    	if(usuario!=null && usuario.getRol().equals("admin")) {
    		
    		String accion = request.getParameter("accion");
    		
    		String mensajeResultadoOperacion = "";
    		if(accion != null) {
    			if (accion.equals("subir")) {
    				mensajeResultadoOperacion = subirImagenesAlServidor(request);
    				
    			} else if (accion.equals("borrar")) {
    				mensajeResultadoOperacion = borrarImagenesDelServidor(request);
    				
    			}
			}
			
    		redireccion = "ConsultaImagenesServlet?mensaje=" + mensajeResultadoOperacion;
			
    	} else {
    		redireccion = "ConsultaProductosServlet";
    	}
    	
		RequestDispatcher rd = request.getRequestDispatcher(redireccion);
		rd.forward(request,response);
		
	}
	
	/** Sube imagenes */
	private String subirImagenesAlServidor(HttpServletRequest request) {
		
		int contadorExito = 0;
		int contadorFracaso = 0;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(256); //Limite tamanio archivos
		factory.setRepository(new File(rutaDirectorio));
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try{
			List<FileItem> items = upload.parseRequest(request); 	/** traigo imagenes */
			
			for(FileItem item: items) { 							/** itero sobre list de imagenes */
				
				String rutaArchivo = rutaDirectorio + File.separator + item.getName();
				File file = new File(rutaArchivo);
				
				if(!file.exists()) {
					item.write(file);
					contadorExito++;
				} else {
					contadorFracaso++;
				}
			}
			
			eliminarArchivosTemporales();
			
		} catch(Exception ex) {
			//System.out.println("Ocurrio un error: " + ex.getMessage());
		}
		
		return construirMensajeSubir(contadorExito,contadorFracaso);
	}
	
	/** Elimina archivos temporales que se crean al intentar subir archivos con nombre ya existente en el directorio */
	private void eliminarArchivosTemporales() {
		
		File dir = new File(rutaDirectorio);
		String[] listadoImagenes = dir.list();
		
		String extensionArchivo;
		for(String s: listadoImagenes) {
			extensionArchivo = s.substring(s.length()-3,s.length());
			String rutaArchivo;
			if(extensionArchivo.equals("tmp")) {
				
				rutaArchivo = rutaDirectorio + File.separator + s;
				File file = new File(rutaArchivo);
				file.delete();
			}
		}
		
	}
	
	/** Construye mensaje que informa resultado de la operacion subir imagenes */
	private String construirMensajeSubir(int contadorExito, int contadorFracaso) {
		
		String mensajeResultadoOperacion = "";
		
		if(contadorExito==1) {
			mensajeResultadoOperacion = "Se subio " + contadorExito + " archivo exitosamente.";
		} else if(contadorExito>1) {
			mensajeResultadoOperacion = "Se subio " + contadorExito + " archivos exitosamente.";
		}
		
		if(contadorFracaso==1) {
			mensajeResultadoOperacion = mensajeResultadoOperacion + " Se intento subir " + contadorFracaso + " archivo ya existente.";
		} else if(contadorFracaso>1) {
			mensajeResultadoOperacion = mensajeResultadoOperacion + " Se intento subir " + contadorFracaso + " archivos ya existentes.";
		}
		
		if(contadorExito==0 && contadorFracaso==0) {
			mensajeResultadoOperacion = "No se selecciono ningun archivo.";
		}
		
		return mensajeResultadoOperacion;
	}
	
	/** Borra imagenes */
	private String borrarImagenesDelServidor(HttpServletRequest request) {
		
		String mensajeResultadoOperacion = "";
		
		String[] imagenes = request.getParameterValues("imagenes"); // trae nombre de archivo de imagenes a eliminar
		
		if(imagenes!=null) {
			
			String rutaArchivo = "";
			int contadorExito = 0;
			for(int i=0 ; i<imagenes.length ; i++) {
				
				rutaArchivo = rutaDirectorio + File.separator + imagenes[i];
				
				File file = new File(rutaArchivo);
				file.delete();
				contadorExito++;
			}
			
			mensajeResultadoOperacion = construirMensajeBorrar(contadorExito);
			
		} else {
			mensajeResultadoOperacion = "No se selecciono ningun archivo.";
		}
	
		return mensajeResultadoOperacion;
	}
	
	/** Construye mensaje que informa resultado de la operacion borrar imagenes */
	private String construirMensajeBorrar(int contadorExito) {
		
		String mensajeResultadoOperacion = "";
		
		if(contadorExito==1) {
			mensajeResultadoOperacion = "Se borro " + contadorExito + " archivo exitosamente.";
		} else if(contadorExito>1) {
			mensajeResultadoOperacion = "Se borro " + contadorExito + " archivos exitosamente.";
		}
		
		return mensajeResultadoOperacion;
	}
	
}
