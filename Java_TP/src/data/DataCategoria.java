package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Categoria;
import entities.Producto;

public class DataCategoria {
	
	//Devuelve todas las categorias.
	public LinkedList<Categoria> getAllCategorias() throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Categoria> categorias = new LinkedList<Categoria>();
		Categoria c;
		
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("SELECT * FROM categoria");
		
		while(rs!=null && rs.next()) {
			
			c = new Categoria();
			c.setId(rs.getInt("id"));
			c.setNombre(rs.getString("nombre"));
			
			categorias.add(c);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return categorias;
	}
	
	//Crea una categoria.
	public int createCategoria(Categoria categoria) throws SQLException {
		
		PreparedStatement stmt = null;
		
		int registrosActualizados = 0;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO categoria(nombre) VALUES (?)");
		stmt.setString(1, categoria.getNombre());
		
		registrosActualizados = stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return registrosActualizados;
	}
	
	//Actualiza una categoria.
	public int updateCategoria(Categoria categoria) throws SQLIntegrityConstraintViolationException, SQLException {
		
		PreparedStatement stmt = null;
		
		int registrosActualizados = 0;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE categoria SET nombre=? WHERE id=?");
		stmt.setString(1, categoria.getNombre());
		stmt.setInt   (2, categoria.getId());
		
		registrosActualizados = stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return registrosActualizados;
	}
	
	//Borra una categoria.
	public int deleteCategoria(Categoria categoria) throws SQLIntegrityConstraintViolationException, SQLException {
		
		PreparedStatement stmt = null;
		
		int registrosActualizados = 0;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM categoria WHERE id=?");
		stmt.setInt(1, categoria.getId());
		
		registrosActualizados = stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return registrosActualizados;
	}
	
	//Al cargar un producto, le setea su categoria.
	public void setCategoria(Producto producto) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM categoria WHERE id=?");
		stmt.setInt(1, producto.getCategoria().getId());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			producto.getCategoria().setNombre(rs.getString("nombre"));
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
	}
	
}
