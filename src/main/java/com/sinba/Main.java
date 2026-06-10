package com.sinba;

import com.formdev.flatlaf.FlatLightLaf;
import com.sinba.view.LoginFrame;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // ===== CONFIGURACIÓN GLOBAL DE FLATLAF =====
        // Bordes redondeados en todos los componentes
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ProgressBar.arc", 10);
        UIManager.put("TabbedPane.arc", 10);
        UIManager.put("TabbedPane.selectedBackground", Color.WHITE);

        // Colores corporativos (verde SinBa)
        UIManager.put("Component.focusedBorderColor", new Color(46, 204, 113));
        UIManager.put("Button.background", new Color(46, 204, 113));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.hoverBackground", new Color(39, 174, 96));
        UIManager.put("Button.pressedBackground", new Color(30, 140, 80));

        // Hipervínculos con color corporativo
        UIManager.put("hyperlinkColor", new Color(46, 204, 113));

        // Fondo general y texto
        UIManager.put("Panel.background", new Color(245, 248, 250));
        UIManager.put("Label.foreground", new Color(44, 62, 80));

        // Fuente moderna (Segoe UI en Windows, buena alternativa en Linux/Mac)
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("defaultFont", defaultFont);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Label.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("TextArea.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));

        // Instalar FlatLaf Light con la configuración anterior
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf. Usando Look & Feel por defecto.");
        }

        // Iniciar la aplicación
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}