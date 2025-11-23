package modelo;

public class PilaLista {
    private Nodo cima; 
    public PilaLista() {
        cima = null;
    }

    // INSERTA UN ELEMENTO AL INICIO (COMPORTAMIENTO LIFO)
    public void pushPila(Object elemento) {
        Nodo nuevo = new Nodo(elemento);
        nuevo.setSiguiente(cima);
        cima = nuevo;
    }

    // VERIFICA SI LA PILA ESTA VACIA
    public boolean pilaVacia() {
        return cima == null;
    }

    // ELIMINA EL ULTIMO ELEMENTO INGRESADO
    public Object popPila() throws Exception {
        if (pilaVacia()) {
            throw new Exception("PILA VACIA, NO SE PUEDE EXTRAER");
        }
        Object aux = cima.getElemento();
        cima = cima.getSiguiente();
        return aux;
    }

    // ELIMINA TODOS LOS ELEMENTOS DE LA PILA
    public void limpiarPila() {
        while (!pilaVacia()) {
            cima = cima.getSiguiente();
        }
    }
}
