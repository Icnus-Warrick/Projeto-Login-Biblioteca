package br.com.warrick.biblioteca.view.screens.livro;

import br.com.warrick.biblioteca.view.components.card.GenericCard;
import br.com.warrick.biblioteca.view.components.card.LivroBiblicoCard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Tela de exemplo que demonstra o uso dos componentes de card.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public class ListaLivrosScreen extends JPanel {
    
    public ListaLivrosScreen() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Título da tela
        JLabel tituloLabel = new JLabel("Livros Bíblicos", JLabel.CENTER);
        tituloLabel.setFont(tituloLabel.getFont().deriveFont(Font.BOLD, 24f));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Painel para os cards
        JPanel cardsPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        // Adiciona alguns cards de exemplo
        adicionarCardGenerico(cardsPanel, "Card Genérico 1", "Este é um card genérico de exemplo.");
        adicionarCardGenerico(cardsPanel, "Card Genérico 2", "Outro exemplo de card genérico.");
        adicionarLivroBiblico(cardsPanel, "GN", "Gênesis", "Pentateuco");
        adicionarLivroBiblico(cardsPanel, "SL", "Salmos", "Poéticos");
        adicionarLivroBiblico(cardsPanel, "MT", "Mateus", "Evangelhos");
        adicionarLivroBiblico(cardsPanel, "AP", "Apocalipse", "Profético");
        
        // Adiciona os componentes ao painel principal
        add(tituloLabel, BorderLayout.NORTH);
        add(new JScrollPane(cardsPanel), BorderLayout.CENTER);
    }
    
    private void adicionarCardGenerico(JPanel panel, String titulo, String conteudo) {
        GenericCard card = new GenericCard(titulo, conteudo);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(
                    ListaLivrosScreen.this,
                    "Card clicado: " + titulo,
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        panel.add(card);
    }
    
    private void adicionarLivroBiblico(JPanel panel, String sigla, String nome, String canon) {
        LivroBiblicoCard card = new LivroBiblicoCard(sigla, nome, canon);
        card.addCardClickListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Livro selecionado: " + nome + " (" + sigla + ")\nCânon: " + canon,
                "Livro Bíblico",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        panel.add(card);
    }
    
    /**
     * Método para testar a tela de forma isolada
     */
    public static void main(String[] args) {
        try {
            // Define o look and feel do sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        JFrame frame = new JFrame("Exemplo de Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        
        ListaLivrosScreen screen = new ListaLivrosScreen();
        frame.add(screen);
        
        frame.setVisible(true);
    }
}
