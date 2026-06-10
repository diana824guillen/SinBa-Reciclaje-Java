package com.sinba.controller;

import com.sinba.model.Reciclaje;
import com.sinba.model.Usuario;
import com.sinba.service.ReciclajeService;
import com.sinba.util.ExcelExporter;
import com.sinba.util.SessionCache;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashboardController {

    public Usuario getUsuario(String email) {
        Usuario user = (Usuario) SessionCache.get("currentUser");
        if (user != null && user.getEmail().equals(email)) {
            return user;
        }
        try {
            return new com.sinba.dao.FirestoreDAO().getUsuarioPorEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Reciclaje> obtenerReciclajes(String userId) {
        ReciclajeService service = new ReciclajeService();
        try {
            return service.obtenerReciclajes(userId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void exportarReciclajes(String userId) {
        List<Reciclaje> lista = obtenerReciclajes(userId);
        try {
            ExcelExporter.exportarReciclajes(lista, "reciclajes.xlsx");
            javax.swing.JOptionPane.showMessageDialog(null, "Exportado a reciclajes.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}