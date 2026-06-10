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
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Material:"));
        materialBox = new JComboBox<>(new String[]{"Plástico","Vidrio","Papel","Cartón","Metal","Electrónicos","Pilas","Aceite","Orgánico"});
        panel.add(materialBox);

        panel.add(new JLabel("Cantidad (kg):"));
        cantidadField = new JTextField();
        panel.add(cantidadField);

        panel.add(new JLabel("Ubicación:"));
        ubicacionBox = new JComboBox<>(new String[]{"Real Plaza Chiclayo","Plaza Mayor","Parque Principal","Centro Comercial","Universidad Tecnológica","Municipalidad"});
        panel.add(ubicacionBox);

        JButton registrarBtn = new JButton("Registrar");
        registrarBtn.addActionListener(e -> {
            String material = (String) materialBox.getSelectedItem();
            double cantidad = Double.parseDouble(cantidadField.getText());
            String ubicacion = (String) ubicacionBox.getSelectedItem();
            if (controller.registrar(userId, material, cantidad, ubicacion)) {
                JOptionPane.showMessageDialog(this, "Reciclaje registrado con éxito!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(registrarBtn);

        add(panel);
    }
}