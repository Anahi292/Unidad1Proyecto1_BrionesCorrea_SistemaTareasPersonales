package modelo;

import java.util.ArrayList;
import java.util.List;

public class ListaDoble {

    private Nodo cabeza;   // PRIMER ELEMENTO
    private Nodo cola;     // ULTIMO ELEMENTO

    public ListaDoble() {
        cabeza = null;
        cola = null;
    }

    // INSERTA AL INICIO DE LA LISTA
    public void insertAtHead(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = cola = nuevo;
        } else {
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
            cabeza = nuevo;
        }
    }

    // INSERTA AL FINAL DE LA LISTA
    public void insertAtTail(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (cola == null) {
            cabeza = cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
    }

    // ELIMINA LA PRIMERA COINCIDENCIA DEL ELEMENTO
    public void delete(Object dato) {
        Nodo actual = cabeza;

        while (actual != null) {
            if (actual.getElemento().equals(dato)) {

                // CASO 1: ES LA CABEZA
                if (actual == cabeza) {
                    cabeza = actual.getSiguiente();
                    if (cabeza != null) cabeza.setAnterior(null);
                    else cola = null; // LISTA VACIA
                }

                // CASO 2: ES LA COLA
                else if (actual == cola) {
                    cola = actual.getAnterior();
                    if (cola != null) cola.setSiguiente(null);
                    else cabeza = null; // LISTA VACIA
                }

                // CASO 3: ESTA EN MEDIO
                else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    // VERIFICA SI LA LISTA ESTA VACIA
    public boolean isEmpty() {
        return cabeza == null;
    }

    // RETORNA LA LISTA EN FORMA DE ARRAYLIST
    public List<Object> toList() {
        List<Object> lista = new ArrayList<>();
        Nodo aux = cabeza;

        while (aux != null) {
            lista.add(aux.getElemento());
            aux = aux.getSiguiente();
        }

        return lista;
    }
    
 // MUESTRA LA LISTA
    public void showList() {
        Nodo aux = cabeza;

        if (aux == null) {
            System.out.println("LA LISTA ESTA VACIA");
            return;
        }

        while (aux != null) {
            System.out.println(aux.getElemento());  
            aux = aux.getSiguiente();               
        }
    }
}
