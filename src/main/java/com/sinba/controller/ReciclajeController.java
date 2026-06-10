package com.sinba.controller;

import com.sinba.service.ReciclajeService;

public class ReciclajeController {
    private ReciclajeService service = new ReciclajeService();

    public boolean registrar(String userId, String material, double cantidad, String ubicacion) {
        try {
            service.registrarReciclaje(userId, material, cantidad, ubicacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}