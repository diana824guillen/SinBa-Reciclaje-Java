package com.sinba.controller;

import com.sinba.model.Usuario;
import com.sinba.service.ReciclajeService;
import com.sinba.model.Reciclaje;
import com.sinba.util.ExcelExporter;
import com.sinba.util.SessionCache;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashboardController {
    public Usuario getUsuario(String email) {
        // Intentar desde caché
        Usuario user = (Usuario) SessionCache.get("currentUser");
        if (user != null && user.getEmail().equals(email)) {
            return user;
        }
        // Fallback: consultar DAO
        try {
            return new com.sinba.dao.FirestoreDAO().getUsuarioPorEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    public void exportarReciclajes(String userId) {
        ReciclajeService service = new ReciclajeService();
        try {
            List<Reciclaje> lista = service.obtenerReciclajes(userId);
            ExcelExporter.exportarReciclajes(lista, "reciclajes.xlsx");
            javax.swing.JOptionPane.showMessageDialog(null, "Exportado a reciclajes.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}