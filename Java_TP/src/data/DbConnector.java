package data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnector {
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private static DbConnector instancia;
	

	/** Localhost */
	private String host = "localhost";
	private String port = "3306";
	private String db = "javatp?serverTimezone=America/Argentina/Buenos_Aires&charset=utf8mb4";//con UTC tenia problemas con fecha y hora(En mi PC)
	private String user = "gr";
	private String password = "2a0v2a0j";
	
	/** Jelastic */
//	private String host = "node64608-grinformatica.jelastic.saveincloud.net";
//	private String port = "3306";
//	private String db = "javatp?serverTimezone=UTC&charset=utf8mb4";//(En Jelastic si tengo que usar UTC)
//	private String user = "root";
//	private String password = "";
	
	private Connection conn = null;
	private int conectados = 0;
	
	private DbConnector () {
		try {
			Class.forName(driver);
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	public static DbConnector getInstancia() {
		if(instancia == null) {
			instancia = new DbConnector();
		}
		return instancia;
	}
	
	public Connection getConn() throws SQLException {
		if(conn==null || conn.isClosed()) {
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db , user , password);
			conectados = 0;
		}
		conectados++;
		return conn;
	}
	
	public void releaseConn() throws SQLException {
		conectados--;
		if(conn!=null && conectados <= 0) {
			conn.close();
		}
	}
	
}
