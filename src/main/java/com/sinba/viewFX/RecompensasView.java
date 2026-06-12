package com.sinba.viewFX;

import com.sinba.controller.RecompensasController;
import com.sinba.model.Usuario;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RecompensasView extends VBox {
    private MainView mainView;
    private Usuario usuario;
    private RecompensasController controller;
    private Label puntosLabel;

    public RecompensasView(MainView mainView, Usuario usuario) {
        this.mainView = mainView;
        this.usuario = usuario;
        controller = new RecompensasController();
        setSpacing(15);
        setPadding(new Insets(20));

        puntosLabel = new Label("Tus puntos: " + usuario.getPuntos());
        puntosLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        HBox header = new HBox(puntosLabel);
        header.getStyleClass().add("header");
        header.setPadding(new Insets(15));
        getChildren().add(header);

        VBox lista = new VBox(10);
        lista.setPadding(new Insets(10));

        Object[][] recompensas = {
                {"Starbucks - Café Gratis", 200, "☕"},
                {"Real Plaza - 20% descuento", 300, "🛍️"},
                {"Cineplanet - 2x1 entradas", 400, "🎬"},
                {"Intercorp - $10 descuento", 500, "💳"},
                {"Plaza Vea - 15% descuento", 250, "🛒"},
                {"KFC - Combo Familiar", 600, "🍗"}
        };

        for (Object[] rec : recompensas) {
            HBox card = new HBox(15);
            card.getStyleClass().add("card");
            card.setAlignment(Pos.CENTER_LEFT);
            Label icono = new Label((String) rec[2]);
            icono.setStyle("-fx-font-size: 30px;");
            VBox info = new VBox(5, new Label((String) rec[0]), new Label("Puntos: " + rec[1]));
            Button canjearBtn = new Button("Canjear");
            int puntosNecesarios = (int) rec[1];
            canjearBtn.setDisable(usuario.getPuntos() < puntosNecesarios);
            canjearBtn.setOnAction(e -> {
                if (controller.canjerRecompensa(usuario, (String) rec[0], puntosNecesarios)) {
                    SinBaDialog.mostrar(SinBaDialog.Tipo.INFO, "¡Canje exitoso! Código generado.");
                    usuario.setPuntos(usuario.getPuntos() - puntosNecesarios);
                    puntosLabel.setText("Tus puntos: " + usuario.getPuntos());
                    // Refrescar la vista de recompensas para actualizar botones
                    mainView.showRecompensas();
                } else {
                    SinBaDialog.mostrar(SinBaDialog.Tipo.ERROR, "Error al canjear.");
                }
            });
            card.getChildren().addAll(icono, info, canjearBtn);
            lista.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        getChildren().add(scroll);
    }
}