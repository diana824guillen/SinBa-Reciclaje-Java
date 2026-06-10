package com.sinba.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sinba.model.Reciclaje;
import com.sinba.model.Usuario;
import com.sinba.util.FirebaseConfig;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirestoreDAO {
    private static Firestore db = FirebaseConfig.getFirestore();

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

    public void guardarReciclaje(Reciclaje reciclaje) {
        db.collection("reciclajes").add(reciclaje);
    }

    // Método corregido
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
            // Convertir cantidad a double (puede venir como Long o Double)
            Object cantidadObj = data.get("cantidad");
            if (cantidadObj instanceof Number) {
                r.setCantidad(((Number) cantidadObj).doubleValue());
            }
            // Convertir puntos a int
            Object puntosObj = data.get("puntos");
            if (puntosObj instanceof Number) {
                r.setPuntos(((Number) puntosObj).intValue());
            }
            r.setDescripcion((String) data.get("descripcion"));
            r.setUbicacion((String) data.get("ubicacion"));

            // Manejar fecha flexible (Timestamp o String)
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

    public void actualizarPuntos(String uid, int nuevosPuntos) {
        db.collection("usuarios").document(uid)
                .update("puntos", nuevosPuntos);
    }
}