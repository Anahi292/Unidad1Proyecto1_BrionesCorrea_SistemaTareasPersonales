package modelo;

public class PilaLista {

	// NODO QUE REPRESENTA LA CIMA 
	private NodoPila cima;

	// CONSTRUCTOR
	public PilaLista() {
		cima = null;
	}

	//AGREGA UN ELEMNTO A LA PILA 
	public void pushPila(Object elemento) {
		NodoPila nuevo = new NodoPila(elemento); // CREA UN NUEVO NODO
		nuevo.setSiguiente(cima); 
		cima = nuevo; 
	}

	//VERIFICA SI LA PILA ESTA VACIA 
	public boolean pilaVacia() {
		return cima == null;
	}

	//EXTRAE EL ELEMNTO DE LA CIMA(POP)
	public Object popPila() throws Exception {
		if (pilaVacia()) {
			throw new Exception("PILA VACIA, NO SE PUEDE EXTRAER"); 
		}
		Object aux = cima.getElemento(); 
		cima = cima.getSiguiente(); 
		return aux; 
	}

	//LIMPIA TODA LA PILA
	public void limpiarPila() {
		while (!pilaVacia()) {
			cima = cima.getSiguiente(); 
		}
	}

	// CLASE INTERNA NODOPILA
	// REPRESENTA CADA ELEMENTO DE LA PILA
	class NodoPila {
		private Object elemento; // DATO GUARDADO
		private NodoPila siguiente; // REFERENCIA AL NODO DE ABAJO

		// CONSTRUCTOR DEL NODO
		public NodoPila(Object x) {
			elemento = x;
			siguiente = null;
		}

		// GETTER Y SETTER
		public Object getElemento() {
			return elemento;
		}

		public NodoPila getSiguiente() {
			return siguiente;
		}

		public void setSiguiente(NodoPila siguiente) {
			this.siguiente = siguiente;
		}
	}
}
