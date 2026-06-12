package com.sinba.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sinba.model.Reciclaje;
import com.sinba.model.Usuario;
import com.sinba.model.Canje;
import com.sinba.util.FirebaseConfig;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirestoreDAO {
    private static Firestore db = FirebaseConfig.getFirestore();

    // ---------- USUARIOS ----------
    public Usuario getUsuarioPorEmail(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("usuarios")
                .whereEqualTo("email", email)
                .limit(1)
                .get();
        QuerySnapshot snapshot = query.get();
        if (!snapshot.isEmpty()) {
            DocumentSnapshot doc = snapshot.getDocuments().get(0);
            return doc.toObject(Usuario.class);
        }
        return null;
    }

    public Usuario getUsuarioPorUid(String uid) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = db.collection("usuarios").document(uid).get().get();
        if (doc.exists()) {
            return doc.toObject(Usuario.class);
        }
        return null;
    }

    public List<Usuario> obtenerTodosUsuarios() throws ExecutionException, InterruptedException {
        List<Usuario> lista = new ArrayList<>();
        for (DocumentSnapshot doc : db.collection("usuarios").get().get().getDocuments()) {
            Map<String, Object> data = doc.getData();
            if (data == null) continue;
            Usuario u = new Usuario();
            u.setUid(doc.getId());
            u.setNombre((String) data.get("nombre"));
            u.setEmail((String) data.get("email"));
            // Convertir puntos de forma segura (Number o String)
            Object puntosObj = data.get("puntos");
            if (puntosObj instanceof Number) {
                u.setPuntos(((Number) puntosObj).intValue());
            } else if (puntosObj instanceof String) {
                try { u.setPuntos(Integer.parseInt((String) puntosObj)); } catch (NumberFormatException e) { u.setPuntos(0); }
            }
            u.setRole((String) data.get("role"));
            u.setTelefono((String) data.get("telefono"));
            u.setDireccion((String) data.get("direccion"));
            lista.add(u);
        }
        return lista;
    }

    public void actualizarPuntos(String uid, int nuevosPuntos) {
        db.collection("usuarios").document(uid).update("puntos", nuevosPuntos);
    }

    public void actualizarUsuario(Usuario usuario) {
        db.collection("usuarios").document(usuario.getUid()).set(usuario);
    }

    // ---------- RECICLAJES ----------
    public void guardarReciclaje(Reciclaje reciclaje) {
        db.collection("reciclajes").add(reciclaje);
    }

    public List<Reciclaje> getReciclajesPorUsuario(String userId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("reciclajes")
                .whereEqualTo("userId", userId)
                .get();
        List<Reciclaje> lista = new ArrayList<>();
        for (DocumentSnapshot doc : query.get().getDocuments()) {
            Map<String, Object> data = doc.getData();
            if (data == null) continue;

            Reciclaje r = new Reciclaje();
            r.setId(doc.getId());
            r.setUserId((String) data.get("userId"));
            r.setMaterial((String) data.get("material"));
            // cantidad
            Object cantidadObj = data.get("cantidad");
            if (cantidadObj instanceof Number) {
                r.setCantidad(((Number) cantidadObj).doubleValue());
            } else if (cantidadObj instanceof String) {
                try { r.setCantidad(Double.parseDouble((String) cantidadObj)); } catch (NumberFormatException e) { r.setCantidad(0.0); }
            }
            // puntos
            Object puntosObj = data.get("puntos");
            if (puntosObj instanceof Number) {
                r.setPuntos(((Number) puntosObj).intValue());
            } else if (puntosObj instanceof String) {
                try { r.setPuntos(Integer.parseInt((String) puntosObj)); } catch (NumberFormatException e) { r.setPuntos(0); }
            }
            r.setDescripcion((String) data.get("descripcion"));
            r.setUbicacion((String) data.get("ubicacion"));
            // fecha flexible
            Object fechaObj = data.get("fecha");
            if (fechaObj instanceof com.google.cloud.Timestamp) {
                r.setFecha(((com.google.cloud.Timestamp) fechaObj).toDate().toInstant().toString());
            } else if (fechaObj instanceof String) {
                r.setFecha((String) fechaObj);
            }
            r.setUserEmail((String) data.get("userEmail"));
            lista.add(r);
        }
        return lista;
    }

    public List<Reciclaje> obtenerTodosReciclajes() throws ExecutionException, InterruptedException {
        List<Reciclaje> lista = new ArrayList<>();
        for (DocumentSnapshot doc : db.collection("reciclajes").get().get().getDocuments()) {
            Map<String, Object> data = doc.getData();
            if (data == null) continue;
            Reciclaje r = new Reciclaje();
            r.setId(doc.getId());
            r.setUserId((String) data.get("userId"));
            r.setMaterial((String) data.get("material"));
            // cantidad
            Object cantidadObj = data.get("cantidad");
            if (cantidadObj instanceof Number) {
                r.setCantidad(((Number) cantidadObj).doubleValue());
            } else if (cantidadObj instanceof String) {
                try { r.setCantidad(Double.parseDouble((String) cantidadObj)); } catch (NumberFormatException e) { r.setCantidad(0.0); }
            }
            // puntos
            Object puntosObj = data.get("puntos");
            if (puntosObj instanceof Number) {
                r.setPuntos(((Number) puntosObj).intValue());
            } else if (puntosObj instanceof String) {
                try { r.setPuntos(Integer.parseInt((String) puntosObj)); } catch (NumberFormatException e) { r.setPuntos(0); }
            }
            r.setDescripcion((String) data.get("descripcion"));
            r.setUbicacion((String) data.get("ubicacion"));
            // fecha
            Object fechaObj = data.get("fecha");
            if (fechaObj instanceof com.google.cloud.Timestamp) {
                r.setFecha(((com.google.cloud.Timestamp) fechaObj).toDate().toInstant().toString());
            } else if (fechaObj instanceof String) {
                r.setFecha((String) fechaObj);
            }
            r.setUserEmail((String) data.get("userEmail"));
            lista.add(r);
        }
        return lista;
    }

    // ---------- CANJES ----------
    public void guardarCanje(Canje canje) {
        db.collection("canjes").add(canje);
    }

    public List<Canje> obtenerTodosCanjes() throws ExecutionException, InterruptedException {
        List<Canje> lista = new ArrayList<>();
        for (DocumentSnapshot doc : db.collection("canjes").get().get().getDocuments()) {
            Map<String, Object> data = doc.getData();
            if (data == null) continue;
            Canje c = new Canje();
            c.setUserId((String) data.get("userId"));
            c.setRecompensa((String) data.get("recompensa"));
            // puntos
            Object puntosObj = data.get("puntos");
            if (puntosObj instanceof Number) {
                c.setPuntos(((Number) puntosObj).intValue());
            } else if (puntosObj instanceof String) {
                try { c.setPuntos(Integer.parseInt((String) puntosObj)); } catch (NumberFormatException e) { c.setPuntos(0); }
            }
            c.setFecha((String) data.get("fecha"));
            c.setEstado((String) data.get("estado"));
            lista.add(c);
        }
        return lista;
    }
}