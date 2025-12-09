package br.com.warrick.biblioteca.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.pushingpixels.trident.Timeline;

/**
 * Componente de checkbox personalizado com aparência similar ao WTextField.
 *
 * <p>Esta classe estende JCheckBox para fornecer um checkbox com
 * linha inferior animada e feedback visual consistente.</p>
 *
 * <p><b>Recursos:</b></p>
 * <ul>
 *   <li>Linha inferior animada ao receber/perder foco</li>
 *   <li>Feedback visual ao passar o mouse</li>
 *   <li>Personalização de cores</li>
 *   <li>Suporte a temas FlatLaf</li>
 *   <li>Animações suaves de transição</li>
 * </ul>
 *
 * @author Warrick
 * @version 1.0.0
 * @since 26/11/2025
 */
public class WCheckBox extends JCheckBox {

    // ============================================ CONSTANTES DE CORES ============================================

    /** Cor padrão da linha inferior quando o checkbox está em foco */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /** Cor de destaque quando o mouse está sobre o componente */
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);

    /** Cor de fundo do checkbox quando selecionado */
    protected static final Color DEFAULT_CHECK_BG_COLOR = new Color(69, 124, 235);

    /** Cor da linha inferior quando o checkbox não está em foco */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    /** Cor de fundo padrão */
    protected static final Color DEFAULT_BG_COLOR = Color.WHITE;
    
    /** Cor do texto quando selecionado */
    protected static final Color DEFAULT_SELECTED_TEXT_COLOR = new Color(50, 50, 50);
    
    /** Cor do texto quando não selecionado */
    protected static final Color DEFAULT_UNSELECTED_TEXT_COLOR = new Color(100, 100, 100);

    // ============================================ CONSTANTES DE LAYOUT ============================================

    /** Altura da linha inferior */
    protected static final int LINE_HEIGHT = 1;

    /** Deslocamento vertical da linha inferior */
    protected static final int LINE_Y_OFFSET = 2;

    /** Espaçamento interno */
    protected static final int PADDING_TOP = 5;
    protected static final int PADDING_LEFT = 10;
    protected static final int PADDING_BOTTOM = 8;
    protected static final int PADDING_RIGHT = 10;

    /** Duração da animação em milissegundos */
    protected static final int ANIMATION_DURATION = 300;

    /** Tamanho do checkbox */
    protected static final int CHECK_SIZE = 16;

    /** Borda arredondada do checkbox */
    protected static final int CHECK_BORDER = 4;

    // ============================================ ATRIBUTOS ============================================

    /** Localização atual da animação da linha (0.0 a 1.0) */
    protected float lineAnimationProgress = 0f;

    /** Indica se o mouse está sobre o componente */
    protected boolean mouseOver = false;

    /** Cor da linha inferior quando em foco */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /** Cor de destaque quando o mouse está sobre o componente */
    protected Color hoverColor = DEFAULT_HOVER_COLOR;

    /** Cor de fundo do checkbox quando selecionado */
    protected Color checkBgColor = DEFAULT_CHECK_BG_COLOR;
    
    /** Cor do texto quando selecionado */
    protected Color selectedTextColor = DEFAULT_SELECTED_TEXT_COLOR;
    
    /** Cor do texto quando não selecionado */
    protected Color unselectedTextColor = DEFAULT_UNSELECTED_TEXT_COLOR;

    // ============================================ CONSTRUTORES ============================================

    /**
     * Cria um novo checkbox sem texto.
     */
    public WCheckBox() {
        this("");
    }

    /**
     * Cria um novo checkbox com o texto especificado.
     *
     * @param text Texto do checkbox
     */
    public WCheckBox(String text) {
        super(text);
        setupCheckBox();
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
        Color themeColor = UIManager.getColor(key);
        return themeColor != null ? themeColor : defaultColor;
    }

    /**
     * Configura as propriedades iniciais do checkbox.
     */
    private void setupCheckBox() {
        // Configuração de borda e cores
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setBackground(getThemeColor("WCheckBox.bgColor", DEFAULT_BG_COLOR));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Inicializa as cores customizáveis do tema
        lineColor = getThemeColor("WCheckBox.lineColor", DEFAULT_LINE_COLOR);
        hoverColor = getThemeColor("WCheckBox.hoverColor", DEFAULT_HOVER_COLOR);
        checkBgColor = getThemeColor("WCheckBox.checkBgColor", DEFAULT_CHECK_BG_COLOR);
        selectedTextColor = getThemeColor("WCheckBox.selectedTextColor", DEFAULT_SELECTED_TEXT_COLOR);
        unselectedTextColor = getThemeColor("WCheckBox.unselectedTextColor", DEFAULT_UNSELECTED_TEXT_COLOR);

        // Listener de mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });
        
        // Listener para mudança de estado
        addItemListener(e -> {
            updateTextColor();
            repaint();
        });
        
        // Define a cor inicial do texto
        updateTextColor();

        // Listener de foco
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                animateLine(true);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                animateLine(false);
            }
        });
    }

    // ============================================ MÉTODOS DE ANIMAÇÃO ============================================

    /**
     * Anima a linha de foco.
     */
    private void animateLine(boolean show) {
        Timeline lineTimeline = new Timeline(this);
        lineTimeline.addPropertyToInterpolate("lineAnimationProgress",
                lineAnimationProgress,
                show ? 1f : 0f);
        lineTimeline.setDuration(ANIMATION_DURATION);
        lineTimeline.play();
    }

    // ============================================ MÉTODOS DE PINTURA ============================================

    /**
     * Atualiza a cor do texto com base no estado de seleção.
     * Verifica as cores do tema antes de aplicar.
     */
    protected void updateTextColor() {
        Color selected = getThemeColor("WCheckBox.selectedTextColor", selectedTextColor);
        Color unselected = getThemeColor("WCheckBox.unselectedTextColor", unselectedTextColor);
        setForeground(isSelected() ? selected : unselected);
    }
    
    /**
     * Define a cor do texto quando o checkbox está selecionado.
     * Se for passado null, usa a cor do tema ou o padrão.
     * 
     * @param color Cor do texto selecionado ou null para usar o tema
     */
    public void setSelectedTextColor(Color color) {
        this.selectedTextColor = color != null ? color : 
            getThemeColor("WCheckBox.selectedTextColor", DEFAULT_SELECTED_TEXT_COLOR);
        updateTextColor();
    }
    
    /**
     * Define a cor do texto quando o checkbox não está selecionado.
     * Se for passado null, usa a cor do tema ou o padrão.
     * 
     * @param color Cor do texto não selecionado ou null para usar o tema
     */
    public void setUnselectedTextColor(Color color) {
        this.unselectedTextColor = color != null ? color : 
            getThemeColor("WCheckBox.unselectedTextColor", DEFAULT_UNSELECTED_TEXT_COLOR);
        updateTextColor();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int lineY = height - LINE_Y_OFFSET;

            // Obtém cor da linha de fundo do tema
            Color currentLineBgColor = getThemeColor("WCheckBox.lineBgColor", DEFAULT_LINE_BG_COLOR);

            // Desenha a linha de fundo
            g2.setColor(currentLineBgColor);
            g2.fillRect(2, lineY, width - 4, 1);

            // Linha de destaque (hover ou foco)
            if (mouseOver || isFocusOwner()) {
                Color highlightColor = mouseOver && !isFocusOwner() ? hoverColor : lineColor;
                g2.setColor(highlightColor);
                int lineWidth = isFocusOwner() ? (int) ((width - 4) * lineAnimationProgress) : (width - 4);
                g2.fillRect(2, lineY - (LINE_HEIGHT / 2), lineWidth, LINE_HEIGHT);
            }

            // Desenha o checkbox customizado
            paintCheckBox(g2);

            // Desenha o texto
            if (getText() != null && !getText().isEmpty()) {
                paintText(g2);
            }

        } finally {
            g2.dispose();
        }
    }

    /**
     * Desenha o checkbox customizado.
     */
    private void paintCheckBox(Graphics2D g2) {
        int ly = (getHeight() - CHECK_SIZE) / 2;

        if (isSelected()) {
            // Checkbox selecionado
            if (isEnabled()) {
                g2.setColor(checkBgColor);
            } else {
                g2.setColor(Color.GRAY);
            }
            g2.fillRoundRect(1, ly, CHECK_SIZE, CHECK_SIZE, CHECK_BORDER, CHECK_BORDER);

            // Desenha o ícone de check
            int px[] = {4, 8, 14, 12, 8, 6};
            int py[] = {ly + 8, ly + 14, ly + 5, ly + 3, ly + 10, ly + 6};
            g2.setColor(Color.WHITE);
            g2.fillPolygon(px, py, px.length);
        } else {
            // Checkbox não selecionado
            g2.setColor(Color.GRAY);
            g2.fillRoundRect(1, ly, CHECK_SIZE, CHECK_SIZE, CHECK_BORDER, CHECK_BORDER);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(2, ly + 1, 14, 14, CHECK_BORDER, CHECK_BORDER);
        }
    }

    /**
     * Desenha o texto do checkbox.
     */
    private void paintText(Graphics2D g2) {
        String text = getText();
        if (text == null || text.isEmpty()) {
            return;
        }
        
        // Obtém a cor correta do texto
        Color textColor;
        if (!isEnabled()) {
            textColor = getThemeColor("CheckBox.disabledText", Color.GRAY);
        } else if (isSelected()) {
            textColor = getThemeColor("WCheckBox.selectedTextColor", selectedTextColor);
        } else {
            textColor = getThemeColor("WCheckBox.unselectedTextColor", unselectedTextColor);
        }
        
        // Configura a fonte e a cor
        g2.setFont(getFont());
        g2.setColor(textColor);
        
        // Calcula a posição do texto
        FontMetrics fm = g2.getFontMetrics();
        int textX = CHECK_SIZE + 8;
        int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        
        // Desenha o texto
        g2.drawString(text, textX, textY);
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }

    public void setCheckBgColor(Color checkBgColor) {
        this.checkBgColor = checkBgColor;
        repaint();
    }

    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================

    public Color getLineColor() {
        return lineColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public Color getCheckBgColor() {
        return checkBgColor;
    }
    
    /**
     * Retorna a cor do texto quando o checkbox está selecionado.
     * 
     * @return Cor do texto selecionado
     */
    public Color getSelectedTextColor() {
        return selectedTextColor;
    }
    
    /**
     * Retorna a cor do texto quando o checkbox não está selecionado.
     * 
     * @return Cor do texto não selecionado
     */
    public Color getUnselectedTextColor() {
        return unselectedTextColor;
    }

    public float getLineAnimationProgress() {
        return lineAnimationProgress;
    }
}