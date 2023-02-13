package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Reserva;

public class ReservaController {
	private ReservaDAO reservaDao;
	
	public ReservaController() {
		Connection connection = new ConnectionFactory().recuperarConexion();
		this.reservaDao = new ReservaDAO(connection);		
	}
	
	public void guardar(Reserva reserva) {		
		this.reservaDao.guardar(reserva);
	}
	
	public List<Reserva> listarReservas(){
		return reservaDao.listaReservas();
	}
	
	public List<Reserva> listarBusquedaReserva(String id){
		return reservaDao.listarBusquedaReserva(id);
	}
	
	public int actualizarReserva(Date fechaEntrada, Date fechaSalida, String valor, String formaPago, int id) {		
		return this.reservaDao.actualizarReserva(fechaEntrada, fechaSalida, valor, formaPago, id);
	}
	
	public int borrarReserva(int id) {
		return this.reservaDao.borrarReserva(id);
	}
	
	

}
