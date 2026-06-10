package com.sinba.view;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class PerfilFrame extends JFrame {
    private Usuario usuario;

    public PerfilFrame(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Mi Perfil");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(245, 248, 250));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("👤 Perfil", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(46, 204, 113));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        card.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        JTextField nombreField = new JTextField(usuario.getNombre(), 20);
        card.add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JLabel emailLabel = new JLabel(usuario.getEmail());
        card.add(emailLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Puntos:"), gbc);
        gbc.gridx = 1;
        card.add(new JLabel(String.valueOf(usuario.getPuntos())), gbc);

        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        JTextField telField = new JTextField(usuario.getTelefono() != null ? usuario.getTelefono() : "", 20);
        card.add(telField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        JTextField dirField = new JTextField(usuario.getDireccion() != null ? usuario.getDireccion() : "", 20);
        card.add(dirField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        JButton guardarBtn = new JButton("Guardar Cambios");
        guardarBtn.setPreferredSize(new Dimension(200, 40));
        guardarBtn.addActionListener(e -> {
            usuario.setTelefono(telField.getText());
            usuario.setDireccion(dirField.getText());
            usuario.setNombre(nombreField.getText());
            FirestoreDAO dao = new FirestoreDAO();
            dao.actualizarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Perfil actualizado.");
        });
        card.add(guardarBtn, gbc);

        background.add(card);
        add(background, BorderLayout.CENTER);
    }
}