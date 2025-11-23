package modelo;

public class Nodo {

	private Object elemento;
	private Nodo siguiente;
	private Nodo anterior;

	public Nodo(Object elemento) {
		this.elemento = elemento;
		this.siguiente = null;
		this.anterior = null;
	}

	// GETTERS Y SETTERS
	public Object getElemento() {
		return elemento;
	}

	public void setElemento(Object elemento) {
		this.elemento = elemento;
	}

	public Nodo getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo siguiente) {
		this.siguiente = siguiente;
	}

	public Nodo getAnterior() {
		return anterior;
	}

	public void setAnterior(Nodo anterior) {
		this.anterior = anterior;
	}
}
