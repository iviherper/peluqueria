import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Modelo {
	private Vista miVista;

	private boolean insertado;
	private Connection conexion;
	private DefaultTableModel table;

	private String sqlMostrar = "SELECT NOMBRE, APELLIDOS, TINTE, MECHAS, FECHA FROM ivan.CLIENTES";
	private String user = "";
	private String pas = "";
	private String miUrl = "";
	private String ssql;

	public void setMiVista(Vista miVista) {
		this.miVista = miVista;
	}

	/**
	 * Este metodo seleccona e fichero ini y recoge datos de el.
	 * 
	 * @throws FileNotFoundException
	 */
	public void seleccionarFichero() throws FileNotFoundException {
		File file = new File("Ajustes.ini");
		int vez = 0;
		if (file.exists()) {
			try {
				Scanner sc = new Scanner(file);
				String[] texto = new String[3];
				while (sc.hasNext()) {
					texto[vez] = sc.nextLine();
					vez++;
				}
				this.user = texto[0];
				this.pas = texto[1];
				this.miUrl = texto[2];
			} catch (IOException e) {
				System.out.println("Error al leer el fichero");
			}
		} else {
			System.out.println("El fichero no existe");
		}
	}

	public String usuario() {
		return this.user;
	}

	public String password() {
		return this.pas;
	}

	public String UrlConexion() {
		return this.miUrl;
	}

	/**
	 * Este metodo es para la conexion con la BBDD.
	 */
	public void ConexionBBDD() {
		try {
			try {
				seleccionarFichero();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection(miUrl, user, pas);
			System.out.println("Conexion con ORACLE establecida");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC No encontrado");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error al conectarse a la BBDD");
			e.printStackTrace();
		}

	}

	public void terminar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int getNumColumnas(String ssql) {
		int num = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(ssql);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			num = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public String getLisCli() {
		return sqlMostrar;
	}
	
	public void conectar() {
		ConexionBBDD();
	}

	public DefaultTableModel getTabla(String ssql) {
		table = new DefaultTableModel();
		int numColumnas = getNumColumnas(ssql);
		Object[] contenido = new Object[numColumnas];
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(ssql);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			for (int i = 0; i < numColumnas; i++) {
				table.addColumn(rsmd.getColumnName(i + 1));
			}
			while (rset.next()) {
				for (int col = 1; col <= numColumnas; col++) {
					contenido[col - 1] = rset.getString(col);
				}
				table.addRow(contenido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	public void insertarCliente(String nombre, String apellidos, String tinte, String mechas, String fecha) {
		String sql = "INSERT INTO ivan.CLIENTES (nombre,apellidos,tinte,mechas,fecha) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setString(1, nombre);
			stmt.setString(2, apellidos);
			stmt.setString(3, tinte);
			stmt.setString(4, mechas);
			stmt.setString(5, fecha);
			stmt.executeUpdate();
			insertado = true;

			System.out.println("Guardado con exito");
		} catch (SQLException e) {
			insertado = false;
			System.out.println("Algun dato incorrecto.");
		}

	}

	public void borrarCliente(String nombre, String apellidos, String fecha) {
		String sql = "DELETE FROM ivan.CLIENTES WHERE nombre = ? AND apellidos=? AND fecha=?";
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setString(1, nombre);
			stmt.setString(2, apellidos);
			stmt.setString(3, fecha);
			stmt.executeUpdate();
			insertado = true;
			System.out.println("Eliminado con exito");

		} catch (SQLException e) {
			insertado = false;
			System.out.println("Algun dato incorrecto.");
		}

	}

	public void modificarCliente(String nombre, String apellidos, String tinte, String mechas, String fecha, String nomAnt, String apAnt, String fechaAnt) {
		String sql = "UPDATE ivan.CLIENTES SET nombre=?,apellidos=?,tinte=?,mechas=?,fecha=? WHERE nombre=? AND apellidos=? AND fecha=? ";
		System.out.println(nombre+  apellidos+  tinte+  mechas+  fecha+  nomAnt+  apAnt+  fechaAnt);
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setString(1, nombre);
			stmt.setString(2, apellidos);
			stmt.setString(3, tinte);
			stmt.setString(4, mechas);
			stmt.setString(5, fecha);
			stmt.setString(6, nomAnt);
			stmt.setString(7, apAnt);
			stmt.setString(8, fechaAnt);
			stmt.executeUpdate();
			System.out.println("Guardado con exito");
			System.out.println(sql);
			insertado = true;

		} catch (SQLException e) {
			insertado = false;
			System.out.println("Algún dato incorrecto");
		}

	}
	
	public boolean malInsertado() {
		boolean resultado;
		if (insertado == true) {
			resultado = true;
		} else {
			resultado = false;
		}
		return resultado;
	}
	
	public void filtrarLisCli(String where) {
		if (where == "")
			JOptionPane.showMessageDialog(null, "Debe introducir algun dato para filtrar", "Adevertencia",
					JOptionPane.WARNING_MESSAGE);
		else {
			// Quitamos el ultimo AND sobrante
			int cant = where.length();
			where = where.subSequence(0, cant - 4).toString();
			// Creamos la consulta y pasamos la condición where y devolvemos el filtrado a
			// la interfaz que sea
				miVista.generaFiltro(getTabla(sqlMostrar + " where" + where));
		}

	}

}
