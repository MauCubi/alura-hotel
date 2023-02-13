package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.text.Format;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import controller.ReservaController;
import modelo.Reserva;

public class ReservaModificar extends JFrame{
	private JPanel contentPane;
	public static JDateChooser txtFechaE;
	public static JDateChooser txtFechaS;
	public static JComboBox<Format> txtFormaPago;
	int xMouse, yMouse;
	private JLabel labelExit;
	private JLabel lblValorSimbolo; 
	private JLabel labelAtras;
	private JTextField txtValor;
	private Double valorDiario = 300.00; 
	
	private ReservaController reservaController;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {				
//				try {
//					
//					ReservaModificar frame = new ReservaModificar();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ReservaModificar(Reserva reserva) {
		super("Reserva");		
		this.reservaController = new ReservaController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReservaModificar.class.getResource("/imagenes/aH-40px.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 560);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		

		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 910, 560);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(SystemColor.textHighlight);
		separator_1_2.setBounds(68, 195, 289, 2);
		separator_1_2.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_2);
		
		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setForeground(SystemColor.textHighlight);
		separator_1_3.setBackground(SystemColor.textHighlight);
		separator_1_3.setBounds(68, 453, 289, 2);
		panel.add(separator_1_3);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(SystemColor.textHighlight);
		separator_1_1.setBounds(68, 281, 289, 11);
		separator_1_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_1);
		
		txtFechaE = new JDateChooser();
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFechaE.getDateEditor();
		editor.setEnabled(false);
		editor.setBackground(Color.white);
		editor.setForeground(Color.BLACK);
		
		txtFechaE.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					
					if (!((JTextField)txtFechaE.getDateEditor().getUiComponent()).getText().equals("") && !((JTextField)txtFechaS.getDateEditor().getUiComponent()).getText().equals("")){
						Date fechaEntrada = new java.sql.Date(txtFechaE.getDate().getTime());
						Date fechaSalida = new java.sql.Date(txtFechaS.getDate().getTime());
						long diffMili = Math.abs(fechaEntrada.getTime() - fechaSalida.getTime());
						long diff = TimeUnit.DAYS.convert(diffMili, TimeUnit.MILLISECONDS);
						
						txtValor.setText(String.valueOf(diff * valorDiario));
					}
				}
			}
		});		

		txtFechaE.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaE.getCalendarButton().setIcon(new ImageIcon(ReservaModificar.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaE.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
		txtFechaE.setBounds(68, 161, 289, 35);
		txtFechaE.getCalendarButton().setBounds(268, 0, 21, 33);
		txtFechaE.setBackground(Color.WHITE);
		txtFechaE.setBorder(new LineBorder(SystemColor.window));
		txtFechaE.setDateFormatString("yyyy-MM-dd");
		txtFechaE.setFont(new Font("Dialog", Font.PLAIN, 16));
		panel.add(txtFechaE);
		
		lblValorSimbolo = new JLabel("$");
		lblValorSimbolo.setBounds(68, 328, 20, 33);
		lblValorSimbolo.setForeground(SystemColor.textHighlight);
		lblValorSimbolo.setFont(new Font("Dialog", Font.BOLD, 18));
		
		panel.add(lblValorSimbolo);
		
		JLabel lblCheckIn = new JLabel("FECHA DE CHECK IN");
		lblCheckIn.setForeground(SystemColor.textInactiveText);
		lblCheckIn.setBounds(68, 136, 289, 14);
		lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("FECHA DE CHECK OUT");
		lblCheckOut.setForeground(SystemColor.textInactiveText);
		lblCheckOut.setBounds(68, 221, 289, 14);
		lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckOut);
		
		txtFechaS = new JDateChooser();
		txtFechaS.getCalendarButton().setIcon(new ImageIcon(ReservaModificar.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaS.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		txtFechaS.setBounds(68, 246, 289, 35);
		txtFechaS.getCalendarButton().setBounds(267, 1, 21, 31);
		txtFechaS.setBackground(Color.WHITE);
		txtFechaS.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		JTextFieldDateEditor editor2 = (JTextFieldDateEditor) txtFechaS.getDateEditor();
		editor2.setEnabled(false);
		editor2.setBackground(Color.white);
		editor2.setForeground(Color.BLACK);
		
		txtFechaS.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					
					if (!((JTextField)txtFechaE.getDateEditor().getUiComponent()).getText().equals("") && !((JTextField)txtFechaS.getDateEditor().getUiComponent()).getText().equals("")){
						Date fechaEntrada = new java.sql.Date(txtFechaE.getDate().getTime());
						Date fechaSalida = new java.sql.Date(txtFechaS.getDate().getTime());
						long diffMili = Math.abs(fechaEntrada.getTime() - fechaSalida.getTime());
						long diff = TimeUnit.DAYS.convert(diffMili, TimeUnit.MILLISECONDS);
						
						txtValor.setText(String.valueOf(diff * valorDiario));
					} 
				}
			}
		});	
		
		txtFechaS.setDateFormatString("yyyy-MM-dd");
		txtFechaS.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaS.setBorder(new LineBorder(new Color(255, 255, 255), 0));
		panel.add(txtFechaS);
		
		JLabel lblValor = new JLabel("VALOR DE LA RESERVA");
		lblValor.setForeground(SystemColor.textInactiveText);
		lblValor.setBounds(72, 303, 285, 14);
		lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblValor);
		
		txtFormaPago = new JComboBox();
		txtFormaPago.setBounds(68, 417, 289, 38);
		txtFormaPago.setBackground(SystemColor.text);
		txtFormaPago.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		txtFormaPago.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFormaPago.setModel(new DefaultComboBoxModel(new String[] {"Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo"}));
		panel.add(txtFormaPago);
		
		JLabel lblFormaPago = new JLabel("FORMA DE PAGO");
		lblFormaPago.setForeground(SystemColor.textInactiveText);
		lblFormaPago.setBounds(68, 382, 187, 24);
		lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblFormaPago);
		
		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setBounds(96, 60, 248, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(428, 0, 482, 560);
		panel_1.setBackground(new Color(12, 138, 199));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setBounds(197, 68, 104, 107);
		panel_1.add(logo);
		logo.setIcon(new ImageIcon(ReservaModificar.class.getResource("/imagenes/Ha-100px.png")));
		
		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 140, 500, 409);
		panel_1.add(imagenFondo);
		imagenFondo.setBackground(Color.WHITE);
		imagenFondo.setIcon(new ImageIcon(ReservaModificar.class.getResource("/imagenes/reservas-img-3.png")));
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal principal = new MenuPrincipal();
				principal.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnexit.setBackground(new Color(12, 138, 199));
			     labelExit.setForeground(Color.white);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setBounds(429, 0, 53, 36);
		panel_1.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
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
		panel.add(header);
		
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
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		
		JPanel btnsiguiente = new JPanel();		
		btnsiguiente.setLayout(null);
		btnsiguiente.setBackground(SystemColor.textHighlight);
		btnsiguiente.setBounds(238, 493, 122, 35);
		panel.add(btnsiguiente);
		btnsiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		JLabel lblSiguiente = new JLabel("GUARDAR");
		lblSiguiente.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguiente.setForeground(Color.WHITE);
		lblSiguiente.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblSiguiente.setBounds(0, 0, 122, 35);
		btnsiguiente.add(lblSiguiente);
		
		txtValor = new JTextField();
		txtValor.setBackground(new Color(255, 255, 255));
		txtValor.setEditable(false);
		txtValor.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtValor.setBorder(null);
		txtValor.setBounds(87, 326, 193, 35);
		panel.add(txtValor);
		txtValor.setColumns(10);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setForeground(SystemColor.textHighlight);
		separator_1_1_1.setBackground(SystemColor.textHighlight);
		separator_1_1_1.setBounds(87, 360, 193, 11);
		panel.add(separator_1_1_1);
		
		txtFechaE.setDate(reserva.getFechaEntrada());
		txtFechaS.setDate(reserva.getFechaSalida());
		txtValor.setText(reserva.getValor());
		
		int itemCount = txtFormaPago.getItemCount();
		
		for (int i = 0; i < itemCount; i++) {
			if (String.valueOf(txtFormaPago.getItemAt(i)).equals(reserva.getFormaPago())) {
				txtFormaPago.setSelectedIndex(i);
				break;
			}
		}
		
		btnsiguiente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ReservaModificar.txtFechaE.getDate() != null && ReservaModificar.txtFechaS.getDate() != null) {	
					String fechaE = ((JTextField)txtFechaE.getDateEditor().getUiComponent()).getText();
			    	String fechaS = ((JTextField)txtFechaS.getDateEditor().getUiComponent()).getText();	    
					int cambios = reservaController.actualizarReserva(java.sql.Date.valueOf(fechaE), java.sql.Date.valueOf(fechaS), txtValor.getText(), txtFormaPago.getSelectedItem().toString(), reserva.getId());
					JOptionPane.showMessageDialog(null, "Reserva Modificada");
					Busqueda busqueda = new Busqueda();
					busqueda.setVisible(true);
					dispose();
					
				} else {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
				}
			}						
		});
		
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
