import java.text.SimpleDateFormat;
import java.util.Date;

public class Controlador {
	private Vista miVista;
	private Modelo miModelo;
	
	public void setMiModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	public void setMiVista(Vista miVista) {
		this.miVista = miVista;
	}
	
	public void filtrarCli(String[] datos) {
		String where = "";
		if (!datos[0].equals(""))
			where += " lower(nombre) LIKE lower('" + datos[0] + "') || '%' AND";
		if (!datos[1].equals(""))
			where += " lower(apellidos) LIKE lower('" + datos[1] + "') || '%' AND";
		if (!datos[2].equals(""))
			where += " lower(tinte) LIKE lower('" + datos[2] + "') || '%' AND";
		if (!datos[3].equals(""))
			where += " lower(mechas) LIKE lower('" + datos[3] + "') || '%' AND";
		if (!datos[4].equals("") && !datos[5].equals(""))
			where += " Fecha between '" + datos[4] + "' and '" + datos[5] + "' AND";
		else if (!datos[4].equals("")) {
			Date myDate = new Date();
			where += " Fecha between '" + datos[4] + "' and '" + new SimpleDateFormat("dd-MM-yyyy").format(myDate)
					+ "' AND";
		} else if (!datos[5].equals("")) {
			where += " Fecha between '01011800' and '" + datos[5] + "' AND";
		}

		miModelo.filtrarLisCli(where);
	}
	
	public void borrarCli() {
		String nombre = miVista.getNombre();
		String apellidos = miVista.getApellidos();
		String fecha = miVista.getFecha();
		miModelo.borrarCliente(nombre, apellidos, fecha);
	}
	
	public void modificarCliente(String nomAnt, String apeAnt, String fecAnt) {
		String fecha = miVista.getFecha();
		String nombre = miVista.getNombre();
		String apellidos = miVista.getApellidos();
		String tinte = miVista.getTinte();
		String mechas = miVista.getMechas();
		System.out.println(fecha);
		miModelo.modificarCliente(nombre, apellidos, tinte, mechas, fecha, nomAnt, apeAnt, fecAnt);
	}
	
	public String modificarFecha(String fec) {
		String Fec_naci = fec.substring(0, 10);
		String[] fechaa = Fec_naci.split("-");
		Fec_naci = fechaa[2] + "/" + fechaa[1] + "/" + fechaa[0];
		return Fec_naci;
	}
	
	public void insertarAlumno() {
		String fecha = miVista.getFecha();
		String nombre = miVista.getNombre();
		String apellidos = miVista.getApellidos();
		String tinte = miVista.getTinte();
		String mechas = miVista.getMechas();
		System.out.println(fecha);
		miModelo.insertarCliente(nombre, apellidos, tinte, mechas, fecha);
	}
	
	
}
