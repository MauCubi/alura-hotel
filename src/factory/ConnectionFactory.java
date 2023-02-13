package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	public DataSource dataSource;
	
	// Crear conexion y pool
	public ConnectionFactory () {
		ComboPooledDataSource comboPooled = new ComboPooledDataSource();		
		comboPooled.setJdbcUrl("jdbc:mysql://localhost/alurahotel?useTimeZone=true&serverTimeZone=UTC");
		comboPooled.setUser("root");
		comboPooled.setPassword("admin");
		comboPooled.setMaxPoolSize(10);
		
		this.dataSource = comboPooled;
	}
	
	
	// Retornar conexion
	public Connection recuperarConexion() {
		
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {			
			throw new RuntimeException(e);
		}
		
	}
}
