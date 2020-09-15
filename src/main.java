import java.sql.SQLException;

public class main {

	public static void main(String[] args) throws SQLException {

		/**
		 * Inicilizamos todas la Interfaces y Clases.
		 */
		Controlador miControlador = new Controlador();
		Modelo miModelo = new Modelo();

		Vista miVista = new Vista();
		/**
		 * Aqui le marcamos al controlador todas las clases e interfaces que debe
		 * conocer.
		 */
		miControlador.setMiModelo(miModelo);
		miControlador.setMiVista(miVista);

		/**
		 * Aqui le marcamos al modelo todas las clases e interfaces que debe conocer.
		 */
		miModelo.setMiVista(miVista);

		/**
		 * Es aqui donde hacemos que las interfaces conozcan a modelo y a controlador
		 * 
		 */
		miVista.setMiControlador(miControlador);
		miVista.setMiModelo(miModelo);
		
		miModelo.conectar();
		miVista.setVisible(true);
		
	}

}
