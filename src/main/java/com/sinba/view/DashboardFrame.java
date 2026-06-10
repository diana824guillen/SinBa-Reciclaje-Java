package com.sinba.view;

import com.sinba.controller.DashboardController;
import com.sinba.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private String email;
    private DashboardController controller;

    public DashboardFrame(String email) {
        this.email = email;
        controller = new DashboardController();
        setTitle("SinBa - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Usuario usuario = controller.getUsuario(email);

        JPanel panel = new JPanel(new BorderLayout());
        if (usuario != null) {
            JLabel puntosLabel = new JLabel("Tus puntos: " + usuario.getPuntos(), SwingConstants.CENTER);
            puntosLabel.setFont(new Font("Arial", Font.BOLD, 24));
            panel.add(puntosLabel, BorderLayout.NORTH);

            JPanel botones = new JPanel(new GridLayout(1,2,10,10));
            JButton registrarBtn = new JButton("Registrar Reciclaje");
            registrarBtn.addActionListener(e -> new RegistroReciclajeFrame(usuario.getUid()).setVisible(true));
            JButton reporteBtn = new JButton("Exportar a Excel");
            reporteBtn.addActionListener(e -> controller.exportarReciclajes(usuario.getUid()));
            botones.add(registrarBtn);
            botones.add(reporteBtn);
            panel.add(botones, BorderLayout.CENTER);
        } else {
            panel.add(new JLabel("Error al cargar datos", SwingConstants.CENTER));
        }
        add(panel);
    }
}