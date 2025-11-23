package controlador;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.ColaLista;
import modelo.GestorTareas;
import modelo.PilaLista;
import modelo.Tarea;
import vista.VistaTareass;

public class ControladorTareas {

    private GestorTareas modelo;
    private VistaTareass vista;

    public ControladorTareas(GestorTareas modelo, VistaTareass vista) {
        this.modelo = modelo;
        this.vista = vista;

        vista.btnAgregar.addActionListener(e -> agregar());
        vista.btnOrdenar.addActionListener(e -> ordenarShell());
        vista.btnCompletar.addActionListener(e -> completar());
        vista.btnEliminar.addActionListener(e -> eliminar());
        vista.btnDeshacer.addActionListener(e -> deshacer());

        actualizarTablas();
    }

    // AGREGAR
    private void agregar() {

        JTextField txtDescripcion = new JTextField();
        JTextArea txtNotas = new JTextArea(3, 15);
        String[] categorias = {"Trabajo", "Personal", "Estudio", "Otro"};
        JComboBox<String> cmbCategoria = new JComboBox<>(categorias);
        JCheckBox chkUrgente = new JCheckBox("Es urgente");

        Object[] mensaje = {
                "Categoría:", cmbCategoria,
                "Descripción:", txtDescripcion,
                "Notas:", new JScrollPane(txtNotas),
                chkUrgente
        };

        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Agregar nueva tarea",
                JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {

            String descripcion = txtDescripcion.getText().trim();
            String categoria = cmbCategoria.getSelectedItem().toString();
            String notas = txtNotas.getText().trim();
            boolean urgente = chkUrgente.isSelected();

            if (!descripcion.isEmpty()) {

                Tarea t = new Tarea(descripcion, categoria, notas, urgente, "Pendiente");
                modelo.agregarTarea(t);
                actualizarTablas();

            } else {
                JOptionPane.showMessageDialog(null, "La descripción no puede estar vacía");
            }
        }
    }

    // ORDENAMIENTO
    private void ordenarShell() {

        ArrayList<Tarea> lista = new ArrayList<>();

        for (Object o : modelo.getTareasPendientes().toList()) {
            lista.add((Tarea) o);
        }

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas pendientes que ordenar.");
            return;
        }

        String[] opciones = {"Descripción", "Categoría"};
        String criterio = (String) JOptionPane.showInputDialog(null,
                "Ordenar por:", "ShellSort",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (criterio == null) return;

        shellSort(lista, criterio);

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tablaOrdenadas.getModel();
        modeloTabla.setRowCount(0);

        for (Tarea t : lista) {
            modeloTabla.addRow(new Object[]{t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado()});
        }
    }

    private void shellSort(ArrayList<Tarea> lista, String criterio) {

        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {

            for (int i = gap; i < n; i++) {

                Tarea temp = lista.get(i);
                int j = i;

                while (j >= gap &&
                        comparar(lista.get(j - gap), temp, criterio) > 0) {

                    lista.set(j, lista.get(j - gap));
                    j -= gap;
                }

                lista.set(j, temp);
            }
        }
    }

    private int comparar(Tarea a, Tarea b, String criterio) {
        if (criterio.equals("Descripción"))
            return a.getDescripcion().compareToIgnoreCase(b.getDescripcion());
        else
            return a.getCategoria().compareToIgnoreCase(b.getCategoria());
    }

    // COMPLETAR
    private void completar() {

        int filaPend = vista.tablaPendientes.getSelectedRow();
        int filaUrg = vista.tablaUrgentes.getSelectedRow();
        int filaOrd = vista.tablaOrdenadas.getSelectedRow();

        if (filaPend == -1 && filaUrg == -1 && filaOrd == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una tarea.");
            return;
        }

        //PENDIENTES
        if (filaPend != -1) {

            Tarea t = obtenerTareaPendiente(filaPend);
            if (t == null) return;

            if (t.isUrgente()) {
                try {
                    Tarea primero = (Tarea) modelo.getTareasUrgentes().frenteCola();

                    if (!t.getDescripcion().equalsIgnoreCase(primero.getDescripcion())) {
                        JOptionPane.showMessageDialog(null,
                                "Una tarea urgente solo puede completarse si es la PRIMERA en la cola (FIFO).");
                        return;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No hay tareas urgentes en cola.");
                    return;
                }
            }

            modelo.completarTarea(t);
            actualizarTablas();
            return;
        }

        // URGENTES
        if (filaUrg != -1) {
            if (filaUrg != 0) {
                JOptionPane.showMessageDialog(null, "Solo puedes completar la PRIMERA tarea urgente (FIFO).");
                return;
            }

            try {
                Tarea frente = (Tarea) modelo.getTareasUrgentes().frenteCola();
                modelo.completarTarea(frente);
                actualizarTablas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al completar tarea urgente.");
            }

            return;
        }

        //ORDENADAS
        if (filaOrd != -1) {

            Tarea t = obtenerTareaDesdeOrdenadas(filaOrd);
            if (t == null) return;

            if (t.isUrgente()) {
                try {
                    Tarea primero = (Tarea) modelo.getTareasUrgentes().frenteCola();

                    if (!t.getDescripcion().equalsIgnoreCase(primero.getDescripcion())) {
                        JOptionPane.showMessageDialog(null,
                                "Una tarea urgente solo puede completarse si es la PRIMERA en la cola (FIFO).");
                        return;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No hay tareas urgentes en cola.");
                    return;
                }
            }

            modelo.completarTarea(t);
            actualizarTablas();
        }
    }

    // NUEVO  ELIMINAR 
    private void eliminar() {

        int filaPend = vista.tablaPendientes.getSelectedRow();
        int filaUrg = vista.tablaUrgentes.getSelectedRow();
        int filaOrd = vista.tablaOrdenadas.getSelectedRow();

        if (filaPend == -1 && filaUrg == -1 && filaOrd == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una tarea para eliminar.");
            return;
        }

        //DESDE PENDIENTES
        if (filaPend != -1) {

            Tarea t = obtenerTareaPendiente(filaPend);
            if (t == null) return;

            // SI NO ES URGENTE ELIMINA DE MANERA NORMAL 
            if (!t.isUrgente()) {
                modelo.eliminarTarea(t);
                actualizarTablas();
                return;
            }

            // SI ES URGENTE SOLO ELIMINA SI ES LA PRIMERA 
            try {
                Tarea primero = (Tarea) modelo.getTareasUrgentes().frenteCola();

                if (!t.getDescripcion().equalsIgnoreCase(primero.getDescripcion())) {
                    JOptionPane.showMessageDialog(null,
                            "Una tarea urgente solo puede eliminarse si es la PRIMERA en la cola (FIFO).");
                    return;
                }

                modelo.eliminarTarea(t);
                actualizarTablas();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No hay tareas urgentes en cola.");
            }

            return;
        }

        //DESDE URGENTES 
        if (filaUrg != -1) {
            if (filaUrg != 0) {
                JOptionPane.showMessageDialog(null,
                        "Solo puedes eliminar la PRIMERA tarea urgente (FIFO).");
                return;
            }

            try {
                Tarea frente = (Tarea) modelo.getTareasUrgentes().frenteCola();
                modelo.eliminarTarea(frente);
                actualizarTablas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar tarea urgente.");
            }

            return;
        }

        //DESDE ORDENADAS
        if (filaOrd != -1) {

            Tarea t = obtenerTareaDesdeOrdenadas(filaOrd);
            if (t == null) return;

            if (!t.isUrgente()) {
                modelo.eliminarTarea(t);
                actualizarTablas();
                return;
            }

            try {
                Tarea primero = (Tarea) modelo.getTareasUrgentes().frenteCola();

                if (!t.getDescripcion().equalsIgnoreCase(primero.getDescripcion())) {
                    JOptionPane.showMessageDialog(null,
                            "Una tarea urgente solo puede eliminarse si es la PRIMERA en cola (FIFO).");
                    return;
                }

                modelo.eliminarTarea(t);
                actualizarTablas();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No hay urgentes en cola.");
            }
        }
    }

    // DESHACER
    private void deshacer() {
        try {
            modelo.deshacerUltimaCompletada();
            actualizarTablas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay tareas para deshacer.");
        }
    }

    private Tarea obtenerTareaPendiente(int fila) {
        String desc = vista.tablaPendientes.getValueAt(fila, 0).toString();

        for (Object o : modelo.getTareasPendientes().toList()) {
            Tarea t = (Tarea) o;
            if (t.getDescripcion().equalsIgnoreCase(desc))
                return t;
        }
        return null;
    }

    private Tarea obtenerTareaDesdeOrdenadas(int fila) {
        String desc = vista.tablaOrdenadas.getValueAt(fila, 0).toString();

        for (Object o : modelo.getTareasPendientes().toList()) {
            Tarea t = (Tarea) o;
            if (t.getDescripcion().equalsIgnoreCase(desc))
                return t;
        }
        return null;
    }

    // ACTUALIZAR TABLAS
    private void actualizarTablas() {
        actualizarPendientes();
        actualizarOrdenadasVacía();
        actualizarUrgentes();
        actualizarCompletadas();
    }

    private void actualizarPendientes() {

        DefaultTableModel modeloTabla =
                (DefaultTableModel) vista.tablaPendientes.getModel();

        modeloTabla.setRowCount(0);

        for (Object o : modelo.getTareasPendientes().toList()) {
            Tarea t = (Tarea) o;

            modeloTabla.addRow(
                    new Object[]{t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado()}
            );
        }
    }

    private void actualizarOrdenadasVacía() {
        DefaultTableModel modeloTabla =
                (DefaultTableModel) vista.tablaOrdenadas.getModel();
        modeloTabla.setRowCount(0);
    }

    private void actualizarUrgentes() {

        DefaultTableModel modeloTabla =
                (DefaultTableModel) vista.tablaUrgentes.getModel();

        modeloTabla.setRowCount(0);

        try {
            ColaLista cola = modelo.getTareasUrgentes();
            ColaLista temp = new ColaLista();

            while (!cola.colaVacia()) {

                Tarea t = (Tarea) cola.quitar();

                modeloTabla.addRow(new Object[]{
                        t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado()
                });

                temp.insertar(t);
            }

            while (!temp.colaVacia())
                cola.insertar(temp.quitar());

        } catch (Exception ignored) {}
    }

    private void actualizarCompletadas() {

        DefaultTableModel modeloTabla =
                (DefaultTableModel) vista.tablaCompletadas.getModel();

        modeloTabla.setRowCount(0);

        try {

            PilaLista pila = modelo.getTareasCompletadas();
            PilaLista temp = new PilaLista();

            while (!pila.pilaVacia()) {

                Tarea t = (Tarea) pila.popPila();

                modeloTabla.addRow(new Object[]{
                        t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado()
                });

                temp.pushPila(t);
            }

            while (!temp.pilaVacia())
                pila.pushPila(temp.popPila());

        } catch (Exception ignored) {}
    }
}
