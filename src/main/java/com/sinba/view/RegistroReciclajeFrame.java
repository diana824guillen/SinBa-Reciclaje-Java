package com.sinba.view;

import com.sinba.controller.ReciclajeController;
import javax.swing.*;
import java.awt.*;

public class RegistroReciclajeFrame extends JFrame {
    private String userId;
    private JComboBox<String> materialBox;
    private JTextField cantidadField;
    private JComboBox<String> ubicacionBox;
    private ReciclajeController controller;

    public RegistroReciclajeFrame(String userId) {
        this.userId = userId;
        controller = new ReciclajeController();
        setTitle("Registrar Reciclaje");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fondo claro
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(245, 248, 250));

        // Tarjeta blanca
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("♻️ Registrar Reciclaje", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(46, 204, 113));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        card.add(new JLabel("Material:"), gbc);
        gbc.gridx = 1;
        materialBox = new JComboBox<>(new String[]{"Plástico", "Vidrio", "Papel", "Cartón", "Metal", "Electrónicos", "Pilas", "Aceite", "Orgánico"});
        card.add(materialBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Cantidad (kg):"), gbc);
        gbc.gridx = 1;
        cantidadField = new JTextField();
        card.add(cantidadField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        card.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        ubicacionBox = new JComboBox<>(new String[]{"Real Plaza Chiclayo", "Plaza Mayor", "Parque Principal", "Centro Comercial", "Universidad Tecnológica", "Municipalidad"});
        card.add(ubicacionBox, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        JButton registrarBtn = new JButton("Registrar Reciclaje");
        registrarBtn.setPreferredSize(new Dimension(200, 40));
        registrarBtn.addActionListener(e -> {
            String material = (String) materialBox.getSelectedItem();
            double cantidad = Double.parseDouble(cantidadField.getText());
            String ubicacion = (String) ubicacionBox.getSelectedItem();
            if (controller.registrar(userId, material, cantidad, ubicacion)) {
                JOptionPane.showMessageDialog(this, "Reciclaje registrado con éxito.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(registrarBtn, gbc);

        background.add(card);
        add(background, BorderLayout.CENTER);
    }
}