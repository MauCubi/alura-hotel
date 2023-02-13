package factory;

import java.sql.Connection;
import java.sql.SQLException;



public class testConnection {

	public static void main(String[] args) throws SQLException {
		System.out.println("probando conexion");
		// ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = new ConnectionFactory().recuperarConexion();
		
		System.out.println("probando conexion");	
		
		
		connection.close();		
		
		System.out.println("conexion cerrada");
	}
}
