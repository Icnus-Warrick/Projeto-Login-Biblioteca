package br.com.warrick.biblioteca.uihelper;

import javax.swing.*;
import java.awt.*;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class DialogHelper {

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Confirmação", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
