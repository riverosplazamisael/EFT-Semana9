package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.dao.UsuarioDAO;
import cl.duoc.biblioteca.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginFrame() {
        setTitle("Acceso al Sistema - Biblioteca Escolar");
        setSize(380, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel panelCorreo = new JPanel(new FlowLayout());
        panelCorreo.add(new JLabel("Correo:"));
        txtCorreo = new JTextField(20);
        panelCorreo.add(txtCorreo);

        JPanel panelPass = new JPanel(new FlowLayout());
        panelPass.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField(20);
        panelPass.add(txtContrasena);

        JButton btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.addActionListener(e -> ingresar());

        add(new JLabel("SISTEMA DE BIBLIOTECA", SwingConstants.CENTER));
        add(panelCorreo);
        add(panelPass);
        add(btnIngresar);
    }

    private void ingresar() {
        String correo = txtCorreo.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();

        Usuario u = usuarioDAO.autenticar(correo, pass);
        if (u != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombre() + " (" + u.getRol() + ")");
            new MainFrame(u.getRol()).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}