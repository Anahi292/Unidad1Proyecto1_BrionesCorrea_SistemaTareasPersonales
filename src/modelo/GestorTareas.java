package modelo;	

public class GestorTareas {
    // ESTRUCTURAS DE DATOS
    private ListaDoble tareasPendientes;   // LISTA DOBLE
    private ColaLista tareasUrgentes;      // COLA
    private PilaLista tareasCompletadas;   // PILA
    // CONSTRUCTOR
    public GestorTareas() {
        tareasPendientes = new ListaDoble();
        tareasUrgentes = new ColaLista();
        tareasCompletadas = new PilaLista();
    }
    // AGREGA UNA NUEVA TAREA
    // SI ES URGENTE TAMBIEN LA PONE EN LA COLA
    public void agregarTarea(Tarea tarea) {
        tareasPendientes.insertAtTail(tarea);
        if (tarea.isUrgente()) {
            tareasUrgentes.insertar(tarea);
        }
    }
    // COMPLETA UNA TAREA
    // CAMBIA ESTADO, LA PONE EN LA PILA Y LA QUITA DE LISTA Y COLA
    public void completarTarea(Tarea tarea) {
        tarea.setEstado("Completada");
        tareasCompletadas.pushPila(tarea);
        tareasPendientes.delete(tarea);
        try {
            eliminarDeCola(tarea);
        } catch (Exception e) {
            // IGNORA SI NO ESTÁ EN LA COLA
        }
    }
    // ELIMINA UNA TAREA DEFINITIVAMENTE
    public void eliminarTarea(Tarea tarea) {
        tareasPendientes.delete(tarea);
        try {
            eliminarDeCola(tarea);
        } catch (Exception e) {
            // NO HACE NADA SI NO ESTA
        }
    }
    // DESHACE LA ULTIMA TAREA COMPLETADA SACA DE LA PILA
    public void deshacerUltimaCompletada() throws Exception {
        if (!tareasCompletadas.pilaVacia()) {
            Tarea t = (Tarea) tareasCompletadas.popPila();
            t.setEstado("Pendiente");
            tareasPendientes.insertAtHead(t);
        }
    }
    // ELIMINA UNA TAREA DE LA COLA DE URGENTES
    // SE USA UNA COLA TEMPORAL PARA MANTENER EL ORDEN
    private void eliminarDeCola(Tarea tarea) throws Exception {
        ColaLista temp = new ColaLista();
        while (!tareasUrgentes.colaVacia()) {
            Tarea t = (Tarea) tareasUrgentes.quitar();
            if (!t.equals(tarea)) {
                temp.insertar(t);
            }
        }
        while (!temp.colaVacia()) {
            tareasUrgentes.insertar(temp.quitar());
        }
    }
    // RETORNA LAS ESTRUCTURAS
    public ListaDoble getTareasPendientes() {
        return tareasPendientes;
    }

    public ColaLista getTareasUrgentes() {
        return tareasUrgentes;
    }

    public PilaLista getTareasCompletadas() {
        return tareasCompletadas;
    }
    // MUESTRA LAS TAREAS PENDIENTES COMO TEXTO
    public String mostrarPendientes() {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        tareasPendientes.showList();
        System.out.flush();
        System.setOut(old);
        return baos.toString();
    }
    // MUESTRA LAS TAREAS URGENTES COMO TEXTO
    public String mostrarUrgentes() {
        StringBuilder sb = new StringBuilder();
        try {
            ColaLista temp = new ColaLista();
            while (!tareasUrgentes.colaVacia()) {
                Tarea t = (Tarea) tareasUrgentes.quitar();
                sb.append(t.toString()).append("\n");
                temp.insertar(t);
            }
            while (!temp.colaVacia()) {
                tareasUrgentes.insertar(temp.quitar());
            }
        } catch (Exception e) {
            sb.append("NO HAY TAREAS URGENTES\n");
        }
        return sb.toString();
    }
    // MUESTRA LAS TAREAS COMPLETADAS COMO TEXTO
    public String mostrarCompletadas() {
        StringBuilder sb = new StringBuilder();
        try {
            PilaLista temp = new PilaLista();
            while (!tareasCompletadas.pilaVacia()) {
                Tarea t = (Tarea) tareasCompletadas.popPila();
                sb.append(t.toString()).append("\n");
                temp.pushPila(t);
            }
            while (!temp.pilaVacia()) {
                tareasCompletadas.pushPila(temp.popPila());
            }
        } catch (Exception e) {
            sb.append("NO HAY TAREAS COMPLETADAS\n");
        }
        return sb.toString();
    }
}
