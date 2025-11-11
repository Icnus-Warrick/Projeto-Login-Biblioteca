package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Painel de animações de abertura de portas COM EFEITO DE ENTRADA (ZOOM)
 * Versão com BufferedImage (sem JLabels) + Zoom In
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
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

    /* ======================================== CONSTANTES DE POSICIONAMENTO ======================================== */
    private static final int LARGURA_PAINEL = 570;
    private static final int ALTURA_PAINEL = 881;
    private static final int POSICAO_INICIAL_PORTA_E = 133;
    private static final int POSICAO_FINAL_PORTA_E = 21;
    private static final int POSICAO_INICIAL_PORTA_D = 284;
    private static final int POSICAO_FINAL_PORTA_D = 391;
    private static final int POSICAO_Y_PORTAS = 89;

    /* ========================================== CONFIGURAÇÕES DE ANIMAÇÃO ========================================= */
    private static final int VELOCIDADE_ANIMACAO = 2;
    private static final int DELAY_ANIMACAO = 16;

    /* ========================================== CONFIGURAÇÕES DE ZOOM/ENTRADA ===================================== */
    private static final double ESCALA_INICIAL = 1.0;      // Tamanho normal
    private static final double ESCALA_FINAL = 2.8;        // Aumenta 2.8x (ajuste a gosto)
    private static final double VELOCIDADE_ZOOM = 0.015;   // Velocidade do zoom (maior = mais rápido)
    private static final double INICIO_FADE = 1.8;         // Quando começa o fade out durante o zoom

    /* ============================================ VARIÁVEIS DE ESTADO ============================================= */
    private int posicaoAtualE;
    private int posicaoAtualD;
    private double escalaAtual = ESCALA_INICIAL;
    private Timer timerAnimacao;
    private boolean animacaoEmAndamento;

    /**
     * Enum para controlar o tipo de animação
     */
    private enum TipoAnimacao {
        ABRINDO,
        FECHANDO,
        ENTRANDO,  // Novo: efeito de zoom/entrada
        PARADA
    }

    private TipoAnimacao tipoAnimacaoAtual = TipoAnimacao.PARADA;

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public void setParentApp(LoginApp parentApp) {
        this.parentApp = parentApp;
    }

    public LoginPortas() {
        try {
            initComponents();

            configurarPainel();
            inicializarBuffer();
            carregarImagens();
            inicializarTimer();

            // Inicializar posições
            posicaoAtualE = POSICAO_INICIAL_PORTA_E;
            posicaoAtualD = POSICAO_INICIAL_PORTA_D;
            escalaAtual = ESCALA_INICIAL;

            // Forçar um redesenho inicial
            repaint();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar LoginPortas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public LoginPortas(LoginApp parent) {
        this();
        this.parentApp = parent;
    }

    /* ============================================ CONFIGURAÇÃO DO PAINEL ============================================ */
    private void configurarPainel() {
        setPreferredSize(new Dimension(LARGURA_PAINEL, ALTURA_PAINEL));
        setMinimumSize(new Dimension(LARGURA_PAINEL, ALTURA_PAINEL));
        setMaximumSize(new Dimension(LARGURA_PAINEL, ALTURA_PAINEL));

        setOpaque(false);
        setDoubleBuffered(true);
        setBackground(new Color(0, 0, 0, 0));

        revalidate();
        repaint();
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

        int largura = Math.max(1, getWidth() > 0 ? getWidth() : LARGURA_PAINEL);
        int altura = Math.max(1, getHeight() > 0 ? getHeight() : ALTURA_PAINEL);

        try {
            buffer = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
            bufferGraphics = buffer.createGraphics();

            bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            bufferGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

            System.out.println("Buffer inicializado: " + largura + "x" + altura);
        } catch (Exception e) {
            System.err.println("Erro ao inicializar buffer: " + e.getMessage());
            e.printStackTrace();
            buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            bufferGraphics = buffer.createGraphics();
        }
    }

    /* =========================================== CARREGAMENTO DAS IMAGENS ========================================== */
    private void carregarImagens() {
        System.out.println(I18nManager.msg("animation.loading"));

        try {
            java.net.URL batenteURL = getClass().getResource("/Imagem/Batente.png");
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
            java.net.URL portaEURL = getClass().getResource("/Imagem/PortaE.png");
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
            java.net.URL portaDURL = getClass().getResource("/Imagem/PortaD.png");
            if (portaDURL != null) {
                imagemPortaD = new ImageIcon(portaDURL).getImage();
                System.out.println("✓ PortaD carregada");
            } else {
                System.err.println("✗ PortaD.png não encontrada");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar PortaD: " + e.getMessage());
        }
    }

    /* ========================================= MÉTODO PRINCIPAL DE DESENHO ======================================== */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (buffer == null || bufferGraphics == null ||
                buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
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
                g.drawImage(buffer, 0, 0, null);
            }
        } catch (Exception e) {
            System.err.println("Erro ao desenhar: " + e.getMessage());
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /* =================================== DESENHA TODOS OS COMPONENTES NO BUFFER =================================== */
    private void desenharNoBuffer() {
        try {
            bufferGraphics.setComposite(AlphaComposite.Clear);
            bufferGraphics.fillRect(0, 0, getWidth(), getHeight());
            bufferGraphics.setComposite(AlphaComposite.SrcOver);

            // Calcular centro para o zoom
            int centroX = getWidth() / 2;
            int centroY = getHeight() / 2;

            // Criar cópia do Graphics2D para aplicar transformações
            Graphics2D g2d = (Graphics2D) bufferGraphics.create();

            // Aplicar transformação de escala (zoom)
            if (escalaAtual != 1.0) {
                // Transladar para o centro, escalar, e transladar de volta
                g2d.translate(centroX, centroY);
                g2d.scale(escalaAtual, escalaAtual);
                g2d.translate(-centroX, -centroY);
            }

            // 1. Desenhar porta esquerda
            if (imagemPortaE != null) {
                g2d.drawImage(imagemPortaE,
                        posicaoAtualE,
                        POSICAO_Y_PORTAS,
                        imagemPortaE.getWidth(this),
                        imagemPortaE.getHeight(this),
                        this);
            }

            // 2. Desenhar porta direita
            if (imagemPortaD != null) {
                g2d.drawImage(imagemPortaD,
                        posicaoAtualD,
                        POSICAO_Y_PORTAS,
                        imagemPortaD.getWidth(this),
                        imagemPortaD.getHeight(this),
                        this);
            }

            // 3. Desenhar batente por último (sobrepõe as portas)
            if (imagemBatente != null) {
                g2d.drawImage(imagemBatente,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this);
            }

            g2d.dispose();

            // Aplicar fade out durante o zoom (para transição suave)
            if (tipoAnimacaoAtual == TipoAnimacao.ENTRANDO && escalaAtual > INICIO_FADE) {
                float progresso = (float)((escalaAtual - INICIO_FADE) / (ESCALA_FINAL - INICIO_FADE));
                float alpha = Math.max(0f, Math.min(1f, 1f - progresso));

                // Criar camada de fade
                bufferGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - alpha));
                bufferGraphics.setColor(Color.BLACK);
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
            timerAnimacao = null;
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
                // ========== ANIMAÇÃO DE ABERTURA DAS PORTAS ==========
                boolean portaEConcluida = false;
                boolean portaDConcluida = false;

                // Porta esquerda abrindo (para a ESQUERDA)
                if (posicaoAtualE > POSICAO_FINAL_PORTA_E) {
                    posicaoAtualE = Math.max(posicaoAtualE - VELOCIDADE_ANIMACAO, POSICAO_FINAL_PORTA_E);
                } else {
                    portaEConcluida = true;
                }

                // Porta direita abrindo (para a DIREITA)
                if (posicaoAtualD < POSICAO_FINAL_PORTA_D) {
                    posicaoAtualD = Math.min(posicaoAtualD + VELOCIDADE_ANIMACAO, POSICAO_FINAL_PORTA_D);
                } else {
                    portaDConcluida = true;
                }

                animacaoConcluida = portaEConcluida && portaDConcluida;

            } else if (tipoAnimacaoAtual == TipoAnimacao.FECHANDO) {
                // ========== ANIMAÇÃO DE FECHAMENTO DAS PORTAS ==========
                boolean portaEConcluida = false;
                boolean portaDConcluida = false;

                // Porta esquerda fechando (para a DIREITA)
                if (posicaoAtualE < POSICAO_INICIAL_PORTA_E) {
                    posicaoAtualE = Math.min(posicaoAtualE + VELOCIDADE_ANIMACAO, POSICAO_INICIAL_PORTA_E);
                } else {
                    portaEConcluida = true;
                }

                // Porta direita fechando (para a ESQUERDA)
                if (posicaoAtualD > POSICAO_INICIAL_PORTA_D) {
                    posicaoAtualD = Math.max(posicaoAtualD - VELOCIDADE_ANIMACAO, POSICAO_INICIAL_PORTA_D);
                } else {
                    portaDConcluida = true;
                }

                animacaoConcluida = portaEConcluida && portaDConcluida;

            } else if (tipoAnimacaoAtual == TipoAnimacao.ENTRANDO) {
                // ========== ANIMAÇÃO DE ENTRADA/ZOOM ==========

                if (escalaAtual < ESCALA_FINAL) {
                    // Calcular aceleração progressiva (zoom fica mais rápido conforme avança)
                    double aceleracao = 1.0 + Math.pow((escalaAtual - ESCALA_INICIAL) / (ESCALA_FINAL - ESCALA_INICIAL), 1.5);
                    double incremento = VELOCIDADE_ZOOM * aceleracao;

                    escalaAtual = Math.min(escalaAtual + incremento, ESCALA_FINAL);

                    if (escalaAtual >= ESCALA_FINAL) {
                        System.out.println("Zoom completado: " + String.format("%.2f", escalaAtual) + "x");
                        animacaoConcluida = true;
                    }
                } else {
                    animacaoConcluida = true;
                }
            }

            // Verifica se a animação foi concluída
            if (animacaoConcluida) {
                System.out.println("=== ANIMAÇÃO " + tipoAnimacaoAtual + " CONCLUÍDA ===");
                stopTimer();

                // Resetar escala se não for animação de entrada
                if (tipoAnimacaoAtual != TipoAnimacao.ENTRANDO) {
                    escalaAtual = ESCALA_INICIAL;
                }

                TipoAnimacao tipoAnterior = tipoAnimacaoAtual;
                tipoAnimacaoAtual = TipoAnimacao.PARADA;

                // Executar callback
                onAnimacaoConcluida();
            }

            repaint();
        } catch (Exception e) {
            System.err.println("Erro durante a animação: " + e.getMessage());
            stopTimer();
        }
    }

    /* ============================================= MÉTODOS PÚBLICOS ============================================= */

    /**
     * Abre as portas com animação
     */
    public void abrirPortas() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::abrirPortas);
            return;
        }

        if (animacaoEmAndamento) {
            stopTimer();
        }

        tipoAnimacaoAtual = TipoAnimacao.ABRINDO;
        posicaoAtualE = POSICAO_INICIAL_PORTA_E;
        posicaoAtualD = POSICAO_INICIAL_PORTA_D;
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

    /**
     * Fecha as portas com animação
     */
    public void fecharPortasComAnimacao() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::fecharPortasComAnimacao);
            return;
        }

        if (animacaoEmAndamento) {
            stopTimer();
        }

        tipoAnimacaoAtual = TipoAnimacao.FECHANDO;
        posicaoAtualE = POSICAO_FINAL_PORTA_E;
        posicaoAtualD = POSICAO_FINAL_PORTA_D;
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

    /**
     * Fecha as portas instantaneamente (sem animação)
     */
    public void fecharPortas() {
        System.out.println("=== FECHANDO PORTAS INSTANTANEAMENTE ===");

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::fecharPortas);
            return;
        }

        stopTimer();

        tipoAnimacaoAtual = TipoAnimacao.PARADA;
        posicaoAtualE = POSICAO_INICIAL_PORTA_E;
        posicaoAtualD = POSICAO_INICIAL_PORTA_D;
        escalaAtual = ESCALA_INICIAL;
        animacaoEmAndamento = false;

        repaint();
    }

    /**
     * Inicia o efeito de entrada/zoom (atravessar as portas)
     */
    public void entrarPelasPortas() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::entrarPelasPortas);
            return;
        }

        if (animacaoEmAndamento) {
            stopTimer();
        }

        tipoAnimacaoAtual = TipoAnimacao.ENTRANDO;
        escalaAtual = ESCALA_INICIAL;
        animacaoEmAndamento = true;

        System.out.println("=== INICIANDO ANIMAÇÃO DE ENTRADA (ZOOM) ===");

        repaint();

        if (timerAnimacao == null) {
            inicializarTimer();
        }

        if (timerAnimacao != null && !timerAnimacao.isRunning()) {
            timerAnimacao.start();
        }
    }

    /**
     * Callback executado quando a animação termina
     */
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

    /**
     * Verifica se há animação em andamento
     */
    public boolean isAnimando() {
        return animacaoEmAndamento;
    }

    /**
     * Inicia a animação e executa o callback ao terminar
     */
    public void iniciarAnimacao(Runnable aoTerminar) {
        this.callbackTermino = aoTerminar;

        if (!animacaoEmAndamento) {
            abrirPortas();
        }
    }

    /**
     * Libera recursos
     */
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
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Remova TODAS as labels via GUI Design do NetBeans
        // Este método deve ficar vazio ou apenas com configurações básicas
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(LARGURA_PAINEL, ALTURA_PAINEL));

        revalidate();
        repaint();
    }// </editor-fold>//GEN-END:initComponents

    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Sem JLabels - tudo é desenhado via BufferedImage
    // End of variables declaration//GEN-END:variables
}