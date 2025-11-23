package modelo;

public class ColaLista {

    protected Nodo frente;   // PRIMER ELEMENTO
    protected Nodo fin;      // ULTIMO ELEMENTO

    public ColaLista() {
        frente = fin = null;
    }

    // INSERTA AL FINAL (COMPORTAMIENTO FIFO)
    public void insertar(Object elemento) {
        Nodo nuevo = new Nodo(elemento);
        if (colaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
    }

    // ELIMINA EL PRIMER ELEMENTO QUE ENTRO
    public Object quitar() throws Exception {
        if (colaVacia()) {
            throw new Exception("ELIMINAR DE UNA COLA VACIA");
        }
        Object aux = frente.getElemento();
        frente = frente.getSiguiente();
        if (frente == null) fin = null;   // SI QUEDA VACIA
        return aux;
    }

    // LIMPIA COMPLETAMENTE LA COLA
    public void borrarCola() {
        frente = fin = null;
        System.gc();
    }

    // DEVUELVE EL PRIMER ELEMENTO SIN ELIMINARLO
    public Object frenteCola() throws Exception {
        if (colaVacia()) {
            throw new Exception("COLA VACIA");
        }
        return frente.getElemento();
    }

    // VERIFICA SI ESTA VACIA
    public boolean colaVacia() {
        return frente == null;
    }
}
