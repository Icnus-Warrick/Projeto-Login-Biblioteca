import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoAnimacao extends JFrame {
    private JLabel lblPortaE, lblPortaD, lblBatente;
    private JButton btnAbrir, btnFechar;
    private Timer timerE, timerD;
    private int posE = 166, posD = 355, maxMove = 80;

    public DemoAnimacao() {
        setTitle("🎭 Demo Animação LApp1");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.LIGHT_GRAY);

        lblBatente = new JLabel("🏛️ BATENTE FIXO");
        lblBatente.setBounds(0, 0, 705, 60);
        lblBatente.setBackground(new Color(139, 69, 19));
        lblBatente.setOpaque(true);
        lblBatente.setHorizontalAlignment(SwingConstants.CENTER);
        lblBatente.setForeground(Color.WHITE);
        panel.add(lblBatente);

        lblPortaE = new JLabel("🚪 PORTA E");
        lblPortaE.setBounds(posE, 80, 100, 180);
        lblPortaE.setBackground(Color.BLUE);
        lblPortaE.setOpaque(true);
        lblPortaE.setHorizontalAlignment(SwingConstants.CENTER);
        lblPortaE.setForeground(Color.WHITE);
        panel.add(lblPortaE);

        lblPortaD = new JLabel("🚪 PORTA D");
        lblPortaD.setBounds(posD, 80, 100, 180);
        lblPortaD.setBackground(Color.RED);
        lblPortaD.setOpaque(true);
        lblPortaD.setHorizontalAlignment(SwingConstants.CENTER);
        lblPortaD.setForeground(Color.WHITE);
        panel.add(lblPortaD);

        add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnAbrir = new JButton("🔓 ABRIR PORTAS");
        btnFechar = new JButton("🔒 FECHAR PORTAS");

        btnAbrir.setBackground(new Color(46, 204, 113));
        btnFechar.setBackground(new Color(231, 76, 60));
        btnAbrir.setForeground(Color.WHITE);
        btnFechar.setForeground(Color.WHITE);

        btnPanel.add(btnAbrir);
        btnPanel.add(btnFechar);
        add(btnPanel, BorderLayout.SOUTH);

        setupTimers();
        setupActions();

        System.out.println("=== 🎭 DEMO ANIMAÇÃO LAPP1 ===");
        System.out.println("Clique em 'ABRIR PORTAS' para ver a animação!");
    }

    private void setupTimers() {
        timerE = new Timer(30, new ActionListener() {
            int move = 0;
            public void actionPerformed(ActionEvent e) {
                if (move < maxMove) {
                    move += 2;
                    lblPortaE.setLocation(posE - move, lblPortaE.getY());
                } else {
                    timerE.stop();
                }
            }
        });

        timerD = new Timer(30, new ActionListener() {
            int move = 0;
            public void actionPerformed(ActionEvent e) {
                if (move < maxMove) {
                    move += 2;
                    lblPortaD.setLocation(posD + move, lblPortaD.getY());
                } else {
                    timerD.stop();
                }
            }
        });
    }

    private void setupActions() {
        btnAbrir.addActionListener(e -> {
            System.out.println("🎬 Abrindo portas...");
            lblPortaE.setLocation(posE, lblPortaE.getY());
            lblPortaD.setLocation(posD, lblPortaD.getY());
            timerE.start();
            timerD.start();
        });

        btnFechar.addActionListener(e -> {
            System.out.println("🔒 Fechando portas...");
            timerE.stop();
            timerD.stop();
            lblPortaE.setLocation(posE, lblPortaE.getY());
            lblPortaD.setLocation(posD, lblPortaD.getY());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DemoAnimacao().setVisible(true));
    }
}
