package br.com.warrick.biblioteca.peripherals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Implementação personalizada de UI para barras de rolagem modernas
 * Fornece uma aparência mais limpa e moderna para as barras de rolagem do aplicativo
 * com cantos arredondados e animações suaves.
 *
 * Projeto: Biblioteca
 *
 * @author Ra Ven - Criador original dos componentes personalizados (YouTube/GitHub)
 * @author Warrick
 * @since 02/11/2025
 */
public class ModernScrollBarUI extends BasicScrollBarUI {
    
    /* ============================================== CONSTANTES ============================================= */
    
    /** Tamanho padrão do thumb (alça) da barra de rolagem */
    private static final int THUMB_SIZE = 40;
    
    /** Cor de fundo da barra de rolagem */
    private static final Color SCROLL_BAR_BG = new Color(240, 240, 240);
    
    /** Cor do thumb (alça) da barra de rolagem */
    private static final Color THUMB_COLOR = new Color(200, 200, 200);
    
    /* ========================================= MÉTODOS SOBRESCRITOS ======================================= */

    /**
     * Obtém o tamanho máximo que o thumb (alça) da barra de rolagem pode ter
     * 
     * @return Dimensão máxima do thumb
     */
    @Override
    protected Dimension getMaximumThumbSize() {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, THUMB_SIZE);
        } else {
            return new Dimension(THUMB_SIZE, 0);
        }
    }

    /**
     * Obtém o tamanho mínimo que o thumb (alça) da barra de rolagem pode ter
     * 
     * @return Dimensão mínima do thumb
     */
    @Override
    protected Dimension getMinimumThumbSize() {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, THUMB_SIZE);
        } else {
            return new Dimension(THUMB_SIZE, 0);
        }
    }

    /**
     * Cria o botão de incremento da barra de rolagem
     * 
     * @param orientation Orientação do botão (ignorado)
     * @return Novo botão de incremento
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ScrollBarButton();
    }

    /**
     * Cria o botão de decremento da barra de rolagem
     * 
     * @param orientation Orientação do botão (ignorado)
     * @return Novo botão de decremento
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ScrollBarButton();
    }

    /**
     * Pinta a trilha (fundo) da barra de rolagem
     * 
     * @param grphcs Contexto gráfico para desenho
     * @param jc Componente que está sendo pintado
     * @param rctngl Área da trilha a ser pintada
     */
    @Override
    protected void paintTrack(Graphics grphcs, JComponent jc, Rectangle rctngl) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Configura a cor de fundo da trilha
        g2.setColor(SCROLL_BAR_BG);
        
        // Pinta a trilha com cantos arredondados
        g2.fillRoundRect(rctngl.x, rctngl.y, rctngl.width, rctngl.height, 10, 10);
    }

    /**
     * Pinta o thumb (alça) da barra de rolagem
     * 
     * @param grphcs Contexto gráfico para desenho
     * @param jc Componente que está sendo pintado
     * @param rctngl Área do thumb a ser pintada
     */
    @Override
    protected void paintThumb(Graphics grphcs, JComponent jc, Rectangle rctngl) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Obtém as dimensões iniciais
        int x = rctngl.x;
        int y = rctngl.y;
        int width = rctngl.width;
        int height = rctngl.height;
        
        // Ajusta as dimensões para criar um efeito visual mais agradável
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            y += 8;          // Adiciona margem superior
            height -= 16;    // Reduz a altura para criar margens
        } else {
            x += 8;         // Adiciona margem esquerda
            width -= 16;    // Reduz a largura para criar margens
        }
        
        // Desenha o thumb com cantos arredondados
        g2.setColor(THUMB_COLOR);
        g2.fillRoundRect(x, y, width, height, 10, 10);
    }

    /**
     * Classe interna que representa um botão personalizado para a barra de rolagem
     * Usado para os botões de incremento e decremento
     */
    private static class ScrollBarButton extends JButton {
        
        /**
         * Cria um novo botão de barra de rolagem com aparência limpa
         */
        public ScrollBarButton() {
            setBorder(null);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
        }
        
        /**
         * Sobrescreve o método de pintura para personalizar a aparência do botão
         * Neste caso, não desenha nada para manter a aparência limpa
         * 
         * @param grphcs Contexto gráfico para desenho
         */
        @Override
        public void paint(Graphics grphcs) {
            // Não pinta nada para manter a aparência limpa
        }
    }
}
