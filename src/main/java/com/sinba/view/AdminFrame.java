package com.sinba.view;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private FirestoreDAO dao = new FirestoreDAO();

    public AdminFrame() {
        setTitle("Panel de Administración");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tabs.add("Usuarios", crearPanelUsuarios());
        tabs.add("Reciclajes", crearPanelReciclajes());
        tabs.add("Canjes", crearPanelCanjes());

        add(tabs, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(new Color(46, 204, 113));
        footer.setPreferredSize(new Dimension(0, 5));
        add(footer, BorderLayout.SOUTH);
    }

    private JScrollPane crearPanelUsuarios() {
        String[] cols = {"UID", "Nombre", "Email", "Puntos", "Rol"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        try {
            List<Usuario> usuarios = dao.obtenerTodosUsuarios();
            for (Usuario u : usuarios) {
                model.addRow(new Object[]{u.getUid(), u.getNombre(), u.getEmail(), u.getPuntos(), u.getRole()});
            }
        } catch (Exception e) { e.printStackTrace(); }
        JTable table = new JTable(model);
        estilizarTabla(table);
        return new JScrollPane(table);
    }

    private JScrollPane crearPanelReciclajes() {
        String[] cols = {"ID", "Usuario", "Material", "Kg", "Puntos", "Ubicación", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        try {
            List<Reciclaje> lista = dao.obtenerTodosReciclajes();
            for (Reciclaje r : lista) {
                model.addRow(new Object[]{r.getId(), r.getUserId(), r.getMaterial(),
                        r.getCantidad(), r.getPuntos(), r.getUbicacion(), r.getFecha()});
            }
        } catch (Exception e) { e.printStackTrace(); }
        JTable table = new JTable(model);
        estilizarTabla(table);
        return new JScrollPane(table);
    }

    private JScrollPane crearPanelCanjes() {
        String[] cols = {"Usuario", "Recompensa", "Puntos", "Fecha", "Estado"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        try {
            List<Canje> lista = dao.obtenerTodosCanjes();
            for (Canje c : lista) {
                model.addRow(new Object[]{c.getUserId(), c.getRecompensa(), c.getPuntos(), c.getFecha(), c.getEstado()});
            }
        } catch (Exception e) { e.printStackTrace(); }
        JTable table = new JTable(model);
        estilizarTabla(table);
        return new JScrollPane(table);
    }

    private void estilizarTabla(JTable table) {
        table.setRowHeight(25);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(46, 204, 113));
        table.getTableHeader().setForeground(Color.WHITE);
    }
}