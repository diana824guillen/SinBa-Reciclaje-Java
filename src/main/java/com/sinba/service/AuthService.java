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
        return null;
    }
}