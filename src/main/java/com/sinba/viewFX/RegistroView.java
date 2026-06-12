package com.sinba.viewFX;

import com.sinba.controller.ReciclajeController;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegistroView extends VBox {
    private MainView mainView;
    private String userId;
    private ReciclajeController controller;
    private ComboBox<String> materialBox;
    private TextField cantidadField;
    private ComboBox<String> ubicacionBox;

    public RegistroView(MainView mainView, String userId) {
        this.mainView = mainView;
        this.userId = userId;
        controller = new ReciclajeController();
        setSpacing(15);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(400);

        Label title = new Label("Registrar Reciclaje");
        title.getStyleClass().add("label-title");

        materialBox = new ComboBox<>();
        materialBox.getItems().addAll("Plástico", "Vidrio", "Papel", "Cartón", "Metal", "Electrónicos", "Pilas", "Aceite", "Orgánico");
        materialBox.setPromptText("Selecciona material");
        materialBox.setMaxWidth(Double.MAX_VALUE);

        cantidadField = new TextField();
        cantidadField.setPromptText("Cantidad (kg)");
        cantidadField.setMaxWidth(Double.MAX_VALUE);

        ubicacionBox = new ComboBox<>();
        ubicacionBox.getItems().addAll("Real Plaza Chiclayo", "Plaza Mayor", "Parque Principal", "Centro Comercial", "Universidad Tecnológica", "Municipalidad");
        ubicacionBox.setPromptText("Selecciona ubicación");
        ubicacionBox.setMaxWidth(Double.MAX_VALUE);

        Button registrarBtn = new Button("Registrar Reciclaje");
        registrarBtn.setMaxWidth(Double.MAX_VALUE);
        registrarBtn.setOnAction(e -> {
            String material = materialBox.getValue();
            String cantidadText = cantidadField.getText();
            String ubicacion = ubicacionBox.getValue();
            if (material == null || cantidadText.isEmpty() || ubicacion == null) {
                SinBaDialog.mostrar(SinBaDialog.Tipo.WARNING, "Completa todos los campos.");
                return;
            }
            try {
                double cantidad = Double.parseDouble(cantidadText);
                if (controller.registrar(userId, material, cantidad, ubicacion)) {
                    SinBaDialog.mostrar(SinBaDialog.Tipo.INFO, "Reciclaje registrado con éxito.");
                    mainView.showDashboard(); // Volver al dashboard
                } else {
                    SinBaDialog.mostrar(SinBaDialog.Tipo.ERROR, "Error al registrar.");
                }
            } catch (NumberFormatException ex) {
                SinBaDialog.mostrar(SinBaDialog.Tipo.ERROR, "Cantidad inválida.");
            }
        });

        card.getChildren().addAll(title, materialBox, cantidadField, ubicacionBox, registrarBtn);
        getChildren().add(card);
    }
}