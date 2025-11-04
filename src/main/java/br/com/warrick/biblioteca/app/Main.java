package br.com.warrick.biblioteca.app;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import br.com.warrick.biblioteca.utils.DatabaseManager;
import br.com.warrick.biblioteca.view.login.LoginApp;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class Main {

    public static void main(String[] args) {
        // Inicializar banco de dados
        DatabaseManager.init();

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar a tela de login
        java.awt.EventQueue.invokeLater(() -> {
            new LoginApp().setVisible(true);
        });
    }
}
