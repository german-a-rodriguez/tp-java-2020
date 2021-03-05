package data;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;

import entities.Compra;
import entities.Usuario;

public class DataUsuario {
	
	//Consulta y devuelve todos los usuarios.
	public LinkedList<Usuario> getAllUsuarios() throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Usuario> allUsuarios = new LinkedList<Usuario>();
		Usuario unUsuario = null;
		
		stmt = DbConnector.getInstancia().getConn().createStatement();
		
		rs = stmt.executeQuery("SELECT id,nombre,apellido,email,rol FROM usuario");
		
		while(rs!=null && rs.next()) {
			unUsuario = new Usuario();
			
			unUsuario.setId			(rs.getInt("id"));
			unUsuario.setNombre		(rs.getString("nombre"));
			unUsuario.setApellido	(rs.getString("apellido"));
			unUsuario.setEmail		(rs.getString("email"));
			unUsuario.setRol		(rs.getString("rol"));
			
			allUsuarios.add(unUsuario);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return allUsuarios;
	}
	
	//Devuelve un usuario buscando por email y password.
	public Usuario getUsuarioByEmailAndPassword(Usuario usuario) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Usuario u = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT id,nombre,apellido,email,rol FROM usuario WHERE email=? AND password=?");
		stmt.setString(1,usuario.getEmail());
		stmt.setString(2,usuario.getPassword());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			int    id  	 	= rs.getInt("id");
			String nombre   = rs.getString("nombre");
			String apellido = rs.getString("apellido");
			String email   	= rs.getString("email");
			String rol   	= rs.getString("rol");
			u = new Usuario(id,nombre,apellido,email,rol);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return u;
	}
	
	//Crea un nuevo usuario.
	public Usuario createUsuario(Usuario usuario) throws SQLIntegrityConstraintViolationException, SQLException {
		
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO usuario(nombre,apellido,email,password,rol) VALUES(?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setString(1, usuario.getNombre());
		stmt.setString(2, usuario.getApellido());
		stmt.setString(3, usuario.getEmail());
		stmt.setString(4, usuario.getPassword());
		stmt.setString(5, usuario.getRol());
		
		stmt.executeUpdate();
		
		keyResultSet = stmt.getGeneratedKeys();
		if(keyResultSet!=null && keyResultSet.next()) {
    		usuario.setId(keyResultSet.getInt(1));
		}
		
		if(keyResultSet!=null) {keyResultSet.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return usuario;
	}
	
	//Al consultar una compra, setea el usuario que la realizo.
	public void setUsuario(Compra compra) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT id,nombre,apellido,email,rol FROM usuario WHERE id=?");
		stmt.setInt(1, compra.getUsuario().getId());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			//id ya esta seteado
			compra.getUsuario().setNombre  (rs.getString("nombre"));
			compra.getUsuario().setApellido(rs.getString("apellido"));
			compra.getUsuario().setEmail   (rs.getString("email"));
			compra.getUsuario().setRol     (rs.getString("rol"));
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
	}
	
}