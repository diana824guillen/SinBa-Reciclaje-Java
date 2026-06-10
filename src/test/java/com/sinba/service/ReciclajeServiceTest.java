package com.sinba.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReciclajeServiceTest {

    private ReciclajeService service;

    @BeforeEach
    void setUp() {
        service = new ReciclajeService();
    }

    @Test
    void calcularPuntosPlasticoDebeSer10PorKg() {
        assertEquals(25, service.calcularPuntos("Plástico", 2.5));
    }

    @Test
    void calcularPuntosVidrioDebeSer8PorKg() {
        assertEquals(16, service.calcularPuntos("Vidrio", 2.0));
    }

    @Test
    void calcularPuntosMaterialDesconocidoUsaDefault5() {
        assertEquals(5, service.calcularPuntos("Ropa", 1.0));
    }

    @Test
    void calcularPuntosCantidadCeroDevuelveCero() {
        assertEquals(0, service.calcularPuntos("Plástico", 0.0));
    }
}