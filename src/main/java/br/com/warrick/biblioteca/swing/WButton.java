package br.com.warrick.biblioteca.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 * Componente de botão personalizado com animações suaves e estilo minimalista.
 *
 * <p>Esta classe estende JButton para fornecer um botão com linha inferior
 * animada e efeito de zoom no texto ao interagir.</p>
 *
 * <p><b>Recursos:</b></p>
 * <ul>
 *   <li>Linha inferior animada ao passar o mouse ou clicar</li>
 *   <li>Efeito de zoom suave no texto</li>
 *   <li>Feedback visual consistente</li>
 *   <li>Personalização de cores</li>
 *   <li>Design minimalista sem bordas tradicionais</li>
 * </ul>
 *
 * @author Warrick
 * @version 1.0.0
 * @since 25/11/2025
 */
public class WButton extends JButton {

    // ============================================ CONSTANTES DE CORES ============================================

    /** Cor padrão da linha inferior quando o botão está em foco ou clicado */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /** Cor de destaque quando o mouse está sobre o componente */
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);

    /** Cor padrão do texto do botão */
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    /** Cor de fundo padrão do botão */
    protected static final Color DEFAULT_BG_COLOR = Color.WHITE;
    
    /** Cor padrão do texto quando o botão está pressionado */
    protected static final Color DEFAULT_PRESSED_TEXT_COLOR = new Color(3, 155, 216);

    /** Cor da linha inferior quando o botão não está em foco */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    // ============================================ CONSTANTES DE LAYOUT ============================================

    /** Altura da linha inferior do botão */
    protected static final int LINE_HEIGHT = 2;

    /** Deslocamento vertical da linha inferior em relação à base do componente */
    protected static final int LINE_Y_OFFSET = 5;

    /** Espaçamento interno superior do botão */
    protected static final int PADDING_TOP = 10;

    /** Espaçamento interno esquerdo do botão */
    protected static final int PADDING_LEFT = 20;

    /** Espaçamento interno inferior do botão */
    protected static final int PADDING_BOTTOM = 10;

    /** Espaçamento interno direito do botão */
    protected static final int PADDING_RIGHT = 20;

    /** Duração da animação em milissegundos */
    protected static final int ANIMATION_DURATION = 300;

    /** Fator de escala máximo do texto (1.0 = normal, 1.10 = 10% maior) */
    protected static final float MAX_TEXT_SCALE = 1.10f;

    // ============================================ ATRIBUTOS ============================================

    /** Localização atual da animação da linha (0.0 a 1.0) */
    protected float lineAnimationProgress = 0f;

    /** Localização atual da animação do texto (0.0 a 1.0) */
    protected float textScaleProgress = 0f;

    /** Indica se o mouse está sobre o componente */
    protected boolean mouseOver = false;

    /** Indica se o botão está sendo pressionado */
    protected boolean pressed = false;

    /** Cor da linha inferior quando o botão está em foco */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /** Cor de destaque quando o mouse está sobre o componente */
    protected Color hoverColor = DEFAULT_HOVER_COLOR;
    
    /** Cor do texto quando o botão está pressionado */
    protected Color pressedTextColor = DEFAULT_PRESSED_TEXT_COLOR;

    /** Controlador de animação para transições suaves da linha */
    protected Timeline lineTimeline;

    /** Controlador de animação para o efeito de zoom do texto */
    protected Timeline textTimeline;

    // ============================================ CONSTRUTORES ============================================

    /**
     * Cria um novo botão com texto vazio.
     */
    public WButton() {
        this("");
    }

    /**
     * Cria um novo botão com o texto especificado.
     *
     * @param text Texto do botão
     */
    public WButton(String text) {
        super(text);
        setupButton();
    }

    // ============================================ MÉTODOS PRIVADOS ============================================

    /**
     * Obtém uma cor do tema FlatLaf ou retorna a cor padrão.
     *
     * @param key Chave da propriedade no tema
     * @param defaultColor Cor padrão se a propriedade não existir
     * @return Cor do tema ou cor padrão
     */
    protected Color getThemeColor(String key, Color defaultColor) {
        try {
            Color color = UIManager.getColor(key);
            return color != null ? color : defaultColor;
        } catch (Exception e) {
            return defaultColor;
        }
    }

    /**
     * Configura as propriedades iniciais do botão.
     */
    private void setupButton() {
        // Configuração de borda e cores
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setBackground(getThemeColor("WButton.bgColor", DEFAULT_BG_COLOR));
        setForeground(getThemeColor("WButton.textColor", DEFAULT_TEXT_COLOR));
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Inicializa as cores do tema
        lineColor = getThemeColor("WButton.lineColor", DEFAULT_LINE_COLOR);
        hoverColor = getThemeColor("WButton.hoverColor", DEFAULT_HOVER_COLOR);

        // Configura os listeners de mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                animateLine(true);
                animateText(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                if (!pressed) {
                    animateLine(false);
                    animateText(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                pressed = true;
                animateLine(true);
                animateText(true);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                pressed = false;
                if (!mouseOver) {
                    animateLine(false);
                    animateText(false);
                }
            }
        });
    }

    // ============================================ MÉTODOS DE ANIMAÇÃO ============================================

    /**
     * Anima a linha inferior do botão.
     *
     * @param show Se verdadeiro, expande a linha; se falso, retrai
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

    /**
     * Anima o efeito de zoom do texto.
     *
     * @param zoom Se verdadeiro, aumenta o texto; se falso, retorna ao tamanho normal
     */
    private void animateText(boolean zoom) {
        if (textTimeline != null && !textTimeline.isDone()) {
            textTimeline.abort();
        }

        textTimeline = new Timeline(this);
        textTimeline.addPropertyToInterpolate("textScaleProgress",
                textScaleProgress,
                zoom ? 1f : 0f);
        textTimeline.setEase(new Spline(0.5f));
        textTimeline.setDuration(ANIMATION_DURATION);
        textTimeline.play();
    }

    // ============================================ MÉTODOS DE PINTURA ============================================

    /**
     * Pinta o componente e seus elementos visuais.
     *
     * @param g O contexto gráfico no qual o componente deve ser desenhado
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Habilita anti-aliasing para renderização suave
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Desenha o fundo
            paintBackground(g2);

            // Desenha as linhas
            paintLines(g2);

            // Desenha o texto com animação
            paintText(g2);

        } finally {
            g2.dispose();
        }
    }

    /**
     * Desenha o fundo do botão.
     *
     * @param g2 O contexto gráfico 2D para desenho
     */
    private void paintBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Desenha as linhas inferior do botão.
     *
     * @param g2 O contexto gráfico 2D para desenho
     */
    private void paintLines(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int lineY = height - LINE_Y_OFFSET;

        // Obtém a cor da linha de fundo do tema
        Color lineBgColor = getThemeColor("WButton.lineBgColor", DEFAULT_LINE_BG_COLOR);
        
        // Desenha a linha de fundo (estado normal)
        g2.setColor(lineBgColor);
        g2.fillRect(2, lineY, width - 4, 1);

        // Desenha a linha de destaque (hover ou pressionado)
        if (mouseOver || pressed) {
            Color currentLineColor;
            
            if (pressed) {
                // Escurece a cor da linha em 20% quando pressionado
                Color pressedLineColor = getThemeColor("WButton.lineColor", lineColor);
                currentLineColor = new Color(
                    Math.max((int)(pressedLineColor.getRed() * 0.8), 0),
                    Math.max((int)(pressedLineColor.getGreen() * 0.8), 0),
                    Math.max((int)(pressedLineColor.getBlue() * 0.8), 0)
                );
            } else {
                currentLineColor = getThemeColor("WButton.hoverColor", hoverColor);
            }
            
            g2.setColor(currentLineColor);

            // Calcula a largura da linha baseada na animação
            int lineWidth = (int) ((width - 4) * lineAnimationProgress);
            int lineX = 2 + (width - 4 - lineWidth) / 2; // Centraliza a linha

            // Aumenta ligeiramente a espessura da linha quando pressionado
            int lineHeight = pressed ? LINE_HEIGHT + 1 : LINE_HEIGHT;
            g2.fillRect(lineX, lineY - (lineHeight / 2), lineWidth, lineHeight);
        }
    }

    /**
     * Desenha o texto do botão com efeito de zoom.
     *
     * @param g2 O contexto gráfico 2D para desenho
     */
    private void paintText(Graphics2D g2) {
        String text = getText();
        if (text == null || text.isEmpty()) {
            return;
        }

        // Obtém a cor do texto do tema, com fallback para a cor atual
        Color textColor = getThemeColor("WButton.textColor", getForeground());
        
        // Se estiver desabilitado, usa a cor de texto desabilitado do tema
        if (!isEnabled()) {
            textColor = getThemeColor("Button.disabledText", Color.GRAY);
        }
        
        // Aplica cor mais escura se o botão estiver pressionado
        if (pressed && isEnabled()) {
            // Escurece a cor em 20%
            textColor = new Color(
                Math.max((int)(textColor.getRed() * 0.8), 0),
                Math.max((int)(textColor.getGreen() * 0.8), 0),
                Math.max((int)(textColor.getBlue() * 0.8), 0)
            );
        } 
        // Aplica cor de hover se o mouse estiver sobre o botão
        else if (mouseOver && isEnabled()) {
            textColor = getThemeColor("WButton.hoverColor", hoverColor);
        }
        
        // Aplica a cor ao texto
        g2.setColor(textColor);

        // Calcula a escala do texto baseada no estado do mouse
        float currentScale = 1.0f;
        if (mouseOver || pressed) {
            currentScale = 1.0f + ((MAX_TEXT_SCALE - 1.0f) * textScaleProgress);
        }

        // Obtém as métricas da fonte com a escala aplicada
        g2.setFont(getFont().deriveFont(getFont().getSize() * currentScale));
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D textBounds = fm.getStringBounds(text, g2);

        // Calcula a posição centralizada do texto
        int textX = (int) ((getWidth() / currentScale - textBounds.getWidth() / currentScale) / 2);
        int textY = (int) ((getHeight() / currentScale - fm.getHeight() / currentScale) / 2 + fm.getAscent() / currentScale);

        // Aplica transformação para o efeito de zoom suave
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                          RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        // Aplica a transformação de escala a partir do centro
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        
        // Move para o centro, aplica o zoom e depois move de volta
        g2.translate(centerX, centerY);
        g2.scale(currentScale, currentScale);
        g2.translate(-centerX, -centerY);
        
        // Desenha o texto
        g2.drawString(text, textX, textY);
        
        // Restaura a transformação
        g2.translate(centerX, centerY);
        g2.scale(1/currentScale, 1/currentScale);
        g2.translate(-centerX, -centerY);
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================

    /**
     * Define a cor da linha inferior.
     *
     * @param lineColor Cor da linha
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    /**
     * Define a cor de destaque ao passar o mouse.
     *
     * @param hoverColor Cor de destaque
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }
    
    /**
     * Define a cor do texto quando o botão está pressionado.
     *
     * @param pressedTextColor Cor do texto pressionado
     */
    public void setPressedTextColor(Color pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
        repaint();
    }
    
    /**
     * Retorna a cor do texto quando o botão está pressionado.
     *
     * @return Cor do texto pressionado
     */
    public Color getPressedTextColor() {
        return pressedTextColor;
    }

    /**
     * Define a localização da animação da linha.
     *
     * @param lineAnimationProgress Valor entre 0 e 1
     */
    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    /**
     * Define o progresso da animação do texto.
     *
     * @param textScaleProgress Valor entre 0 e 1
     */
    public void setTextScaleProgress(float textScaleProgress) {
        this.textScaleProgress = textScaleProgress;
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================

    /**
     * Retorna a cor da linha inferior.
     *
     * @return Cor da linha
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Retorna a cor de destaque ao passar o mouse.
     *
     * @return Cor de destaque
     */
    public Color getHoverColor() {
        return hoverColor;
    }

    /**
     * Retorna a localização atual da animação da linha.
     *
     * @return Valor entre 0 e 1 representando o progresso da animação
     */
    public float getLineAnimationProgress() {
        return lineAnimationProgress;
    }

    /**
     * Retorna o progresso atual da animação do texto.
     *
     * @return Valor entre 0 e 1 representando o progresso da animação
     */
    public float getTextScaleProgress() {
        return textScaleProgress;
    }
}
