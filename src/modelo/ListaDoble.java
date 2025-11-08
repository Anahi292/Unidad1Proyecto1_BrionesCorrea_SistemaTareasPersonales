package modelo;

public class ListaDoble {

	// CLASE INTERNA NODO
	private static class Nodo {
		Object dato;
		Nodo siguiente;
		Nodo anterior;

		Nodo(Object d) {
			dato = d;
			siguiente = null;
			anterior = null;
		}
	}

	// CABEZA Y COLA DE LA LISTA
	private Nodo cabeza;
	private Nodo cola;

	// CONSTRUCTOR CREA LISTA VACIA
	public ListaDoble() {
		cabeza = null;
		cola = null;
	}

	// INSERTA AL INICIO 
	public void insertAtHead(Object dato) {
		Nodo nuevo = new Nodo(dato);
		if (cabeza == null) {
			cabeza = cola = nuevo;
		} else {
			nuevo.siguiente = cabeza;
			cabeza.anterior = nuevo;
			cabeza = nuevo;
		}
	}

	// INSERTA AL FINAL - ASI LA LISTA MUESTRA EN ORDEN DE REGISTRO
	public void insertAtTail(Object dato) {
		Nodo nuevo = new Nodo(dato);
		if (cola == null) { // LISTA VACIA
			cabeza = cola = nuevo;
		} else {
			cola.siguiente = nuevo;
			nuevo.anterior = cola;
			cola = nuevo;
		}
	}

	// ELIMINA LA PRIMERA OCURRENCIA DEL DATO
	public void delete(Object dato) {
		Nodo actual = cabeza;
		while (actual != null) {
			if (actual.dato.equals(dato)) {
				if (actual == cabeza) {
					cabeza = actual.siguiente;
					if (cabeza != null)
						cabeza.anterior = null;
					else
						cola = null; // SE VACIÓ LA LISTA
				} else if (actual == cola) {
					cola = actual.anterior;
					if (cola != null)
						cola.siguiente = null;
					else
						cabeza = null;
				} else {
					actual.anterior.siguiente = actual.siguiente;
					actual.siguiente.anterior = actual.anterior;
				}
				return;
			}
			actual = actual.siguiente;
		}
	}

	// MUESTRA LA LISTA 
	public void showList() {
		Nodo aux = cabeza;
		if (aux == null) {
			System.out.println("LA LISTA ESTA VACIA");
			return;
		}
		while (aux != null) {
			System.out.println(aux.dato);
			aux = aux.siguiente;
		}
	}

	// DEVUELVE TRUE SI ESTA VACIA
	public boolean isEmpty() {
		return cabeza == null;
	}

	// CONVIERTE LA LISTA PERSONALIZA EN UNA ESTANDAR PARA PODER USAR TODAS LAS FUNCIONES 
	public java.util.List<Object> toList() {
		java.util.List<Object> lista = new java.util.ArrayList<>();
		Nodo actual = cabeza;
		while (actual != null) {
			lista.add(actual.dato);
			actual = actual.siguiente;
		}
		return lista;
	}
}
