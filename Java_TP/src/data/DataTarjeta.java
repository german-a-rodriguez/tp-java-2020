package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Compra;
import entities.Tarjeta;

public class DataTarjeta {
	
	//Al consultar una compra, setea tarjeta utilizada.
	public void setTarjeta(Compra compra) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM tarjeta WHERE numero=?");
		stmt.setString(1, compra.getTarjeta().getNumero());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			//nro ya esta seteado
			compra.getTarjeta().setEntidadEmisora(rs.getString("entidadEmisora"));
			compra.getTarjeta().setMesVencimiento(rs.getInt("mesVencimiento"));
			compra.getTarjeta().setAnioVencimiento(rs.getInt("anioVencimiento"));
			compra.getTarjeta().setDniTitular(rs.getString("dniTitular"));
			compra.getTarjeta().setNyaTitular(rs.getString("nyaTitular"));
			
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
	
	}
	
	//Devuelve una tarjeta. Busca por numero de tarjeta.
	public Tarjeta getTarjeta(Tarjeta tarjeta) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM tarjeta WHERE numero=?");
		stmt.setString(1, tarjeta.getNumero());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			tarjeta = new Tarjeta();
			tarjeta.setNumero			(rs.getString("numero"));
			tarjeta.setEntidadEmisora	(rs.getString("entidadEmisora"));
			tarjeta.setMesVencimiento	(rs.getInt("mesVencimiento"));
			tarjeta.setAnioVencimiento	(rs.getInt("anioVencimiento"));
			tarjeta.setDniTitular		(rs.getString("dniTitular"));
			tarjeta.setNyaTitular		(rs.getString("nyaTitular"));
		} else {
			tarjeta = null;
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return tarjeta;
	}
	
}