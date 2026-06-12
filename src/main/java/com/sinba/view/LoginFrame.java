package com.sinba.view;

import com.sinba.controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private LoginController controller;

    public LoginFrame() {
        controller = new LoginController();
        setTitle("SinBa - Iniciar Sesión");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel fondo (color claro)
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(245, 248, 250));

        // Tarjeta blanca
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo / título
        JLabel title = new JLabel("SinBa", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(46, 204, 113));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        // Email
        gbc.gridy++; gbc.gridwidth = 1;
        card.add(new JLabel("Correo electrónico"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        card.add(emailField, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Contraseña"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        card.add(passwordField, gbc);

        // Botón login
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        JButton loginBtn = new JButton("Iniciar Sesión");
        loginBtn.setPreferredSize(new Dimension(200, 40));
        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (controller.authenticate(email, password)) {
                dispose();
                new DashboardFrame(email).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(loginBtn, gbc);

        background.add(card);
        add(background, BorderLayout.CENTER);
    }
}