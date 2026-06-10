package com.sinba.view;

import com.sinba.controller.DashboardController;
import com.sinba.model.Reciclaje;
import com.sinba.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private String email;
    private DashboardController controller;
    private JLabel puntosLabel;
    private JLabel totalReciclajesLabel;
    private JLabel co2Label;
    private JTextArea ultimosArea;
    private Usuario usuario;

    public DashboardFrame(String email) {
        this.email = email;
        controller = new DashboardController();
        setTitle("SinBa - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        usuario = controller.getUsuario(email);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos del usuario");
            return;
        }

        // Panel principal con fondo claro
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 248, 250));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cabecera: puntos con fondo verde
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        puntosLabel = new JLabel("Tus puntos: " + usuario.getPuntos());
        puntosLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        puntosLabel.setForeground(Color.WHITE);
        headerPanel.add(puntosLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Centro: tarjetas de reciclajes y estadísticas
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setOpaque(false);

        // Tarjeta "Últimos reciclajes"
        JPanel reciclajesCard = new JPanel(new BorderLayout());
        reciclajesCard.setBackground(Color.WHITE);
        reciclajesCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        reciclajesCard.add(new JLabel("Últimos reciclajes"), BorderLayout.NORTH);
        ultimosArea = new JTextArea();
        ultimosArea.setEditable(false);
        ultimosArea.setOpaque(false);
        reciclajesCard.add(new JScrollPane(ultimosArea), BorderLayout.CENTER);
        centerPanel.add(reciclajesCard);

        // Tarjeta "Estadísticas"
        JPanel statsCard = new JPanel(new GridLayout(3, 1, 10, 10));
        statsCard.setBackground(Color.WHITE);
        statsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        totalReciclajesLabel = new JLabel("Total reciclajes: 0");
        co2Label = new JLabel("kg CO₂ ahorrados: 0.0");
        statsCard.add(totalReciclajesLabel);
        statsCard.add(co2Label);
        statsCard.add(new JLabel("Materiales:"));
        centerPanel.add(statsCard);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Botones inferiores
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton registrarBtn = new JButton("Registrar Reciclaje");
        registrarBtn.setPreferredSize(new Dimension(180, 40));
        registrarBtn.addActionListener(e -> new RegistroReciclajeFrame(usuario.getUid()).setVisible(true));

        JButton mapaBtn = new JButton("Ver Mapa");
        mapaBtn.setPreferredSize(new Dimension(180, 40));
        mapaBtn.addActionListener(e -> abrirMapa());

        JButton recompensasBtn = new JButton("Recompensas");
        recompensasBtn.setPreferredSize(new Dimension(180, 40));
        recompensasBtn.addActionListener(e -> new RecompensasFrame(usuario).setVisible(true));

        JButton perfilBtn = new JButton("Perfil");
        perfilBtn.setPreferredSize(new Dimension(180, 40));
        perfilBtn.addActionListener(e -> new PerfilFrame(usuario).setVisible(true));

        JButton exportarBtn = new JButton("Exportar Excel");
        exportarBtn.setPreferredSize(new Dimension(180, 40));
        exportarBtn.addActionListener(e -> controller.exportarReciclajes(usuario.getUid()));

        buttonPanel.add(registrarBtn);
        buttonPanel.add(mapaBtn);
        buttonPanel.add(recompensasBtn);
        buttonPanel.add(perfilBtn);
        buttonPanel.add(exportarBtn);

        if ("admin".equals(usuario.getRole())) {
            JButton adminBtn = new JButton("Admin");
            adminBtn.setPreferredSize(new Dimension(180, 40));
            adminBtn.addActionListener(e -> new AdminFrame().setVisible(true));
            buttonPanel.add(adminBtn);
        }

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        cargarDatos(usuario.getUid());
    }

    private void cargarDatos(String uid) {
        new Thread(() -> {
            List<Reciclaje> reciclajes = controller.obtenerReciclajes(uid);
            int total = reciclajes.size();
            double co2 = total * 2.5;
            SwingUtilities.invokeLater(() -> {
                totalReciclajesLabel.setText("Total reciclajes: " + total);
                co2Label.setText(String.format("kg CO₂ ahorrados: %.1f", co2));
                StringBuilder sb = new StringBuilder();
                for (Reciclaje r : reciclajes) {
                    sb.append(r.getMaterial()).append(" - ")
                            .append(r.getCantidad()).append(" kg (+")
                            .append(r.getPuntos()).append(" pts)\n");
                }
                ultimosArea.setText(sb.toString());
            });
        }).start();
    }

    private void abrirMapa() {
        try {
            int puerto = com.sinba.util.MapServer.start();
            String url = "http://localhost:" + puerto + "/mapa.html";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir el mapa: " + ex.getMessage());
        }
    }
}