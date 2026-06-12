package com.sinba.viewFX;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.Usuario;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PerfilView extends VBox {
    private MainView mainView;
    private Usuario usuario;

    public PerfilView(MainView mainView, Usuario usuario) {
        this.mainView = mainView;
        this.usuario = usuario;
        setSpacing(15);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(400);

        Label title = new Label("👤 Perfil");
        title.getStyleClass().add("label-title");

        TextField nombreField = new TextField(usuario.getNombre());
        nombreField.setPromptText("Nombre");
        Label emailLabel = new Label("Email: " + usuario.getEmail());
        Label puntosLabel = new Label("Puntos: " + usuario.getPuntos());
        TextField telField = new TextField(usuario.getTelefono() != null ? usuario.getTelefono() : "");
        telField.setPromptText("Teléfono");
        TextField dirField = new TextField(usuario.getDireccion() != null ? usuario.getDireccion() : "");
        dirField.setPromptText("Dirección");

        Button guardarBtn = new Button("Guardar Cambios");
        guardarBtn.setMaxWidth(Double.MAX_VALUE);
        guardarBtn.setOnAction(e -> {
            usuario.setTelefono(telField.getText());
            usuario.setDireccion(dirField.getText());
            usuario.setNombre(nombreField.getText());
            new FirestoreDAO().actualizarUsuario(usuario);
            SinBaDialog.mostrar(SinBaDialog.Tipo.INFO, "Perfil actualizado.");
        });

        card.getChildren().addAll(title, nombreField, emailLabel, puntosLabel, telField, dirField, guardarBtn);
        getChildren().add(card);
    }
}