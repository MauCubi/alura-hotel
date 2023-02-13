package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HuespedController;
import controller.ReservaController;
import modelo.Huesped;
import modelo.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloH;
	private JLabel labelAtras;
	private JLabel labelExit;
	private int flag = 0;
	private List<Reserva> reservas = new ArrayList<>();
	private List<Huesped> huespedes = new ArrayList<>();
	private List<Reserva> reservasBusqueda = new ArrayList<>();
	private List<Huesped> huespedesBusqueda = new ArrayList<>();
	private Huesped huespedSelected;
	private Reserva reservaSelected;
	private ReservaController reservaController;
	private HuespedController huespedController;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		
		this.reservaController = new ReservaController();
		this.huespedController = new HuespedController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();		
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(322, 62, 289, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);		
		
		tbReservas = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {		       
		       return false;
		    }
		};
		
		
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas, null);
		
		modelo = (DefaultTableModel) tbReservas.getModel();				
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");	
		
		cargarTablaReserva();
		
		
		tbHuespedes = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {		       
		       return false;
		    }
		};
		
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes, null);
		modeloH = (DefaultTableModel) tbHuespedes.getModel();
		modeloH.addColumn("Numero de Huesped");
		modeloH.addColumn("Nombre");
		modeloH.addColumn("Apellido");
		modeloH.addColumn("Fecha de Nacimiento");
		modeloH.addColumn("Nacionalidad");
		modeloH.addColumn("Telefono");
		modeloH.addColumn("Numero de Reserva");
		
		cargarTablaHuespedes();
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modeloH.setRowCount(0);
				modelo.setRowCount(0);
				
				if (txtBuscar.getText().length() != 0) {
					reservasBusqueda = reservaController.listarBusquedaReserva(txtBuscar.getText());	
					huespedesBusqueda = huespedController.listarHuespedesBusqueda(txtBuscar.getText());				
					
					reservasBusqueda.forEach(reserva -> modelo.addRow(
							new Object[] {						
									reserva.getId(),
									reserva.getFechaEntrada(),
									reserva.getFechaSalida(),
									reserva.getValor(),
									reserva.getFormaPago()	
							}));
					
					huespedesBusqueda.forEach(huesped -> modeloH.addRow(
							new Object[] {						
									huesped.getId(),
									huesped.getNombre(),
									huesped.getApellido(),
									huesped.getFechaNacimiento(),
									huesped.getNacionalidad(),
									huesped.getTelefono(),
									huesped.getIdReserva()
							}));
				} 	else {
					cargarTablaHuespedes();
					cargarTablaReserva();
				}
			}
		});
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();		
		btnEditar.setEnabled(false);
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setEnabled(false);
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setEnabled(false);
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		
		
		tbHuespedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				if (tbHuespedes.rowAtPoint(e.getPoint()) != -1) {					
					int fila = tbHuespedes.rowAtPoint(e.getPoint());		
					flag = 2;
					int idMod = (int) tbHuespedes.getValueAt(fila, 0);
					String nombreMod = (String) tbHuespedes.getValueAt(fila, 1);
					String apellidoMod = (String) tbHuespedes.getValueAt(fila, 2);
					Date fechaNacimientoMod = (Date) tbHuespedes.getValueAt(fila, 3);
					String nacionalidadMod = (String) tbHuespedes.getValueAt(fila, 4);
					String telefonoMod = (String) tbHuespedes.getValueAt(fila, 5);
					int idReservaMod = (int) tbHuespedes.getValueAt(fila, 6);
					huespedSelected = new Huesped(idMod, nombreMod, apellidoMod, fechaNacimientoMod, nacionalidadMod,
							telefonoMod, idReservaMod);		
					
					lblEditar.setEnabled(true);
					btnEditar.setEnabled(true);
				}	
			}
		});
		
		tbReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {					
				
				if (tbReservas.rowAtPoint(e.getPoint()) != -1) {
					int fila = tbReservas.rowAtPoint(e.getPoint());		
					flag = 1;
					int idMod = (int) tbReservas.getValueAt(fila, 0);
					Date fechaIMod = (Date) tbReservas.getValueAt(fila, 1);
					Date fechaSMod = (Date) tbReservas.getValueAt(fila, 2);
					String valorMod = (String) tbReservas.getValueAt(fila, 3);
					String formaPagoMod = (String) tbReservas.getValueAt(fila, 4);
					reservaSelected = new Reserva(idMod, fechaIMod, fechaSMod, valorMod, formaPagoMod);
					lblEditar.setEnabled(true);
					btnEditar.setEnabled(true);
					
				}	

				
			}
		});		
		
		lblEditar.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {				
				switch (flag) {
				case 1:
					ReservaModificar reservaMod = new ReservaModificar(reservaSelected);
					reservaMod.setVisible(true);		
					dispose();
					break;
				case 2:
					HuespedModificar huespedMod = new HuespedModificar(huespedSelected);
					huespedMod.setVisible(true);
					dispose();
					break;
				default:
					break;
				}
				
				
			}
		});
		
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int option;
				switch (flag) {
				case 1:
					option = JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar la reserva?");
					if (option == JOptionPane.YES_OPTION) {
						reservaController.borrarReserva(reservaSelected.getId());
						modelo.setRowCount(0);
						cargarTablaReserva();
						lblEditar.setEnabled(false);
						lblEliminar.setEnabled(false);
					} 
				case 2:
					option = JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar al huesped?");
					if (option == JOptionPane.YES_OPTION) {
						huespedController.borrarHuesped(huespedSelected.getId());
						modeloH.setRowCount(0);
						cargarTablaHuespedes();
						lblEditar.setEnabled(false);
						lblEliminar.setEnabled(false);
					} 
					break;
				default:
					break;
				}
			}
		});
	}
	
	private void cargarTablaReserva() {
		reservas = reservaController.listarReservas();
		
		reservas.forEach( reserva -> modelo.addRow(
				new Object[] {						
						reserva.getId(),
						reserva.getFechaEntrada(),
						reserva.getFechaSalida(),
						reserva.getValor(),
						reserva.getFormaPago()						
				}));
	}	
	
	private void cargarTablaHuespedes() {
		huespedes = huespedController.listarHuespedes();
		
		huespedes.forEach( huesped -> modeloH.addRow(
				new Object[] {						
						huesped.getId(),
						huesped.getNombre(),
						huesped.getApellido(),
						huesped.getFechaNacimiento(),
						huesped.getNacionalidad(),
						huesped.getTelefono(),
						huesped.getIdReserva()
				}));
	}

	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
