package br.com.warrick.biblioteca.peripherals;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * Componente personalizado de ComboBox com suporte a animações e estilização avançada.
 * Permite personalização de cores, animações e aparência do dropdown.
 * 
 * @param <E> Tipo dos itens que serão exibidos no ComboBox
 * @author Ra Ven - Criador original dos componentes personalizados (YouTube/GitHub)
 * @author Warrick
 * @since 02/11/2025
 */

public class ComboBox<E> extends JComboBox<E> {
    // Texto do rótulo que será exibido acima do ComboBox
    private String labelText = "Label";
    // Cor da linha inferior do ComboBox
    private Color lineColor = new Color(3, 155, 216);
    // Indica se o mouse está sobre o componente
    private boolean mouseOver;
    // Deslocamento horizontal do label
    private int labelXOffset = 5;
    // Indica se o label deve ser exibido
    private boolean showLabel = true;

    /**
     * Define o texto do label exibido acima do ComboBox
     * @param labelText Texto do label
     */
    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    /**
     * Retorna o texto atual do label
     * @return Texto do label
     */
    public String getLabelText() {
        return labelText;
    }
    
    /**
     * Define o deslocamento horizontal do label
     * @param offset Deslocamento em pixels (padrão: 30)
     */
    public void setLabelXOffset(int offset) {
        this.labelXOffset = offset;
        repaint();
    }
    
    /**
     * Define se o label deve ser exibido
     * @param show true para exibir, false para ocultar
     */
    public void setShowLabel(boolean show) {
        this.showLabel = show;
        repaint();
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

   
    /**
     * Atualiza a interface do usuário do componente.
     * Chamado quando o look and feel é alterado.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        installUI();
        lineColor = UIManager.getColor("Component.focusColor");
    }

    /**
     * Instala a interface do usuário personalizada para o ComboBox.
     * Configura o renderizador de células e o estilo visual.
     */
    private void installUI() {
        // Define a UI personalizada
        setUI(new ComboUI(this));
        
        // Configura o renderizador de células personalizado
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                // Obtém o componente padrão do renderizador
                Component com = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                // Define as bordas do item da lista
                setBorder(new EmptyBorder(5, 0, 5, 5));
                
                // Configura as cores com base no estado de seleção
                if (isSelected) {
                    // Quando selecionado na lista suspensa - Tom ocre/amarelo amarronzado
                    com.setBackground(new Color(205, 133, 63)); // Cor ocre
                    com.setForeground(Color.BLACK); // Texto preto para melhor contraste
                } else {
                    // Quando não selecionado
                    com.setBackground(new Color(255, 253, 250)); // Fundo branco levemente amarelado
                    com.setForeground(new Color(50, 50, 50)); // Texto cinza escuro
                }
                
                // Se for o item atualmente selecionado no ComboBox (não na lista suspensa)
                if (index == -1 && value != null) {
                    // Fundo transparente quando o ComboBox está fechado
                    com.setBackground(new Color(0, 0, 0, 0));
                    // Mantém a cor do texto padrão ou usa preto para melhor visibilidade
                    com.setForeground(getForeground() != null ? getForeground() : Color.BLACK);
                }
                
                return com;
            }
        });
    }

    /**
     * Construtor padrão do ComboBox personalizado.
     * Inicializa o componente com configurações padrão.
     */
    public ComboBox() {
        // Define o fundo como transparente por padrão
        setBackground(new Color(0, 0, 0, 0));
        // Define a cor do texto como preto por padrão
        setForeground(Color.BLACK);
        // Ajusta as bordas para posicionamento correto dos elementos
        setBorder(new EmptyBorder(15, 1, -5, 0));
        // Torna o componente opaco para permitir transparência
        setOpaque(false);
        // Instala a UI personalizada
        installUI();
    }

    /**
     * Classe interna que implementa a interface do usuário personalizada para o ComboBox.
     * Responsável por desenhar o componente e gerenciar seu comportamento visual.
     */
    private class ComboUI extends BasicComboBoxUI {
        // Controlador de animação para efeitos visuais
        private final Animator animator;
        // Controla se deve animar o texto de dica
        private boolean animateHinText = true;
        // Posição atual da animação
        private float location;
        // Indica se deve mostrar a animação
        private boolean show;
        // Referência para o ComboBox pai
        private ComboBox combo;

        

        public ComboUI(ComboBox combo) {
            this.combo = combo;
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
            addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    if (!isFocusOwner()) {
                        if (getSelectedIndex() == -1) {
                            showing(true);
                        } else {
                            showing(false);
                        }
                    }
                }
            });
            addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(new Color(0,204,204));
//                        arrowButton.putClientProperty(FlatClientProperties.STYLE, ""
//                                        + "background:$ComboBox.buttonPressedArrowColor");
                    }
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(new Color(0,102,0));
//                        arrowButton.putClientProperty(FlatClientProperties.STYLE, ""
//                                        + "background:$ComboBox.buttonArrowColor");
                    }
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent pme) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(new Color(102,0,102));
//                        arrowButton.putClientProperty(FlatClientProperties.STYLE, ""
//                                        + "background:$ComboBox.buttonHoverArrowColor");
                    }
                }
            });
            TimingTarget target = new TimingTargetAdapter() {
                @Override
                public void begin() {
                    animateHinText = getSelectedIndex() == -1;
                }

                @Override
                public void timingEvent(float fraction) {
                    location = fraction;
                    repaint();
                }

            };
            animator = new Animator(300, target);
            animator.setResolution(0);
            animator.setAcceleration(0.5f);
            animator.setDeceleration(0.5f);
        }

        @Override
        public void paintCurrentValueBackground(Graphics grphcs, Rectangle rctngl, boolean bln) {

        }

        /**
         * Cria e retorna o botão da seta do ComboBox.
         * @return JButton personalizado que atua como a seta do ComboBox
         */
        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }

        /**
         * Cria o popup que será exibido quando o ComboBox for clicado.
         * @return Uma instância de ComboPopup personalizada
         */
        @Override
        protected ComboPopup createPopup() {
            // Cria um popup personalizado
            BasicComboPopup pop = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    // Define a altura fixa para cada item da lista
                    list.setFixedCellHeight(30);
                    // Cria o painel de rolagem
                    JScrollPane scroll = new JScrollPane(list);
                    // Define a cor de fundo
                    scroll.setBackground(new Color(193, 156, 7));
                    // Cria uma barra de rolagem personalizada
                    ScrollBarCustom sb = new ScrollBarCustom();
                    sb.setUnitIncrement(30);
                    sb.setForeground(new Color(153, 102, 0));
                    scroll.setVerticalScrollBar(sb);
                    return scroll;
                }
            };
            // Define a borda do popup
            pop.setBorder(new LineBorder(new Color(102, 102, 102), 1));
            return pop;
        }

        /**
         * Desenha o componente na tela.
         * @param grphcs O contexto gráfico para desenhar
         * @param jc O componente a ser desenhado
         */
        @Override
        public void paint(Graphics grphcs, JComponent jc) {
            super.paint(grphcs, jc);
            Graphics2D g2 = (Graphics2D) grphcs;
            // Melhora a qualidade do desenho
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            
            int width = getWidth();
            int height = getHeight();
            
            // Define a cor com base no estado do mouse
            if (mouseOver) {
                g2.setColor(lineColor);
            } else {
                g2.setColor(new Color(255, 255, 255));
            }
            
            // Desenha a linha inferior
            g2.fillRect(2, height - 1, width - 4, 2);
            
            // Desenha o texto de dica e o estilo da linha
            createHintText(g2);
            createLineStyle(g2);
            
            // Libera recursos gráficos
            g2.dispose();
        }

        /**
         * Desenha o texto de dica (label) do ComboBox.
         * @param g2 O contexto gráfico para desenhar
         */
        private void createHintText(Graphics2D g2) {
            if (!combo.showLabel || combo.getLabelText() == null || combo.getLabelText().isEmpty()) {
                return;  // Não desenha se o label estiver desativado ou vazio
            }
            
            // Obtém as margens internas
            Insets in = getInsets();
            // Define a cor do texto de dica (cinza médio)
            g2.setColor(new Color(150, 150, 150));
            
            // Obtém as métricas da fonte para cálculos de posicionamento
            FontMetrics ft = g2.getFontMetrics();
            // Calcula o retângulo que envolve o texto
            Rectangle2D r2 = ft.getStringBounds(combo.getLabelText(), g2);
            
            // Calcula as dimensões disponíveis
            double height = getHeight() - in.top - in.bottom;
            double width = getWidth();
            // Calcula a posição Y centralizada
            double textY = (height - r2.getHeight()) / 2;
            double size;
            
            // Aplica animação ao texto de dica, se necessário
            if (animateHinText) {
                if (show) {
                    size = 14 * (1 - location);  // Reduzido de 18 para 14
                } else {
                    size = 14 * location;  // Reduzido de 18 para 14
                }
            } else {
                size = 14;  // Reduzido de 18 para 14
            }
            
            // Calcula a posição X do texto (deslocamento personalizado)
            int xPos = in.right + combo.labelXOffset;
            
            // Desenha o texto de dica na posição calculada
            g2.setFont(g2.getFont().deriveFont((float)(18 - size/2)));  // Ajusta o tamanho da fonte
            g2.drawString(combo.getLabelText(), xPos, (int) (in.top + textY + ft.getAscent() - size));
        }

        /**
         * Desenha o estilo da linha inferior quando o ComboBox tem foco.
         * @param g2 O contexto gráfico para desenhar
         */
        private void createLineStyle(Graphics2D g2) {
            // Apenas desenha se o componente tiver foco
            if (isFocusOwner()) {
                double width = getWidth() - 4;
                int height = getHeight();
                g2.setColor(lineColor);
                
                // Calcula o tamanho da linha com base na animação
                double size;
                if (show) {
                    size = width * (1 - location);
                } else {
                    size = width * location;
                }
                
                // Centraliza a linha
                double x = (width - size) / 2;
                // Desenha a linha animada
                g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
            }
        }

        /**
         * Controla a animação de mostrar/esconder o texto de dica.
         * @param action true para mostrar a animação, false para esconder
         */
        private void showing(boolean action) {
            // Para a animação se estiver em andamento
            if (animator.isRunning()) {
                animator.stop();
            } else {
                location = 1;
            }
            
            // Configura a animação
            animator.setStartFraction(1f - location);
            show = action;
            location = 1f - location;
            // Inicia a animação
            animator.start();
        }

        /**
         * Classe interna que representa o botão de seta do ComboBox.
         * Responsável por desenhar a seta personalizada.
         */
        private class ArrowButton extends JButton {
            /**
             * Constrói o botão da seta com configurações iniciais.
             */
            public ArrowButton() {
                // Configurações visuais do botão
                setContentAreaFilled(false);  // Remove o preenchimento padrão
                setBorder(new EmptyBorder(0, 0, 0, 0));  // Define o espaçamento interno
                setBackground(new Color(204, 204, 0));  // Cor de fundo amarela
            }

            /**
             * Desenha o botão da seta.
             * @param grphcs O contexto gráfico para desenhar
             */
            @Override
            public void paint(Graphics grphcs) {
                super.paint(grphcs);
                Graphics2D g2 = (Graphics2D) grphcs;
                // Melhora a qualidade do desenho
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Obtém as dimensões do botão
                int width = getWidth();
                int height = getHeight();
                
                // Define o tamanho da seta
                int size = 10;
                
                // Calcula a posição centralizada da seta
                int x = (width - size) / 4;
                int y = (height - size) / 2 + 4;
                
                // Define os pontos do triângulo (seta para baixo)
                int px[] = {x, x + size, x + size / 2};
                int py[] = {y, y, y + size};
                
                // Desenha a seta
                g2.setColor(getBackground());
                g2.fillPolygon(px, py, px.length);
                
                // Libera recursos gráficos
                g2.dispose();
            }
        }
    }
}
