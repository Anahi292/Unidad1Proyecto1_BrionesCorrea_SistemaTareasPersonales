package modelo;

public class ColaLista {
	
	// CLASE INTERNA NODO
		// REPRESENTA CADA ELEMENTO DE LA COLA
		class Nodo {
			Object elemento; 
			Nodo siguiente;  

			// CONSTRUCTOR DEL NODO
			public Nodo(Object x) {
				elemento = x;
				siguiente = null;
			}
		}
	
	// NODOS QUE MARCAN EL INICIO Y EL FINAL  DE LA COLA
	protected Nodo frente;
	protected Nodo fin;

	// CONSTRUCTOR
	public ColaLista() {
		frente = fin = null;
	}

	//INSERTA UN ELEMNTO AL FINAL DE LA COLA
	public void insertar(Object elemento) {
		Nodo nuevo = new Nodo(elemento); 
		if (colaVacia()) {
			frente = nuevo; 
		} else {
			fin.siguiente = nuevo; 
		}
		fin = nuevo; 
	}

	// ELIMINA EL ELEMNTO DEL FRENTE DE LA COLA 
	public Object quitar() throws Exception {
		Object aux;
		if (!colaVacia()) {
			aux = frente.elemento; 
			frente = frente.siguiente; 
		} else {
			throw new Exception("ELIMINAR DE UNA COLA VACIA"); 
		}
		return aux; 
	}

	// BORRA TODOS LOS ELEMENTOS DE LA COLA 
	public void borrarCola() {
		for (; frente != null;) {
			frente = frente.siguiente; 
		}
		System.gc(); 
	}

	//CONSULTA EL ELEMENTO DEL FRENTE SIN ELIMINARLO
	public Object frenteCola() throws Exception {
		if (colaVacia()) {
			throw new Exception("COLA VACIA"); 
		}
		return frente.elemento; 
	}

	// VERIFICA SI LA COLA ESTA VACIA 
	public boolean colaVacia() {
		return (frente == null);
	}

}
