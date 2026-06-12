package com.sinba.viewFX;

import com.sinba.controller.LoginController;
import com.sinba.model.Usuario;
import com.sinba.util.SessionCache;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView extends VBox {
    private TextField emailField;
    private PasswordField passwordField;
    private LoginController controller;

    public LoginView() {
        controller = new LoginController();
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(30));

        VBox card = new VBox();
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(15);
        card.setPadding(new Insets(30));

        Label title = new Label("♻️ SinBa");
        title.getStyleClass().add("label-title");

        emailField = new TextField();
        emailField.setPromptText("Correo electrónico");
        passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        Button loginBtn = new Button("Iniciar Sesión");
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            if (controller.authenticate(email, password)) {
                Usuario usuario = (Usuario) SessionCache.get("currentUser");
                Stage stage = (Stage) getScene().getWindow();
                MainView mainView = new MainView(stage, usuario);
                Scene scene = new Scene(mainView, 800, 600);
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("SinBa");
            } else {
                SinBaDialog.mostrar(SinBaDialog.Tipo.ERROR, "Credenciales inválidas");
            }
        });

        card.getChildren().addAll(title, emailField, passwordField, loginBtn);
        getChildren().add(card);
    }
}