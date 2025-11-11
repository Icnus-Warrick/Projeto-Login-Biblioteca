package br.com.warrick.biblioteca.view.components.card;

import javax.swing.*;
import java.awt.*;

/**
 * Componente de card genérico que pode ser personalizado com diferentes conteúdos.
 * Pode ser usado diretamente ou estendido para criar cards mais específicos.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public class GenericCard extends BaseCard {
    
    private JLabel titleLabel;
    private JLabel contentLabel;
    private JPanel contentPanel;
    
    public GenericCard() {
        super();
        initComponents();
    }
    
    public GenericCard(String title, String content) {
        this();
        setTitle(title);
        setContent(content);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        
        // Painel para o conteúdo principal
        contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.setOpaque(false);
        
        // Título do card
        titleLabel = new JLabel();
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        
        // Conteúdo do card
        contentLabel = new JLabel();
        contentLabel.setFont(contentLabel.getFont().deriveFont(12f));
        
        // Adiciona os componentes ao painel de conteúdo
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(contentLabel, BorderLayout.CENTER);
        
        // Adiciona o painel de conteúdo ao card
        add(contentPanel, BorderLayout.CENTER);
        
        // Configura o hover
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setHovered(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setHovered(false);
            }
        });
    }
    
    // Getters e Setters
    public String getTitle() {
        return titleLabel.getText();
    }
    
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public String getContent() {
        return contentLabel.getText();
    }
    
    public void setContent(String content) {
        contentLabel.setText(content);
    }
    
    public void setContentComponent(Component component) {
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        float alpha = enabled ? 1.0f : 0.5f;
        setAlpha(this, alpha);
    }
    
    private void setAlpha(Component component, float alpha) {
        if (component instanceof JComponent) {
            ((JComponent) component).setOpaque(alpha == 1.0f);
        }
        component.setEnabled(alpha == 1.0f);
        
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setAlpha(child, alpha);
            }
        }
    }
}
