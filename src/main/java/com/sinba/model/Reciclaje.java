package com.sinba.model;

public class Reciclaje {
    private String id;
    private String userId;
    private String material;
    private double cantidad;
    private int puntos;
    private String descripcion;
    private String ubicacion;
    private String fecha;
    private String userEmail;

    public Reciclaje() {}

    public Reciclaje(String userId, String material, double cantidad, int puntos, String ubicacion, String fecha) {
        this.userId = userId;
        this.material = material;
        this.cantidad = cantidad;
        this.puntos = puntos;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}