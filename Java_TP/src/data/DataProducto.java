package data;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;

import entities.Categoria;
import entities.Producto;
import entities.ProductoEnCompra;
import entities.Usuario;

public class DataProducto {
	
	//Devuelve todos los productos.
	public LinkedList<Producto> getAllProductos(Usuario usuario) throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		DataCategoria dc = new DataCategoria();
		
		LinkedList<Producto> productos = new LinkedList<Producto>();
		Producto p;
		Categoria c;
		
		stmt = DbConnector.getInstancia().getConn().createStatement();
		
		//Para no tener que hacer un metodo a parte para el admin
		if(usuario!=null && usuario.getRol().equals("admin")) {
			rs = stmt.executeQuery("SELECT * FROM producto");
		} else {
			rs = stmt.executeQuery("SELECT * FROM producto WHERE bajaLogica=true");
		}
		
		while(rs!=null && rs.next()) {
			
			p = new Producto();
			c = new Categoria();
			p.setCategoria(c);
			
			p.setId					(rs.getInt("id"));
			p.setDescripcion		(rs.getString("descripcion"));
			p.setLinkFoto			(rs.getString("linkFoto"));
			p.getCategoria().setId	(rs.getInt("idCategoria"));
			p.setPrecio				(rs.getDouble("precio"));
			p.setStock				(rs.getInt("stock"));
			//Para no tener que hacer un metodo a parte para el admin
			if(usuario!=null && usuario.getRol().equals("admin")) {
				p.setBajaLogica		(rs.getBoolean("bajaLogica"));
			}
			
			dc.setCategoria(p);
			
			productos.add(p);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return productos;
	}
	
	//Devuelve todos los productos pertenecientes a una categoria que llega como parametro.
	public LinkedList<Producto> getAllProductosFiltroCategoria(Categoria categoria) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DataCategoria dc = new DataCategoria();
		
		LinkedList<Producto> productos = new LinkedList<Producto>();
		Producto p;
		Categoria c;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM producto WHERE idCategoria=? AND bajaLogica=true");
		stmt.setInt(1, categoria.getId());
		
		rs = stmt.executeQuery();
		
		while(rs!=null && rs.next()) {
			
			p = new Producto();
			c = new Categoria();
			p.setCategoria(c);
			
			p.setId					(rs.getInt("id"));
			p.setDescripcion		(rs.getString("descripcion"));
			p.setLinkFoto			(rs.getString("linkFoto"));
			p.getCategoria().setId	(rs.getInt("idCategoria"));
			p.setPrecio				(rs.getDouble("precio"));
			p.setStock				(rs.getInt("stock"));
			
			dc.setCategoria(p);
			
			productos.add(p);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return productos;
	}
	
	//Devuelve un producto buscando por su id.
	public Producto getProductoById(Producto producto) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataCategoria dc = new DataCategoria();
		Categoria categoria;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM producto WHERE id=?");
		stmt.setInt(1, producto.getId());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			categoria = new Categoria();
			producto.setCategoria(categoria);
			
			producto.setId(rs.getInt("id"));
			producto.setDescripcion(rs.getString("descripcion"));
			producto.setLinkFoto(rs.getString("linkFoto"));
			producto.getCategoria().setId(rs.getInt("idCategoria"));
			producto.setPrecio(rs.getDouble("precio"));
			producto.setStock(rs.getInt("stock"));
			
			dc.setCategoria(producto);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return producto;
	}
	
	//Crea un producto.
	public int createProducto(Producto producto) throws SQLIntegrityConstraintViolationException, SQLException {
		
		PreparedStatement stmt = null;
		
		int registrosActualizados = 0;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO producto(descripcion,linkFoto,idCategoria,precio,stock,bajaLogica) VALUES(?,?,?,?,?,true)");
		stmt.setString(1, producto.getDescripcion());
		stmt.setString(2, producto.getLinkFoto());
		stmt.setInt   (3, producto.getCategoria().getId());
		stmt.setDouble(4, producto.getPrecio());
		stmt.setInt   (5, producto.getStock());
		
		registrosActualizados = stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return registrosActualizados;
	}
	
	//Actualiza un producto.
	public int updateProducto(Producto producto) throws SQLException {
		
		PreparedStatement stmt = null;
		
		int registrosActualizados = 0;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE producto SET descripcion=?,linkFoto=?,idCategoria=?,precio=?,stock=? WHERE id=?");
		stmt.setString(1, producto.getDescripcion());
		stmt.setString(2, producto.getLinkFoto());
		stmt.setInt   (3, producto.getCategoria().getId());
		stmt.setDouble(4, producto.getPrecio());
		stmt.setInt   (5, producto.getStock());
		stmt.setInt   (6, producto.getId());
		
		registrosActualizados = stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();

		return registrosActualizados;
	}
	
	//Borra un producto.
	public int deleteProducto(Producto producto) throws SQLException {
		
		PreparedStatement stmt = null;
		
		int registrosActualizados = 0;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM producto WHERE id=?");
		stmt.setInt(1, producto.getId());
		
		registrosActualizados = stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return registrosActualizados;
	}
	
	//Setea producto en la consulta de productos en compra.
	public void setProducto(ProductoEnCompra pec) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DataCategoria dc = new DataCategoria();
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM producto WHERE id=?");
		stmt.setInt(1, pec.getProducto().getId());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			pec.getProducto().setCategoria(new Categoria());
			//id ya esta seteado
			pec.getProducto().setDescripcion		(rs.getString("descripcion"));
			pec.getProducto().setLinkFoto			(rs.getString("linkFoto"));
			pec.getProducto().getCategoria().setId	(rs.getInt("idCategoria"));
			pec.getProducto().setPrecio				(rs.getDouble("precio"));
			pec.getProducto().setStock				(rs.getInt("stock"));
			
			dc.setCategoria(pec.getProducto());
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
	//Modifica el atributo bajaLogica
	public void updateBajaLogica(Producto producto) throws SQLException {
		
		PreparedStatement stmt = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE producto SET bajaLogica=? WHERE id=?");
		stmt.setBoolean	(1, producto.getBajaLogica());
		stmt.setInt		(2, producto.getId());
		
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
}
