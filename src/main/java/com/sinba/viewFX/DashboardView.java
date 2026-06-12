package com.sinba.viewFX;

import com.sinba.controller.DashboardController;
import com.sinba.model.Reciclaje;
import com.sinba.model.Usuario;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class DashboardView extends VBox {
    private MainView mainView;
    private Usuario usuario;
    private DashboardController controller;
    private Label puntosLabel;
    private ListView<String> ultimosList;
    private Label totalReciclajesValue, co2Value;

    public DashboardView(MainView mainView, Usuario usuario) {
        this.mainView = mainView;
        this.usuario = usuario;
        controller = new DashboardController();
        setSpacing(20);
        setPadding(new Insets(20));

        // Cabecera de puntos
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15));
        puntosLabel = new Label("Tus puntos: " + (usuario != null ? usuario.getPuntos() : 0));
        puntosLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        header.getChildren().add(puntosLabel);
        getChildren().add(header);

        // Centro: últimos reciclajes + estadísticas
        HBox center = new HBox(20);

        // Panel izquierdo: últimos reciclajes
        VBox reciclajesCard = new VBox(10);
        reciclajesCard.getStyleClass().add("card");
        reciclajesCard.setPrefWidth(400);
        Label reciclajesTitle = new Label("Últimos reciclajes");
        reciclajesTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        ultimosList = new ListView<>();
        ultimosList.setPlaceholder(new Label("Aún no has registrado reciclajes"));
        reciclajesCard.getChildren().addAll(reciclajesTitle, ultimosList);

        // Panel derecho: estadísticas en tarjetas
        VBox statsContainer = new VBox(15);
        statsContainer.setPrefWidth(250);

        // Tarjeta Total Reciclajes
        VBox totalCard = new VBox(5);
        totalCard.getStyleClass().add("stat-card");
        totalCard.setAlignment(Pos.CENTER);
        totalReciclajesValue = new Label("...");
        totalReciclajesValue.getStyleClass().add("stat-number");
        Label totalLabel = new Label("Total reciclajes");
        totalLabel.getStyleClass().add("stat-label");
        totalCard.getChildren().addAll(totalReciclajesValue, totalLabel);

        // Tarjeta CO2
        VBox co2Card = new VBox(5);
        co2Card.getStyleClass().add("stat-card");
        co2Card.setAlignment(Pos.CENTER);
        co2Value = new Label("...");
        co2Value.getStyleClass().add("stat-number");
        Label co2Label = new Label("kg CO₂ ahorrados");
        co2Label.getStyleClass().add("stat-label");
        co2Card.getChildren().addAll(co2Value, co2Label);

        statsContainer.getChildren().addAll(totalCard, co2Card);
        center.getChildren().addAll(reciclajesCard, statsContainer);
        getChildren().add(center);

        // Botones inferiores
        HBox botones = new HBox(15);
        botones.setAlignment(Pos.CENTER);
        Button registrarBtn = new Button("Registrar Reciclaje");
        registrarBtn.setOnAction(e -> mainView.showRegistroReciclaje());
        Button mapaBtn = new Button("Ver Mapa");
        mapaBtn.setOnAction(e -> {
            try {
                int puerto = com.sinba.util.MapServer.start();
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://localhost:" + puerto + "/mapa.html"));
            } catch (Exception ex) {
                SinBaDialog.mostrar(SinBaDialog.Tipo.ERROR, "No se pudo abrir el mapa.");
            }
        });
        Button recompensasBtn = new Button("Recompensas");
        recompensasBtn.setOnAction(e -> mainView.showRecompensas());
        Button perfilBtn = new Button("Perfil");
        perfilBtn.setOnAction(e -> mainView.showPerfil());
        Button exportarBtn = new Button("Exportar Excel");
        exportarBtn.setOnAction(e -> controller.exportarReciclajes(usuario.getUid()));

        botones.getChildren().addAll(registrarBtn, mapaBtn, recompensasBtn, perfilBtn, exportarBtn);
        if (usuario != null && "admin".equals(usuario.getRole())) {
            Button adminBtn = new Button("Admin");
            adminBtn.setOnAction(e -> mainView.showAdmin());
            botones.getChildren().add(adminBtn);
        }
        getChildren().add(botones);

        cargarDatos(usuario.getUid());
    }

    private void cargarDatos(String uid) {
        new Thread(() -> {
            List<Reciclaje> reciclajes = controller.obtenerReciclajes(uid);
            int total = reciclajes.size();
            double co2 = total * 2.5;
            Platform.runLater(() -> {
                totalReciclajesValue.setText(String.valueOf(total));
                co2Value.setText(String.format("%.1f", co2));
                ObservableList<String> items = FXCollections.observableArrayList();
                for (Reciclaje r : reciclajes) {
                    items.add(r.getMaterial() + " - " + r.getCantidad() + " kg (+" + r.getPuntos() + " pts)");
                }
                ultimosList.setItems(items);
                // Actualizar puntos desde Firestore (por si cambiaron)
                try {
                    Usuario actualizado = new com.sinba.dao.FirestoreDAO().getUsuarioPorUid(uid);
                    if (actualizado != null) {
                        usuario.setPuntos(actualizado.getPuntos());
                        puntosLabel.setText("Tus puntos: " + actualizado.getPuntos());
                    }
                } catch (Exception e) { e.printStackTrace(); }
            });
        }).start();
    }
}