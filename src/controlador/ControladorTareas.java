package controlador;

import modelo.*;
import vista.VistaTareass; // 🔹 CAMBIADO: antes era vista.VistaTareas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTareas implements ActionListener {

	private GestorTareas gestor;
	private VistaTareass vista;

	// CONSTRUCTOR QUE RECIBE LA VISTA Y EL MODELO
	public ControladorTareas(GestorTareas gestor, VistaTareass vista) {
		this.gestor = gestor;
		this.vista = vista;
		// ASOCIA LOS BOTONES A ESTE CONTROLADOR
		this.vista.btnAgregar.addActionListener(this);
		this.vista.btnCompletar.addActionListener(this);
		this.vista.btnEliminar.addActionListener(this);
		this.vista.btnDeshacer.addActionListener(this);

		actualizarTablas();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == vista.btnAgregar) {
			agregarTarea();

		} else if (e.getSource() == vista.btnCompletar) {
			completarTarea();

		} else if (e.getSource() == vista.btnEliminar) {
			eliminarTarea();

		} else if (e.getSource() == vista.btnDeshacer) {
			deshacerTarea();
		}
	}

	// METODO PARA AGREGAR UNA NUEVA TAREA
	private void agregarTarea() {
		JTextField txtDescripcion = new JTextField();
		JTextArea txtNotas = new JTextArea(3, 15);
		String[] categorias = { "Trabajo", "Personal", "Estudio", "Otro" };
		JComboBox<String> cmbCategoria = new JComboBox<>(categorias);
		JCheckBox chkUrgente = new JCheckBox("Es urgente");

		Object[] mensaje = { "Categoria:", cmbCategoria, "Descripcion:", txtDescripcion, "Notas:",
				new JScrollPane(txtNotas), chkUrgente };

		int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Agregar nueva tarea", JOptionPane.OK_CANCEL_OPTION);

		if (opcion == JOptionPane.OK_OPTION) {
			String descripcion = txtDescripcion.getText().trim();
			String categoria = cmbCategoria.getSelectedItem().toString();
			String notas = txtNotas.getText().trim();
			boolean urgente = chkUrgente.isSelected();

			if (!descripcion.isEmpty()) {
				Tarea nueva = new Tarea(descripcion, categoria, notas, urgente, "Pendiente");
				gestor.agregarTarea(nueva);
				actualizarTablas();
			} else {
				JOptionPane.showMessageDialog(null, "La descripcion no puede estar vacia");
			}
		}
	}

	// METODO PARA COMPLETAR UNA TAREA
	private void completarTarea() {
		int fila = vista.tablaPendientes.getSelectedRow();
		int filaUrgente = vista.tablaUrgentes.getSelectedRow();

		if (fila >= 0) {
			// SE OBTIENEN TODOS LOS DATOS DE LA FILA SELECCIONADA
			String desc = vista.tablaPendientes.getValueAt(fila, 0).toString();
			String categoria = vista.tablaPendientes.getValueAt(fila, 1).toString();
			String notas = vista.tablaPendientes.getValueAt(fila, 2).toString();

			// 🔹 CORREGIDO: AHORA SE CONSERVAN CATEGORIA Y NOTAS
			Tarea tarea = new Tarea(desc, categoria, notas, false, "Pendiente");
			gestor.completarTarea(tarea);
			actualizarTablas();

		} else if (filaUrgente >= 0) {
			// SE OBTIENEN LOS DATOS DE LA TAREA URGENTE
			String desc = vista.tablaUrgentes.getValueAt(filaUrgente, 0).toString();
			String categoria = vista.tablaUrgentes.getValueAt(filaUrgente, 1).toString();
			String notas = vista.tablaUrgentes.getValueAt(filaUrgente, 2).toString();

			// 🔹 CORREGIDO: TAMBIEN SE PASAN CATEGORIA Y NOTAS
			Tarea tarea = new Tarea(desc, categoria, notas, true, "Pendiente");
			gestor.completarTarea(tarea);
			actualizarTablas();

		} else {
			JOptionPane.showMessageDialog(null, "Selecciona una tarea para marcar como completada");
		}
	}

	// METODO PARA ELIMINAR UNA TAREA
	private void eliminarTarea() {
		int fila = vista.tablaPendientes.getSelectedRow();
		if (fila >= 0) {
			String desc = vista.tablaPendientes.getValueAt(fila, 0).toString();
			Tarea tarea = new Tarea(desc, "", "", false, "Pendiente");
			gestor.eliminarTarea(tarea);
			actualizarTablas();
		} else {
			JOptionPane.showMessageDialog(null, "Selecciona una tarea pendiente para eliminar");
		}
	}

	// METODO PARA DESHACER LA ULTIMA TAREA COMPLETADA
	private void deshacerTarea() {
		try {
			gestor.deshacerUltimaCompletada();
			actualizarTablas();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No hay tareas completadas para deshacer");
		}
	}

	// METODO QUE ACTUALIZA LAS TABLAS
	private void actualizarTablas() {
		DefaultTableModel modeloPend = (DefaultTableModel) vista.tablaPendientes.getModel();
		DefaultTableModel modeloUrg = (DefaultTableModel) vista.tablaUrgentes.getModel();
		DefaultTableModel modeloComp = (DefaultTableModel) vista.tablaCompletadas.getModel();

		modeloPend.setRowCount(0);
		modeloUrg.setRowCount(0);
		modeloComp.setRowCount(0);

		// LLENA LISTA PENDIENTE
		for (Object obj : gestor.getTareasPendientes().toList()) {
			Tarea t = (Tarea) obj;
			modeloPend.addRow(new Object[] { t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado() });
		}

		// LLENA COLA URGENTE
		try {
			ColaLista temp = new ColaLista();
			while (!gestor.getTareasUrgentes().colaVacia()) {
				Tarea t = (Tarea) gestor.getTareasUrgentes().quitar();
				modeloUrg.addRow(new Object[] { t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado() });
				temp.insertar(t);
			}
			while (!temp.colaVacia()) {
				gestor.getTareasUrgentes().insertar(temp.quitar());
			}
		} catch (Exception e) {
			// NO HACER NADA
		}

		// LLENA PILA COMPLETADAS
		try {
			PilaLista temp = new PilaLista();
			while (!gestor.getTareasCompletadas().pilaVacia()) {
				Tarea t = (Tarea) gestor.getTareasCompletadas().popPila();
				modeloComp.addRow(new Object[] { t.getDescripcion(), t.getCategoria(), t.getNotas(), t.getEstado() });
				temp.pushPila(t);
			}
			while (!temp.pilaVacia()) {
				gestor.getTareasCompletadas().pushPila(temp.popPila());
			}
		} catch (Exception e) {
			// NO HACER NADA
		}
	}
}
