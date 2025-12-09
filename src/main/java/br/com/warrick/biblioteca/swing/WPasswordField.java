package br.com.warrick.biblioteca.swing;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 * Componente de campo de senha personalizado com suporte a animações, rótulo flutuante e validação.
 *
 * <p>Esta classe estende JPasswordField para fornecer um campo de senha com
 * rótulo flutuante, feedback visual consistente e sistema de validação integrado.</p>
 *
 * <p><b>Recursos:</b></p>
 * <ul>
 *   <li>Rótulo flutuante que se move suavemente</li>
 *   <li>Animação de transição ao receber/perder foco</li>
 *   <li>Botão para mostrar/esconder a senha</li>
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
 * @since 25/11/2025
 */
public class WPasswordField extends JPasswordField {
    // ============================================ CONSTANTES DE CORES ============================================

    /** Cor padrão da linha inferior quando o campo está em foco */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /** Cor de destaque quando o mouse está sobre o componente */
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);

    /** Cor padrão do texto digitado no campo */
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

    /** Largura do botão de mostrar/esconder senha */
    protected static final int EYE_RIGHT_PADDING = 35;

    /** Tamanho do ícone de olho */
    protected static final int EYE_ICON_SIZE = 20;

    /** Margem ao redor do ícone de olho */
    protected static final int EYE_ICON_MARGIN = 5;

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

    /** Indica se a senha está oculta */
    protected boolean hidePassword = true;

    /** Habilita/desabilita o botão de mostrar/esconder senha */
    protected boolean showAndHide = true;

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

    /** Ícone para mostrar a senha */
    protected Image eyeIcon;

    /** Ícone para esconder a senha */
    protected Image eyeHideIcon;

    // ============================================ CONSTRUTORES ============================================

    /**
     * Cria um novo campo de senha com rótulo vazio.
     */
    public WPasswordField() {
        this("");
    }

    /**
     * Cria um novo campo de senha com o rótulo especificado.
     *
     * @param labelText Texto do rótulo que será exibido como dica flutuante
     */
    public WPasswordField(String labelText) {
        super();
        this.labelText = labelText;
        setupField();
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
        animationLocation = getPassword().length == 0 ? 0f : 1f;
        showLabel = getPassword().length > 0;
        errorAnimationLocation = 0f;
    }

    /**
     * Configura as propriedades iniciais do campo.
     */
    private void setupField() {
        // Configuração de borda e cores (usando tema se disponível)
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, EYE_RIGHT_PADDING));
        setBackground(getThemeColor("WPasswordField.bgColor", DEFAULT_BG_COLOR));
        setForeground(getThemeColor("WPasswordField.textColor", DEFAULT_TEXT_COLOR));
        setCaretColor(getThemeColor("WPasswordField.textColor", DEFAULT_TEXT_COLOR));
        setOpaque(false);
        setEchoChar('•');

        // Inicializa as cores customizáveis do tema
        lineColor = getThemeColor("WPasswordField.lineColor", DEFAULT_LINE_COLOR);
        hoverColor = getThemeColor("WPasswordField.hoverColor", DEFAULT_HOVER_COLOR);

        // Inicializa a animação
        initAnimation();

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

            @Override
            public void mousePressed(MouseEvent me) {
                if (showAndHide) {
                    int buttonSize = EYE_RIGHT_PADDING - EYE_ICON_MARGIN;
                    int x = getWidth() - EYE_RIGHT_PADDING;
                    if (new Rectangle(x, 0, buttonSize, getHeight()).contains(me.getPoint())) {
                        togglePasswordVisibility();
                    }
                }
            }
        });

        // Listener para cursor no botão mostrar/esconder
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                if (showAndHide) {
                    int buttonSize = EYE_RIGHT_PADDING - EYE_ICON_MARGIN;
                    int x = getWidth() - EYE_RIGHT_PADDING;
                    if (new Rectangle(x, 0, buttonSize, getHeight()).contains(me.getPoint())) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.TEXT_CURSOR));
                    }
                }
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

        // Listener para limpar erros durante a digitação
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            private void limparErroSeNecessario() {
                if (hasError && getPassword().length > 0) {
                    limparErro();
                }
            }
        });

        // Carrega os ícones
        loadIcons();
    }

    /**
     * Alterna a visibilidade da senha.
     */
    private void togglePasswordVisibility() {
        hidePassword = !hidePassword;
        setEchoChar(hidePassword ? '•' : (char) 0);
        repaint();
    }

    /**
     * Carrega os ícones de mostrar/esconder senha.
     */
    private void loadIcons() {
        try {
            eyeIcon = new ImageIcon(getClass().getResource("/br/com/warrick/biblioteca/Icone/eye.png")).getImage();
            eyeHideIcon = new ImageIcon(getClass().getResource("/br/com/warrick/biblioteca/Icone/eye_hide.png")).getImage();
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível carregar os ícones do classpath: " + e.getMessage());
            showAndHide = false;
            System.err.println("O botão de mostrar/esconder senha foi desabilitado.");
        }
    }

    /**
     * Pinta o componente e seus elementos visuais.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintComponents(g2);

            // Desenha o botão de mostrar/esconder senha, se habilitado
            if (showAndHide) {
                paintToggleButton(g2);
            }
        } finally {
            g2.dispose();
        }
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
                getThemeColor("WPasswordField.successColor", SUCCESS_COLOR) :
                getThemeColor("WPasswordField.errorColor", ERROR_COLOR);

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
     * Atualiza o estado do rótulo com base no foco e conteúdo.
     */
    private void updateLabelState(boolean hasFocus) {
        boolean shouldShow = hasFocus || getPassword().length > 0;

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

    // ============================================ MÉTODOS DE PINTURA ============================================

    /**
     * Desenha os componentes visuais do campo.
     */
    protected void paintComponents(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int lineY = height - LINE_Y_OFFSET;

        // Obtém cor da linha de fundo do tema
        Color currentLineBgColor = getThemeColor("WPasswordField.lineBgColor", DEFAULT_LINE_BG_COLOR);

        // Desenha a linha de fundo
        g2.setColor(currentLineBgColor);
        g2.fillRect(2, lineY, width - 4, 1);

        // Linha de destaque (hover, foco ou erro)
        if (hasError) {
            Color errorLineColor = isSuccessMessage ?
                    getThemeColor("WPasswordField.successColor", SUCCESS_COLOR) :
                    getThemeColor("WPasswordField.errorColor", ERROR_COLOR);
            g2.setColor(errorLineColor);
            g2.fillRect(2, lineY - (LINE_HEIGHT / 2), width - 4, LINE_HEIGHT);
        } else if (mouseOver || isFocusOwner()) {
            Color highlightColor = mouseOver && !isFocusOwner() ? hoverColor : lineColor;
            g2.setColor(highlightColor);
            int lineWidth = isFocusOwner() ? (int) ((width - 4) * lineAnimationProgress) : (width - 4);
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

        Insets in = getInsets();

        // Define cor do rótulo baseada no estado
        Color labelColor;
        if (hasError) {
            labelColor = isSuccessMessage ?
                    getThemeColor("WPasswordField.successColor", SUCCESS_COLOR) :
                    getThemeColor("WPasswordField.errorColor", ERROR_COLOR);
        } else if (isFocusOwner()) {
            labelColor = lineColor;
        } else if (getPassword().length > 0) {
            labelColor = getThemeColor("WPasswordField.textColor", DEFAULT_TEXT_COLOR);
        } else {
            labelColor = getThemeColor("WPasswordField.hintColor", DEFAULT_HINT_COLOR);
        }
        g2.setColor(labelColor);

        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D textBounds = fm.getStringBounds(labelText, g2);

        // Calcula posições com animação
        double centerY = (getHeight() - textBounds.getHeight()) / 2 + fm.getAscent();
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

    /**
     * Desenha o botão de mostrar/esconder senha.
     */
    protected void paintToggleButton(Graphics2D g2) {
        int buttonX = getWidth() - EYE_RIGHT_PADDING + EYE_ICON_MARGIN;
        int buttonY = (getHeight() - EYE_ICON_SIZE) / 2;

        Image icon = hidePassword ? eyeIcon : eyeHideIcon;
        if (icon != null) {
            g2.drawImage(icon, buttonX, buttonY, EYE_ICON_SIZE, EYE_ICON_SIZE, null);
        } else {
            // Fallback: desenha um retângulo simples se o ícone não estiver disponível
            g2.setColor(Color.GRAY);
            g2.fillRect(buttonX, buttonY, EYE_ICON_SIZE, EYE_ICON_SIZE);
        }
    }

    // ========================================= MÉTODOS PRIVADOS DE VALIDAÇÃO =========================================

    /**
     * Verifica se o campo obrigatório está preenchido.
     * Se o campo tiver um rótulo definido, ele será incluído na mensagem de erro.
     *
     * @return true se o campo for válido, false caso contrário
     */
    private boolean validarObrigatorio() {
        if (obrigatorio && getPassword().length == 0) {
            // Se tiver um rótulo definido, usa no texto de erro
            if (labelText != null && !labelText.trim().isEmpty()) {
                // Remove os dois pontos do final, se existirem
                String rotulo = labelText.trim();
                if (rotulo.endsWith(":") || rotulo.endsWith(": ")) {
                    rotulo = rotulo.substring(0, rotulo.length() - 1).trim();
                }
                // Capitaliza a primeira letra do rótulo
                rotulo = rotulo.substring(0, 1).toUpperCase() + rotulo.substring(1).toLowerCase();
                setMensagem(rotulo + " é obrigatório", false);
            } else {
                // Se não tiver rótulo definido, usa a mensagem genérica
                setMensagem("Campo obrigatório", false);
            }
            return false;
        }
        return true;
    }

    // ========================================= MÉTODOS PÚBLICOS DE VALIDAÇÃO =========================================

    /**
     * Define uma mensagem (erro ou sucesso) no campo.
     *
     * @param mensagem Texto da mensagem
     * @param sucesso true para mensagem de sucesso, false para erro
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
     * Esta mensagem sobrepõe qualquer mensagem de erro ou obrigatoriedade.
     *
     * @param mensagem Texto da mensagem de sucesso
     */
    public void mostrarSucesso(String mensagem) {
        setMensagem(mensagem, true);
    }

    /**
     * Valida o campo.
     *
     * @return true se o campo for válido, false caso contrário
     */
    public boolean validar() {
        return validarObrigatorio();
    }

    /**
     * Valida com mensagem personalizada.
     *
     * @param mensagem Mensagem de erro personalizada
     * @return false (sempre retorna false ao mostrar erro)
     */
    public boolean validarComMensagem(String mensagem) {
        mostrarErro(mensagem);
        return false;
    }

    /**
     * Exibe uma mensagem de erro personalizada.
     * Esta mensagem sobrepõe a mensagem de campo obrigatório, se houver.
     *
     * @param mensagem Texto da mensagem de erro
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
    public void setText(String text) {
        super.setText(text);
        updateLabelState(isFocusOwner());
        if (hasError && text != null && !text.trim().isEmpty()) {
            limparErro();
        }
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    public void setShowAndHide(boolean showAndHide) {
        this.showAndHide = showAndHide;
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

    public boolean isShowAndHide() {
        return showAndHide;
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

    public boolean isPasswordVisible() {
        return !hidePassword;
    }
}