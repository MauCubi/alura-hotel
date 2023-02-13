package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.HuespedDAO;
import dao.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Huesped;
import modelo.Reserva;

public class HuespedController {
	private HuespedDAO huespedDao;
	
	public HuespedController() {
		Connection connection = new ConnectionFactory().recuperarConexion();
		this.huespedDao = new HuespedDAO(connection);
	}
	
	public void guardar(Huesped huesped) {		
		this.huespedDao.guardar(huesped);
	}
	
	public List<Huesped> listarHuespedes(){
		return huespedDao.listarHuespedes();
	}
	
	public List<Huesped> listarHuespedesBusqueda(String input){
		return huespedDao.listarHuespedesBusqueda(input);
	}
	
	public int actualizarHuesped(int id, String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, int idReserva) {		
		return this.huespedDao.actualizarHuesped(id, nombre, apellido, fechaN, nacionalidad, telefono, idReserva);
	}
	
	public int borrarHuesped(int id) {
		return this.huespedDao.borrarHuesped(id);
	}
	
}
