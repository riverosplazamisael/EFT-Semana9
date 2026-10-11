package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.BibliotecaController;
import cl.duoc.biblioteca.model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EstudiantesPanel extends JPanel {
    private final BibliotecaController controller = new BibliotecaController();
    private JTable tablaEstudiantes;
    private DefaultTableModel modelTabla;
    private JTextField txtNombre, txtRut, txtCurso, txtCorreo;

    public EstudiantesPanel() {
        setLayout(new BorderLayout(10, 10));

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Estudiante"));

        txtNombre = new JTextField();
        txtRut = new JTextField();
        txtCurso = new JTextField();
        txtCorreo = new JTextField();

        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("RUT:")); panelForm.add(txtRut);
        panelForm.add(new JLabel("Curso:")); panelForm.add(txtCurso);
        panelForm.add(new JLabel("Correo:")); panelForm.add(txtCorreo);

        JButton btnGuardar = new JButton("Registrar Estudiante");
        btnGuardar.addActionListener(e -> guardarEstudiante());

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(btnGuardar, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // Tabla
        modelTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "RUT", "Curso", "Correo"}, 0);
        tablaEstudiantes = new JTable(modelTabla);
        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {
        modelTabla.setRowCount(0);
        for (Estudiante e : controller.obtenerEstudiantes()) {
            modelTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getRut(), e.getCurso(), e.getCorreo()});
        }
    }

    private void guardarEstudiante() {
        if (controller.agregarEstudiante(txtNombre.getText(), txtRut.getText(), txtCurso.getText(), txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.");
            cargarDatos();
            txtNombre.setText(""); txtRut.setText(""); txtCurso.setText(""); txtCorreo.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}