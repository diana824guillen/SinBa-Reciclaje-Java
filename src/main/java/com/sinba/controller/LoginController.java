package com.sinba.controller;

import com.sinba.model.Usuario;
import com.sinba.service.AuthService;
import com.sinba.util.SessionCache;

import java.util.concurrent.ExecutionException;

public class LoginController {
    private AuthService authService = new AuthService();

    public boolean authenticate(String email, String password) {
        try {
            Usuario user = authService.login(email, password);
            if (user != null) {
                SessionCache.put("currentUser", user);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}