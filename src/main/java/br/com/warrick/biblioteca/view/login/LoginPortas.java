package br.com.warrick.biblioteca.view.login;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Painel de animações de abertura de portas COM EFEITO DE ENTRADA (ZOOM)
 * Versão TELA CHEIA - Zoom nas imagens com movimento realista das portas
 */
public class LoginPortas extends javax.swing.JPanel {

    private LoginApp parentApp;
    private Runnable callbackTermino;

    /* ============================================== BUFFER DE IMAGEM ============================================== */
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;

    /* ======================================== IMAGENS DAS PORTAS E BATENTES ======================================= */
    private Image imagemBatente;
    private Image imagemPortaE;
    private Image imagemPortaD;
    private Image imagemParede;

    /* ======================================== CONSTANTES DE POSICIONAMENTO ======================================== */
    // Dimensões originais das imagens
    private static final int LARGURA_BATENTE_ORIGINAL = 570;
    private static final int ALTURA_BATENTE_ORIGINAL = 881;
    private static final int ALTURA_PORTA_ORIGINAL = 708;

    // Posições relativas das portas em relação ao batente
    private static final int OFFSET_PORTA_E_FECHADA = 134;
    private static final int LARGURA_PORTA_E_FECHADA = 156;
    private static final int OFFSET_PORTA_E_ABERTA = 21;
    private static final int LARGURA_PORTA_E_ABERTA = 110;

    private static final int OFFSET_PORTA_D_FECHADA = 286;
    private static final int LARGURA_PORTA_D_FECHADA = 156;
    private static final int OFFSET_PORTA_D_ABERTA = 440;
    private static final int LARGURA_PORTA_D_ABERTA = 100;

    private static final int OFFSET_PORTAS_Y = 90;

    /* ========================================== CONFIGURAÇÕES DE ANIMAÇÃO ========================================= */
    private static final int VELOCIDADE_ANIMACAO = 3;
    private static final int DELAY_ANIMACAO = 16; // ~60 FPS
    private static final int DURACAO_ANIMACAO_FRAMES = 60; // Duração em frames para sincronizar

    /* ========================================== CONFIGURAÇÕES DE ZOOM/ENTRADA ===================================== */
    private static final double ESCALA_INICIAL = 1.0;
    private double escalaFinal;
    private static final double VELOCIDADE_ZOOM = 0.020;
    private double inicioFade;

    /* ============================================ VARIÁVEIS DE ESTADO ============================================= */
    // Posições e larguras atuais das portas (animadas)
    private int offsetAtualPortaE;
    private int larguraAtualPortaE;
    private int offsetAtualPortaD;
    private int larguraAtualPortaD;

    private double escalaAtual = ESCALA_INICIAL;
    private Timer timerAnimacao;
    private boolean animacaoEmAndamento;
    private int frameAtual = 0; // Contador de frames para sincronização

    private enum TipoAnimacao {
        ABRINDO,
        FECHANDO,
        ENTRANDO,
        PARADA
    }

    private TipoAnimacao tipoAnimacaoAtual = TipoAnimacao.PARADA;

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public void setParentApp(LoginApp parentApp) {
        this.parentApp = parentApp;
        calcularEscalaMaxima();
    }

    public LoginPortas() {
        try {
            initComponents();
            calcularEscalaMaxima();
            carregarImagens();
            inicializarTimer();

            // Inicializar offsets e larguras (portas fechadas)
            offsetAtualPortaE = OFFSET_PORTA_E_FECHADA;
            larguraAtualPortaE = LARGURA_PORTA_E_FECHADA;
            offsetAtualPortaD = OFFSET_PORTA_D_FECHADA;
            larguraAtualPortaD = LARGURA_PORTA_D_FECHADA;
            escalaAtual = ESCALA_INICIAL;

            // Garantir que o painel ocupe toda a tela
            setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            setOpaque(false); // Painel transparente para mostrar a parede de fundo
            setDoubleBuffered(true);

            // Remover o layout absoluto para controle manual
            setLayout(null);

            // IMPORTANTE: Esconder a label para que não apareça na frente
            if (lblParede != null) {
                lblParede.setVisible(false);
            }

            repaint();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar LoginPortas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public LoginPortas(LoginApp parent) {
        this();
        this.parentApp = parent;
        calcularEscalaMaxima();
    }



    /**
     * Calcula a escala máxima baseada no tamanho da tela
     */
    private void calcularEscalaMaxima() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int larguraTela = screenSize.width;
            int alturaTela = screenSize.height;

            System.out.println("Resolução da tela: " + larguraTela + "x" + alturaTela);

            double escalaLargura = (double) larguraTela / LARGURA_BATENTE_ORIGINAL;
            double escalaAltura = (double) alturaTela / ALTURA_BATENTE_ORIGINAL;

            // Usar a maior escala e adicionar 30% para garantir cobertura total
            escalaFinal = Math.max(escalaLargura, escalaAltura) * 1.3;

            // Limitar entre 3.0 e 8.0 para segurança
            escalaFinal = Math.max(3.0, Math.min(escalaFinal, 8.0));

            // Iniciar fade quando estiver 65% do caminho
            inicioFade = ESCALA_INICIAL + (escalaFinal - ESCALA_INICIAL) * 0.65;

            System.out.println("Escala calculada: " + String.format("%.2f", escalaFinal) + "x");
            System.out.println("Fade iniciará em: " + String.format("%.2f", inicioFade) + "x");

        } catch (Exception e) {
            System.err.println("Erro ao calcular escala: " + e.getMessage());
            escalaFinal = 4.5;
            inicioFade = 3.0;
        }
    }

    /* ====================================== INICIALIZAÇÃO DO BUFFER DE IMAGEM ===================================== */
    private void inicializarBuffer() {
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
            bufferGraphics = null;
        }
        if (buffer != null) {
            buffer.flush();
            buffer = null;
        }

        int largura = Math.max(1, getWidth());
        int altura = Math.max(1, getHeight());

        try {
            buffer = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
            bufferGraphics = buffer.createGraphics();

            bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

            System.out.println("Buffer inicializado: " + largura + "x" + altura);
        } catch (Exception e) {
            System.err.println("Erro ao inicializar buffer: " + e.getMessage());
            buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            bufferGraphics = buffer.createGraphics();
        }
    }

    /* =========================================== CARREGAMENTO DAS IMAGENS ========================================== */
    private void carregarImagens() {
        System.out.println("Carregando imagens...");

        try {
            java.net.URL batenteURL = getClass().getResource("/br/com/warrick/biblioteca/Imagem/Batente.png");
            if (batenteURL != null) {
                imagemBatente = new ImageIcon(batenteURL).getImage();
                System.out.println("✓ Batente carregado");
            } else {
                System.err.println("✗ Batente.png não encontrado");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar Batente: " + e.getMessage());
        }

        try {
            java.net.URL portaEURL = getClass().getResource("/br/com/warrick/biblioteca/Imagem/PortaE.png");
            if (portaEURL != null) {
                imagemPortaE = new ImageIcon(portaEURL).getImage();
                System.out.println("✓ PortaE carregada");
            } else {
                System.err.println("✗ PortaE.png não encontrada");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar PortaE: " + e.getMessage());
        }

        try {
            java.net.URL portaDURL = getClass().getResource("/br/com/warrick/biblioteca/Imagem/PortaD.png");
            if (portaDURL != null) {
                imagemPortaD = new ImageIcon(portaDURL).getImage();
                System.out.println("✓ PortaD carregada");
            } else {
                System.err.println("✗ PortaD.png não encontrada");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar PortaD: " + e.getMessage());
        }

        try {
            java.net.URL paredeURL = getClass().getResource("/br/com/warrick/biblioteca/Imagem/Parede1.png");
            if (paredeURL != null) {
                imagemParede = new ImageIcon(paredeURL).getImage();
                System.out.println("✓ Parede carregada");
            } else {
                System.err.println("✗ Parede1.png não encontrada");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar Parede: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Não desenhar fundo preto - a parede já é o fundo

        if (buffer == null || bufferGraphics == null
                || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            inicializarBuffer();
        }

        if (imagemBatente == null || imagemPortaE == null || imagemPortaD == null) {
            carregarImagens();
            if (imagemBatente != null && imagemPortaE != null && imagemPortaD != null) {
                repaint();
            }
            return;
        }

        try {
            bufferGraphics.setComposite(AlphaComposite.Clear);
            bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
            bufferGraphics.setComposite(AlphaComposite.SrcOver);

            desenharNoBuffer();

            if (buffer != null) {
                Graphics2D g2dFinal = (Graphics2D) g;
                g2dFinal.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2dFinal.drawImage(buffer, 0, 0, null);
            }
        } catch (Exception e) {
            System.err.println("Erro ao desenhar: " + e.getMessage());
        }
    }

    /* =================================== DESENHA TODOS OS COMPONENTES NO BUFFER =================================== */
    private void desenharNoBuffer() {
        try {
            // Limpar buffer (transparente, não preto - a parede é o fundo)
            bufferGraphics.setComposite(AlphaComposite.Clear);
            bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
            bufferGraphics.setComposite(AlphaComposite.SrcOver);

            // Calcular centro da tela
            int centroTelaX = getWidth() / 2;
            int centroTelaY = getHeight() / 2;

            // Calcular posição do batente centralizado
            int batenteX = centroTelaX - (LARGURA_BATENTE_ORIGINAL / 2);
            int batenteY = centroTelaY - (ALTURA_BATENTE_ORIGINAL / 2);

            Graphics2D g2d = (Graphics2D) bufferGraphics.create();

            // APLICAR ZOOM A PARTIR DO CENTRO DA TELA
            if (escalaAtual != 1.0) {
                g2d.translate(centroTelaX, centroTelaY);
                g2d.scale(escalaAtual, escalaAtual);
                g2d.translate(-centroTelaX, -centroTelaY);
            }

            // 1. DESENHAR PAREDE DE FUNDO CENTRALIZADA (PRIMEIRO - MAIS AO FUNDO)
            if (imagemParede != null) {
                int larguraParede = imagemParede.getWidth(this);
                int alturaParede = imagemParede.getHeight(this);

                int paredeX = centroTelaX - (larguraParede / 2);
                int paredeY = centroTelaY - (alturaParede / 2);

                g2d.drawImage(imagemParede, paredeX, paredeY, larguraParede, alturaParede, this);
            }

            // Calcular posições absolutas das portas baseadas no batente
            int portaEX = batenteX + offsetAtualPortaE;
            int portaEY = batenteY + OFFSET_PORTAS_Y;

            int portaDX = batenteX + offsetAtualPortaD;
            int portaDY = batenteY + OFFSET_PORTAS_Y;

            // 2. DESENHAR PORTA ESQUERDA (SEGUNDA CAMADA)
            if (imagemPortaE != null) {
                g2d.drawImage(imagemPortaE,
                        portaEX,
                        portaEY,
                        larguraAtualPortaE,
                        ALTURA_PORTA_ORIGINAL,
                        this);
            }

            // 3. DESENHAR PORTA DIREITA (TERCEIRA CAMADA)
            if (imagemPortaD != null) {
                g2d.drawImage(imagemPortaD,
                        portaDX,
                        portaDY,
                        larguraAtualPortaD,
                        ALTURA_PORTA_ORIGINAL,
                        this);
            }

            // 4. DESENHAR BATENTE CENTRALIZADO (ÚLTIMA CAMADA - NA FRENTE)
            if (imagemBatente != null) {
                g2d.drawImage(imagemBatente,
                        batenteX,
                        batenteY,
                        LARGURA_BATENTE_ORIGINAL,
                        ALTURA_BATENTE_ORIGINAL,
                        this);
            }

            g2d.dispose();

            // Aplicar clareamento progressivo durante o zoom
            if (tipoAnimacaoAtual == TipoAnimacao.ENTRANDO && escalaAtual > inicioFade) {
                float progresso = (float) ((escalaAtual - inicioFade) / (escalaFinal - inicioFade));
                progresso = Math.max(0f, Math.min(1f, progresso));

                // Curva suave para o clareamento (ease-in)
                float alphaSuave = progresso * progresso;

                bufferGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaSuave));
                bufferGraphics.setColor(Color.WHITE);
                bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
                bufferGraphics.setComposite(AlphaComposite.SrcOver);
            }

        } catch (Exception e) {
            System.err.println("Erro ao desenhar no buffer: " + e.getMessage());
        }
    }

    /* ===================================== INICIALIZAÇÃO DO TIMER DE ANIMAÇÃO ===================================== */
    private void inicializarTimer() {
        if (timerAnimacao != null) {
            timerAnimacao.stop();
            timerAnimacao = null;
        }

        timerAnimacao = new Timer(DELAY_ANIMACAO, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!isVisible() || !isDisplayable()) {
                        stopTimer();
                        return;
                    }
                    animar();
                } catch (Exception ex) {
                    System.err.println("Erro durante a animação: " + ex.getMessage());
                    stopTimer();
                }
            }
        });

        timerAnimacao.setCoalesce(true);
    }

    private void stopTimer() {
        if (timerAnimacao != null) {
            timerAnimacao.stop();
        }
    }

    /* ============================================= LÓGICA DA ANIMAÇÃO ============================================= */
    private void animar() {
        try {
            if (!isVisible() || !isDisplayable()) {
                stopTimer();
                return;
            }

            boolean animacaoConcluida = false;

            if (tipoAnimacaoAtual == TipoAnimacao.ABRINDO) {
                frameAtual++;
                float progresso = Math.min(1.0f, (float) frameAtual / DURACAO_ANIMACAO_FRAMES);

                // Calcular posições e larguras usando interpolação linear sincronizada
                // Porta Esquerda
                int deltaOffsetE = OFFSET_PORTA_E_ABERTA - OFFSET_PORTA_E_FECHADA;
                offsetAtualPortaE = OFFSET_PORTA_E_FECHADA + (int)(deltaOffsetE * progresso);

                int deltaLarguraE = LARGURA_PORTA_E_ABERTA - LARGURA_PORTA_E_FECHADA;
                larguraAtualPortaE = LARGURA_PORTA_E_FECHADA + (int)(deltaLarguraE * progresso);

                // Porta Direita
                int deltaOffsetD = OFFSET_PORTA_D_ABERTA - OFFSET_PORTA_D_FECHADA;
                offsetAtualPortaD = OFFSET_PORTA_D_FECHADA + (int)(deltaOffsetD * progresso);

                int deltaLarguraD = LARGURA_PORTA_D_ABERTA - LARGURA_PORTA_D_FECHADA;
                larguraAtualPortaD = LARGURA_PORTA_D_FECHADA + (int)(deltaLarguraD * progresso);

                animacaoConcluida = (progresso >= 1.0f);

            } else if (tipoAnimacaoAtual == TipoAnimacao.FECHANDO) {
                frameAtual++;
                float progresso = Math.min(1.0f, (float) frameAtual / DURACAO_ANIMACAO_FRAMES);

                // Calcular posições e larguras usando interpolação linear sincronizada
                // Porta Esquerda
                int deltaOffsetE = OFFSET_PORTA_E_FECHADA - OFFSET_PORTA_E_ABERTA;
                offsetAtualPortaE = OFFSET_PORTA_E_ABERTA + (int)(deltaOffsetE * progresso);

                int deltaLarguraE = LARGURA_PORTA_E_FECHADA - LARGURA_PORTA_E_ABERTA;
                larguraAtualPortaE = LARGURA_PORTA_E_ABERTA + (int)(deltaLarguraE * progresso);

                // Porta Direita
                int deltaOffsetD = OFFSET_PORTA_D_FECHADA - OFFSET_PORTA_D_ABERTA;
                offsetAtualPortaD = OFFSET_PORTA_D_ABERTA + (int)(deltaOffsetD * progresso);

                int deltaLarguraD = LARGURA_PORTA_D_FECHADA - LARGURA_PORTA_D_ABERTA;
                larguraAtualPortaD = LARGURA_PORTA_D_ABERTA + (int)(deltaLarguraD * progresso);

                animacaoConcluida = (progresso >= 1.0f);

            } else if (tipoAnimacaoAtual == TipoAnimacao.ENTRANDO) {
                System.out.println("DEBUG ZOOM - Escala atual: " + String.format("%.3f", escalaAtual) + " / Escala final: " + String.format("%.3f", escalaFinal));

                if (escalaAtual < escalaFinal) {
                    double progresso = (escalaAtual - ESCALA_INICIAL) / (escalaFinal - ESCALA_INICIAL);
                    double curva = 1.0 - Math.pow(1.0 - progresso, 2);
                    double aceleracao = 1.0 + curva * 1.5;
                    double incremento = VELOCIDADE_ZOOM * aceleracao;

                    escalaAtual = Math.min(escalaAtual + incremento, escalaFinal);

                    System.out.println("DEBUG ZOOM - Progresso: " + String.format("%.1f%%", progresso * 100));

                    if (escalaAtual >= escalaFinal) {
                        System.out.println("Zoom completado: " + String.format("%.2f", escalaAtual) + "x");
                        animacaoConcluida = true;
                    }
                } else {
                    System.out.println("DEBUG ZOOM - Já atingiu a escala final!");
                    animacaoConcluida = true;
                }
            }

            if (animacaoConcluida) {
                System.out.println("=== ANIMAÇÃO " + tipoAnimacaoAtual + " CONCLUÍDA ===");

                TipoAnimacao tipoAnterior = tipoAnimacaoAtual;
                tipoAnimacaoAtual = TipoAnimacao.PARADA;
                stopTimer();

                // Se acabou de abrir as portas, iniciar o zoom automaticamente
                if (tipoAnterior == TipoAnimacao.ABRINDO) {
                    System.out.println("Portas abertas! Iniciando zoom em 500ms...");
                    animacaoEmAndamento = false; // Liberar para a próxima animação

                    // Delay antes de iniciar o zoom
                    Timer delayZoom = new Timer(500, ev -> {
                        System.out.println("Executando zoom agora!");
                        tipoAnimacaoAtual = TipoAnimacao.ENTRANDO;
                        escalaAtual = ESCALA_INICIAL;
                        animacaoEmAndamento = true;

                        if (timerAnimacao == null) {
                            inicializarTimer();
                        }

                        if (timerAnimacao != null && !timerAnimacao.isRunning()) {
                            timerAnimacao.start();
                        }
                    });
                    delayZoom.setRepeats(false);
                    delayZoom.start();
                    return; // Não chamar onAnimacaoConcluida ainda
                } else {
                    if (tipoAnterior != TipoAnimacao.ENTRANDO) {
                        escalaAtual = ESCALA_INICIAL;
                    }
                    animacaoEmAndamento = false;
                    onAnimacaoConcluida();
                }
            }

            repaint();
        } catch (Exception e) {
            System.err.println("Erro durante a animação: " + e.getMessage());
            stopTimer();
        }
    }

    /* ============================================= MÉTODOS PÚBLICOS ============================================= */
    public void abrirPortas() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::abrirPortas);
            return;
        }

        if (animacaoEmAndamento) {
            System.out.println("Animação já em andamento, ignorando nova chamada.");
            return;
        }

        tipoAnimacaoAtual = TipoAnimacao.ABRINDO;
        frameAtual = 0; // Resetar contador de frames
        offsetAtualPortaE = OFFSET_PORTA_E_FECHADA;
        larguraAtualPortaE = LARGURA_PORTA_E_FECHADA;
        offsetAtualPortaD = OFFSET_PORTA_D_FECHADA;
        larguraAtualPortaD = LARGURA_PORTA_D_FECHADA;
        escalaAtual = ESCALA_INICIAL;
        animacaoEmAndamento = true;

        System.out.println("=== INICIANDO ANIMAÇÃO DE ABERTURA ===");

        repaint();

        if (timerAnimacao == null) {
            inicializarTimer();
        }

        if (timerAnimacao != null && !timerAnimacao.isRunning()) {
            timerAnimacao.start();
        }
    }

    public void fecharPortasComAnimacao() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::fecharPortasComAnimacao);
            return;
        }

        if (animacaoEmAndamento) {
            stopTimer();
        }

        tipoAnimacaoAtual = TipoAnimacao.FECHANDO;
        frameAtual = 0; // Resetar contador de frames
        offsetAtualPortaE = OFFSET_PORTA_E_ABERTA;
        larguraAtualPortaE = LARGURA_PORTA_E_ABERTA;
        offsetAtualPortaD = OFFSET_PORTA_D_ABERTA;
        larguraAtualPortaD = LARGURA_PORTA_D_ABERTA;
        escalaAtual = ESCALA_INICIAL;
        animacaoEmAndamento = true;

        System.out.println("=== INICIANDO ANIMAÇÃO DE FECHAMENTO ===");

        repaint();

        if (timerAnimacao == null) {
            inicializarTimer();
        }

        if (timerAnimacao != null && !timerAnimacao.isRunning()) {
            timerAnimacao.start();
        }
    }

    public void fecharPortas() {
        System.out.println("=== FECHANDO PORTAS INSTANTANEAMENTE ===");

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::fecharPortas);
            return;
        }

        stopTimer();

        tipoAnimacaoAtual = TipoAnimacao.PARADA;
        offsetAtualPortaE = OFFSET_PORTA_E_FECHADA;
        larguraAtualPortaE = LARGURA_PORTA_E_FECHADA;
        offsetAtualPortaD = OFFSET_PORTA_D_FECHADA;
        larguraAtualPortaD = LARGURA_PORTA_D_FECHADA;
        escalaAtual = ESCALA_INICIAL;
        animacaoEmAndamento = false;

        repaint();
    }

    public void iniciarAnimacaoEntrada(Runnable callback) {
        if (callback != null) {
            this.callbackTermino = callback;
        }

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> iniciarAnimacaoEntrada(callback));
            return;
        }

        if (animacaoEmAndamento && tipoAnimacaoAtual == TipoAnimacao.ENTRANDO) {
            System.out.println("Zoom já em andamento, ignorando nova chamada.");
            return;
        }

        if (animacaoEmAndamento) {
            stopTimer();
        }

        if (escalaFinal == 0 || escalaFinal < 3.0) {
            calcularEscalaMaxima();
        }

        tipoAnimacaoAtual = TipoAnimacao.ENTRANDO;
        escalaAtual = ESCALA_INICIAL;
        animacaoEmAndamento = true;

        System.out.println("=== INICIANDO ANIMAÇÃO DE ENTRADA (ZOOM) ===");
        System.out.println("Escala alvo: " + String.format("%.2f", escalaFinal) + "x");

        repaint();

        if (timerAnimacao == null) {
            inicializarTimer();
        }

        if (timerAnimacao != null && !timerAnimacao.isRunning()) {
            timerAnimacao.start();
        }
    }

    protected void onAnimacaoConcluida() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::onAnimacaoConcluida);
            return;
        }

        animacaoEmAndamento = false;

        if (callbackTermino != null) {
            try {
                callbackTermino.run();
            } catch (Exception e) {
                System.err.println("Erro ao executar callback: " + e.getMessage());
            }
            callbackTermino = null;
        }
    }

    public boolean isAnimando() {
        return animacaoEmAndamento;
    }

    public void iniciarAnimacao(Runnable aoTerminar) {
        this.callbackTermino = aoTerminar;

        if (!animacaoEmAndamento) {
            abrirPortas();
        }
    }

    public void dispose() {
        if (timerAnimacao != null) {
            timerAnimacao.stop();
            timerAnimacao = null;
        }

        if (bufferGraphics != null) {
            bufferGraphics.dispose();
            bufferGraphics = null;
        }

        imagemBatente = null;
        imagemPortaE = null;
        imagemPortaD = null;
        imagemParede = null;
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    /**
     * Este método é chamado dentro do construtor para inicializar o formulário.
     * AVISO: NÃO modifique este código. O conteúdo deste método é sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblParede = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));

        lblParede.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblParede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/warrick/biblioteca/Imagem/Parede1.png"))); // NOI18N
        lblParede.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        // Adicionar a label ao painel
        add(lblParede);
    }// </editor-fold>//GEN-END:initComponents

    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblParede;
    // End of variables declaration//GEN-END:variables
}