package com.sinba.view;

import com.sinba.controller.RecompensasController;
import com.sinba.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class RecompensasFrame extends JFrame {
    private Usuario usuario;
    private RecompensasController controller;
    private JLabel puntosLabel;
    private JPanel recompensasPanel;

    public RecompensasFrame(Usuario usuario) {
        this.usuario = usuario;
        controller = new RecompensasController();
        setTitle("SinBa - Recompensas");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fondo general
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 248, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cabecera con puntos
        JPanel header = new JPanel();
        header.setBackground(new Color(46, 204, 113));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        puntosLabel = new JLabel("Tus puntos: " + usuario.getPuntos());
        puntosLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        puntosLabel.setForeground(Color.WHITE);
        header.add(puntosLabel);
        mainPanel.add(header, BorderLayout.NORTH);

        // Lista de recompensas (en tarjetas)
        recompensasPanel = new JPanel();
        recompensasPanel.setLayout(new BoxLayout(recompensasPanel, BoxLayout.Y_AXIS));
        recompensasPanel.setOpaque(false);
        cargarRecompensas();

        JScrollPane scroll = new JScrollPane(recompensasPanel);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        mainPanel.add(scroll, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void cargarRecompensas() {
        recompensasPanel.removeAll();

        Object[][] recompensas = {
                {"Starbucks - Café Gratis", 200, "☕"},
                {"Real Plaza - 20% descuento", 300, "🛍️"},
                {"Cineplanet - 2x1 entradas", 400, "🎬"},
                {"Intercorp - $10 descuento", 500, "💳"},
                {"Plaza Vea - 15% descuento", 250, "🛒"},
                {"KFC - Combo Familiar", 600, "🍗"}
        };

        for (Object[] rec : recompensas) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            JLabel icono = new JLabel((String) rec[2], SwingConstants.CENTER);
            icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
            card.add(icono, BorderLayout.WEST);

            JPanel info = new JPanel(new GridLayout(2, 1));
            info.setOpaque(false);
            info.add(new JLabel((String) rec[0]));
            info.add(new JLabel("Puntos: " + rec[1]));
            card.add(info, BorderLayout.CENTER);

            JButton canjearBtn = new JButton("Canjear");
            canjearBtn.setPreferredSize(new Dimension(100, 35));
            int puntosNecesarios = (int) rec[1];
            canjearBtn.setEnabled(usuario.getPuntos() >= puntosNecesarios);
            canjearBtn.addActionListener(e -> {
                if (controller.canjerRecompensa(usuario, (String) rec[0], puntosNecesarios)) {
                    JOptionPane.showMessageDialog(this, "¡Canje exitoso! Código generado.");
                    usuario.setPuntos(usuario.getPuntos() - puntosNecesarios);
                    puntosLabel.setText("Tus puntos: " + usuario.getPuntos());
                    cargarRecompensas(); // refrescar botones
                } else {
                    JOptionPane.showMessageDialog(this, "Error al canjear.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            card.add(canjearBtn, BorderLayout.EAST);

            recompensasPanel.add(card);
            recompensasPanel.add(Box.createVerticalStrut(10));
        }

        recompensasPanel.revalidate();
        recompensasPanel.repaint();
    }
}