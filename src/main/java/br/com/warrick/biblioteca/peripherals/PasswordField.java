package br.com.warrick.biblioteca.peripherals;

import com.formdev.flatlaf.FlatClientProperties;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

/**
 * Componente personalizado de campo de senha com rótulo flutuante e
 * feedback visual. Estende JPasswordField para fornecer uma melhor experiência
 * de usuário com animações e estilização.
 *
 * Projeto: Biblioteca
 *
 * @author Ra Ven - Criador original dos componentes personalizados (YouTube/GitHub)
 * @author Warrick
 * @since 02/11/2025
 */
public class PasswordField extends JPasswordField {
    /* ============================================== CONSTANTES ============================================= */
    
    /** Cor padrão da linha inferior */
    private static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);
    
    /* ============================================== ATRIBUTOS ============================================= */
    
    /** Mostrar/esconder senha */
    private boolean showAndHide = true;
    
    /** Texto do rótulo */
    private String labelText = "Senha";
    
    /** Cor da linha inferior do campo */
    private Color lineColor = DEFAULT_LINE_COLOR;
    
    /* ========================================== MÉTODOS PÚBLICOS ========================================== */
    
    /**
     * Verifica se o botão de mostrar/esconder está ativo
     * 
     * @return true se ativo, false caso contrário
     */
    public boolean isShowAndHide() {
        return showAndHide;
    }

    /**
     * Ativa/desativa o botão de mostrar/esconder
     * 
     * @param showAndHide true para ativar, false para desativar
     */
    public void setShowAndHide(boolean showAndHide) {
        this.showAndHide = showAndHide;
        repaint();
    }

    /**
     * Obtém o texto do rótulo
     * 
     * @return Texto atual do rótulo
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * Define o texto do rótulo
     * 
     * @param labelText Novo texto para o rótulo
     */
    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    /**
     * Obtém a cor da linha
     * 
     * @return Cor atual da linha
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Define a cor da linha
     * 
     * @param lineColor Nova cor para a linha
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    /* ========================================== ATRIBUTOS DE CONTROLE ========================================== */
    
    /** Controlador de animação */
    private final Animator animator;
    
    /** Controla a animação do texto de dica */
    private boolean animateHintText = true;
    
    /** Posição da animação */
    private float location;
    
    /** Indica se o texto está visível */
    private boolean show;
    
    /** Indica se o mouse está sobre o componente */
    private boolean mouseOver = false;
    
    /** Indica se a senha está oculta */
    private boolean hide = true;
    
    /* ========================================== RECURSOS ========================================== */
    
    /** Ícone de olho aberto */
    private Image eye;
    
    /** Ícone de olho fechado */
    private Image eye_hide;
    /**
     * Carrega uma imagem dos recursos
     * 
     * @param image Nome do arquivo
     * @return Imagem carregada ou null
     */
    private Image getImage(String image) {
        try {
            // Tenta carregar do classpath primeiro
            String path = "/Icone/" + image;
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL).getImage();
            } else {
                // Se não encontrar, tenta carregar diretamente do sistema de arquivos
                return new ImageIcon("src/main/resources/Icone/" + image).getImage();
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + image + " - " + e.getMessage());
            return null;
        }
    }

    /* ========================================== CONSTRUTOR ========================================== */
    
    /**
     * Cria um novo campo de senha
     * Configura bordas, cores e listeners
     */
    public PasswordField() {
        // Configuração inicial do componente
        setBorder(new EmptyBorder(20, 3, 10, 30));
        setSelectionColor(new Color(76, 204, 255));
        
        // Listener para detectar quando o mouse está sobre o componente
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
        
        // Listener para detectar ganho/perda de foco
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                showing(false);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                showing(true);
            }
        });
        
        // Listener para detectar movimento do mouse sobre o botão de mostrar/esconder
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                if (showAndHide) {
                    int x = getWidth() - 30;
                    if (new Rectangle(x, 0, 30, 30).contains(me.getPoint())) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.TEXT_CURSOR));
                    }
                }
            }
        });
        
        // Listener para detectar clique no botão de mostrar/esconder
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (showAndHide) {
                    int x = getWidth() - 30;
                    if (new Rectangle(x, 0, 30, 30).contains(me.getPoint())) {
                        hide = !hide;
                        if (hide) {
                            setEchoChar('•');
                        } else {
                            setEchoChar((char) 0);
                        }
                        repaint();
                    }
                }
            }
        });
        
        // Configuração de propriedades visuais
        putClientProperty(FlatClientProperties.STYLE, "showClearButton:false");
        putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "");
        
        // Configuração da animação do rótulo
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                // Atualiza o estado da animação baseado no conteúdo do campo
                animateHintText = getPassword().length == 0;
            }

            @Override
            public void timingEvent(float fraction) {
                location = fraction;
                repaint();
            }
        };
        
        // Configuração do animador
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.setStartDelay(100);
        
        // Carrega os ícones de mostrar/esconder senha
        try {
            eye = new ImageIcon(getClass().getResource("/Icone/eye.png")).getImage();
            eye_hide = new ImageIcon(getClass().getResource("/Icone/eye_hide.png")).getImage();
            System.out.println("Ícones carregados com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícones: " + e.getMessage());
            // Tenta carregar com caminho alternativo
            try {
                eye = new ImageIcon("src/main/resources/Icone/eye.png").getImage();
                eye_hide = new ImageIcon("src/main/resources/Icone/eye_hide.png").getImage();
                System.out.println("Ícones carregados com caminho alternativo!");
            } catch (Exception ex) {
                System.err.println("Falha ao carregar ícones com caminho alternativo: " + ex.getMessage());
            }
        }
    }

    /**
     * Controla a exibição do rótulo com animação
     * 
     * @param show true para mostrar o rótulo, false para esconder
     */
    private void showing(boolean show) {
        if (animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        animator.setStartFraction(1f - location);
        this.show = show;
        location = 1f - location;
        animator.start();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        
        try {
            // Configuração de qualidade de renderização
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            
            // Desenha a linha inferior com cor diferente quando o mouse está sobre o campo
            int width = getWidth();
            int height = getHeight();
            g2.setColor(mouseOver ? lineColor : new Color(150, 150, 150));
            g2.fillRect(2, height - 1, width - 4, 1);
            
            // Desenha os elementos visuais adicionais
            createHintText(g2);
            createLineStyle(g2);
            
            // Desenha o botão de mostrar/esconder senha, se habilitado
            if (isShowAndHide()) {
                createShowHide(g2);
            }
        } finally {
            g2.dispose();
        }
    }

    /**
     * Desenha o botão de mostrar/esconder senha
     * 
     * @param g2 Contexto gráfico para desenho
     */
    private void createShowHide(Graphics2D g2) {
        if (eye == null || eye_hide == null) {
            return; // Não desenha se os ícones não foram carregados
        }
        
        int x = getWidth() - 30;  // Posiciona o ícone na borda direita
        int y = (getHeight() - 20) / 2;  // Centraliza verticalmente
        
        // Desenha o ícone apropriado baseado no estado atual
        Image icon = hide ? eye : eye_hide;
        if (icon != null) {
            g2.drawImage(icon, x, y, 20, 20, null);
        }
    }

    /**
     * Desenha o texto do rótulo com animação
     * 
     * @param g2 Contexto gráfico para desenho
     */
    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double size = 18; // Tamanho padrão do texto
        
        // Calcula o tamanho do texto com base no estado da animação, se necessário
        if (animateHintText) {
            size = 18 * (show ? (1 - location) : location);
        }
        
        // Desenha o texto do rótulo
        g2.drawString(labelText, in.right, (int) (in.top + textY + ft.getAscent() - size));
    }

    /**
     * Desenha a linha de destaque quando o campo está em foco
     * 
     * @param g2 Contexto gráfico para desenho
     */
    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            
            // Calcula o tamanho da linha com base no estado da animação
            double size = width * (show ? (1 - location) : location);
            double x = (width - size) / 2;
            
            // Desenha a linha de destaque
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }
    }

    @Override
    public void setText(String string) {
        // Atualiza o estado da animação quando o texto é alterado
        String currentText = new String(getPassword());
        if (!currentText.equals(string)) {
            showing(string.isEmpty());
        }
        super.setText(string);
    }
}
