package br.com.warrick.biblioteca.view.app;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.*;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import br.com.warrick.biblioteca.persistence.config.ConnectionFactory;
import br.com.warrick.biblioteca.view.login.LoginApp;
import javax.swing.*;
import java.awt.Font;

/**
 * Classe principal do Sistema de Autenticação
 * 
 * Responsável pela inicialização do sistema, configuração do tema e gerenciamento
 * da interface gráfica principal.
 *
 * Projeto Login para Biblioteca
 *
 * @author Warrick
 * @version 1.0.0
 * @since 28/10/2025
 */
public class Main {

    /* ========================================= MÉTODO PRINCIPAL ============================================== */
    public static void main(String[] args) {
        // Inicializar a fábrica de conexões (o banco será criado automaticamente se não existir)
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Configura a fonte Roboto
        FlatRobotoFont.install();

        // Define a fonte Roboto como padrão
        FlatLaf.registerCustomDefaultsSource("resources.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        // Configura o tema escuro
        FlatLightLaf.setup();

        // Iniciar a tela de login
        java.awt.EventQueue.invokeLater(() -> {
            new LoginApp().setVisible(true);
        });
    }
}
