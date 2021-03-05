package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Compra;

public class DataDireccion {
	
	//Al cargar una compra, le setea la direccion del usuario que la realizo.
	public void setDireccion(Compra compra) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM direccion WHERE id=?");
		stmt.setInt(1, compra.getDireccion().getId());
		
		rs = stmt.executeQuery();
		
		if(rs!=null && rs.next()) {
			//id ya esta seteado
			compra.getDireccion().setProvincia		(rs.getString("provincia"));
			compra.getDireccion().setLocalidad		(rs.getString("localidad"));
			compra.getDireccion().setDireccion		(rs.getString("direccion"));
			compra.getDireccion().setPiso			(rs.getString("piso"));
			compra.getDireccion().setDepartamento	(rs.getString("departamento"));
			compra.getDireccion().setCodigoAreaTelef(rs.getString("codigoAreaTelef"));
			compra.getDireccion().setNroTelef		(rs.getString("nroTelef"));
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
}
