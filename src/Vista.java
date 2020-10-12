import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javaas.TextPrompt;
import javax.swing.text.JTextComponent;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private JFrame frame;
	private Controlador miControlador;
	private Modelo miModelo;
	private JPanel contentPane;
	private JTable table;
	private JTable tabla;
	private JPanel panel;
	private JPanel raya;
	private JScrollPane scrollPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtTinte;
	private JTextField txtMechas;
	private JTextField txtFecha;
	private JButton btnFiltrar;
	private JPanel panel_modif;
	private JTextField filNombre;
	private JTextField filApellidos;
	private JTextField filTinte;
	private JTextField filMechas;
	private JTextField filFecha;
	private JTextField txtFechaNext;

	public void setMiControlador(Controlador miControlador) {
		this.miControlador = miControlador;
	}

	public void setMiModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	public String getNombre() {
		String nombre = filNombre.getText();
		return nombre;
	}

	public String getApellidos() {
		String apellidos = filApellidos.getText();
		return apellidos;
	}

	public String getTinte() {
		String tinte = filTinte.getText();
		return tinte;
	}

	public String getMechas() {
		String mechas = filMechas.getText();
		return mechas;
	}

	public String getFecha() {
		String fecha = filFecha.getText();
		return fecha;
	}

	public Vista() {
		getContentPane().setBackground(new Color(255, 245, 238));

		setTitle("Unisex Belly");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 720);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(255, 250, 250));
		panel.setBounds(0, 87, 1275, 594);
		getContentPane().add(panel);
		panel.setLayout(null);
		panel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				table.getSelectionModel().clearSelection();
//				limpiarCampos();
//			}
		});

		raya = new JPanel();
		raya.setBackground(new Color(220, 20, 60));
		raya.setBounds(0, 0, 1265, 4);
		panel.add(raya);
		raya.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 103, 822, 450);
		panel.add(scrollPane);

		tabla = new JTable();
		scrollPane.setColumnHeaderView(tabla);

		txtNombre = new JTextField();
		txtNombre.setBounds(23, 59, 85, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		table = new JTable();
		contentPane = new JPanel();
		table.setAutoCreateRowSorter(true);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				actualizarDatos();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		/**
		 * Conseguir sql Listado de alumnos
		 */
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				String ssql = miModelo.getLisCli();
				table.setModel(miModelo.getTabla(ssql));
			}
		});

		/**
		 * Actualiza los TextField
		 */
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				actualizarDatos();
			}
		});

		txtApellidos = new JTextField();
		txtApellidos.setBounds(118, 59, 85, 20);
		panel.add(txtApellidos);
		txtApellidos.setColumns(10);

		txtTinte = new JTextField();
		txtTinte.setColumns(10);
		txtTinte.setBounds(213, 59, 85, 20);
		panel.add(txtTinte);

		txtMechas = new JTextField();
		txtMechas.setColumns(10);
		txtMechas.setBounds(308, 59, 85, 20);
		panel.add(txtMechas);

		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(403, 59, 85, 20);
		panel.add(txtFecha);
		
		txtFechaNext = new JTextField();
		txtFechaNext.setColumns(10);
		txtFechaNext.setBounds(498, 59, 85, 20);
		panel.add(txtFechaNext);
		
		TextPrompt placeNombre = new TextPrompt("Nombre", txtNombre);
		TextPrompt placeApellidos = new TextPrompt("Apellidos", txtApellidos);
		TextPrompt placeTinte = new TextPrompt("Tinte", txtTinte);
		TextPrompt placeMechas = new TextPrompt("Mechas", txtMechas);
		TextPrompt placeFecha = new TextPrompt("Fecha Inicio", txtFecha);
		TextPrompt placeFechaFin = new TextPrompt("Fecha Fin", txtFechaNext);

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miControlador.filtrarCli(getDatos());
			}
		});
		btnFiltrar.setBackground(new Color(220, 220, 220));
		btnFiltrar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnFiltrar.setBounds(747, 53, 100, 30);
		panel.add(btnFiltrar);

		panel_modif = new JPanel();
		panel_modif.setBackground(new Color(220, 20, 60, 190));
		panel_modif.setBounds(873, 103, 371, 450);
		panel.add(panel_modif);
		panel_modif.setLayout(null);

		JLabel lblModif = new JLabel("Edici\u00F3n Clientes");
		lblModif.setForeground(new Color(255, 255, 255));
		lblModif.setHorizontalAlignment(SwingConstants.CENTER);
		lblModif.setFont(new Font("Arial Black", Font.PLAIN, 30));
		lblModif.setBounds(51, 34, 283, 38);
		panel_modif.add(lblModif);

		filNombre = new JTextField();
		filNombre.setBackground(new Color(255, 255, 255));
		filNombre.setBounds(38, 126, 182, 20);
		panel_modif.add(filNombre);
		filNombre.setColumns(10);

		filApellidos = new JTextField();
		filApellidos.setBackground(new Color(255, 255, 255));
		filApellidos.setBounds(38, 156, 182, 20);
		panel_modif.add(filApellidos);
		filApellidos.setColumns(10);

		filTinte = new JTextField();
		filTinte.setColumns(10);
		filTinte.setBackground(Color.WHITE);
		filTinte.setBounds(38, 187, 182, 20);
		panel_modif.add(filTinte);

		filMechas = new JTextField();
		filMechas.setColumns(10);
		filMechas.setBackground(Color.WHITE);
		filMechas.setBounds(38, 218, 182, 20);
		panel_modif.add(filMechas);

		filFecha = new JTextField();
		filFecha.setColumns(10);
		filFecha.setBackground(Color.WHITE);
		filFecha.setBounds(38, 249, 182, 20);
		panel_modif.add(filFecha);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fsel = table.getSelectedRow();
				int resp = JOptionPane.YES_NO_OPTION;
				int option;
				if (fsel == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar el cliente a eliminar", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
				} else {
					option = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este cliente?", "Eliminar",
							resp);

					if (option == 0) {
						miControlador.borrarCli();
						limpiarCampos();
						JOptionPane.showMessageDialog(null, "La fila ha sido borrada con éxito", "Advertencia",
								JOptionPane.INFORMATION_MESSAGE);

					}

				}
			}
		});
		btnBorrar.setBounds(30, 352, 100, 30);
		panel_modif.add(btnBorrar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean fsel = false;
				if (!filNombre.getText().isEmpty() && !filApellidos.getText().isEmpty()
						&& !filFecha.getText().isEmpty()) {
					fsel = true;
				}
				int resp = JOptionPane.YES_NO_OPTION;
				int option;
				if (!fsel) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar el cliente a modificar", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
				} else {
					option = JOptionPane.showConfirmDialog(null, "Está seguro de modificar este cliente?", "Modificar",
							resp);
					if (option == 0) {
						miControlador.modificarCliente(getDatosOld()[0], getDatosOld()[1], getDatosOld()[2]);
						limpiarCampos();
						boolean insertado = miModelo.malInsertado();
						if (insertado) {
							JOptionPane.showMessageDialog(null, "La fila ha sido modificada con éxito", "Advertencia",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"La fila no ha sido modificada, datos mal introducidos ", "Advertencia",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}

				}
			}
		});
		btnModificar.setBounds(140, 352, 100, 30);
		panel_modif.add(btnModificar);

		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fsel = table.getSelectedRow();
				miControlador.insertarAlumno();
				limpiarCampos();
				boolean insertado = miModelo.malInsertado();
				if (insertado) {
					JOptionPane.showMessageDialog(null, "La nueva fila ha sido insertada con éxito", "Advertencia",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "La nueva fila no ha sido insertada, datos mal introducidos ",
							"Advertencia", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		btnInsertar.setBounds(250, 352, 100, 30);
		panel_modif.add(btnInsertar);

		TextPrompt EdiNombre = new TextPrompt("Nombre", filNombre);
		TextPrompt EdiApellidos = new TextPrompt("Apellidos", filApellidos);
		TextPrompt EdiTinte = new TextPrompt("Tinte", filTinte);
		TextPrompt EdiMechas = new TextPrompt("Mechas", filMechas);
		TextPrompt EdiFecha = new TextPrompt("Fecha", filFecha);

		JLabel lblTitulo = new JLabel("Unisex Belly");
		lblTitulo.setForeground(new Color(220, 20, 60));
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 37));
		lblTitulo.setBounds(547, 21, 209, 39);

		getContentPane().add(lblTitulo);
	}

	private void limpiarCampos() {
		filNombre.setText("");
		filApellidos.setText("");
		filTinte.setText("");
		filMechas.setText("");
		filFecha.setText("");

	}

	/**
	 * Permite modificar la fila obteniendo el DNI antiguo(Primary Key)
	 * 
	 * @return DNI
	 */
	private String[] getDatosOld() {
		int fila = table.getSelectedRow();
		String[] datosOld = new String[3];
		datosOld[0] = (String) table.getValueAt(fila, 0);
		datosOld[1] = (String) table.getValueAt(fila, 1);
		String fecha = (String) table.getValueAt(fila, 4);
//		String[] fechaa = fecha.split("/");
//		fecha = fechaa[2] + "/" + fechaa[1] + "/" + fechaa[0];
		datosOld[2] = fecha;

		return datosOld;
	}

	/**
	 * Rellena los textField
	 */
	private void actualizarDatos() {
		int fila = table.getSelectedRow();
		String fecha = (String) table.getValueAt(fila, 4);
		filNombre.setText((String) table.getValueAt(fila, 0));
		filApellidos.setText((String) table.getValueAt(fila, 1));
		filTinte.setText((String) table.getValueAt(fila, 2));
		filMechas.setText((String) table.getValueAt(fila, 3));
//		String[] fechaa = fecha.split("/");
//		fecha = fechaa[2] + "/" + fechaa[1] + "/" + fechaa[0];
		filFecha.setText(fecha);
	}

	public String[] getDatos() {
		String[] datos = new String[6];
		datos[0] = txtNombre.getText();
		datos[1] = txtApellidos.getText();
		datos[2] = txtTinte.getText();
		datos[3] = txtMechas.getText();
		datos[4] = txtFecha.getText();
		datos[5] = txtFechaNext.getText();
		return datos;
	}

	public void generaFiltro(DefaultTableModel tabla) {
		table.setModel(tabla);
	}
}
