package br.com.warrick.biblioteca.view.components.card;

import javax.swing.*;
import java.awt.*;

/**
 * Componente de card para exibição de livros bíblicos.
 * Exibe informações como sigla, nome do livro e cânon.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public class LivroBiblicoCard extends BaseCard {
    
    private JLabel siglaLabel;
    private JLabel nomeLabel;
    private JLabel canonLabel;
    
    public LivroBiblicoCard() {
        super();
        initComponents();
        setupListeners();
    }
    
    public LivroBiblicoCard(String sigla, String nome, String canon) {
        this();
        setSigla(sigla);
        setNome(nome);
        setCanon(canon);
    }
    
    private void initComponents() {
        // Configurações de tamanho
        setMinimumSize(new Dimension(100, 65));
        setPreferredSize(new Dimension(100, 65));
        setLayout(new OverlayLayout(this));
        
        // Painel para o conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Sigla (em destaque)
        siglaLabel = new JLabel("", JLabel.CENTER);
        siglaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        siglaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Nome do cânon (em itálico, menor)
        canonLabel = new JLabel("", JLabel.CENTER);
        canonLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        canonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Nome do livro
        nomeLabel = new JLabel("", JLabel.CENTER);
        nomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adiciona os componentes ao painel de conteúdo
        contentPanel.add(Box.createVerticalStrut(5)); // Espaçamento superior
        contentPanel.add(siglaLabel);
        contentPanel.add(canonLabel);
        contentPanel.add(nomeLabel);
        contentPanel.add(Box.createVerticalStrut(5)); // Espaçamento inferior
        
        // Adiciona o painel de conteúdo ao card
        add(contentPanel);
    }
    
    private void setupListeners() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firePropertyChange("cardClicked", false, true);
            }
            
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
    public String getSigla() {
        return siglaLabel.getText();
    }
    
    public void setSigla(String sigla) {
        siglaLabel.setText(sigla);
    }
    
    public String getNome() {
        return nomeLabel.getText();
    }
    
    public void setNome(String nome) {
        nomeLabel.setText(nome);
    }
    
    public String getCanon() {
        return canonLabel.getText();
    }
    
    public void setCanon(String canon) {
        canonLabel.setText(canon);
    }
    
    /**
     * Adiciona um listener para o evento de clique no card
     * @param listener ActionListener que será notificado quando o card for clicado
     */
    public void addCardClickListener(java.awt.event.ActionListener listener) {
        addPropertyChangeListener("cardClicked", evt -> {
            if (evt.getNewValue().equals(true)) {
                listener.actionPerformed(
                    new java.awt.event.ActionEvent(this, 
                        java.awt.event.ActionEvent.ACTION_PERFORMED, "cardClicked")
                );
            }
        });
    }
}
