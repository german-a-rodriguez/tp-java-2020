package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Compra;
import entities.Producto;
import entities.ProductoEnCompra;

public class DataProductoEnCompra {
	
	//Devuelve los productos en compra de una compra de un cliente (carrito comprado en esa compra)
	public void setProductosEnCompra(Compra compra) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DataProducto dp = new DataProducto();
		
		ProductoEnCompra pec;
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM producto_en_compra WHERE idCompra=?");
		stmt.setInt(1, compra.getId());
		
		rs = stmt.executeQuery();
		
		while(rs!=null && rs.next()) {
			
			pec = new ProductoEnCompra();
			pec.setProducto(new Producto());
			
			pec.setId				(rs.getInt("id"));
			pec.getProducto().setId	(rs.getInt("idProducto"));
			pec.setPrecio			(rs.getDouble("precio"));
			pec.setCantidad			(rs.getInt("cantidad"));
			
			dp.setProducto(pec);
			
			compra.getCarritoComprado().add(pec);
		}
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	}
	
}
