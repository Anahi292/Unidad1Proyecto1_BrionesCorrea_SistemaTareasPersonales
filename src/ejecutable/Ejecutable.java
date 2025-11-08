package ejecutable;

import modelo.*;
import vista.*;
import controlador.*;

public class Ejecutable {

	public static void main(String[] args) {
		GestorTareas modelo = new GestorTareas();
		VistaTareass vista = new VistaTareass();
		new ControladorTareas(modelo, vista);
		vista.setVisible(true);


	}

}
