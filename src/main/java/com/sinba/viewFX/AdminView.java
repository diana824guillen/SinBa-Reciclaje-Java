package com.sinba.viewFX;

import com.sinba.dao.FirestoreDAO;
import com.sinba.model.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class AdminView extends VBox {
    private MainView mainView;

    public AdminView(MainView mainView) {
        this.mainView = mainView;
        setSpacing(10);
        setPadding(new Insets(10));
        getStyleClass().add("card"); // opcional, para fondo blanco general

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                crearTabUsuarios(),
                crearTabReciclajes(),
                crearTabCanjes()
        );
        getChildren().add(tabPane);
    }

    private Tab crearTabUsuarios() {
        TableView<String[]> tabla = new TableView<>();
        String[] cols = {"UID", "Nombre", "Email", "Puntos", "Rol"};
        for (int i = 0; i < cols.length; i++) {
            TableColumn<String[], String> col = new TableColumn<>(cols[i]);
            final int idx = i;
            col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[idx]));
            tabla.getColumns().add(col);
        }
        tabla.setPlaceholder(new Label("No hay usuarios registrados"));
        cargarUsuarios(tabla);
        Tab tab = new Tab("Usuarios", tabla);
        tab.setClosable(false);
        return tab;
    }

    private Tab crearTabReciclajes() {
        TableView<String[]> tabla = new TableView<>();
        String[] cols = {"ID", "Usuario", "Material", "Kg", "Puntos", "Ubicación", "Fecha"};
        for (int i = 0; i < cols.length; i++) {
            TableColumn<String[], String> col = new TableColumn<>(cols[i]);
            final int idx = i;
            col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[idx]));
            tabla.getColumns().add(col);
        }
        tabla.setPlaceholder(new Label("No hay reciclajes registrados"));
        cargarReciclajes(tabla);
        Tab tab = new Tab("Reciclajes", tabla);
        tab.setClosable(false);
        return tab;
    }

    private Tab crearTabCanjes() {
        TableView<String[]> tabla = new TableView<>();
        String[] cols = {"Usuario", "Recompensa", "Puntos", "Fecha", "Estado"};
        for (int i = 0; i < cols.length; i++) {
            TableColumn<String[], String> col = new TableColumn<>(cols[i]);
            final int idx = i;
            col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[idx]));
            tabla.getColumns().add(col);
        }
        tabla.setPlaceholder(new Label("No hay canjes realizados"));
        cargarCanjes(tabla);
        Tab tab = new Tab("Canjes", tabla);
        tab.setClosable(false);
        return tab;
    }

    private void cargarUsuarios(TableView<String[]> tabla) {
        new Thread(() -> {
            try {
                List<Usuario> lista = new FirestoreDAO().obtenerTodosUsuarios();
                ObservableList<String[]> datos = FXCollections.observableArrayList();
                for (Usuario u : lista) {
                    datos.add(new String[]{u.getUid(), u.getNombre(), u.getEmail(), String.valueOf(u.getPuntos()), u.getRole()});
                }
                javafx.application.Platform.runLater(() -> tabla.setItems(datos));
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> tabla.setPlaceholder(new Label("Error al cargar usuarios")));
            }
        }).start();
    }

    private void cargarReciclajes(TableView<String[]> tabla) {
        new Thread(() -> {
            try {
                List<Reciclaje> lista = new FirestoreDAO().obtenerTodosReciclajes();
                ObservableList<String[]> datos = FXCollections.observableArrayList();
                for (Reciclaje r : lista) {
                    datos.add(new String[]{r.getId(), r.getUserId(), r.getMaterial(),
                            String.valueOf(r.getCantidad()), String.valueOf(r.getPuntos()),
                            r.getUbicacion(), r.getFecha()});
                }
                javafx.application.Platform.runLater(() -> tabla.setItems(datos));
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> tabla.setPlaceholder(new Label("Error al cargar reciclajes")));
            }
        }).start();
    }

    private void cargarCanjes(TableView<String[]> tabla) {
        new Thread(() -> {
            try {
                List<Canje> lista = new FirestoreDAO().obtenerTodosCanjes();
                ObservableList<String[]> datos = FXCollections.observableArrayList();
                for (Canje c : lista) {
                    datos.add(new String[]{c.getUserId(), c.getRecompensa(), String.valueOf(c.getPuntos()),
                            c.getFecha(), c.getEstado()});
                }
                javafx.application.Platform.runLater(() -> tabla.setItems(datos));
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> tabla.setPlaceholder(new Label("Error al cargar canjes")));
            }
        }).start();
    }
}