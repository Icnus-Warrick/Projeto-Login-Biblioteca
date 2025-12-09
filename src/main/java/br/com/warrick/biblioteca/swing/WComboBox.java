package br.com.warrick.biblioteca.swing;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;
/**
 * Componente de combo box personalizado com suporte a animações, rótulo flutuante e validação.
 *
 * <p>Esta classe estende JComboBox para fornecer um combo box com
 * rótulo flutuante, feedback visual consistente e sistema de validação integrado.</p>
 *
 * <p><b>Recursos:</b></p>
 * <ul>
 *   <li>Rótulo flutuante que se move suavemente</li>
 *   <li>Animação de transição ao receber/perder foco</li>
 *   <li>Feedback visual ao passar o mouse</li>
 *   <li>Sistema de validação com mensagens de erro animadas</li>
 *   <li>Validação de campos obrigatórios (através do método setObrigatorio(true))</li>
 *   <li>Personalização de cores</li>
 *   <li>Suporte a temas FlatLaf</li>
 *   <li>Validação em tempo real</li>
 * </ul>
 *
 * @author Warrick
 * @version 3.2.2
 * @since 26/11/2025
 */
public class WComboBox<E> extends JComboBox<E> {
    // ============================================ CONSTANTES DE CORES ============================================

    /** Cor padrão da linha inferior quando o campo está em foco */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /** Cor de destaque quando o mouse está sobre o componente */
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);

    /** Cor padrão do texto selecionado no campo */
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    /** Cor padrão do texto de dica (hint) quando o campo está vazio */
    protected static final Color DEFAULT_HINT_COLOR = new Color(150, 150, 150);

    /** Cor de fundo padrão do campo */
    protected static final Color DEFAULT_BG_COLOR = Color.WHITE;

    /** Cor da linha inferior quando o campo não está em foco */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    /** Cor de erro para mensagens de validação */
    protected static final Color ERROR_COLOR = new Color(220, 53, 69);

    /** Cor de sucesso para mensagens de validação */
    protected static final Color SUCCESS_COLOR = new Color(40, 167, 69);

    /** Cor de fundo da lista dropdown */
    protected static final Color DEFAULT_LIST_BG_COLOR = new Color(250, 250, 250);

    /** Cor de seleção na lista */
    protected static final Color DEFAULT_SELECTION_COLOR = new Color(3, 155, 216);

    // ============================================ CONSTANTES DE LAYOUT ============================================

    /** Altura da linha inferior do campo */
    protected static final int LINE_HEIGHT = 1;

    /** Deslocamento vertical da linha inferior em relação à base do componente */
    protected static final int LINE_Y_OFFSET = 14;

    /** Espaçamento interno superior do campo */
    protected static final int PADDING_TOP = 20;

    /** Espaçamento interno esquerdo do campo */
    protected static final int PADDING_LEFT = 10;

    /** Espaçamento interno inferior do campo */
    protected static final int PADDING_BOTTOM = 15;

    /** Espaçamento interno direito do campo */
    protected static final int PADDING_RIGHT = 10;

    /** Posição vertical do rótulo quando na posição superior */
    protected static final int LABEL_TOP_POSITION = 13;

    /** Duração da animação em milissegundos */
    protected static final int ANIMATION_DURATION = 300;

    /** Posição vertical da mensagem de erro */
    protected static final int ERROR_MESSAGE_Y_OFFSET = -2;

    // ============================================ ATRIBUTOS ============================================

    /** Localização atual da animação da linha (0.0 a 1.0) */
    protected float lineAnimationProgress = 0f;

    /** Localização atual da animação do rótulo (0.0 a 1.0) */
    protected float animationLocation = 0f;

    /** Localização atual da animação da mensagem de erro (0.0 a 1.0) */
    protected float errorAnimationLocation = 0f;

    /** Indica se o rótulo deve ser mostrado na posição superior */
    protected boolean showLabel = false;

    /** Indica se o mouse está sobre o componente */
    protected boolean mouseOver = false;

    /** Indica se o campo é obrigatório */
    protected boolean obrigatorio = false;

    /** Indica se há um erro de validação ativo */
    protected boolean hasError = false;

    /** Indica se a mensagem atual é de sucesso */
    protected boolean isSuccessMessage = false;

    /** Texto do rótulo flutuante */
    protected String labelText = "";

    /** Mensagem de erro atual */
    protected String errorMessage = "";

    /** Cor da linha inferior quando o campo está em foco */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /** Cor de destaque quando o mouse está sobre o componente */
    protected Color hoverColor = DEFAULT_HOVER_COLOR;

    /** Controlador de animação para transições suaves do rótulo */
    protected Timeline timeline;

    /** Controlador de animação para mensagens de erro */
    protected Timeline errorTimeline;

    // ============================================ CONSTRUTORES ============================================

    /**
     * Cria um novo combo box com rótulo vazio.
     */
    public WComboBox() {
        this("");
    }

    /**
     * Cria um novo combo box com o rótulo especificado.
     *
     * @param labelText Texto do rótulo que será exibido como dica flutuante
     */
    public WComboBox(String labelText) {
        super();
        this.labelText = labelText;
        setupComboBox();
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
     * Inicializa as animações do componente.
     */
    private void initAnimation() {
        animationLocation = getSelectedIndex() != -1 ? 1f : 0f;
        showLabel = getSelectedIndex() != -1;
        errorAnimationLocation = 0f;
    }

    /**
     * Configura as propriedades iniciais do combo box.
     */
    private void setupComboBox() {
        // Configuração de borda e cores (usando tema se disponível)
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setBackground(getThemeColor("WComboBox.bgColor", DEFAULT_BG_COLOR));
        setForeground(getThemeColor("WComboBox.textColor", DEFAULT_TEXT_COLOR));
        setOpaque(false);

        // Inicializa as cores customizáveis do tema
        lineColor = getThemeColor("WComboBox.lineColor", DEFAULT_LINE_COLOR);
        hoverColor = getThemeColor("WComboBox.hoverColor", DEFAULT_HOVER_COLOR);

        // Inicializa a animação
        initAnimation();

        // Instala a UI customizada
        setUI(new WComboBoxUI(this));

        // Renderer customizado
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Component com = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(8, 10, 8, 10));

                if (isSelected) {
                    com.setBackground(getThemeColor("WComboBox.selectionColor", DEFAULT_SELECTION_COLOR));
                    com.setForeground(Color.WHITE);
                } else {
                    com.setBackground(getThemeColor("WComboBox.listBgColor", DEFAULT_LIST_BG_COLOR));
                    com.setForeground(getThemeColor("WComboBox.textColor", DEFAULT_TEXT_COLOR));
                }
                return com;
            }
        });

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

        // Listener de foco
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                updateLabelState(true);
                animateLine(true);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                updateLabelState(false);
                animateLine(false);
            }
        });

        // Listener de seleção para limpar erros
        addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (hasError) {
                    limparErro();
                }
                updateLabelState(isFocusOwner());
            }
        });
    }

    /**
     * Sobrescreve o paint para permitir desenhar a mensagem fora dos limites.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Desenha a mensagem de erro/sucesso fora dos limites do componente
        if (hasError && errorMessage != null && !errorMessage.isEmpty() && errorAnimationLocation > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                paintExternalMessage(g2);
            } finally {
                g2.dispose();
            }
        }
    }

    /**
     * Desenha a mensagem de erro/sucesso abaixo do campo.
     */
    private void paintExternalMessage(Graphics2D g2) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return;
        }

        // Obtém cor do tema ou usa padrão
        Color messageColor = isSuccessMessage ?
                getThemeColor("WComboBox.successColor", SUCCESS_COLOR) :
                getThemeColor("WComboBox.errorColor", ERROR_COLOR);

        // Aplica transparência baseada na animação
        int alpha = (int)(255 * errorAnimationLocation);
        g2.setColor(new Color(messageColor.getRed(), messageColor.getGreen(),
                messageColor.getBlue(), alpha));

        Font originalFont = g2.getFont();
        g2.setFont(originalFont.deriveFont(Font.PLAIN, 11f));

        int erroY = getHeight() + ERROR_MESSAGE_Y_OFFSET + (int)(3 * (1 - errorAnimationLocation));
        g2.drawString(errorMessage, PADDING_LEFT, erroY);

        g2.setFont(originalFont);
    }

    /**
     * Atualiza o estado do rótulo com base no foco e seleção.
     */
    private void updateLabelState(boolean hasFocus) {
        boolean shouldShow = hasFocus || getSelectedIndex() != -1;

        if (showLabel == shouldShow && (timeline == null || timeline.isDone())) {
            return;
        }

        if (timeline != null && !timeline.isDone()) {
            timeline.abort();
        }

        showLabel = shouldShow;
        animateLabel(showLabel);
    }

    // ============================================ MÉTODOS DE ANIMAÇÃO ============================================

    /**
     * Anima a transição do rótulo.
     */
    private void animateLabel(boolean show) {
        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("animationLocation",
                animationLocation,
                show ? 1f : 0f);
        timeline.setEase(new Spline(0.5f));
        timeline.setDuration(ANIMATION_DURATION);
        timeline.play();
    }

    /**
     * Anima a mensagem de erro.
     */
    private void animateError(boolean show) {
        if (errorTimeline != null && !errorTimeline.isDone()) {
            errorTimeline.abort();
        }

        errorTimeline = new Timeline(this);
        errorTimeline.addPropertyToInterpolate("errorAnimationLocation",
                errorAnimationLocation,
                show ? 1f : 0f);
        errorTimeline.setEase(new Spline(0.5f));
        errorTimeline.setDuration(ANIMATION_DURATION);
        errorTimeline.play();
    }

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

    // ============================================ CLASSE INTERNA - UI CUSTOMIZADA ============================================

    private class WComboBoxUI extends BasicComboBoxUI {
        private final WComboBox<E> combo;

        public WComboBoxUI(WComboBox<E> combo) {
            this.combo = combo;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Não pinta fundo - deixamos o componente pai fazer isso
        }

        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    list.setFixedCellHeight(35);
                    JScrollPane scroll = new JScrollPane(list);
                    scroll.setBackground(getThemeColor("WComboBox.listBgColor", DEFAULT_LIST_BG_COLOR));

                    // Usar ScrollBar customizado
                    WScrollBar sb = new WScrollBar();
                    sb.setUnitIncrement(35);
                    sb.setForeground(lineColor);
                    scroll.setVerticalScrollBar(sb);

                    return scroll;
                }
            };
            popup.setBorder(new LineBorder(lineColor, 1));

            // Listener para mudança de cor do botão
            popup.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(lineColor);
                        arrowButton.repaint();
                    }
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(getThemeColor("WComboBox.bgColor", DEFAULT_BG_COLOR));
                        arrowButton.repaint();
                    }
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(getThemeColor("WComboBox.bgColor", DEFAULT_BG_COLOR));
                        arrowButton.repaint();
                    }
                }
            });

            return popup;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                paintComponents(g2);
            } finally {
                g2.dispose();
            }
        }

        /**
         * Desenha os componentes visuais do campo.
         */
        protected void paintComponents(Graphics2D g2) {
            int width = combo.getWidth();
            int height = combo.getHeight();
            int lineY = height - LINE_Y_OFFSET;

            // Obtém cor da linha de fundo do tema
            Color currentLineBgColor = getThemeColor("WComboBox.lineBgColor", DEFAULT_LINE_BG_COLOR);

            // Desenha a linha de fundo
            g2.setColor(currentLineBgColor);
            g2.fillRect(2, lineY, width - 4, 1);

            // Linha de destaque (hover, foco ou erro)
            if (hasError) {
                Color errorLineColor = isSuccessMessage ?
                        getThemeColor("WComboBox.successColor", SUCCESS_COLOR) :
                        getThemeColor("WComboBox.errorColor", ERROR_COLOR);
                g2.setColor(errorLineColor);
                g2.fillRect(2, lineY - (LINE_HEIGHT / 2), width - 4, LINE_HEIGHT);
            } else if (mouseOver || combo.isFocusOwner()) {
                Color highlightColor = mouseOver && !combo.isFocusOwner() ? hoverColor : lineColor;
                g2.setColor(highlightColor);
                int lineWidth = combo.isFocusOwner() ? (int) ((width - 4) * lineAnimationProgress) : (width - 4);
                g2.fillRect(2, lineY - (LINE_HEIGHT / 2), lineWidth, LINE_HEIGHT);
            }

            // Desenha o rótulo
            paintLabel(g2);
        }

        /**
         * Desenha o rótulo flutuante.
         */
        private void paintLabel(Graphics2D g2) {
            if (labelText == null || labelText.isEmpty()) {
                return;
            }

            Insets in = combo.getInsets();

            // Define cor do rótulo baseada no estado
            Color labelColor;
            if (hasError) {
                labelColor = isSuccessMessage ?
                        getThemeColor("WComboBox.successColor", SUCCESS_COLOR) :
                        getThemeColor("WComboBox.errorColor", ERROR_COLOR);
            } else if (combo.isFocusOwner()) {
                labelColor = lineColor;
            } else if (combo.getSelectedIndex() != -1) {
                labelColor = getThemeColor("WComboBox.textColor", DEFAULT_TEXT_COLOR);
            } else {
                labelColor = getThemeColor("WComboBox.hintColor", DEFAULT_HINT_COLOR);
            }
            g2.setColor(labelColor);

            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D textBounds = fm.getStringBounds(labelText, g2);

            // Calcula posições com animação
            double centerY = (combo.getHeight() - textBounds.getHeight()) / 2 + fm.getAscent();
            double topY = LABEL_TOP_POSITION;
            double currentY = centerY - ((centerY - topY) * animationLocation);

            double bottomX = in.left;
            double topX = 5;
            double currentX = bottomX - ((bottomX - topX) * animationLocation);

            float scale = 1.0f + (0.04f * animationLocation);

            Graphics2D g2d = (Graphics2D) g2.create();
            try {
                g2d.translate(currentX, 0);
                g2d.scale(scale, scale);
                g2d.drawString(labelText, 0, (float) (currentY / scale));
            } finally {
                g2d.dispose();
            }
        }

        private class ArrowButton extends JButton {
            public ArrowButton() {
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(10, 5, 5, 5));
                setBackground(getThemeColor("WComboBox.bgColor", DEFAULT_BG_COLOR));
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int width = getWidth();
                    int height = getHeight();
                    int size = 8;
                    int x = (width - size) / 2;
                    int y = (height - size) / 2 + 3;

                    int[] px = {x, x + size, x + size / 2};
                    int[] py = {y, y, y + size};

                    Color arrowColor = hasError ?
                            (isSuccessMessage ? SUCCESS_COLOR : ERROR_COLOR) :
                            (combo.isFocusOwner() ? lineColor : getThemeColor("WComboBox.textColor", DEFAULT_TEXT_COLOR));

                    g2.setColor(arrowColor);
                    g2.fillPolygon(px, py, px.length);
                } finally {
                    g2.dispose();
                }
            }
        }
    }

    // ========================================= MÉTODOS PRIVADOS DE VALIDAÇÃO =========================================

    /**
     * Verifica se o campo obrigatório está preenchido.
     */
    private boolean validarObrigatorio() {
        if (obrigatorio && getSelectedIndex() == -1) {
            // Se tiver um rótulo definido, usa no texto de erro
            if (labelText != null && !labelText.trim().isEmpty()) {
                String rotulo = labelText.trim();
                if (rotulo.endsWith(":") || rotulo.endsWith(": ")) {
                    rotulo = rotulo.substring(0, rotulo.length() - 1).trim();
                }
                rotulo = rotulo.substring(0, 1).toUpperCase() + rotulo.substring(1).toLowerCase();
                setMensagem(rotulo + " é obrigatório", false);
            } else {
                setMensagem("Seleção obrigatória", false);
            }
            return false;
        }
        return true;
    }

    // ========================================= MÉTODOS PÚBLICOS DE VALIDAÇÃO =========================================

    /**
     * Define uma mensagem (erro ou sucesso) no campo.
     */
    public void setMensagem(String mensagem, boolean sucesso) {
        this.errorMessage = mensagem;
        this.isSuccessMessage = sucesso;
        this.hasError = !sucesso;

        updateLabelState(isFocusOwner());
        animateError(true);
        repaint();
    }

    /**
     * Exibe uma mensagem de sucesso.
     */
    public void mostrarSucesso(String mensagem) {
        setMensagem(mensagem, true);
    }

    /**
     * Valida o campo.
     */
    public boolean validar() {
        return validarObrigatorio();
    }

    /**
     * Valida com mensagem personalizada.
     */
    public boolean validarComMensagem(String mensagem) {
        mostrarErro(mensagem);
        return false;
    }

    /**
     * Exibe uma mensagem de erro personalizada.
     */
    public void mostrarErro(String mensagem) {
        setMensagem(mensagem, false);
    }

    /**
     * Limpa a mensagem de erro/sucesso.
     */
    public void limparMensagem() {
        if (!hasError && (errorMessage == null || errorMessage.isEmpty())) {
            return;
        }

        if (errorTimeline != null && !errorTimeline.isDone()) {
            errorTimeline.abort();
        }

        animateError(false);

        new Thread(() -> {
            try {
                Thread.sleep(ANIMATION_DURATION);
                this.hasError = false;
                this.errorMessage = "";
                this.isSuccessMessage = false;
                updateLabelState(isFocusOwner());
                repaint();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * @deprecated Use {@link #limparMensagem()}
     */
    @Deprecated
    public void limparErro() {
        limparMensagem();
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================

    @Override
    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
        updateLabelState(isFocusOwner());
        if (hasError && index != -1) {
            limparErro();
        }
    }

    @Override
    public void setSelectedItem(Object item) {
        super.setSelectedItem(item);
        updateLabelState(isFocusOwner());
        if (hasError && item != null) {
            limparErro();
        }
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }

    public void setObrigatorio(boolean obrigatorio) {
        boolean oldValue = this.obrigatorio;
        this.obrigatorio = obrigatorio;
        firePropertyChange("obrigatorio", oldValue, obrigatorio);
    }

    public void setAnimationLocation(float animationLocation) {
        this.animationLocation = animationLocation;
        repaint();
    }

    public void setErrorAnimationLocation(float errorAnimationLocation) {
        this.errorAnimationLocation = errorAnimationLocation;
        repaint();
    }

    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================

    public String getLabelText() {
        return labelText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public float getAnimationLocation() {
        return animationLocation;
    }

    public float getErrorAnimationLocation() {
        return errorAnimationLocation;
    }

    public float getLineAnimationProgress() {
        return lineAnimationProgress;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public boolean hasError() {
        return hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // ============================================ CLASSE INTERNA - SCROLLBAR CUSTOMIZADO ============================================

    /**
     * ScrollBar customizado para o dropdown do WComboBox.
     * Implementa uma barra de rolagem moderna e minimalista.
     */
    private static class WScrollBar extends JScrollBar {

        public WScrollBar() {
            setUI(new WScrollBarUI());
            setPreferredSize(new Dimension(8, 8));
            setForeground(new Color(180, 180, 180));
            setBackground(Color.WHITE);
        }

        private class WScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {

            private final int THUMB_SIZE = 60;

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = WScrollBar.this.getForeground();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int orientation = scrollbar.getOrientation();
                if (orientation == JScrollBar.VERTICAL) {
                    int x = trackBounds.x + (trackBounds.width / 2) - 2;
                    g2.setColor(new Color(240, 240, 240));
                    g2.fillRect(x, trackBounds.y, 4, trackBounds.height);
                } else {
                    int y = trackBounds.y + (trackBounds.height / 2) - 2;
                    g2.setColor(new Color(240, 240, 240));
                    g2.fillRect(trackBounds.x, y, trackBounds.width, 4);
                }
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int orientation = scrollbar.getOrientation();
                if (orientation == JScrollBar.VERTICAL) {
                    int x = thumbBounds.x + (thumbBounds.width / 2) - 3;
                    int y = thumbBounds.y;
                    int width = 6;
                    int height = thumbBounds.height;

                    g2.setColor(thumbColor);
                    g2.fillRoundRect(x, y, width, height, 6, 6);
                } else {
                    int x = thumbBounds.x;
                    int y = thumbBounds.y + (thumbBounds.height / 2) - 3;
                    int width = thumbBounds.width;
                    int height = 6;

                    g2.setColor(thumbColor);
                    g2.fillRoundRect(x, y, width, height, 6, 6);
                }
            }

            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        }
    }
}