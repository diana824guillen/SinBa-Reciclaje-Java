package com.sinba.controller;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.Canje;
import com.sinba.model.Usuario;
import java.util.Date;

public class RecompensasController {
    public boolean canjerRecompensa(Usuario usuario, String recompensa, int puntosNecesarios) {
        if (usuario.getPuntos() < puntosNecesarios) return false;
        try {
            FirestoreDAO dao = new FirestoreDAO();
            Canje canje = new Canje();
            canje.setUserId(usuario.getUid());
            canje.setRecompensa(recompensa);
            canje.setPuntos(puntosNecesarios);
            canje.setFecha(new Date().toString());
            canje.setEstado("pendiente");
            dao.guardarCanje(canje);
            dao.actualizarPuntos(usuario.getUid(), usuario.getPuntos() - puntosNecesarios);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}