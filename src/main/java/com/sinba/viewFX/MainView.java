package com.sinba.viewFX;

import com.sinba.controller.DashboardController;
import com.sinba.model.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainView extends BorderPane {
    private Stage stage;
    private Usuario usuario;
    private ToolBar toolBar;
    private Label titleLabel;
    private Button backButton;
    private StackPane contentPane;
    private DashboardController dashboardController;

    public MainView(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
        this.dashboardController = new DashboardController();
        setPrefSize(800, 600);

        // Barra superior
        toolBar = new ToolBar();
        backButton = new Button("← Volver");
        backButton.getStyleClass().add("button");
        backButton.setVisible(false);
        titleLabel = new Label("SinBa");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        toolBar.getItems().addAll(backButton, titleLabel);
        toolBar.setStyle("-fx-background-color: #2ecc71;");

        // Acción del botón volver
        backButton.setOnAction(e -> showDashboard());

        setTop(toolBar);

        // Contenedor central
        contentPane = new StackPane();
        setCenter(contentPane);

        // Mostrar dashboard inicial
        showDashboard();
    }

    // Cambia la vista central
    public void setView(Region view, String title) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(view);
        titleLabel.setText(title);
        backButton.setVisible(!title.equals("Dashboard"));
    }

    // Muestra el dashboard (refrescando datos)
    public void showDashboard() {
        DashboardView dashboard = new DashboardView(this, usuario);
        setView(dashboard, "Dashboard");
    }

    // Muestra el registro de reciclaje
    public void showRegistroReciclaje() {
        RegistroView registro = new RegistroView(this, usuario.getUid());
        setView(registro, "Registrar Reciclaje");
    }

    // Muestra las recompensas
    public void showRecompensas() {
        RecompensasView recompensas = new RecompensasView(this, usuario);
        setView(recompensas, "Recompensas");
    }

    // Muestra el perfil
    public void showPerfil() {
        PerfilView perfil = new PerfilView(this, usuario);
        setView(perfil, "Perfil");
    }

    // Muestra el panel de administración
    public void showAdmin() {
        AdminView admin = new AdminView(this);
        setView(admin, "Administración");
    }

    // Cierra sesión y vuelve al login
    public void logout() {
        // Podrías limpiar caché aquí
        stage.close();
        // Volver a abrir login
        LoginView login = new LoginView();
        Scene scene = new Scene(login, 450, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("SinBa - Iniciar Sesión");
        stage.show();
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}