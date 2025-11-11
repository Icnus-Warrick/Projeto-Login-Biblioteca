package br.com.warrick.biblioteca.peripherals;

import com.formdev.flatlaf.FlatClientProperties;
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
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * Componente personalizado de campo de senha para tela de login com suporte a
 * mostrar/esconder senha e animações de rótulo flutuante.
 *
 * Projeto: Biblioteca
 *
 * @author Ra Ven - Criador original dos componentes personalizados (YouTube/GitHub)
 * @author Warrick
 * @since 02/11/2025
 */


public class PasswordFieldLogin extends JPasswordField {

    public boolean isShowAndHide() {
        return showAndHide;
    }

    public void setShowAndHide(boolean showAndHide) {
        System.out.println("setShowAndHide chamado com: " + showAndHide);
        this.showAndHide = showAndHide;
        repaint();
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    private final Animator animator;
    private boolean animateHinText = true;
    private float location;
    private boolean show;
    private boolean mouseOver = false;
    private String labelText = "Label";
    private Color lineColor = new Color(218, 165, 4);
    private Image eye;
    private Image eye_hide;
    private boolean hide = true;
    private boolean showAndHide;

    public PasswordFieldLogin() {
        // Ajuste as margens: top, left, bottom, right
        // O segundo valor controla o posicionamento do label
        setBorder(new EmptyBorder(15, 3, 5, 30));
        setSelectionColor(new Color(171, 122, 24));
        
        // Garante que o botão de mostrar/esconder senha está ativado
        this.showAndHide = true;
        System.out.println("PasswordFieldLogin inicializado. showAndHide: " + showAndHide);
        
        // Define a margem direita para o botão de mostrar/esconder
        setMargin(new Insets(0, 10, 0, 30));
        
        // Carrega os ícones
        loadIcons();
        
        // Habilita o botão de mostrar/esconder senha
        setShowAndHide(true);
        
        // Define a margem esquerda do texto (sem afetar o label)
        setMargin(new Insets(0, 10, 0, 0));  // Ajuste este valor para mover o texto
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
                    int x = getWidth() - 30;
                    if (new Rectangle(x, 0, 30, getHeight()).contains(me.getPoint())) {
                        // Inverte o estado de hide
                        hide = !hide;
                        
                        // Ajusta o echo char baseado no estado
                        if (hide) {
                            // Se está escondendo a senha, mostra os caracteres como '•'
                            setEchoChar('•');
                        } else {
                            // Se está mostrando a senha, remove o echo char
                            setEchoChar((char) 0);
                        }
                        
                        // Força o redesenho para atualizar o ícone
                        repaint();
                    }
                }
            }
        });
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
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                if (showAndHide) {
                    int x = getWidth() - 25;
                    if (new Rectangle(x, 25, 20, 20).contains(me.getPoint())) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.TEXT_CURSOR));
                    }
                }
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHinText = String.valueOf(getPassword()).equals("");
            }

            @Override
            public void timingEvent(float fraction) {
                location = fraction;
                repaint();
            }

        };
        animator = new Animator(300, target);
        loadIcons();
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    /**
     * Carrega os ícones de mostrar/esconder senha
     */
    private void loadIcons() {
        System.out.println("Tentando carregar ícones do classpath...");
        try {
            // Tenta carregar do classpath - caminho relativo ao pacote
            String eyePath = "/Icone/eye.png";
            String eyeHidePath = "/Icone/eye_hide.png";
            
            System.out.println("Tentando carregar: " + eyePath);
            System.out.println("Tentando carregar: " + eyeHidePath);
            
            // Usa o class loader para carregar os recursos
            ClassLoader classLoader = getClass().getClassLoader();
            java.net.URL eyeUrl = classLoader.getResource("Icone/eye.png");
            java.net.URL eyeHideUrl = classLoader.getResource("Icone/eye_hide.png");
            
            System.out.println("Caminho do eye.png: " + (eyeUrl != null ? eyeUrl.toString() : "não encontrado"));
            System.out.println("Caminho do eye_hide.png: " + (eyeHideUrl != null ? eyeHideUrl.toString() : "não encontrado"));
            
            if (eyeUrl != null && eyeHideUrl != null) {
                eye = new ImageIcon(eyeUrl).getImage();
                eye_hide = new ImageIcon(eyeHideUrl).getImage();
                System.out.println("Ícones carregados com sucesso do classpath!");
                return;
            }
            
            // Se não encontrou, tenta carregar diretamente do sistema de arquivos
            System.out.println("Tentando carregar do sistema de arquivos...");
            String basePath = System.getProperty("user.dir");
            String[] possiblePaths = {
                basePath + "/src/main/resources/Icone/",
                basePath + "/resources/Icone/",
                basePath + "/Icone/"
            };
            
            for (String path : possiblePaths) {
                java.io.File eyeFile = new java.io.File(path + "eye.png");
                java.io.File eyeHideFile = new java.io.File(path + "eye_hide.png");
                
                System.out.println("Verificando caminho: " + eyeFile.getAbsolutePath() + " (Existe: " + eyeFile.exists() + ")");
                
                if (eyeFile.exists() && eyeHideFile.exists()) {
                    eye = new ImageIcon(eyeFile.getAbsolutePath()).getImage();
                    eye_hide = new ImageIcon(eyeHideFile.getAbsolutePath()).getImage();
                    System.out.println("Ícones carregados com sucesso do diretório: " + path);
                    return;
                }
            }
            
            System.err.println("Arquivos de ícone não encontrados em nenhum dos caminhos esperados.");
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showing(boolean action) {
        if (animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        animator.setStartFraction(1f - location);
        show = action;
        location = 1f - location;
        animator.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Primeiro, desenha o componente normalmente
        super.paintComponent(g);
        
        // Agora desenha os elementos adicionais
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        
        int width = getWidth();
        int height = getHeight();
        
        // Desenha a linha inferior
        if (mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor(new Color(255, 255, 255));
            putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$h1.background");
        }
        g2.fillRect(2, height - 1, width - 4, 1);
        
        // Desenha o texto de dica e o estilo da linha
        createHintText(g2);
        createLineStyle(g2);
        
        // Desenha o ícone de mostrar/esconder senha, se necessário
        if (showAndHide) {
            createShowHide(g2);
        }
        
        g2.dispose();
        
        // Força o redesenho se os ícones ainda não foram carregados
        if (showAndHide && (eye == null || eye_hide == null)) {
            System.out.println("Ícones não carregados, tentando novamente...");
            loadIcons();
            repaint();
        }
    }
    
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        
        int width = getWidth();
        int height = getHeight();
        
        // Desenha a linha inferior
        if (mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor(new Color(255, 255, 255));
            putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$h1.background");
        }
        g2.fillRect(2, height - 1, width - 4, 1);
        
        // Desenha o texto de dica e o estilo da linha
        createHintText(g2);
        createLineStyle(g2);
        
        // Desenha o ícone de mostrar/esconder senha, se necessário
        if (showAndHide) {
            createShowHide(g2);
        }
        
        g2.dispose();
    }


    private void createShowHide(Graphics2D g2) {
        if (eye == null || eye_hide == null) {
            // Se os ícones não foram carregados, tenta carregar novamente
            loadIcons();
            if (eye == null || eye_hide == null) {
                // Se ainda não conseguiu carregar, desenha um retângulo vermelho
                int x = getWidth() - 30;
                int y = (getHeight() - 20) / 2;
                g2.setColor(Color.RED);
                g2.fillRect(x, y, 20, 20);
                return;
            }
        }
        
        // Calcula a posição do ícone
        int x = getWidth() - 30;  // 30 pixels da borda direita
        int y = (getHeight() - 20) / 2;  // Centralizado verticalmente
        
        // Inverte a lógica dos ícones
        // Agora, quando hide = true, mostra o ícone de olho fechado (com risco)
        // e quando hide = false, mostra o ícone de olho aberto
        Image icon = hide ? eye_hide : eye;
        if (icon != null) {
            // Desenha apenas o ícone, sem fundo
            g2.drawImage(icon, x, y, 20, 20, null);
        }
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(255, 255, 255));
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double size;
        if (animateHinText) {
            if (show) {
                size = 18 * (1 - location);
            } else {
                size = 18 * location;
            }
        } else {
            size = 18;
        }
        g2.drawString(labelText, in.left, (int) (in.top + textY + ft.getAscent() - size));
    }

    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            double size;
            if (show) {
                size = width * (1 - location);
            } else {
                size = width * location;
            }
            double x = (width - size) / 2;
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }
    }

    @Override
    public void setText(String string) {
        if (!String.valueOf(getPassword()).equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }
}
