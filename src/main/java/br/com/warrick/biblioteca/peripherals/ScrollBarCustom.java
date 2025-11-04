package br.com.warrick.biblioteca.peripherals;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 02/11/2025
 */


public class ScrollBarCustom extends JScrollBar {

    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(new Color(48, 144, 216));
        setBackground(Color.WHITE);
    }
}
