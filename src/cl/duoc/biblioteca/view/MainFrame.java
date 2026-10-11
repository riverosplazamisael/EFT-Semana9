package cl.duoc.biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String rolUsuario) {
        setTitle("Sistema de Gestión de Biblioteca Escolar - Rol: " + rolUsuario.toUpperCase());
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Control de roles: Bibliotecario tiene gestión completa, Estudiante solo préstamos y consultas
        if ("bibliotecario".equalsIgnoreCase(rolUsuario)) {
            tabbedPane.addTab("Gestión de Libros", new LibrosPanel());
            tabbedPane.addTab("Gestión de Estudiantes", new EstudiantesPanel());
        }

        tabbedPane.addTab("Préstamos y Devoluciones", new PrestamosPanel());
        tabbedPane.addTab("Reportes", new ReportesPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}