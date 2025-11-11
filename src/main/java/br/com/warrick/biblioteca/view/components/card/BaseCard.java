package br.com.warrick.biblioteca.view.components.card;

import javax.swing.*;
import java.awt.*;

/**
 * Classe base abstrata para todos os componentes de card na aplicação.
 * Fornece funcionalidades comuns como hover effects e estilização básica.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public abstract class BaseCard extends JPanel {
    
    protected Color defaultBackground;
    protected Color hoverBackground;
    protected boolean isHovered;
    
    public BaseCard() {
        this.defaultBackground = Color.WHITE;
        this.hoverBackground = new Color(240, 240, 240);
        this.isHovered = false;
        
        setOpaque(true);
        setBackground(defaultBackground);
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Adiciona margem interna
        setBorder(BorderFactory.createCompoundBorder(
            getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    /**
     * Define a cor de fundo do card
     * @param color Cor de fundo
     */
    public void setCorFundo(Color color) {
        setBackground(color);
        repaint();
    }
    
    /**
     * Atualiza o estado de hover do card
     * @param hovered true se o mouse está sobre o card, false caso contrário
     */
    protected void setHovered(boolean hovered) {
        if (this.isHovered != hovered) {
            this.isHovered = hovered;
            setCorFundo(hovered ? hoverBackground : defaultBackground);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Adiciona um efeito de elevação sutil
        if (isHovered) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(0, 0, 0, 20));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2d.dispose();
        }
    }
}
