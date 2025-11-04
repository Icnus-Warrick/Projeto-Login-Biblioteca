package br.com.warrick.biblioteca.uimanager;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class ThemeManager {

    public static void applyLightTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyDarkTheme() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshUI() {
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }
}
