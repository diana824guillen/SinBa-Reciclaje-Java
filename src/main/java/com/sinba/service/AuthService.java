package com.sinba.service;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private FirestoreDAO dao = new FirestoreDAO();

    public Usuario login(String email, String password) throws ExecutionException, InterruptedException {
        // Aquí deberías validar contra Firebase Auth. Como no podemos verificar password directamente,
        // supondremos que el usuario ya existe en Firestore y ha sido autenticado previamente.
        // Para el avance, simplemente buscamos por email y retornamos el usuario.
        // (En producción se usaría Firebase Auth REST API para validar credenciales)
        Usuario user = dao.getUsuarioPorEmail(email);
        if (user != null) {
            logger.info("Usuario {} logueado exitosamente", email);
            return user;
        } else {
            logger.warn("Fallo login para {}", email);
            return null;
        }
    }

    public Usuario registrar(String nombre, String email, String password) throws ExecutionException, InterruptedException {
        // Similar: se crearía el usuario en Firebase Auth y luego en Firestore.
        // Para el demo, asumimos que ya está en Firestore (o lo insertamos manualmente en BD)
        // Devolvemos null por ahora.
        return null;
    }
}