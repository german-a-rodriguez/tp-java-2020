package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Compra;
import entities.Direccion;
import entities.ProductoEnCompra;
import entities.Tarjeta;

public class DataRegistrarCompra {
	
	public Direccion createDireccion(Direccion direccion) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO direccion(provincia,localidad,direccion,piso,departamento,codigoAreaTelef,nroTelef) VALUES(?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setString(1, direccion.getProvincia());
		stmt.setString(2, direccion.getLocalidad());
		stmt.setString(3, direccion.getDireccion());
		stmt.setString(4, direccion.getPiso());
		stmt.setString(5, direccion.getDepartamento());
		stmt.setString(6, direccion.getCodigoAreaTelef());
		stmt.setString(7, direccion.getNroTelef());
		
		stmt.executeUpdate();
		
		keyResultSet = stmt.getGeneratedKeys();
		if(keyResultSet!=null && keyResultSet.next()){
    		direccion.setId(keyResultSet.getInt(1));
		}
		
		if(keyResultSet!=null) {keyResultSet.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
			
		return direccion;
	}
	
	public Tarjeta createTarjeta(Tarjeta tarjeta) throws SQLException {
		
		PreparedStatement stmt = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO tarjeta(numero,entidadEmisora,mesVencimiento,anioVencimiento,dniTitular,nyaTitular) VALUES(?,?,?,?,?,?)");
		stmt.setString(1, tarjeta.getNumero());
		stmt.setString(2, tarjeta.getEntidadEmisora());
		stmt.setInt   (3, tarjeta.getMesVencimiento());
		stmt.setInt   (4, tarjeta.getAnioVencimiento());
		stmt.setString(5, tarjeta.getDniTitular());
		stmt.setString(6, tarjeta.getNyaTitular());
		
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return tarjeta;
	}
	
	public Compra createCompra(Compra compra) throws  SQLException {
		
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		
		java.util.Date fechaHoraActual	= new java.util.Date();
		java.sql.Date  fechaActual		= new java.sql.Date(fechaHoraActual.getTime());
		java.sql.Time  horaActual		= new java.sql.Time(fechaHoraActual.getTime());
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO compra(idUsuario,idDireccion,nroTarjeta,fecha,hora,importe,estadoValuacion) VALUES(?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setInt   (1, compra.getUsuario().getId());
		stmt.setInt   (2, compra.getDireccion().getId());
		stmt.setString(3, compra.getTarjeta().getNumero());
		stmt.setDate  (4, fechaActual);
		stmt.setTime  (5, horaActual);
		stmt.setDouble(6, compra.getImporte());
		stmt.setString(7, compra.getValuacion().getEstado());
		
		stmt.executeUpdate();
		
		keyResultSet = stmt.getGeneratedKeys();
		if(keyResultSet!=null && keyResultSet.next()) {
    		compra.setId(keyResultSet.getInt(1));
		}
		
		if(keyResultSet!=null) {keyResultSet.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return compra;
	}
	
	public void createProductosEnCompra(LinkedList<ProductoEnCompra> carritoProductos, Compra compra) throws SQLException {
		
		PreparedStatement stmt = null;
		
		for(ProductoEnCompra pec : carritoProductos) {
			
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO producto_en_compra(idProducto,idCompra,precio,cantidad) VALUES(?,?,?,?)");
			stmt.setInt   (1, pec.getProducto().getId());
			stmt.setInt   (2, compra.getId());
			stmt.setDouble(3, pec.getProducto().getPrecio());
			stmt.setInt   (4, pec.getCantidad());
			
			stmt.executeUpdate();
		}
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
	public void updateStockProductos(LinkedList<ProductoEnCompra> carritoProductos) throws SQLException {
		
		PreparedStatement stmt = null;
		
		for(ProductoEnCompra pec : carritoProductos) {
			int nuevoStock = pec.getProducto().getStock() - pec.getCantidad();
			
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE producto SET stock=? WHERE id=?");
			stmt.setInt(1, nuevoStock);
			stmt.setInt(2, pec.getProducto().getId());
			
			stmt.executeUpdate();
		}
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
}