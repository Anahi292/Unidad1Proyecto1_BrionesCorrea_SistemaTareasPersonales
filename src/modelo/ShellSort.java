package modelo;
import java.util.List;

public class ShellSort {

    // ORDENA UNA LISTA DE TAREAS POR DESCRIPCION
    public static void ordenarPorDescripcion(List<Tarea> lista) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Tarea temp = lista.get(i);
                int j;
                for (j = i; j >= gap && lista.get(j - gap).getDescripcion().compareToIgnoreCase(temp.getDescripcion()) > 0; j -= gap) {
                    lista.set(j, lista.get(j - gap));
                }
                lista.set(j, temp);
            }
        }
    }

    // ORDENA UNA LISTA DE TAREAS POR CATEGORIA
    public static void ordenarPorCategoria(List<Tarea> lista) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Tarea temp = lista.get(i);
                int j;
                for (j = i; j >= gap && lista.get(j - gap).getCategoria().compareToIgnoreCase(temp.getCategoria()) > 0; j -= gap) {
                    lista.set(j, lista.get(j - gap));
                }
                lista.set(j, temp);
            }
        }
    }
}