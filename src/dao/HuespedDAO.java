package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Huesped;
import modelo.Reserva;

public class HuespedDAO {

	private Connection connection;
	
	public HuespedDAO (Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Huesped huesped) {
		
		String sql = "INSERT INTO huespedes (nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva) "
				+ "VALUES (?,?,?,?,?,?)";
		
		try( PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstm.setString(1, huesped.getNombre());
			pstm.setString(2, huesped.getApellido());
			pstm.setDate(3, huesped.getFechaNacimiento());
			pstm.setString(4, huesped.getNacionalidad());
			pstm.setString(5, huesped.getTelefono());
			pstm.setInt(6, huesped.getIdReserva());
			
			pstm.execute();
			
			try (ResultSet rst = pstm.getGeneratedKeys()){
				while(rst.next()) {
					huesped.setId(rst.getInt(1));
				}
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huesped> listarHuespedes(){
		String sql = "SELECT idHuespedes, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes";
		List<Huesped> huespedes = new ArrayList<>();
		
		try(PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();
			
			ResultSet rst = pstm.getResultSet();
			
			try (rst) {
                while (rst.next()) {
                	huespedes.add(new Huesped(
                    		rst.getInt("idHuespedes"),
                    		rst.getString("nombre"),
                    		rst.getString("apellido"),
                    		rst.getDate("fechaNacimiento"),
                    		rst.getString("nacionalidad"),
                    		rst.getString("telefono"),
                    		rst.getInt("idReserva")));
                }
            }
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return huespedes;
	}
	
	public List<Huesped> listarHuespedesBusqueda(String input){
		
		List<Huesped> huespedes = new ArrayList<>();
		String sql;
		boolean flag = true;
		
		if (isNumeric(input)) {			
			sql = "SELECT idHuespedes, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes "
					+ "WHERE idReserva LIKE ?";
			flag = true;
		} else {
			sql = "SELECT idHuespedes, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes "
					+ "WHERE apellido LIKE ?";	
			flag = false;
		}		
		
		try(PreparedStatement pstm = connection.prepareStatement(sql)) {
			
			if (flag) {
				pstm.setInt(1, Integer.valueOf(input));
			} else {
				pstm.setString(1, '%'+input+'%');
			}			
			
			pstm.execute();
			
			ResultSet rst = pstm.getResultSet();
			
			try (rst) {
                while (rst.next()) {
                	huespedes.add(new Huesped(
                    		rst.getInt("idHuespedes"),
                    		rst.getString("nombre"),
                    		rst.getString("apellido"),
                    		rst.getDate("fechaNacimiento"),
                    		rst.getString("nacionalidad"),
                    		rst.getString("telefono"),
                    		rst.getInt("idReserva")));
                }
            }
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		return huespedes;
	}
	
	public int actualizarHuesped(int id, String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, int idReserva) {					
		try {
			String sql = "UPDATE huespedes SET "					
					+ "nombre = ?, "
					+ "apellido = ?, "
					+ "fechaNacimiento = ?, "
					+ "nacionalidad = ?, "
					+ "telefono = ?, "
					+ "idReserva = ? "
					+ "WHERE idHuespedes = ?";
			PreparedStatement pstm; pstm = connection.prepareStatement(sql);		
		
		try(pstm) {
			pstm.setInt(7, id);
			pstm.setString(1, nombre);
			pstm.setString(2, apellido);
			pstm.setDate(3, fechaN);
			pstm.setString(4, nacionalidad);	
			pstm.setString(5, telefono);
			pstm.setInt(6, idReserva);
			pstm.execute();			
			
			int updateCount = pstm.getUpdateCount();

            return updateCount;
		}
		
	} catch (SQLException e1) {		
		throw new RuntimeException(e1);
	}
	}
	
	public int borrarHuesped(int id) {
		
		try {
			String sql = "DELETE FROM huespedes WHERE idHuespedes = ?";
			PreparedStatement pstm; pstm = connection.prepareStatement(sql);
		
			try (pstm){
				pstm.setInt(1, id);
				pstm.execute();				
				int updateCount = pstm.getUpdateCount();
				return updateCount;				
			}
		}catch (SQLException e1){
			throw new RuntimeException(e1);
		}
	
	}
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
}
