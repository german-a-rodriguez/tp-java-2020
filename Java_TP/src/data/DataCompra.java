package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Compra;
import entities.Direccion;
import entities.ProductoEnCompra;
import entities.Usuario;
import entities.Valuacion;
import entities.Tarjeta;

public class DataCompra {
	
	//Devuelve todas las compras realizadas
	public LinkedList<Compra> getAllCompras() throws SQLException {
		
		Statement			 stmt 	= null;
		ResultSet			 rs   	= null;
		DataUsuario   		 du 	= new DataUsuario();
		DataDireccion 		 dd 	= new DataDireccion();
		DataTarjeta   		 dt 	= new DataTarjeta();
		DataProductoEnCompra dpec 	= new DataProductoEnCompra();
		
		LinkedList<Compra> todasLasCompras = new LinkedList<Compra>();
		Compra unaCompra;
		
		stmt = DbConnector.getInstancia().getConn().createStatement();
		
		rs = stmt.executeQuery("SELECT * FROM compra");
		
		while (rs!=null && rs.next()) {
			
			unaCompra = new Compra();
			unaCompra.setUsuario		(new Usuario());
			unaCompra.setDireccion		(new Direccion());
			unaCompra.setTarjeta		(new Tarjeta());
			unaCompra.setValuacion		(new Valuacion());
			unaCompra.setCarritoComprado(new LinkedList<ProductoEnCompra>());
			
			unaCompra.setId							(rs.getInt("id"));
			unaCompra.getUsuario().setId			(rs.getInt("idUsuario"));
			unaCompra.getDireccion().setId			(rs.getInt("idDireccion"));
			unaCompra.getTarjeta().setNumero		(rs.getString("nroTarjeta"));
			//A los ProductosEnCompra no le seteo su PK por que se los busca con la PK de la compra!
			unaCompra.setFecha						(rs.getDate("fecha"));
			unaCompra.setHora						(rs.getTime("hora"));
			unaCompra.setImporte					(rs.getDouble("importe"));
			unaCompra.getValuacion().setFecha		(rs.getDate("fechaValuacion"));
			unaCompra.getValuacion().setHora		(rs.getTime("horaValuacion"));
			unaCompra.getValuacion().setPuntaje		(rs.getInt("puntaje"));
			unaCompra.getValuacion().setComentario	(rs.getString("comentario"));
			unaCompra.getValuacion().setEstado		(rs.getString("estadoValuacion"));
			
			du.setUsuario			 (unaCompra);
			dd.setDireccion			 (unaCompra);
			dt.setTarjeta			 (unaCompra);
			dpec.setProductosEnCompra(unaCompra);
			
			todasLasCompras.add(unaCompra);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return todasLasCompras;
	}
	
	//Devuelve todas las compras realizadas por un usuario
	public LinkedList<Compra> getAllComprasUnUsuario(Usuario usuario) throws SQLException {
		
		PreparedStatement 	 stmt	= null;
		ResultSet 			 rs		= null;
		DataUsuario   		 du 	= new DataUsuario();
		DataDireccion		 dd 	= new DataDireccion();
		DataTarjeta   		 dt 	= new DataTarjeta();
		DataProductoEnCompra dpec 	= new DataProductoEnCompra();
		
		LinkedList<Compra> comprasUnUsuario = new LinkedList<Compra>();
		Compra unaCompra;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM compra WHERE idUsuario=?");
		stmt.setInt(1, usuario.getId());
		
		rs = stmt.executeQuery();
		
		while (rs!=null && rs.next()) {
			
			unaCompra = new Compra();
			unaCompra.setUsuario		(new Usuario());
			unaCompra.setDireccion		(new Direccion());
			unaCompra.setTarjeta		(new Tarjeta());
			unaCompra.setValuacion		(new Valuacion());
			unaCompra.setCarritoComprado(new LinkedList<ProductoEnCompra>());
			
			unaCompra.setId							(rs.getInt("id"));
			unaCompra.getUsuario().setId			(rs.getInt("idUsuario"));
			unaCompra.getDireccion().setId			(rs.getInt("idDireccion"));
			unaCompra.getTarjeta().setNumero		(rs.getString("nroTarjeta"));
			//A los ProductosEnCompra no le seteo su PK por que se los busca con la PK de la compra!
			unaCompra.setFecha						(rs.getDate("fecha"));
			unaCompra.setHora						(rs.getTime("hora"));
			unaCompra.setImporte					(rs.getDouble("importe"));
			unaCompra.getValuacion().setFecha		(rs.getDate("fechaValuacion"));
			unaCompra.getValuacion().setHora		(rs.getTime("horaValuacion"));
			unaCompra.getValuacion().setPuntaje		(rs.getInt("puntaje"));
			unaCompra.getValuacion().setComentario	(rs.getString("comentario"));
			unaCompra.getValuacion().setEstado		(rs.getString("estadoValuacion"));
			
			du.setUsuario			 (unaCompra);
			dd.setDireccion			 (unaCompra);
			dt.setTarjeta			 (unaCompra);
			dpec.setProductosEnCompra(unaCompra);
			
			comprasUnUsuario.add(unaCompra);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return  comprasUnUsuario;
	}
	
	//Modifica atributos correspondientes a la valuacion de una compra.
	public void updateValuacion(Compra compra) throws SQLException {
		
		PreparedStatement stmt = null;
		
		java.util.Date fechaHoraActual	= new java.util.Date();
		java.sql.Date  fechaActual		= new java.sql.Date(fechaHoraActual.getTime());
		java.sql.Time  horaActual		= new java.sql.Time(fechaHoraActual.getTime());
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE compra SET fechaValuacion=?, horaValuacion=?, puntaje=?, comentario=?, estadoValuacion=? WHERE id=?");
		stmt.setDate	(1, fechaActual);
		stmt.setTime	(2, horaActual);
		stmt.setInt		(3, compra.getValuacion().getPuntaje());
		stmt.setString	(4, compra.getValuacion().getComentario());
		stmt.setString	(5, "Realizada");
		stmt.setInt		(6, compra.getId());
		
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
}