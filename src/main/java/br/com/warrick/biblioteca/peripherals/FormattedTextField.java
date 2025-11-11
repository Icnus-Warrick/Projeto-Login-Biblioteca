package br.com.warrick.biblioteca.peripherals;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import javax.swing.JFormattedTextField;

/**
 * Componente personalizado de campo de texto formatado com rótulo flutuante
 * e animações suaves. Estende JFormattedTextField para fornecer uma experiência
 * de usuário mais rica com feedback visual.
 *
 * Projeto: Biblioteca
 *
 * @author Ra Ven - Criador original dos componentes personalizados (YouTube/GitHub)
 * @author Warrick
 * @since 02/11/2025
 */
public class FormattedTextField extends JFormattedTextField {
    
    /* ============================================== CONSTANTES ============================================= */
    
    /** Cor padrão da linha inferior */
    private static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);
    
    /* ============================================== ATRIBUTOS ============================================= */
    
    /** Controlador de animação */
    private final Animator animator;
    
    /** Indica se a animação do texto de dica está ativada */
    private boolean animateHintText = true;
    
    /** Posição do rótulo durante a animação */
    private float location;
    
    /** Indica se o rótulo deve ser mostrado */
    private boolean show;
    
    /** Indica se o mouse está sobre o componente */
    private boolean mouseOver = false;
    
    /** Texto do rótulo que será exibido acima do campo */
    private String labelText = "Label";
    
    /** Cor da linha inferior do campo */
    private Color lineColor = DEFAULT_LINE_COLOR;
    
    /* ============================================== CONSTRUTOR ============================================ */

    /**
     * Obtém o texto do rótulo exibido acima do campo
     * 
     * @return O texto do rótulo
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * Define o texto do rótulo exibido acima do campo
     * 
     * @param labelText O texto a ser exibido como rótulo
     */
    public void setLabelText(String labelText) {
        String old = this.labelText;
        this.labelText = labelText;
        firePropertyChange("labelText", old, this.labelText);
        repaint();
    }

    /**
     * Obtém a cor da linha inferior do campo
     * 
     * @return A cor da linha inferior
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Define a cor da linha inferior do campo
     * 
     * @param lineColor A nova cor da linha inferior
     */
    public void setLineColor(Color lineColor) {
        Color old = this.lineColor;
        this.lineColor = lineColor;
        firePropertyChange("lineColor", old, this.lineColor);
        repaint();
    }

    /**
     * Cria uma nova instância de FormattedTextField com configurações padrão
     * Configura bordas, cores de seleção e adiciona os listeners necessários
     */
    public FormattedTextField() {
        // Configuração inicial do componente
        setBorder(new EmptyBorder(20, 3, 10, 3));
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
        
        // Configuração da animação do rótulo
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHintText = getText().equals("");
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
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        
        // Configuração de qualidade de renderização
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        
        int width = getWidth();
        int height = getHeight();
        
        // Desenha a linha inferior com cor diferente quando o mouse está sobre o campo
        g2.setColor(mouseOver ? lineColor : new Color(150, 150, 150));
        g2.fillRect(2, height - 1, width - 4, 1);
        
        // Desenha o rótulo e a linha de destaque
        createHintText(g2);
        createLineStyle(g2);
        
        g2.dispose();
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
        double size;
        
        // Calcula o tamanho do texto com base no estado da animação
        if (animateHintText) {
            size = 18 * (show ? (1 - location) : location);
        } else {
            size = 18;
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
        if (!getText().equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }
}
