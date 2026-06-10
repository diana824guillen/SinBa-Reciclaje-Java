package com.sinba.model;

public class Usuario {
    private String uid;
    private String nombre;
    private String email;
    private int puntos;
    private String role;
    private String telefono;
    private String direccion;
    private String fechaRegistro;
    private String fechaActualizacion;

    public Usuario() {}

    // Getters y Setters de todos los campos
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}