package logic;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.io.File;

import data.DataCategoria;
import data.DataCompra;
import data.DataRegistrarCompra;
import data.DataTarjeta;
import data.DataProducto;
import data.DataUsuario;
import entities.Categoria;
import entities.Compra;
import entities.Direccion;
import entities.Producto;
import entities.ProductoEnCompra;
import entities.Tarjeta;
import entities.Usuario;

public class LogicController {
	
	public LogicController() {}
	
	//--------------------------------------
	
	/** REGISTRO CLIENTE, INICIO DE SESIÓN */
	
	public Usuario create(Usuario usuario) throws SQLIntegrityConstraintViolationException, SQLException {
		DataUsuario du = new DataUsuario();
		return du.createUsuario(usuario);
	}
	
	public Usuario validar(Usuario usuario) throws SQLException {
		DataUsuario du = new DataUsuario();
		return du.getUsuarioByEmailAndPassword(usuario);
	}
	
	//--------------------------------------
	
	/** ABMC CATEGORIAS */
	
	public LinkedList<Categoria> getAllCategorias() throws SQLException {
		DataCategoria dca  = new DataCategoria();
		return dca.getAllCategorias();
	}
	
	public int create(Categoria categoria) throws SQLException {
		DataCategoria dca  = new DataCategoria();
		return dca.createCategoria(categoria);
	}
	
	public int update(Categoria categoria) throws SQLIntegrityConstraintViolationException, SQLException {
		DataCategoria dca  = new DataCategoria();
		return dca.updateCategoria(categoria);
	}
	
	public int delete(Categoria categoria) throws SQLIntegrityConstraintViolationException, SQLException {
		DataCategoria dca  = new DataCategoria();
		return dca.deleteCategoria(categoria);
	}
	
	//--------------------------------------
	
	/** ABMC PRODUCTOS */
	
	public LinkedList<Producto> getAllProductos(Usuario usuario) throws SQLException {
		DataProducto dp = new DataProducto();
		return dp.getAllProductos(usuario);
	}
	
	public LinkedList<Producto> getAllProductosFiltroCategoria(Categoria categoria) throws SQLException {
		DataProducto dp = new DataProducto();
		return dp.getAllProductosFiltroCategoria(categoria);
	}
	
	public Producto getProductoById(Producto producto) throws SQLException{
		DataProducto dp = new DataProducto();
		return dp.getProductoById(producto);
	}
	
	public int create(Producto producto) throws SQLIntegrityConstraintViolationException, SQLException {
		DataProducto dp = new DataProducto();
		return dp.createProducto(producto);
	}
	
	public int update(Producto producto) throws SQLException {
		DataProducto dp = new DataProducto();
		return dp.updateProducto(producto);
	}
	
	public int delete(Producto producto) throws SQLException {
		DataProducto dp = new DataProducto();
		return dp.deleteProducto(producto);
	}
	
	//--------------------------------------
	
	/** REALIZAR COMPRA */
	
	public Direccion create(Direccion direccion) throws SQLException {
		DataRegistrarCompra drc  = new DataRegistrarCompra();
		return drc.createDireccion(direccion);
	}
	
	public Tarjeta getTarjeta(Tarjeta tarjeta) throws SQLException {
		DataTarjeta dt = new DataTarjeta();
		return dt.getTarjeta(tarjeta);
	}
	
	public Tarjeta create(Tarjeta tarjeta) throws SQLException {
		DataRegistrarCompra drc  = new DataRegistrarCompra();
		return drc.createTarjeta(tarjeta);
	}
	
	public Compra create(Compra compra) throws  SQLException {
		DataRegistrarCompra drc  = new DataRegistrarCompra();
		return drc.createCompra(compra);
	}
	
	public void create(LinkedList<ProductoEnCompra> carritoProductos, Compra compra) throws SQLException {
		DataRegistrarCompra drc  = new DataRegistrarCompra();
		drc.createProductosEnCompra(carritoProductos, compra);
	}
	
	public void updateStockProductos(LinkedList<ProductoEnCompra> carritoProductos) throws SQLException {
		DataRegistrarCompra drc  = new DataRegistrarCompra();
		drc.updateStockProductos(carritoProductos);
	}
	
	//--------------------------------------
	
	/** LISTADOS DEL CLIENTE */
	
	public LinkedList<Compra> getAllComprasUnUsuario(Usuario usuario) throws SQLException {
		DataCompra dco  = new DataCompra();
		return dco.getAllComprasUnUsuario(usuario);
	}
	
	//--------------------------------------
	
	/** OBTENER CANTIDAD VALUACIONES PENDIENTES DEL CLIENTE */
	
	public int getCantidadValuacionesPendientes(Usuario usuario) throws SQLException {
		DataCompra dco = new DataCompra();
		LinkedList<Compra> comprasUnUsuario = dco.getAllComprasUnUsuario(usuario);
		
		int cantidadValuacionesPendientes = 0;
		for(Compra c: comprasUnUsuario) {
			if(c.getValuacion().getEstado().equals("Pendiente")) {
				cantidadValuacionesPendientes++;
			}
		}
		
		return cantidadValuacionesPendientes;
	}
	
	//--------------------------------------
	
	/** REALIZAR VALUACION - CLIENTE*/
	
	public void registrarValuacion(Compra compra) throws SQLException {
		DataCompra dco = new DataCompra();
		dco.updateValuacion(compra);
	}
	
	//--------------------------------------
	
	/** LISTADOS DEL ADMIN */
	
	public LinkedList<Compra> getAllCompras() throws SQLException {
		DataCompra dco  = new DataCompra();
		return dco.getAllCompras();
	}
	
	public LinkedList<Usuario> getAllUsuarios() throws SQLException {
		DataUsuario du = new DataUsuario();
		return du.getAllUsuarios();
	}
	
	//--------------------------------------
	
	/** MODIFICAR ATRIBUTO bajaLogica DE UN/A PRODUCTO - ADMIN */
	
	public void darDeAltaProducto(Producto producto) throws SQLException {
		DataProducto dp = new DataProducto();
		dp.updateBajaLogica(producto);
	}
	
	public void darDeBajaProducto(Producto producto) throws SQLException {
		DataProducto dp = new DataProducto();
		dp.updateBajaLogica(producto);
	}
	
	//--------------------------------------
	
	/** CONSULTAR LISTA IMAGENES */
	
	public String[] getListadoImagenes() {
		
		/** Localhost */
		String rutaDirectorio = "C:" + File.separator + "Users" 
				 					 + File.separator + "Usuario" 
				 					 + File.separator + "Desarrollo" 
				 					 + File.separator + "eclipse-workspace-utn-web" 
				 					 + File.separator + "Java_TP" 
				 					 + File.separator + "WebContent" 
				 					 + File.separator + "images" 
				 					 + File.separator + "productos";
		
		/** Jelastic */
//		String rutaDirectorio = File.separator + "opt" + 
//								File.separator + "tomcat" + 
//								File.separator + "webapps" + 
//								File.separator + "ROOT" +
//								File.separator + "images" + 
//								File.separator + "productos";
		
		File dir = new File(rutaDirectorio);
		String[] listadoImagenes = dir.list();
		String[] listadoImagenesSinTmp = filtrarArchivosTmp(listadoImagenes);
		
		return listadoImagenesSinTmp;
	}
	
	private String[] filtrarArchivosTmp(String[] listadoImagenes) {
		
		//Cuento archivos no temporales(.tmp) para armar array de ese tamanio
		String extensionArchivo;
		int contadorArchivosNoTmp = 0;
		for(String s: listadoImagenes) {
			extensionArchivo = s.substring(s.length()-3,s.length());
			if(!extensionArchivo.equals("tmp")) {contadorArchivosNoTmp++;}
		}
		
		//Armo nuevo array filtrando archivos .tmp
		String listadoImagenesSinTmp[] = new String[contadorArchivosNoTmp];
		int contador = 0;
		for(int i=0 ; i<listadoImagenes.length ; i++) {
			String s = listadoImagenes[i];
			extensionArchivo = s.substring(s.length()-3,s.length());
			if(!extensionArchivo.equals("tmp")) {
				listadoImagenesSinTmp[contador] = listadoImagenes[i];
				contador++;
			}
		}
		
		return listadoImagenesSinTmp;
	}
	
	//--------------------------------------
	
}
