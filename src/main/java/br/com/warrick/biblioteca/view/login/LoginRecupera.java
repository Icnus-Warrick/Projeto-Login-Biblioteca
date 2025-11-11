package br.com.warrick.biblioteca.view.login;

import java.awt.Color;

/**
 * Painel de recuperação de senha da aplicação
 * Gerencia o processo de recuperação de senha do usuário
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginRecupera extends javax.swing.JPanel {


    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public LoginRecupera() {
        initComponents();
        setPreferredSize(new java.awt.Dimension(1035, 720));
        setSize(1035, 720);
        setBackground(new Color(0,0,0,0));
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    /**
     * Este método é chamado dentro do construtor para inicializar o formulário.
     * AVISO: NÃO modifique este código. O conteúdo deste método é sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTituloInf = new javax.swing.JLabel();
        lblTituloInf1 = new javax.swing.JLabel();
        lblInf1 = new javax.swing.JLabel();
        lblInf2 = new javax.swing.JLabel();
        lblInf3 = new javax.swing.JLabel();
        lblInf4 = new javax.swing.JLabel();
        lblInf5 = new javax.swing.JLabel();
        lblInf6 = new javax.swing.JLabel();
        lblInf7 = new javax.swing.JLabel();
        lblInf8 = new javax.swing.JLabel();
        lblInf9 = new javax.swing.JLabel();
        lblTituloRecuperacao = new javax.swing.JLabel();
        PainelRec = new javax.swing.JPanel();
        txtSenha1 = new br.com.warrick.biblioteca.peripherals.PasswordFieldLogin();
        lblInfSenha1 = new javax.swing.JLabel();
        txtSenha2 = new br.com.warrick.biblioteca.peripherals.PasswordFieldLogin();
        lblInfSenha2 = new javax.swing.JLabel();
        cmdConfirmar = new br.com.warrick.biblioteca.peripherals.WButton();
        cmdCancelar = new br.com.warrick.biblioteca.peripherals.WButton();
        PainelCod = new javax.swing.JPanel();
        txtCod1 = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        txtCod2 = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        txtCod3 = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        txtCod4 = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFundo = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloInf.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloInf.setText("Perdeu sua senha? Sem Problema!");
        add(lblTituloInf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 320, 30));

        lblTituloInf1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloInf1.setText("Podemos de Ajudar, é Rapidinho!");
        add(lblTituloInf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 320, 30));

        lblInf1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf1.setText("Digite seu e-mail de cadastro.");
        add(lblInf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 440, 30));
        lblInf1.getAccessibleContext().setAccessibleName("Primeiro, Digite seu e-mail de cadastro.");

        lblInf2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf2.setText("Você receberá o Código de recuperação no seu e-mail.");
        add(lblInf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 440, -1));

        lblInf3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf3.setText("Copie o código de recuperação!");
        add(lblInf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 440, -1));

        lblInf4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf4.setText("Adicione o Código na página ao Lado.");
        add(lblInf4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 440, -1));

        lblInf5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf5.setText("Depois, é só criar uma nova Senha!");
        add(lblInf5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 440, -1));

        lblInf6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf6.setText("Depois, é só criar uma nova Senha!");
        add(lblInf6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 440, -1));

        lblInf7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf7.setText("Depois, é só criar uma nova Senha!");
        add(lblInf7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 440, -1));

        lblInf8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf8.setText("Depois, é só criar uma nova Senha!");
        add(lblInf8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 440, -1));

        lblInf9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInf9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInf9.setText("Depois, é só criar uma nova Senha!");
        add(lblInf9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 440, -1));

        lblTituloRecuperacao.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTituloRecuperacao.setForeground(new java.awt.Color(115, 88, 7));
        lblTituloRecuperacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloRecuperacao.setText("Recupere sua Senha!");
        add(lblTituloRecuperacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 320, -1));

        PainelRec.setBackground(new java.awt.Color(255, 255, 255));
        PainelRec.setOpaque(false);
        PainelRec.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSenha1.setLabelText("DIGITE A NOVA SENHA");
        PainelRec.add(txtSenha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 369, -1));

        txtSenha2.setLabelText("REPETIR NOVA SENHA");
        PainelRec.add(txtSenha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 369, -1));

        cmdConfirmar.setText("CONFIRMAR");
        cmdConfirmar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        PainelRec.add(cmdConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 178, 39));

        cmdCancelar.setText("CANCELAR");
        cmdCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        PainelRec.add(cmdCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 178, 39));

        // Adiciona o PainelRec ao painel principal com tamanho fixo
        add(PainelRec, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 450, 300));

        PainelCod.setBackground(new java.awt.Color(204, 153, 0));
        PainelCod.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCod1.setLabelText("LAD");
        txtCod1.setName(""); // NOI18N
        PainelCod.add(txtCod1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 40, 30));

        txtCod2.setLabelText("LAD");
        txtCod2.setName(""); // NOI18N
        PainelCod.add(txtCod2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 40, 30));

        txtCod3.setLabelText("LAD");
        txtCod3.setName(""); // NOI18N
        PainelCod.add(txtCod3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 40, 30));

        txtCod4.setLabelText("LAD");
        txtCod4.setName(""); // NOI18N
        PainelCod.add(txtCod4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 40, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-");
        PainelCod.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 70, 10, 15));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("-");
        PainelCod.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 70, 10, 15));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("-");
        PainelCod.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 70, 10, 15));

        add(PainelCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 450, 300));

        lblFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/Aberto.png"))); // NOI18N
        lblFundo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lblFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1035, 720));
        
        // Centralizar o PainelRec e PainelCod verticalmente
        int panelY = (720 - 300) / 2; // Centraliza verticalmente (altura total - altura do painel) / 2
        PainelRec.setBounds(540, panelY, 450, 300);
        PainelCod.setBounds(540, panelY, 450, 300);
    }// </editor-fold>//GEN-END:initComponents


    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelCod;
    private javax.swing.JPanel PainelRec;
    private br.com.warrick.biblioteca.peripherals.WButton cmdCancelar;
    private br.com.warrick.biblioteca.peripherals.WButton cmdConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblFundo;
    private javax.swing.JLabel lblInf1;
    private javax.swing.JLabel lblInf2;
    private javax.swing.JLabel lblInf3;
    private javax.swing.JLabel lblInf4;
    private javax.swing.JLabel lblInf5;
    private javax.swing.JLabel lblInf6;
    private javax.swing.JLabel lblInf7;
    private javax.swing.JLabel lblInf8;
    private javax.swing.JLabel lblInf9;
    private javax.swing.JLabel lblInfSenha1;
    private javax.swing.JLabel lblInfSenha2;
    private javax.swing.JLabel lblTituloInf;
    private javax.swing.JLabel lblTituloInf1;
    private javax.swing.JLabel lblTituloRecuperacao;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtCod1;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtCod2;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtCod3;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtCod4;
    private br.com.warrick.biblioteca.peripherals.PasswordFieldLogin txtSenha1;
    private br.com.warrick.biblioteca.peripherals.PasswordFieldLogin txtSenha2;
    // End of variables declaration//GEN-END:variables
}
