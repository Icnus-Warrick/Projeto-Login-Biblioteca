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
 * Componente de campo de texto personalizado com suporte a animações, rótulo flutuante e validação.
 *
 * <p>
 * Esta classe estende JTextField para fornecer um campo de texto com rótulo flutuante, feedback visual consistente e
 * sistema de validação integrado.</p>
 *
 * <p>
 * Para documentação completa, consulte:
 * <a href="docs/WTextField.md">Documentação Detalhada</a></p>
 *
 * @author Warrick
 * @version 3.2.2
 * @since 25/11/2025
 * @see JTextField
 */
public class WTextField extends JTextField {
    // ============================================ CONSTANTES DE CORES ============================================

    /**
     * Cor padrão da linha inferior quando o campo está em foco.
     * <p>
     * Valor padrão: Azul (#039bd8)
     * </p>
     * @see #setLineColor(Color)
     * @see #getLineColor()
     * @since 1.0.0
     */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /**
     * Cor de destaque quando o mouse está sobre o componente.
     * <p>
     * Valor padrão: Azul claro (#64b4dc)
     * </p>
     * @see #setHoverColor(Color)
     * @see #getHoverColor()
     * @since 1.0.0
     */
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);

    /**
     * Cor padrão para o texto digitado no campo.
     * <p>
     * Valor padrão: Cinza escuro (#323232)
     * </p>
     * @see #setForeground(Color)
     * @since 1.0.0
     */
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    /**
     * Cor do texto de dica (hint) quando o campo está vazio.
     * <p>
     * Valor padrão: Cinza médio (#969696)
     * </p>
     * @since 1.0.0
     */
    protected static final Color DEFAULT_HINT_COLOR = new Color(150, 150, 150);

    /**
     * Cor de fundo padrão do campo.
     * <p>
     * Valor padrão: Branco (#ffffff)
     * </p>
     * @see #setBackground(Color)
     * @since 1.0.0
     */
    protected static final Color DEFAULT_BG_COLOR = Color.WHITE;

    /**
     * Cor da linha inferior quando o campo não está em foco.
     * <p>
     * Valor padrão: Cinza claro (#c8c8c8)
     * </p>
     * @see #DEFAULT_LINE_COLOR
     * @since 1.0.0
     */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    /**
     * Cor usada para mensagens e indicadores de erro.
     * <p>
     * Valor padrão: Vermelho (#dc3545)
     * </p>
     * @see #mostrarErro(String)
     * @see #validarComMensagem(String)
     * @since 1.0.0
     */
    protected static final Color ERROR_COLOR = new Color(220, 53, 69);

    /**
     * Cor usada para mensagens e indicadores de sucesso.
     * <p>
     * Valor padrão: Verde (#28a745)
     * </p>
     * @see #mostrarSucesso(String)
     * @since 1.0.0
     */
    protected static final Color SUCCESS_COLOR = new Color(40, 167, 69);

    // ============================================ CONSTANTES DE LAYOUT ============================================
    /**
     * Altura em pixels da linha inferior do campo.
     *
     * @since 1.0.0
     */
    protected static final int LINE_HEIGHT = 1;

    /**
     * Deslocamento vertical em pixels da linha inferior em relação à base do componente. Controla a posição vertical da
     * linha de destaque quando o campo está em foco.
     *
     * @since 1.0.0
     */
    protected static final int LINE_Y_OFFSET = 14;

    /**
     * Espaçamento interno superior do campo em pixels. Controla a distância entre o topo do componente e o texto.
     *
     * @since 1.0.0
     */
    protected static final int PADDING_TOP = 20;

    /**
     * Espaçamento interno esquerdo do campo em pixels. Controla a distância entre a borda esquerda e o início do texto.
     *
     * @since 1.0.0
     */
    protected static final int PADDING_LEFT = 10;

    /**
     * Espaçamento interno inferior do campo em pixels. Controla a distância entre o texto e a linha inferior.
     *
     * @since 1.0.0
     */
    protected static final int PADDING_BOTTOM = 15;

    /**
     * Espaçamento interno direito do campo em pixels. Controla a distância entre o final do texto e a borda direita.
     *
     * @since 1.0.0
     */
    protected static final int PADDING_RIGHT = 10;

    /**
     * Posição vertical em pixels do rótulo quando na posição superior. Controla a altura do texto do rótulo quando o
     * campo está com foco ou preenchido.
     *
     * @since 1.0.0
     */
    protected static final int LABEL_TOP_POSITION = 13;

    /**
     * Duração em milissegundos das animações do componente. Inclui animações de foco, hover e validação.
     *
     * @since 1.0.0
     */
    protected static final int ANIMATION_DURATION = 300;

    /**
     * Deslocamento vertical em pixels para posicionamento da mensagem de erro. Controla a distância vertical entre a
     * linha do campo e a mensagem de erro.
     *
     * @since 1.0.0
     */
    protected static final int ERROR_MESSAGE_Y_OFFSET = -2;

    // ============================================ ATRIBUTOS ============================================
    /**
     * Progresso atual da animação da linha (0.0 a 1.0).
     *
     * @see #animateLine(boolean)
     * @since 1.0.0
     */
    protected float lineAnimationProgress = 0f;

    /**
     * Progresso atual da animação do rótulo (0.0 a 1.0). Controla a posição e opacidade do rótulo flutuante.
     *
     * @see #animateLabel(boolean)
     * @since 1.0.0
     */
    protected float animationLocation = 0f;

    /**
     * Progresso atual da animação da mensagem de erro (0.0 a 1.0). Controla a opacidade e deslocamento da mensagem de
     * erro.
     *
     * @see #animateError(boolean)
     * @since 1.0.0
     */
    protected float errorAnimationLocation = 0f;

    /**
     * Indica se o rótulo deve ser mostrado na posição superior.
     *
     * @since 1.0.0
     */
    protected boolean showLabel = false;

    /**
     * Indica se o cursor do mouse está sobre o componente.
     *
     * @see #addMouseListener(MouseListener)
     * @since 1.0.0
     */
    protected boolean mouseOver = false;

    /**
     * Indica se o campo é obrigatório. Quando verdadeiro, exibe um asterisco vermelho ao lado do rótulo.
     *
     * @see #setObrigatorio(boolean)
     * @since 1.0.0
     */
    protected boolean obrigatorio = false;

    /**
     * Indica se há um erro de validação ativo no campo.
     *
     * @see #setMensagem(String, boolean)
     * @see #limparMensagem()
     * @since 1.0.0
     */
    protected boolean hasError = false;

    /**
     * Indica se a mensagem atual é uma mensagem de sucesso.
     *
     * @see #setMensagem(String, boolean)
     * @see #mostrarSucesso(String)
     * @since 1.0.0
     */
    protected boolean isSuccessMessage = false;

    /**
     * Texto exibido como rótulo flutuante do campo.
     *
     * @see #setLabelText(String)
     * @see #getLabelText()
     * @since 1.0.0
     */
    protected String labelText = "Label";

    /**
     * Mensagem de erro ou sucesso atual do campo.
     *
     * @see #setMensagem(String, boolean)
     * @see #limparMensagem()
     * @since 1.0.0
     */
    protected String errorMessage = "";

    /**
     * Cor da linha inferior quando o campo está em foco.
     *
     * @see #setLineColor(Color)
     * @see #getLineColor()
     * @since 1.0.0
     */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /**
     * Cor de destaque quando o mouse está sobre o componente.
     *
     * @see #setHoverColor(Color)
     * @see #getHoverColor()
     * @since 1.0.0
     */
    protected Color hoverColor = DEFAULT_HOVER_COLOR;

    /**
     * Controlador de animação para transições suaves do rótulo e outros elementos. Utiliza a biblioteca Trident para
     * animações fluidas.
     *
     * @see org.pushingpixels.trident.Timeline
     * @since 1.0.0
     */
    protected Timeline timeline;

    /**
     * Controlador de animação para mensagens de erro e sucesso. Gerencia as transições de exibição e ocultação das
     * mensagens.
     *
     * @see org.pushingpixels.trident.Timeline
     * @see #animateError(boolean)
     * @since 1.0.0
     */
    protected Timeline errorTimeline;

    /**
     * Indica se a cor da linha foi definida manualmente (via IDE ou código).
     */
    private boolean lineColorSet = false;

    /**
     * Indica se a cor de hover foi definida manualmente (via IDE ou código).
     */
    private boolean hoverColorSet = false;

    /**
     * Indica se deve usar as cores do tema FlatLaf.
     * Se true, as cores do tema têm prioridade sobre cores definidas manualmente.
     * Se false, as cores definidas manualmente têm prioridade.
     */
    private boolean usarCoresTema = false;


    // ============================================ CONSTRUTORES ============================================
    /**
     * Cria um novo campo de texto com rótulo vazio.
     * <p>
     * Este construtor inicializa o campo com valores padrão e sem texto. O rótulo flutuante
     * será exibido apenas após chamar {@link #setLabelText(String)}.
     * </p>
     *
     * @see #WTextField(String)
     * @see #setLabelText(String)
     * @since 1.0.0
     */
    public WTextField() {
        this("");
    }

    /**
     * Cria um novo campo de texto com o rótulo especificado.
     * <p>
     * Inicializa o campo com o rótulo fornecido e configura os listeners necessários
     * para as animações e comportamentos padrão.
     * </p>
     *
     * @param labelText Texto do rótulo que será exibido como dica flutuante. 
     *        Se for {@code null}, será tratado como uma string vazia.
     * @see #setLabelText(String)
     * @see #setObrigatorio(boolean)
     * @see #setupField()
     * @since 1.0.0
     */
    public WTextField(String labelText) {
        super();
        this.labelText = labelText;
        setupField();
    }

    // ============================================ MÉTODOS PRIVADOS ============================================
    /**
     * Obtém uma cor do tema FlatLaf ou retorna a cor padrão fornecida.
     *
     * <p>
     * Este método é usado internamente para permitir a personalização das cores através do sistema de temas do FlatLaf,
     * mantendo valores padrão quando o tema não define uma cor específica.</p>
     *
     * @param key Chave da propriedade no tema (ex: "WTextField.lineColor")
     * @param defaultColor Cor padrão que será retornada se a propriedade não existir
     * @return Cor definida no tema ou a cor padrão fornecida
     *
     * @see UIManager#getColor(Object)
     * @since 1.0.0
     */
    protected Color getThemeColor(String key, Color defaultColor) {
        Color themeColor = UIManager.getColor(key);
        return themeColor != null ? themeColor : defaultColor;
    }

    /**
     * Inicializa os estados iniciais das animações do campo.
     * <p>
     * Este método define os valores iniciais para as propriedades de animação:
     * </p>
     * <ul>
     * <li>Se o campo estiver vazio, o rótulo fica na posição de dica (animationLocation = 0f)</li>
     * <li>Se o campo tiver texto, o rótulo é movido para a posição flutuante (animationLocation = 1f)</li>
     * <li>Mensagens de erro são inicialmente ocultas (errorAnimationLocation = 0f)</li>
     * <li>O estado do rótulo é definido com base no conteúdo do campo (showLabel)</li>
     * </ul>
     *
     * @see #animateLabel(boolean)
     * @see #animateError(boolean)
     * @see #animationLocation
     * @see #showLabel
     * @see #errorAnimationLocation
     * @since 1.0.0
     */
    private void initAnimation() {
        animationLocation = getText().isEmpty() ? 0f : 1f;
        showLabel = !getText().isEmpty();
        errorAnimationLocation = 0f;
    }

    /**
     * Configura as propriedades iniciais do campo de texto. Este método é chamado pelo construtor para configurar:
     * <ul>
     * <li>Bordas e margens</li>
     * <li>Cores padrão e do tema</li>
     * <li>Ouvintes de eventos (foco, mouse e documento)</li>
     * <li>Animações e estados iniciais</li>
     * <li>Configurações de aparência</li>
     * </ul>
     *
     * @see #initAnimation()
     * @see #getThemeColor(String, Color)
     * @since 1.0.0
     */
    private void setupField() {
        // Configuração de borda e cores (usando tema se disponível)
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setBackground(getThemeColor("WTextField.bgColor", DEFAULT_BG_COLOR));
        setForeground(getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR));
        setCaretColor(getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR));
        setOpaque(false);

        // Inicializa as cores customizáveis do tema
        lineColor = getThemeColor("WTextField.lineColor", DEFAULT_LINE_COLOR);
        hoverColor = getThemeColor("WTextField.hoverColor", DEFAULT_HOVER_COLOR);

        // Inicializa a animação
        initAnimation();
        
        // Garante que o estado inicial do rótulo esteja correto
        updateLabelState(isFocusOwner());

        // Listener de foco
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                updateLabelState(true);
                animateLine(true);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                updateLabelState(false);
                animateLine(false);
                repaint();
            }
        });

        // Listener de alteração de texto
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabelState(isFocusOwner());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabelState(isFocusOwner());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabelState(isFocusOwner());
            }
        });

        // Listener de mouse
        addMouseListener(new MouseAdapter() {
            /**
             * Chamado quando o mouse entra na área do componente. Atualiza o estado de hover e solicita repintura para
             * feedback visual.
             *
             * @param me Evento de mouse que contém informações sobre a ação
             * @see #mouseOver
             * @since 1.0.0
             */
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            /**
             * Chamado quando o mouse sai da área do componente. Atualiza o estado de hover e solicita repintura para
             * remover o feedback visual.
             *
             * @param me Evento de mouse que contém informações sobre a ação
             * @see #mouseOver
             * @see #repaint()
             * @since 1.0.0
             */
            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }

            /**
             * Chamado quando o campo perde o foco. Ajusta as animações de acordo com o estado do campo.
             *
             * @param fe Evento de foco que contém informações sobre a mudança
             * @since 1.0.0
             */
            public void focusLost(FocusEvent fe) {
                if (getText().isEmpty()) {
                    animateLabel(false);
                }
                animateLine(false);
                repaint();
            }
        });

        // Listener para limpar erros durante a digitação
        getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Chamado quando texto é inserido no campo. Limpa mensagens de erro ativas, se houver.
             *
             * @param e Evento de documento contendo informações sobre a alteração
             * @see #limparErroSeNecessario()
             * @since 1.0.0
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            /**
             * Chamado quando texto é removido do campo. Limpa mensagens de erro ativas, se houver.
             *
             * @param e Evento de documento contendo informações sobre a alteração
             * @see #limparErroSeNecessario()
             * @since 1.0.0
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            /**
             * Chamado quando atributos do texto são alterados. Limpa mensagens de erro ativas, se houver.
             *
             * @param e Evento de documento contendo informações sobre a alteração
             * @see #limparErroSeNecessario()
             * @since 1.0.0
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            /**
             * Limpa a mensagem de erro se o campo tiver texto e houver um erro ativo. Chamado automaticamente durante a
             * digitação para remover o estado de erro quando o usuário começa a corrigir o campo.
             *
             * @since 1.0.0
             */
            private void limparErroSeNecessario() {
                if (hasError && !getText().trim().isEmpty()) {
                    limparErro();
                }
            }
        });
    }

    /**
     * Método principal de renderização do componente.
     * <p>
     * Este método é responsável por orquestrar a renderização de todos os elementos visuais do componente,
     * incluindo:
     * </p>
     * <ol>
     * <li><b>Fundo</b>: Desenha o fundo do componente</li>
     * <li><b>Componentes</b>: Chama {@link #paintComponents(Graphics2D)} para desenhar os elementos internos</li>
     * <li><b>Rótulo flutuante</b>: Renderiza o rótulo usando {@link #paintLabel(Graphics2D)}</li>
     * <li><b>Linha inferior</b>: Desenha a linha de destaque com efeito de foco/hover</li>
     * <li><b>Mensagens</b>: Exibe mensagens de erro/sucesso via {@link #paintExternalMessage(Graphics2D)}</li>
     * </ol>
     * <p>
     * O método configura o contexto gráfico com anti-aliasing para melhor qualidade visual e garante
     * que os recursos sejam liberados corretamente após o uso.
     * </p>
     *
     * @param g O contexto gráfico no qual pintar
     * @see #paintLabel(Graphics2D)
     * @see #paintComponents(Graphics2D)
     * @see #paintExternalMessage(Graphics2D)
     * @see RenderingHints#KEY_ANTIALIASING
     * @since 1.0.0
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintComponents(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Sobrescreve o método paint para permitir desenho fora dos limites padrão do componente.
     *
     * <p>
     * Este método é responsável por:</p>
     * <ul>
     * <li>Chamar a implementação padrão de pintura</li>
     * <li>Desenhar mensagens de erro/sucesso que podem se estender além dos limites do campo</li>
     * <li>Garantir que o texto de validação seja visível mesmo que ultrapasse os limites do componente</li>
     * </ul>
     *
     * <p>
     * Nota: A mensagem de erro/sucesso é desenhada em uma posição calculada com base no tamanho do componente e no
     * tamanho da fonte.</p>
     *
     * @param g O contexto gráfico no qual pintar
     * @see #paintExternalMessage(Graphics2D)
     * @see #paintComponent(Graphics)
     * @since 1.0.0
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
     * Desenha a mensagem de erro ou sucesso abaixo do campo de texto.
     *
     * <p>
     * Este método é responsável por renderizar mensagens de feedback (erro ou sucesso)
     * abaixo do campo de texto, aplicando um efeito de animação de deslize para cima.
     * </p>
     * <p>
     * Características do desenho:
     * <ul>
     * <li>Cor baseada no tipo de mensagem (erro ou sucesso)</li>
     * <li>Fonte em itálico e tamanho reduzido (11px)</li>
     * <li>Posicionamento abaixo do campo com deslocamento vertical</li>
     * <li>Efeito de transparência baseado na animação</li>
     * </ul>
     * </p>
     * <p>
     * O método não faz nada se não houver mensagem definida.
     * </p>
     *
     * @param g2 O contexto gráfico 2D no qual desenhar a mensagem
     * @see #hasError
     * @see #isSuccessMessage
     * @see #errorMessage
     * @see #errorAnimationLocation
     * @see #paint(Graphics)
     * @since 1.0.0
     */
    private void paintExternalMessage(Graphics2D g2) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return;
        }

        // Obtém cor do tema ou usa padrão
        Color messageColor = isSuccessMessage
                ? getThemeColor("WTextField.successColor", SUCCESS_COLOR)
                : getThemeColor("WTextField.errorColor", ERROR_COLOR);

        // Aplica transparência baseada na animação
        int alpha = (int) (255 * errorAnimationLocation);
        g2.setColor(new Color(messageColor.getRed(), messageColor.getGreen(),
                messageColor.getBlue(), alpha));

        Font originalFont = g2.getFont();
        g2.setFont(originalFont.deriveFont(Font.PLAIN, 11f));

        int erroY = getHeight() + ERROR_MESSAGE_Y_OFFSET + (int) (3 * (1 - errorAnimationLocation));
        g2.drawString(errorMessage, PADDING_LEFT, erroY);

        g2.setFont(originalFont);
    }

    /**
     * Atualiza o estado do rótulo flutuante com base no foco e conteúdo do campo.
     * <p>
     * Este método determina se o rótulo deve ser mostrado na posição flutuante com base no estado atual do campo:
     * </p>
     * <ul>
     * <li>O rótulo é mostrado se o campo tem foco OU contém texto</li>
     * <li>O rótulo é ocultado se o campo não tem foco E está vazio</li>
     * </ul>
     * <p>
     * O método gerencia a animação do rótulo, garantindo transições suaves entre os estados.
     * Se uma animação estiver em andamento, ela será interrompida antes de iniciar uma nova.
     * </p>
     *
     * @param hasFocus Indica se o campo atualmente tem o foco
     * @see #animateLabel(boolean)
     * @see #showLabel
     * @see #timeline
     * @since 1.0.0
     */
    private void updateLabelState(boolean hasFocus) {
        boolean shouldShow = hasFocus || !getText().trim().isEmpty();

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
     * Anima a transição do rótulo entre os estados de repouso e flutuante.
     * <p>
     * Este método utiliza a biblioteca Trident para criar uma animação suave que move o rótulo
     * entre as posições de repouso (quando o campo está vazio) e flutuante (quando o campo
     * tem foco ou conteúdo).
     * </p>
     * <p>
     * Características da animação:
     * <ul>
     * <li>Duração: {@link #ANIMATION_DURATION} milissegundos</li>
     * <li>Curva de aceleração: Spline(0.5f) para movimento natural</li>
     * <li>Propriedade animada: {@code animationLocation} (0.0 a 1.0)</li>
     * </ul>
     * </p>
     * <p>
     * A animação é interrompida se já estiver em andamento quando este método for chamado.
     * </p>
     *
     * @param show Se {@code true}, anima para a posição flutuante (1.0);
     *             se {@code false}, anima para a posição de repouso (0.0)
     * @see #animationLocation
     * @see #showLabel
     * @see #timeline
     * @see #ANIMATION_DURATION
     * @see org.pushingpixels.trident.Timeline
     * @since 1.0.0
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
     * Controla a animação de exibição/ocultação da mensagem de erro ou sucesso.
     *
     * <p>
     * Este método gerencia a transição suave da mensagem de feedback (erro ou sucesso) usando um efeito de
     * fade-in/fade-out. A animação é baseada na propriedade {@code errorAnimationLocation} que varia entre 0.0
     * (totalmente transparente) e 1.0 (totalmente visível).</p>
     *
     * <p>
     * Características da animação:</p>
     * <ul>
     * <li>Duração controlada por {@link #ANIMATION_DURATION}</li>
     * <li>Usa interpolação Spline para movimento suave</li>
     * <li>Atualiza a interface automaticamente durante a animação</li>
     * </ul>
     *
     * @param show Se true, exibe a mensagem com animação; se false, esconde com animação
     * @see #errorAnimationLocation
     * @see #hasError
     * @see #ANIMATION_DURATION
     * @since 1.0.0
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
     * Anima a linha inferior do campo para indicar o estado de foco ou hover.
     *
     * <p>
     * Este método controla a animação da linha que fica na parte inferior do campo, que muda de cor e espessura para
     * fornecer feedback visual quando o campo está com foco ou quando o mouse está sobre ele.</p>
     *
     * <p>
     * Comportamento da animação:</p>
     * <ul>
     * <li>Quando {@code show} é true, a linha é animada para a cor de destaque ({@link #lineColor})</li>
     * <li>Quando {@code show} é false, a linha retorna à cor padrão</li>
     * <li>A transição é suave e dura {@link #ANIMATION_DURATION} milissegundos</li>
     * </ul>
     *
     * @param show Se true, ativa a animação para o estado de foco/hover; se false, desativa
     * @see #lineAnimationProgress
     * @see #lineColor
     * @see #DEFAULT_LINE_BG_COLOR
     * @see #ANIMATION_DURATION
     * @since 1.0.0
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
     * Desenha todos os componentes visuais personalizados do campo.
     *
     * <p>
     * Este método é responsável por renderizar os seguintes elementos na ordem:</p>
     * <ol>
     * <li>Fundo do campo (se opaco)</li>
     * <li>Texto do campo (herdado de JTextField)</li>
     * <li>Rótulo flutuante (quando aplicável)</li>
     * <li>Linha inferior com efeito de foco/hover</li>
     * </ol>
     *
     * <p>
     * O método é chamado automaticamente pelo {@link #paintComponent(Graphics)}.</p>
     *
     * @param g2 O contexto gráfico 2D no qual desenhar os componentes
     * @see #paintLabel(Graphics2D)
     * @see #paintExternalMessage(Graphics2D)
     * @since 1.0.0
     */
    protected void paintComponents(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int lineY = height - LINE_Y_OFFSET;

        // Obtém cor da linha de fundo do tema
        Color currentLineBgColor = getThemeColor("WTextField.lineBgColor", DEFAULT_LINE_BG_COLOR);

        // Desenha a linha de fundo
        g2.setColor(currentLineBgColor);
        g2.fillRect(2, lineY, width - 4, 1);

        // Linha de destaque (hover, foco ou erro)
        if (hasError) {
            Color errorLineColor = isSuccessMessage
                    ? getThemeColor("WTextField.successColor", SUCCESS_COLOR)
                    : getThemeColor("WTextField.errorColor", ERROR_COLOR);
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
     * Desenha o rótulo flutuante acima do campo de texto.
     *
     * <p>
     * Este método é responsável por renderizar o texto do rótulo na posição correta, aplicando efeitos de animação e
     * formatação visual. O rótulo pode ter os seguintes estados:</p>
     *
     * <ul>
     * <li><b>Flutuante</b>: Quando o campo tem foco ou conteúdo, o rótulo é mostrado em cima do campo com tamanho de
     * fonte reduzido.</li>
     * <p>
     * Comportamentos:
     * <ul>
     *   <li>Se não houver texto de rótulo definido, o método retorna imediatamente</li>
     *   <li>A cor do rótulo varia de acordo com o estado:
     *     <ul>
     *       <li>Erro: Vermelho (ou cor de erro definida)</li>
     *       <li>Sucesso: Verde (ou cor de sucesso definida)</li>
     *       <li>Focado: Cor da linha de foco</li>
     *       <li>Preenchido: Cor do texto padrão</li>
     *       <li>Em repouso: Cor de dica</li>
     *     </ul>
     *   </li>
     *   <li>Aplica animação de posição e escala baseada em {@code animationLocation}</li>
     * </ul>
     * </p>
     *
     * @param g2 O contexto gráfico 2D no qual desenhar o rótulo
     *
     * @see #labelText
     * @see #animationLocation
     * @see #hasError
     * @see #isSuccessMessage
     * @see #lineColor
     * @see #getThemeColor(String, Color)
     * @since 1.0.0
     */
    private void paintLabel(Graphics2D g2) {
        if (labelText == null || labelText.isEmpty()) {
            return;
        }

        Insets in = getInsets();

        // Define cor do rótulo baseada no estado
        Color userTextColor = getForeground();
        Color baseTextColor = userTextColor != null
                ? userTextColor
                : getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR);
        Color baseHintColor = getThemeColor("WTextField.hintColor", DEFAULT_HINT_COLOR);

        Color labelColor;
        if (hasError) {
            labelColor = isSuccessMessage
                    ? getThemeColor("WTextField.successColor", SUCCESS_COLOR)
                    : getThemeColor("WTextField.errorColor", ERROR_COLOR);
        } else if (isFocusOwner()) {
            labelColor = lineColor;
        } else if (!getText().isEmpty()) {
            // Campo preenchido sem foco: usa a cor de texto configurada (por exemplo, branco definido no GUI)
            labelColor = baseTextColor;
        } else {
            // Campo vazio: usa cor de dica
            labelColor = baseHintColor;
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
    // ========================================= MÉTODOS PRIVADOS DE VALIDAÇÃO =========================================

    /**
     * Valida se um campo obrigatório foi preenchido.
     * <p>
     * Se o campo for obrigatório e estiver vazio:
     * <ul>
     * <li>Usa o rótulo do campo na mensagem de erro, se disponível</li>
     * <li>Remove dois pontos finais e capitaliza a primeira letra do rótulo</li>
     * <li>Usa mensagem genérica se não houver rótulo definido</li>
     * </ul>
     * </p>
     *
     * @return true se o campo não for obrigatório ou estiver preenchido,
     *         false se for obrigatório e estiver vazio
     * @see #obrigatorio
     * @see #setMensagem(String, boolean)
     * @since 1.0.0
     */
    private boolean validarObrigatorio() {
        if (labelText != null && !labelText.trim().isEmpty()) {
            // Remove os dois pontos do final, se existirem
            String rotulo = labelText.trim();
            if (rotulo.endsWith(":") || rotulo.endsWith(": ")) {
                rotulo = rotulo.substring(0, rotulo.length() - 1).trim();
                // Capitaliza a primeira letra do rótulo
                rotulo = rotulo.substring(0, 1).toUpperCase() + rotulo.substring(1).toLowerCase();
                setMensagem(rotulo + " é obrigatório", false);
            } else {
                // Se não tiver rótulo definido, usa a mensagem genérica
                setMensagem("Campo obrigatório", false);
            }
            return false;
        }
        // Não limpa a mensagem aqui para não apagar mensagens de erro personalizadas
        return true;
    }

    // ========================================= MÉTODOS PÚBLICOS DE VALIDAÇÃO =========================================
    /**
     * Define uma mensagem de feedback (erro ou sucesso) para ser exibida abaixo do campo.
     *
     * <p>
     * Este método configura uma mensagem de feedback que será exibida abaixo do campo de texto. A mensagem pode ser
     * estilizada como erro (vermelho) ou sucesso (verde) dependendo do parâmetro.</p>
     *
     * <p>
     * Comportamento:</p>
     * <ul>
     * <li>Se {@code mensagem} for nula ou vazia, a mensagem atual é limpa</li>
     * <li>Se {@code sucesso} for true, a mensagem é exibida em verde (sucesso)</li>
     * <li>Se {@code sucesso} for false, a mensagem é exibida em vermelho (erro)</li>
     * </ul>
     *
     * <p>
     * O método também atualiza o estado interno do componente para refletir se há uma mensagem de erro ativa, o que
     * pode afetar a validação e a aparência do campo.</p>
     *
     * @param mensagem A mensagem a ser exibida. Pode ser nula ou vazia para limpar a mensagem atual.
     * @param sucesso Se true, exibe como mensagem de sucesso (verde); se false, exibe como mensagem de erro (vermelho)
     *
     * @see #hasError
     * @see #isSuccessMessage
     * @see #errorMessage
     * @see #animateError(boolean)
     * @see #limparMensagem()
     * @since 1.0.0
     */
    public void setMensagem(String mensagem, boolean sucesso) {
        this.errorMessage = mensagem;
        this.isSuccessMessage = sucesso;
        this.hasError = !sucesso; // Define hasError como true para erros, false para sucesso

        updateLabelState(isFocusOwner());
        animateError(true);
        repaint();
    }

    /**
     * Exibe uma mensagem de sucesso estilizada abaixo do campo.
     *
     * <p>
     * Este é um método de conveniência que chama {@link #setMensagem(String, boolean)} com o parâmetro {@code sucesso}
     * definido como {@code true}, resultando em uma mensagem estilizada em verde.</p>
     *
     * <p>
     * Use este método para fornecer feedback positivo ao usuário, como confirmação de que os dados foram salvos com
     * sucesso ou que uma operação foi concluída.</p>
     *
     * @param mensagem A mensagem de sucesso a ser exibida. Se nula ou vazia, limpa a mensagem atual.
     *
     * @see #setMensagem(String, boolean)
     * @see #mostrarErro(String)
     * @see #limparMensagem()
     * @since 1.0.0
     */
    public void mostrarSucesso(String mensagem) {
        setMensagem(mensagem, true);
    }

    /**
     * Valida o campo de acordo com as regras configuradas.
     *
     * <p>
     * Este método executa todas as validações configuradas no campo, incluindo:
     * <ul>
     * <li>Verifica se o campo é obrigatório (quando {@code obrigatorio} é {@code true})</li>
     * <li>Verifica se o campo está vazio (após remoção de espaços em branco)</li>
     *
     * @return {@code true} se o campo for válido, {@code false} caso contrário
     * @see #validarObrigatorio()
     * @since 1.0.0
     */
    public boolean validar() {
        return validarObrigatorio();
    }

    /**
     * Valida o campo e exibe uma mensagem de erro personalizada se a validação falhar.
     *
     * <p>
     * Este método é semelhante a {@link #validar()}, mas permite especificar uma mensagem de erro personalizada que
     * será exibida caso a validação falhe.</p>
     *
     * <p>
     * O método sempre retorna {@code false} quando chamado, indicando que a validação falhou. Isso permite o uso em
     * construções como:</p>
     *
     * <pre>
     * if (campoVazio) {
     *     return textField.validarComMensagem("Por favor, preencha este campo");
     * }
     * </pre>
     *
     * @param mensagem A mensagem de erro a ser exibida se a validação falhar
     * @return Sempre retorna {@code false} para permitir encadeamento em validações
     *
     * @see #validar()
     * @see #mostrarErro(String)
     * @since 1.0.0
     */
    public boolean validarComMensagem(String mensagem) {
        mostrarErro(mensagem);
        return false;
    }

    /**
     * Exibe uma mensagem de erro estilizada abaixo do campo.
     *
     * <p>
     * Este é um método de conveniência que chama {@link #setMensagem(String, boolean)} com o parâmetro {@code sucesso}
     * definido como {@code false}, resultando em uma mensagem estilizada em vermelho.</p>
     *
     * <p>
     * Use este método para fornecer feedback de erro ao usuário, como quando uma validação de formulário falha ou
     * ocorre um problema ao processar os dados.</p>
     *
     * @param mensagem A mensagem de erro a ser exibida. Se nula ou vazia, limpa a mensagem atual.
     *
     * @see #setMensagem(String, boolean)
     * @see #mostrarSucesso(String)
     * @see #limparMensagem()
     * @since 1.0.0
     */
    public void mostrarErro(String mensagem) {
        setMensagem(mensagem, false);
    }

    /**
     * Remove qualquer mensagem de erro ou sucesso atualmente exibida no campo.
     *
     * <p>
     * Este método limpa a mensagem de feedback (seja de erro ou sucesso) que está sendo exibida abaixo do campo,
     * restaurando o estado normal do componente.</p>
     *
     * <p>
     * Após chamar este método:</p>
     * <ul>
     * <li>A mensagem é removida da interface</li>
     * <li>O estado de erro é desativado</li>
     * <li>O estado de mensagem de sucesso é desativado</li>
     * </ul>
     *
     * @see #setMensagem(String, boolean)
     * @see #mostrarSucesso(String)
     * @see #mostrarErro(String)
     * @since 1.0.0
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
     * Método obsoleto para limpar mensagens de erro.
     *
     * @deprecated A partir da versão 1.0.0, use {@link #limparMensagem()} em vez deste método. O método
     * {@code limparMensagem()} é mais genérico e lida com ambos os tipos de mensagens (erro e sucesso).
     *
     * @see #limparMensagem()
     */
    @Deprecated
    public void limparErro() {
        limparMensagem();
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================
    /**
     * Define o texto do campo e atualiza o estado do rótulo flutuante.
     * <p>
     * Este método estende o comportamento de {@link JTextField#setText(String)} para incluir:
     * </p>
     * <ul>
     *   <li>Atualização do estado do rótulo flutuante através de {@link #updateLabelState(boolean)}</li>
     *   <li>Limpeza automática de erros de validação quando um texto não vazio é definido</li>
     * </ul>
     * <p>
     * Se o campo tiver um erro de validação ativo e um texto não vazio for definido,
     * o erro será automaticamente limpo.
     * </p>
     *
     * @param text O texto a ser definido no campo. Se for {@code null}, será tratado como string vazia.
     * 
     * @see #updateLabelState(boolean)
     * @see #limparMensagem()
     * @see javax.swing.JTextField#setText(String)
     * @since 1.0.0
     */
    @Override
    public void setText(String text) {
        super.setText(text);
        updateLabelState(isFocusOwner());
        if (hasError && text != null && !text.trim().isEmpty()) {
            limparErro();
        }
    }

    /**
     * Define o texto do rótulo flutuante do campo.
     *
     * <p>Este método atualiza o texto exibido como rótulo flutuante acima do campo
     * quando o mesmo está vazio ou quando o campo tem foco.</p>
     *
     * @param labelText O texto a ser exibido como rótulo flutuante
     * @see #getLabelText()
     * @see #labelText
     * @since 1.0.0
     */
    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    /**
     * Define a cor da linha inferior do campo.
     *
     * <p>Esta cor é usada para desenhar a linha inferior do campo e também
     * como cor de destaque quando o campo está focado.</p>
     *
     * @param lineColor A cor da linha inferior
     * @see #getLineColor()
     * @see #lineColor
     * @since 1.0.0
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    /**
     * Define a cor de destaque quando o mouse está sobre o campo.
     *
     * <p>Esta cor é usada para realçar visualmente o campo quando o mouse
     * está sobre ele, melhorando a experiência do usuário.</p>
     *
     * @param hoverColor A cor de destaque no hover
     * @see #getHoverColor()
     * @see #hoverColor
     * @since 1.0.0
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }

    /**
     * Define se o campo é obrigatório para preenchimento.
     *
     * <p>Quando definido como {@code true}, o campo exibirá um asterisco (*) vermelho
     * ao lado do rótulo, indicando que é de preenchimento obrigatório.</p>
     *
     * @param obrigatorio {@code true} se o campo for obrigatório, {@code false} caso contrário
     * @see #isObrigatorio()
     * @see #obrigatorio
     * @since 1.0.0
     */
    public void setObrigatorio(boolean obrigatorio) {
        boolean oldValue = this.obrigatorio;
        this.obrigatorio = obrigatorio;
        firePropertyChange("obrigatorio", oldValue, obrigatorio);
    }

    /**
     * Define a posição atual da animação do rótulo.
     *
     * <p>Este método é usado internamente pelo sistema de animação para controlar
     * a posição do rótulo durante as transições. Valores variam de 0.0 (repouso)
     * a 1.0 (flutuante).</p>
     *
     * @param animationLocation A posição da animação (0.0 a 1.0)
     * @see #getAnimationLocation()
     * @see #animationLocation
     * @since 1.0.0
     */
    public void setAnimationLocation(float animationLocation) {
        this.animationLocation = animationLocation;
        repaint();
    }

    /**
     * Define a posição atual da animação da mensagem de erro/sucesso.
     *
     * <p>Este método é usado internamente pelo sistema de animação para controlar
     * a opacidade da mensagem de erro/sucesso durante as transições.
     * Valores variam de 0.0 (invisível) a 1.0 (totalmente visível).</p>
     *
     * @param errorAnimationLocation A posição da animação (0.0 a 1.0)
     * @see #getErrorAnimationLocation()
     * @see #errorAnimationLocation
     * @since 1.0.0
     */
    public void setErrorAnimationLocation(float errorAnimationLocation) {
        this.errorAnimationLocation = errorAnimationLocation;
        repaint();
    }

    /**
     * Define o progresso da animação da linha inferior.
     *
     * <p>Este método é usado internamente pelo sistema de animação para controlar
     * o efeito de preenchimento da linha inferior quando o campo está focado.
     * Valores variam de 0.0 (não preenchido) a 1.0 (totalmente preenchido).</p>
     *
     * @param lineAnimationProgress O progresso da animação (0.0 a 1.0)
     * @see #getLineAnimationProgress()
     * @see #lineAnimationProgress
     * @since 1.0.0
     */
    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================
    /**
     * Retorna o texto atual do rótulo flutuante do campo.
     *
     * @return O texto do rótulo flutuante, ou {@code null} se nenhum estiver definido
     * @see #setLabelText(String)
     * @see #labelText
     * @since 1.0.0
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * Retorna a cor atual da linha inferior do campo.
     *
     * @return A cor da linha inferior
     * @see #setLineColor(Color)
     * @see #lineColor
     * @since 1.0.0
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Retorna a cor de destaque usada quando o mouse está sobre o campo.
     *
     * @return A cor de destaque no hover
     * @see #setHoverColor(Color)
     * @see #hoverColor
     * @since 1.0.0
     */
    public Color getHoverColor() {
        return hoverColor;
    }

    /**
     * Retorna a posição atual da animação do rótulo.
     *
     * @return Um valor entre 0.0 (repouso) e 1.0 (flutuante)
     * @see #setAnimationLocation(float)
     * @see #animationLocation
     * @since 1.0.0
     */
    public float getAnimationLocation() {
        return animationLocation;
    }

    /**
     * Retorna a posição atual da animação da mensagem de erro/sucesso.
     *
     * @return Um valor entre 0.0 (invisível) e 1.0 (totalmente visível)
     * @see #setErrorAnimationLocation(float)
     * @see #errorAnimationLocation
     * @since 1.0.0
     */
    public float getErrorAnimationLocation() {
        return errorAnimationLocation;
    }

    /**
     * Retorna o progresso atual da animação da linha inferior.
     *
     * @return Um valor entre 0.0 (não preenchido) e 1.0 (totalmente preenchido)
     * @see #setLineAnimationProgress(float)
     * @see #lineAnimationProgress
     * @since 1.0.0
     */
    public float getLineAnimationProgress() {
        return lineAnimationProgress;
    }

    /**
     * Verifica se o campo é obrigatório para preenchimento.
     *
     * @return {@code true} se o campo for obrigatório, {@code false} caso contrário
     * @see #setObrigatorio(boolean)
     * @see #obrigatorio
     * @since 1.0.0
     */
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    /**
     * Verifica se o campo atualmente possui uma mensagem de erro ativa.
     *
     * <p>Este método retorna {@code true} se o campo tiver uma mensagem de erro
     * sendo exibida atualmente, ou {@code false} caso contrário.</p>
     *
     * @return {@code true} se houver uma mensagem de erro ativa, {@code false} caso contrário
     * @see #hasError
     * @see #getErrorMessage()
     * @see #mostrarErro(String)
     * @see #limparErro()
     * @since 1.0.0
     */
    public boolean hasError() {
        return hasError;
    }

    /**
     * Retorna a mensagem de erro/sucesso atualmente exibida no campo.
     *
     * <p>Este método retorna a mensagem de erro ou sucesso que está sendo exibida
     * abaixo do campo. Se não houver mensagem sendo exibida, retorna {@code null}.</p>
     *
     * @return A mensagem de erro/sucesso atualmente exibida, ou {@code null} se não houver mensagem
     * @see #errorMessage
     * @see #hasError()
     * @see #isSuccessMessage()
     * @see #mostrarErro(String)
     * @see #mostrarSucesso(String)
     * @since 1.0.0
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
