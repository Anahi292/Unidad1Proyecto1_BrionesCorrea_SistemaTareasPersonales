package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// CLASE PRINCIPAL DE LA INTERFAZ GRÁFICA
// MUESTRA LAS TABLAS Y LOS BOTONES DE CONTROL

public class VistaTareass extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// BOTONES
	public JButton btnAgregar, btnCompletar, btnEliminar, btnDeshacer;

	// TABLAS
	public JTable tablaPendientes, tablaUrgentes, tablaCompletadas;

	// CONSTRUCTOR DEL FRAME
	public VistaTareass() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		setTitle("SISTEMA DE GESTION DE TAREAS PERSONALES");

		// PANEL PRINCIPAL
		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);

		// PANEL SUPERIOR CON TITULO Y BOTONES
		JPanel panelSuperior = new JPanel(new BorderLayout());
		contentPane.add(panelSuperior, BorderLayout.NORTH);

		// TÍTULO SUPERIOR
		JLabel lblTitulo = new JLabel("SISTEMA DE GESTION DE TAREAS PERSONALES", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setForeground(Color.DARK_GRAY);
		panelSuperior.add(lblTitulo, BorderLayout.NORTH);

		// PANEL DE BOTONES DEBAJO DEL TÍTULO
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		btnAgregar = new JButton("Agregar Tarea");
		btnCompletar = new JButton("Completar Tarea");
		btnEliminar = new JButton("Eliminar Tarea");
		btnDeshacer = new JButton("Deshacer Última");

		panelBotones.add(btnAgregar);
		panelBotones.add(btnCompletar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnDeshacer);

		panelSuperior.add(panelBotones, BorderLayout.SOUTH);

		// PANEL CENTRAL CON LAS TRES TABLAS
		JPanel panelTablas = new JPanel(new GridLayout(1, 3, 10, 10));

		// TABLA DE TAREAS PENDIENTES
		tablaPendientes = new JTable(
				new DefaultTableModel(new Object[] { "Descripción", "Categoría", "Notas", "Estado" }, 0));
		JScrollPane scrollPend = new JScrollPane(tablaPendientes);
		JPanel panelPend = new JPanel(new BorderLayout());
		panelPend.add(new JLabel("TAREAS PENDIENTES (Lista Doble)", SwingConstants.CENTER), BorderLayout.NORTH);
		panelPend.add(scrollPend, BorderLayout.CENTER);

		// TABLA DE TAREAS URGENTES
		tablaUrgentes = new JTable(
				new DefaultTableModel(new Object[] { "Descripción", "Categoría", "Notas", "Estado" }, 0));
		JScrollPane scrollUrg = new JScrollPane(tablaUrgentes);
		JPanel panelUrg = new JPanel(new BorderLayout());
		panelUrg.add(new JLabel("TAREAS URGENTES (Cola)", SwingConstants.CENTER), BorderLayout.NORTH);
		panelUrg.add(scrollUrg, BorderLayout.CENTER);

		// TABLA DE TAREAS COMPLETADAS
		tablaCompletadas = new JTable(
				new DefaultTableModel(new Object[] { "Descripción", "Categoría", "Notas", "Estado" }, 0));
		JScrollPane scrollComp = new JScrollPane(tablaCompletadas);
		JPanel panelComp = new JPanel(new BorderLayout());
		panelComp.add(new JLabel("TAREAS COMPLETADAS (Pila)", SwingConstants.CENTER), BorderLayout.NORTH);
		panelComp.add(scrollComp, BorderLayout.CENTER);

		// AGREGA LAS TRES TABLAS AL PANEL
		panelTablas.add(panelPend);
		panelTablas.add(panelUrg);
		panelTablas.add(panelComp);

		contentPane.add(panelTablas, BorderLayout.CENTER);
	}

	// MÉTODO MAIN PARA PROBAR LA INTERFAZ
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaTareass frame = new VistaTareass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
