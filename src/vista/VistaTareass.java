package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaTareass extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public JButton btnAgregar, btnOrdenar, btnCompletar, btnEliminar, btnDeshacer;
    public JTable tablaPendientes, tablaOrdenadas, tablaUrgentes, tablaCompletadas;

    public VistaTareass() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocationRelativeTo(null);
        setTitle("SISTEMA DE GESTION DE TAREAS PERSONALES");

        Color azulFuerte = new Color(0, 76, 153);          
        Color azulMedio = new Color(0, 102, 204);          
        Color azulCeleste = new Color(187, 222, 251);      
        Color textoBlanco = Color.WHITE;

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(azulFuerte);
        barraSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(barraSuperior, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("SISTEMA DE GESTION DE TAREAS PERSONALES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoBlanco);
        barraSuperior.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(azulFuerte);

        btnAgregar = crearBoton("Agregar Tarea", azulCeleste, Color.BLACK);
        btnOrdenar = crearBoton("Ordenar ShellSort", azulCeleste, Color.BLACK);
        btnCompletar = crearBoton("Completar Tarea", azulCeleste, Color.BLACK);
        btnEliminar = crearBoton("Eliminar Tarea", azulCeleste, Color.BLACK);
        btnDeshacer = crearBoton("Deshacer Última", azulCeleste, Color.BLACK);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnOrdenar);
        panelBotones.add(btnCompletar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnDeshacer);

        barraSuperior.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelTablas = new JPanel(new GridLayout(1, 4, 10, 10));
        panelTablas.setBackground(Color.WHITE);

        tablaPendientes = crearTabla();
        panelTablas.add(crearPanelTabla("PENDIENTES (LISTA DOBLE)", tablaPendientes, azulMedio, textoBlanco));

        tablaOrdenadas = crearTabla();
        panelTablas.add(crearPanelTabla("ORDENADAS (SHELLSORT)", tablaOrdenadas, azulMedio, textoBlanco));

        tablaUrgentes = crearTabla();
        panelTablas.add(crearPanelTabla("URGENCIAS (COLA)", tablaUrgentes, azulMedio, textoBlanco));

        tablaCompletadas = crearTabla();
        panelTablas.add(crearPanelTabla("COMPLETADAS (PILA)", tablaCompletadas, azulMedio, textoBlanco));

        contentPane.add(panelTablas, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton btn = new JButton(texto);
        btn.setBackground(fondo);
        btn.setForeground(textoColor);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        // Efecto hover suave
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(fondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(fondo);
            }
        });

        return btn;
    }

    private JTable crearTabla() {
        JTable tabla = new JTable(
                new DefaultTableModel(new Object[]{"Descripción", "Categoría", "Notas", "Estado"}, 0));
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        return tabla;
    }

    private JPanel crearPanelTabla(String titulo, JTable tabla, Color azul, Color textoBlanco) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(azul);
        lblTitulo.setForeground(textoBlanco);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }
}
