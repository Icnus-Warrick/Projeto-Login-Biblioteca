package br.com.warrick.biblioteca.app;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import br.com.warrick.biblioteca.persistence.config.ConnectionFactory;
import br.com.warrick.biblioteca.view.login.LoginApp;

/**
 * Classe principal da aplicação Biblioteca
 * Responsável pela inicialização do sistema e configurações iniciais
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 28/10/2025
 */
public class Main {

    /* ========================================= MÉTODO PRINCIPAL ============================================== */
    public static void main(String[] args) {
        // Inicializar a fábrica de conexões (o banco será criado automaticamente se não existir)
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        
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
