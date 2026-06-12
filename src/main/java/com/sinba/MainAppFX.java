package com.sinba;

import com.sinba.viewFX.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAppFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView, 450, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setTitle("SinBa - Iniciar Sesión");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}