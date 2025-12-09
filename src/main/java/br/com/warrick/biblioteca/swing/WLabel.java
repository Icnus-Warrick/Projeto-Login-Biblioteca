package br.com.warrick.biblioteca.swing;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 * Componente de label personalizado com suporte a animações de linha inferior interativa.
 *
 * <p>
 * Esta classe estende JLabel para fornecer um label com linha inferior animada ao passar o mouse, com suporte completo
 * para alinhamentos horizontal e vertical.</p>
 *
 * @author Warrick
 * @version 2.1.0
 * @since 27/11/2025
 * @see JLabel
 */
public class WLabel extends JLabel {
    // ============================================ CONSTANTES DE CORES ============================================

    /**
     * Cor padrão da linha inferior quando o mouse está sobre o componente.
     */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /**
     * Cor padrão para o texto do label.
     */
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    /**
     * Cor da linha inferior quando o mouse não está sobre o componente.
     */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    // ============================================ CONSTANTES DE LAYOUT ============================================
    /**
     * Altura em pixels da linha inferior do label.
     */
    protected static final int LINE_HEIGHT = 1;

    /**
     * Espaçamento em pixels entre o texto e a linha inferior.
     */
    protected int lineSpacing = 3;

    /**
     * Duração em milissegundos das animações do componente.
     */
    protected static final int ANIMATION_DURATION = 300;

    // ============================================ ATRIBUTOS ============================================
    /**
     * Progresso atual da animação da linha (0.0 a 1.0).
     */
    protected float lineAnimationProgress = 0f;

    /**
     * Indica se o cursor do mouse está sobre o componente.
     */
    protected boolean mouseOver = false;

    /**
     * Cor da linha inferior quando o mouse está sobre o label.
     */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /**
     * Controlador de animação para transições suaves da linha.
     */
    protected Timeline lineTimeline;

    // ============================================ CONSTRUTORES ============================================
    /**
     * Cria um novo label vazio.
     */
    public WLabel() {
        this("");
    }

    /**
     * Cria um novo label com o texto especificado.
     *
     * @param text Texto a ser exibido no label
     */
    public WLabel(String text) {
        super(text);
        setupLabel();
    }

    /**
     * Cria um novo label com texto e alinhamento horizontal especificados.
     *
     * @param text Texto a ser exibido no label
     * @param horizontalAlignment Alinhamento horizontal (SwingConstants.LEFT, CENTER, RIGHT, LEADING, TRAILING)
     */
    public WLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setupLabel();
    }

    // ============================================ MÉTODOS PRIVADOS ============================================
    /**
     * Obtém uma cor do tema FlatLaf ou retorna a cor padrão fornecida.
     */
    protected Color getThemeColor(String key, Color defaultColor) {
        Color themeColor = UIManager.getColor(key);
        return themeColor != null ? themeColor : defaultColor;
    }

    /**
     * Configura as propriedades iniciais do label.
     */
    private void setupLabel() {
        // Configuração de cores
        setForeground(getThemeColor("WLabel.textColor", DEFAULT_TEXT_COLOR));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Inicializa a cor da linha do tema
        lineColor = getThemeColor("WLabel.lineColor", DEFAULT_LINE_COLOR);

        // Listener de mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                animateLine(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                animateLine(false);
            }
        });
    }

    /**
     * Método principal de renderização do componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Desenha o texto
            super.paintComponent(g2);

            // Desenha a linha
            paintLine(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Calcula a posição Y do texto baseado no alinhamento vertical.
     */
    private int calculateTextY() {
        FontMetrics fm = getFontMetrics(getFont());
        Insets insets = getInsets();
        int textHeight = fm.getHeight();
        int verticalAlignment = getVerticalAlignment();

        int availableHeight = getHeight() - insets.top - insets.bottom;

        if (verticalAlignment == SwingConstants.TOP) {
            return insets.top + fm.getAscent();
        } else if (verticalAlignment == SwingConstants.BOTTOM) {
            return getHeight() - insets.bottom - fm.getDescent();
        } else { // CENTER (padrão)
            return insets.top + (availableHeight - textHeight) / 2 + fm.getAscent();
        }
    }

    /**
     * Desenha a linha inferior do label.
     */
    private void paintLine(Graphics2D g2) {
        FontMetrics fm = getFontMetrics(getFont());
        String text = getText();

        if (text == null || text.isEmpty()) {
            return;
        }

        // Calcula a largura do texto
        int textWidth = fm.stringWidth(text);

        // Obtém as margens do componente
        Insets insets = getInsets();

        // Calcula a posição X baseada no alinhamento horizontal
        int x = 0;
        int horizontalAlignment = getHorizontalAlignment();

        if (horizontalAlignment == SwingConstants.CENTER) {
            x = (getWidth() - textWidth) / 2;
        } else if (horizontalAlignment == SwingConstants.RIGHT || horizontalAlignment == SwingConstants.TRAILING) {
            x = getWidth() - textWidth - insets.right;
        } else { // LEFT ou LEADING
            x = insets.left;
        }

        // Calcula a posição Y da linha baseada no alinhamento vertical
        int textY = calculateTextY();
        int lineY = textY + fm.getDescent() + lineSpacing;

        // Obtém cor da linha de fundo do tema
        Color currentLineBgColor = getThemeColor("WLabel.lineBgColor", DEFAULT_LINE_BG_COLOR);

        // Desenha a linha de fundo
        g2.setColor(currentLineBgColor);
        g2.fillRect(x, lineY, textWidth, LINE_HEIGHT);

        // Linha de destaque (hover) - agora respeitando o alinhamento
        if (mouseOver || lineAnimationProgress > 0) {
            g2.setColor(lineColor);
            int lineWidth = (int) (textWidth * lineAnimationProgress);

            // Ajusta a posição inicial baseado no alinhamento horizontal
            int lineX;
            if (horizontalAlignment == SwingConstants.CENTER) {
                // Cresce do centro para as bordas
                int offset = (textWidth - lineWidth) / 2;
                lineX = x + offset;
            } else if (horizontalAlignment == SwingConstants.RIGHT || horizontalAlignment == SwingConstants.TRAILING) {
                // Cresce da direita para a esquerda
                lineX = x + textWidth - lineWidth;
            } else { // LEFT ou LEADING
                // LEFT ou LEADING = cresce da esquerda para direita
                lineX = x;
            }

            g2.fillRect(lineX, lineY, lineWidth, LINE_HEIGHT);
        }
    }

    // ============================================ MÉTODOS DE ANIMAÇÃO ============================================
    /**
     * Anima a transição da linha inferior.
     */
    private void animateLine(boolean show) {
        if (lineTimeline != null && !lineTimeline.isDone()) {
            lineTimeline.abort();
        }

        lineTimeline = new Timeline(this);
        lineTimeline.addPropertyToInterpolate("lineAnimationProgress",
                lineAnimationProgress,
                show ? 1f : 0f);
        lineTimeline.setEase(new Spline(0.5f));
        lineTimeline.setDuration(ANIMATION_DURATION);
        lineTimeline.play();
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================
    /**
     * Define a cor da linha inferior do label.
     *
     * @param lineColor A cor da linha inferior
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    /**
     * Define o espaçamento entre o texto e a linha inferior.
     *
     * @param lineSpacing O espaçamento em pixels (deve ser >= 0)
     */
    public void setLineSpacing(int lineSpacing) {
        int oldValue = this.lineSpacing;
        this.lineSpacing = Math.max(0, lineSpacing);
        firePropertyChange("lineSpacing", oldValue, this.lineSpacing);
        repaint();
    }

    /**
     * Define o progresso da animação da linha inferior. Usado internamente pelo sistema de animação.
     *
     * @param lineAnimationProgress O progresso da animação (0.0 a 1.0)
     */
    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    /**
     * Sobrescreve setText para garantir que a linha seja redesenhada.
     */
    @Override
    public void setText(String text) {
        super.setText(text);
        repaint();
    }

    /**
     * Sobrescreve setHorizontalAlignment para redesenhar a linha.
     */
    @Override
    public void setHorizontalAlignment(int alignment) {
        super.setHorizontalAlignment(alignment);
        repaint();
    }

    /**
     * Sobrescreve setVerticalAlignment para redesenhar a linha.
     */
    @Override
    public void setVerticalAlignment(int alignment) {
        super.setVerticalAlignment(alignment);
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================
    /**
     * Retorna a cor atual da linha inferior do label.
     *
     * @return A cor da linha inferior
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Obtém o espaçamento atual entre o texto e a linha inferior.
     *
     * @return O espaçamento em pixels
     */
    public int getLineSpacing() {
        return lineSpacing;
    }

    /**
     * Retorna o progresso atual da animação da linha inferior.
     *
     * @return Um valor entre 0.0 e 1.0
     */
    public float getLineAnimationProgress() {
        return lineAnimationProgress;
    }

    /**
     * Verifica se o mouse está sobre o componente.
     *
     * @return true se o mouse estiver sobre o componente
     */
    public boolean isMouseOver() {
        return mouseOver;
    }
}
