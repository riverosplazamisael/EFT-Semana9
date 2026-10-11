package cl.duoc.biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class ReportesPanel extends JPanel {

    public ReportesPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Módulo de Reportes de Biblioteca"));

        JTextArea txtReportes = new JTextArea();
        txtReportes.setEditable(false);
        txtReportes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtReportes.setText("--- RESUMEN GENERAL DE BIBLIOTECA ---\n\n" +
                "• Clic en los botones superiores para consultar estadisticas.\n");

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnLibrosPrestados = new JButton("Libros Más Prestados");
        JButton btnHistorial = new JButton("Historial Préstamos");

        btnLibrosPrestados.addActionListener(e -> {
            txtReportes.setText("=== LIBROS MÁS PRESTADOS ===\n" +
                    "1. Estructura de Datos - (15 Préstamos)\n" +
                    "2. Java a Fondo - (12 Préstamos)\n" +
                    "3. Base de Datos Relacionales - (8 Préstamos)\n");
        });

        btnHistorial.addActionListener(e -> {
            txtReportes.setText("=== HISTORIAL DE PRÉSTAMOS RECIENTES ===\n" +
                    "• Estudiante: Juan Pérez | Libro: Java a Fondo | Fecha: 2026-10-01 | Estado: Devuelto\n" +
                    "• Estudiante: Maria Lopez | Libro: Base de Datos | Fecha: 2026-10-05 | Estado: En Préstamo\n");
        });

        panelBotones.add(btnLibrosPrestados);
        panelBotones.add(btnHistorial);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(txtReportes), BorderLayout.CENTER);
    }
}