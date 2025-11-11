package br.com.warrick.biblioteca.peripherals;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * Botão personalizado com efeitos visuais avançados, incluindo animações de hover e clique.
 * Fornece feedback visual rico para melhorar a experiência do usuário.
 *
 * Projeto: Biblioteca
 *
 * @author Ra Ven - Criador original dos componentes personalizados (YouTube/GitHub)
 * @author Warrick
 * @since 02/11/2025
 */
public class WButton extends JButton {

    /* ========================================== ATRIBUTOS ========================================== */
    
    /** Controladores de animação */
    private Animator animatorOver;
    private Animator animatorPress;
    
    /** Estados da animação */
    private float animateOver;
    private float animatePress;
    
    /** Estados do mouse */
    private boolean mouseOver;
    private boolean mousePress;
    
    /* ========================================== APARÊNCIA ========================================== */
    
    /** Tamanho da borda */
    private int borderSize = 2;
    
    /** Ponto de clique do mouse */
    private Point mousePoint;
    
    /** Cores personalizadas */
    private Color selectedColor;
    private Color effectColor;

    /* ========================================== CONSTRUTOR ========================================== */
    
    /**
     * Cria um novo botão personalizado
     * Inicializa as animações e aparência
     */
    public WButton() {
        // Inicializa com uma cor padrão
        this.selectedColor = new Color(0, 120, 212); // Azul padrão
        this.effectColor = new Color(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), 60);
        init();
    }

    /* ========================================== INICIALIZAÇÃO ========================================== */
    
    /**
     * Configura as propriedades iniciais
     */
    private void init() {       
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0)); // Fundo transparente
        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Button.background,"
                + "foreground:$Button.foreground");
        initAnimator();
        MouseAdapter mouseEvent = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                startAnimationOver();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                startAnimationOver();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    mousePress = true;
                    startAnimationPress();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    startAnimationPress();
                    mousePress = false;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mousePoint = e.getPoint();
                repaint();
            }
            
        };               
        addMouseListener(mouseEvent);
        addMouseMotionListener(mouseEvent);
    }

    /* ========================================== MÉTODOS PRIVADOS ========================================== */
    
    /**
     * Inicializa as animações do botão
     * Configura as propriedades de tempo e interpolação
     */
    private void initAnimator() {
        animatorOver = new Animator(250, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animateOver = mouseOver ? fraction : 1f - fraction;
                repaint();
            }
        });
        animatorPress = new Animator(250, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animatePress = mousePress ? fraction : 1f - fraction;
                repaint();
            }
        });        
        animatorOver.setResolution(0);
        animatorOver.setAcceleration(.5f);
        animatorOver.setDeceleration(.5f);
        animatorPress.setResolution(0);
        animatorPress.setAcceleration(.5f);
        animatorPress.setDeceleration(.5f);
        
    }

    /**
     * Inicia a animação de hover
     * Controla o efeito quando o mouse está sobre o botão
     */
    private void startAnimationOver() {
        if (animatorOver.isRunning()) {
            float f = animatorOver.getTimingFraction();
            animatorOver.stop();
            animatorOver.setStartFraction(1f - f);
        } else {
            animatorOver.setStartFraction(0);
        }
        animatorOver.start();
        updateEffectColor();
    }

    /**
     * Inicia a animação de clique
     * Controla o efeito quando o botão é pressionado
     */
    private void startAnimationPress() {
        if (animatorPress.isRunning()) {
            float f = animatorPress.getTimingFraction();
            animatorPress.stop();
            animatorPress.setStartFraction(1f - f);
        } else {
            animatorPress.setStartFraction(0);
        }
        animatorPress.start();
    }

    /* ========================================== MÉTODOS SOBRESCRITOS ========================================== */
    
    /**
     * Desenha o componente personalizado
     * 
     * @param g Contexto gráfico para desenho
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = 0;
        int y = 0;
        int width = getWidth();
        int height = getHeight();
        Rectangle rec = new Rectangle(x, y, width, height);
        if (isEnabled()) {
            // Fundo transparente - não preencher
            // g2.setColor(getBackground());
            // g2.fill(rec);
            
            if (animateOver > 0 || animatePress > 0) {
                Area area = new Area(rec);
                Rectangle rec_in = new Rectangle(x + borderSize, y + borderSize, width - borderSize * 2, height - borderSize * 2);
                area.subtract(new Area(rec_in));
                
                if (animateOver > 0 && mousePoint != null) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, animateOver));
                    g2.setPaint(getGradient(mousePoint, 255, 1.5f));
                    g2.fill(area);
                    g2.setPaint(getGradient(mousePoint, 70, 0.3f));
                    g2.fill(rec_in);
                }
                
                if (animatePress > 0) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, animatePress));
                    g2.setColor(selectedColor);
                    g2.fill(rec_in);
                }
            }
            
        } else {
            g2.setColor(Color.GRAY);
            g2.fill(rec);
        }
        
        g2.dispose();
        super.paintComponent(g);
        
        // Desenhar borda dourada
        g2 = (Graphics2D) g.create();
        g2.setColor(new Color(255, 215, 0)); // Cor dourada
        g2.drawRect(x, y, width - 1, height - 1);
        g2.dispose();
    }

    /**
     * Cria um gradiente radial para o efeito de hover
     * 
     * @param p Ponto central do gradiente
     * @param alpha Valor de transparência (0-255)
     * @param size Tamanho do gradiente
     * @return Objeto RadialGradientPaint configurado
     */
    private RadialGradientPaint getGradient(Point2D p, int alpha, float size) {
        int width = getWidth();
        int height = getHeight();
        Point2D center = p;
        float radius = (float) Math.max(width, height) * size;
        float[] dist = {0.0f, 1.0f};
        int red = effectColor.getRed();
        int green = effectColor.getGreen();
        int blue = effectColor.getBlue();
        Color[] colors = {new Color(red, green, blue, alpha), new Color(red, green, blue, 0)};
        return new RadialGradientPaint(center, radius, dist, colors);
        
    }

    /**
     * Retorna o tamanho da borda do botão
     * 
     * @return Tamanho da borda
     */
    public int getBorderSize() {
        return borderSize;
    }

    /**
     * Define o tamanho da borda do botão
     * 
     * @param borderSize Tamanho da borda
     */
    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        repaint();
    }

    /**
     * Retorna a cor selecionada do botão
     * 
     * @return Cor selecionada
     */
    public Color getSelectedColor() {
        return selectedColor;
    }

    /**
     * Define a cor selecionada do botão
     * 
     * @param selectedColor Cor selecionada
     */
    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        repaint();
    }

    /**
     * Retorna a cor do efeito do botão
     * 
     * @return Cor do efeito
     */
    public Color getEffectColor() {
        return effectColor;
    }

    /**
     * Define a cor do efeito do botão
     * 
     * @param effectColor Cor do efeito
     */
    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
        repaint();
    }
    
    /**
     * Atualiza a cor do efeito com transparência
     * Baseado na cor selecionada
     */
    private void updateEffectColor() {
        effectColor = new Color(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), 60);
    }
}
