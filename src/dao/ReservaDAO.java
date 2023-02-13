package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import modelo.Reserva;

public class ReservaDAO {
	private Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	public void guardar(Reserva reserva) {		
		String sql = "INSERT INTO reservas (fechaEntrada, fechaSalida, valor, formaPago)"
				+ "VALUES (?,?,?,?)";
		
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setDate(1, reserva.getFechaEntrada());
			pstm.setDate(2, reserva.getFechaSalida());
			pstm.setString(3, reserva.getValor());
			pstm.setString(4, reserva.getFormaPago());
			
			pstm.execute();			
			
			try (ResultSet rst = pstm.getGeneratedKeys()){
				while(rst.next()) {
					reserva.setId(rst.getInt(1));
				}
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> listaReservas(){
		String sql = "SELECT idReservas, fechaEntrada, fechaSalida, valor, formaPago FROM reservas";
		List<Reserva> reservas = new ArrayList<>();
		
		try(PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();
			
			ResultSet rst = pstm.getResultSet();
			
			try (rst) {
                while (rst.next()) {
                    reservas.add(new Reserva(
                    		rst.getInt("idReservas"),
                    		rst.getDate("fechaEntrada"),
                    		rst.getDate("fechaSalida"),
                    		rst.getString("valor"),
                    		rst.getString("formaPago")));
                }
            }
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return reservas;
	}
	
	public List<Reserva> listarBusquedaReserva(String id){
		
		List<Reserva> reservas = new ArrayList<>();
		
		if (isNumeric(id)) {
			String sql = "SELECT idReservas, fechaEntrada, fechaSalida, valor, formaPago FROM reservas "
					+ "WHERE idReservas LIKE ?";	
		
		try(PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setInt(1, Integer.valueOf(id));			
			pstm.execute();	
			
			ResultSet rst = pstm.getResultSet();
			
			try (rst) {
                while (rst.next()) {
                    reservas.add(new Reserva(
                    		rst.getInt("idReservas"),
                    		rst.getDate("fechaEntrada"),
                    		rst.getDate("fechaSalida"),
                    		rst.getString("valor"),
                    		rst.getString("formaPago")));
                }
            }
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		
		}
		
		return reservas;
	}
	
	
	public int actualizarReserva(Date fechaEntrada, Date fechaSalida, String valor, String formaPago, int id) {					
		try {
			String sql = "UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE idReservas = ?";
			PreparedStatement pstm; pstm = connection.prepareStatement(sql);		
		
		try(pstm) {
			pstm.setDate(1, fechaEntrada);
			pstm.setDate(2, fechaSalida);
			pstm.setString(3, valor);
			pstm.setString(4, formaPago);
			pstm.setInt(5, id);			
			pstm.execute();			
			
			int updateCount = pstm.getUpdateCount();

            return updateCount;
		}
		
	} catch (SQLException e1) {		
		throw new RuntimeException(e1);
	}
	}
	
	public int borrarReserva(int id) {
		
		try {
			String sql = "DELETE FROM reservas WHERE idReservas = ?";
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
