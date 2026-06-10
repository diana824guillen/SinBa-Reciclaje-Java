package com.sinba.service;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.Reciclaje;
import com.sinba.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ReciclajeService {
    private static final Logger logger = LoggerFactory.getLogger(ReciclajeService.class);
    private FirestoreDAO dao = new FirestoreDAO();

    public int calcularPuntos(String material, double cantidad) {
        Map<String, Integer> puntosPorMaterial = Map.of(
                "Plástico", 10,
                "Vidrio", 8,
                "Papel", 5,
                "Cartón", 5,
                "Metal", 15,
                "Electrónicos", 20,
                "Pilas", 25,
                "Aceite", 12,
                "Orgánico", 3
        );
        int factor = puntosPorMaterial.getOrDefault(material, 5);
        return (int) Math.round(cantidad * factor);
    }

    public void registrarReciclaje(String userId, String material, double cantidad, String ubicacion) throws ExecutionException, InterruptedException {
        int puntos = calcularPuntos(material, cantidad);
        Reciclaje reciclaje = new Reciclaje(userId, material, cantidad, puntos, ubicacion, java.time.Instant.now().toString());
        dao.guardarReciclaje(reciclaje);

        // Actualizar puntos del usuario
        Usuario user = dao.getUsuarioPorUid(userId);
        if (user != null) {
            int nuevosPuntos = user.getPuntos() + puntos;
            dao.actualizarPuntos(userId, nuevosPuntos);
            logger.info("Reciclaje registrado. Puntos sumados: {}", puntos);
        }
    }

    public List<Reciclaje> obtenerReciclajes(String userId) throws ExecutionException, InterruptedException {
        return dao.getReciclajesPorUsuario(userId);
    }
}