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
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * Painel de animações de abertura de portas
 * Versão Refaturada: JLabel = BufferedImage
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginLoadingPortas extends javax.swing.JPanel {

    /* ================================ BUFFER DE IMAGEM = */
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;

    /* ================================ IMAGENS DAS PORTAS E BATENTES = */
    private Image imagemBatente;
    private Image imagemPortaE;
    private Image imagemPortaD;

    /* ================================ CONSTANTES DE POSICIONAMENTO = */
    private static final int LARGURA_PAINEL = 570;
    private static final int ALTURA_PAINEL = 881;
    private static final int POSICAO_INICIAL_PORTA_E = 133;
    private static final int POSICAO_FINAL_PORTA_E = 21;
    private static final int POSICAO_INICIAL_PORTA_D = 284;
    private static final int POSICAO_FINAL_PORTA_D = 390;
    private static final int POSICAO_Y_PORTAS = 89;

    /* ================================ CONFIGURAÇÕES DE ANIMAÇÃO = */
    private static final int VELOCIDADE_ANIMACAO = 2;
    private static final int DELAY_ANIMACAO = 16;
    private static final int DELAY_INICIAL = 500;

    /* ================================ VARIÁVEIS DE CONTROLE DA ANIMAÇÃO = */
    private int posicaoAtualE;
    private int posicaoAtualD;
    private Timer timerAnimacao;
    private boolean animacaoEmAndamento;

    /* ================================ CONSTRUTOR PADRÃO = */
    public LoginLoadingPortas() {
        initComponents();
        
        configurarPainel();
        inicializarBuffer();
        carregarImagens();
        inicializarTimer();

        //  Inicializar posições
        posicaoAtualE = POSICAO_INICIAL_PORTA_E;
        posicaoAtualD = POSICAO_INICIAL_PORTA_D;

        // Iniciar animação após um delay
        Timer startupTimer = new Timer(DELAY_INICIAL, e -> abrirPortas());
        startupTimer.setRepeats(false);
        startupTimer.start();
    }

    /* ================================ CONSTRUTOR COM PARÂMETRO  = */
    public LoginLoadingPortas(LoginLoadingPainel parentApp) {
        this();
    }

    /* ================================ CONSTRUTOR DO PAINEL = */
    private void configurarPainel() {
        setPreferredSize(new Dimension(LARGURA_PAINEL, ALTURA_PAINEL));
        setSize(LARGURA_PAINEL, ALTURA_PAINEL);

        setOpaque(false);
        setDoubleBuffered(false);

        setBackground(new Color(0, 0, 0, 0));
    }

    /* ================================ INICIALIZAÇÂO DO BUFFER DE IMAGEM = */
    private void inicializarBuffer() {
        // Criar buff com suporte a trasnparência (ARGB)
        buffer = new BufferedImage(
                LARGURA_PAINEL,
                ALTURA_PAINEL,
                BufferedImage.TYPE_INT_ARGB
        );

        // Obter Graphics2D do buffer
        bufferGraphics = buffer.createGraphics();

        // Configurar renderização de alta qualidade
        bufferGraphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        bufferGraphics.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );
        bufferGraphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
    }

    /* ================================ CARREGAMENTO DAS IMAGEM = */
    private void carregarImagens() {
        System.out.println(I18nManager.msg("animation.loading"));
        
        // Carregar Batente
        try {
            java.net.URL batenteURL = getClass().getResource("/Imagem/Batente.png");
            if (batenteURL != null) {
                imagemBatente = new ImageIcon(batenteURL).getImage();
                System.out.println("✓ Batente carregado com sucesso");
            } else {
                System.err.println("✗ " + I18nManager.msg("error.image.not.found") + ": Batente.png");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar Batente: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Carregar Porta esquerda
        try {
            java.net.URL portaEURL = getClass().getResource("/Imagem/PortaE.png");
            if (portaEURL != null) {
                imagemPortaE = new ImageIcon(portaEURL).getImage();
                System.out.println("✓ PortaE carregada com sucesso");
            } else {
                System.err.println("✗ " + I18nManager.msg("error.image.not.found") + ": PortaE.png");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar PortaE: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Carregar Porta direita
        try {
            java.net.URL portaDURL = getClass().getResource("/Imagem/PortaD.png");
            if (portaDURL != null) {
                imagemPortaD = new ImageIcon(portaDURL).getImage();
                System.out.println("✓ PortaD carregada com sucesso");
            } else {
                System.err.println("✗ " + I18nManager.msg("error.image.not.found") + ": PortaD.png");
            }
        } catch (Exception e) {
            System.err.println("✗ ERRO ao carregar PortaD: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Resumo do carregamento:");
        System.out.println("  Batente: " + (imagemBatente != null ? "OK" : "FALHA"));
        System.out.println("  PortaE: " + (imagemPortaE != null ? "OK" : "FALHA"));
        System.out.println("  PortaD: " + (imagemPortaD != null ? "OK" : "FALHA"));
    }

    /* ================================ MÉTODO PRINCIPAL DE DESENHO = */
    // ESTE É O CORAÇÃO DA SOLUÇÃO: DESENHA TUDO NO BUFFER PRIMEIRO
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Passo 1: Limpar o buffer completamente (Transparente)
        bufferGraphics.setComposite(AlphaComposite.Clear);
        bufferGraphics.fillRect(0, 0, LARGURA_PAINEL, ALTURA_PAINEL);

        // Passo 2: Restaurar modo de deseno normal
        bufferGraphics.setComposite(AlphaComposite.SrcOver);

        // Passo 3: Desenha tudo no buffer na ordem correta
        desenharNoBuffer();

        // Passo 4: Transferir o buffer interiro para a tela
        g.drawImage(buffer, 0, 0, null);

    }

    /* ================================ DESENHA TODOS OS COMPONENTES NO BUFFER = */
    private void desenharNoBuffer() {
        // Verificar se as imagens foram carregadas
        if (imagemBatente == null || imagemPortaE == null || imagemPortaD == null) {
            return;
        }

        // 1. Desenhar porta esquerda na posição atual (fundo)
        bufferGraphics.drawImage(imagemPortaE, posicaoAtualE, POSICAO_Y_PORTAS, null);

        // 2. Desenhar porta direita na posição atual (fundo)
        bufferGraphics.drawImage(imagemPortaD, posicaoAtualD, POSICAO_Y_PORTAS, null);

        // 3. Desenhar Batente por último (frente) - sobrepõe as portas
        bufferGraphics.drawImage(imagemBatente, 0, 0, null);

    }

    /* ================================ INICIALIZAÇÂO DO TIMER DE ANIMAÇÂO = */
    private void inicializarTimer() {
        timerAnimacao = new Timer(DELAY_ANIMACAO, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animar();
            }
        });
    }

    /* ================================ LÓGICA DA ANIMAÇÂO = */
    private void animar() {
        // Verifica se o componente está visível
        if (!isVisible() || !isDisplayable()) {
            timerAnimacao.stop();
            return;
        }
        boolean portaEConcluida = false;
        boolean portaDConcluida = false;

        // Move porta esquerda
        if (posicaoAtualE > POSICAO_FINAL_PORTA_E) {
            posicaoAtualE = Math.max(
                    posicaoAtualE - VELOCIDADE_ANIMACAO,
                    POSICAO_FINAL_PORTA_E
            );
        } else {
            portaEConcluida = true;
        }

        // move porta direita
        if (posicaoAtualD < POSICAO_FINAL_PORTA_D) {
            posicaoAtualD = Math.min(
                    posicaoAtualD + VELOCIDADE_ANIMACAO,
                    POSICAO_FINAL_PORTA_D
            );
        } else {
            portaDConcluida = true;
        }

        // Redesenha
        repaint();

        // Verificar se a animação terminou
        if (portaEConcluida && portaDConcluida) {
            timerAnimacao.stop();
            animacaoEmAndamento = false;
            onAnimacaoConcluida();
        }
    }

    // INICIA A ANIMAÇÂO DA ABERTURA DAS PORTAS
    public void abrirPortas() {
        if (animacaoEmAndamento) {
            return;
        }

        // Resetar posições
        posicaoAtualE = POSICAO_INICIAL_PORTA_E;
        posicaoAtualD = POSICAO_INICIAL_PORTA_D;
        animacaoEmAndamento = true;

        // Redesenha posições iniciais
        repaint();

        // Iniciar timer
        timerAnimacao.start();

    }

    // FECHA AS PORTAS
    public void fecharPortas() {
        // Parar animação
        if (timerAnimacao != null && timerAnimacao.isRunning()) {
            timerAnimacao.stop();
        }

        // Resetar posições
        posicaoAtualE = POSICAO_INICIAL_PORTA_E;
        posicaoAtualD = POSICAO_INICIAL_PORTA_D;
        animacaoEmAndamento = false;

        // Redesenhar
        repaint();
    }

    // CALLBACK: Chamdao quando a animação é concluida
    protected void onAnimacaoConcluida() {
        // Chamar tela Principal
    }

    // VERIFICA SE A ANIMAÇÂO ESTÁ EM ANDAMENTO
    public boolean isAnimando() {
        return animacaoEmAndamento;
    }

    //LIBERA RECURSOS
    public void dispose() {
        // Parar timer
        if (timerAnimacao != null) {
            timerAnimacao.stop();
            timerAnimacao = null;
        }

        // Libera graphics do buffer
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
            bufferGraphics = null;
        }

        // Limpar referências das imagens
        imagemBatente = null;
        imagemPortaE = null;
        imagemPortaD = null;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Não adicionar componentes - usamos paintComponent customizado
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
