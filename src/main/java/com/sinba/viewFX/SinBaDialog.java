package com.sinba.viewFX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SinBaDialog extends Stage {

    public enum Tipo { INFO, ERROR, WARNING }

    public SinBaDialog(Tipo tipo, String mensaje) {
        setTitle("SinBa");
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(30));
        root.getStyleClass().add("card");

        // Ícono según tipo
        String icono = "ℹ️";
        if (tipo == Tipo.ERROR) icono = "❌";
        else if (tipo == Tipo.WARNING) icono = "⚠️";

        Label iconoLabel = new Label(icono);
        iconoLabel.setStyle("-fx-font-size: 40px;");

        Label mensajeLabel = new Label(mensaje);
        mensajeLabel.setWrapText(true);
        mensajeLabel.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
        mensajeLabel.setAlignment(Pos.CENTER);

        Button aceptarBtn = new Button("Aceptar");
        aceptarBtn.setPrefWidth(120);
        aceptarBtn.setOnAction(e -> close());

        root.getChildren().addAll(iconoLabel, mensajeLabel, aceptarBtn);

        Scene scene = new Scene(root, 350, 200);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        setScene(scene);
    }

    public static void mostrar(Tipo tipo, String mensaje) {
        SinBaDialog dialog = new SinBaDialog(tipo, mensaje);
        dialog.showAndWait();
    }
}