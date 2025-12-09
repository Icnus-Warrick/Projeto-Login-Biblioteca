package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;
import java.awt.Color;

/**
 * Painel de recuperação de senha da aplicação Gerencia o processo de recuperação de senha do usuário
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginRecupera extends javax.swing.JPanel {

    /* =========================================== VARIÁVEIS DE INSTÂNCIA =========================================== */
    private LoginApp parentApp;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JLabel lblInfo;

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public LoginRecupera() {
        initComponents();
        setSize(1035, 720);
        setBackground(new Color(0, 0, 0, 0));
        configComponentes();

        // Inicia sempre mostrando o painel de código
        java.awt.CardLayout cl = (java.awt.CardLayout) PainelCard.getLayout();
        cl.show(PainelCard, "card3");

        // Configura listeners dos botões
        setupListeners();
        revalidate();
        repaint();
    }

    /* ================================================ CONFIGURAÇÕES =============================================== */
    private void configComponentes() {
        PainelCard.setOpaque(false);
        PainelCod.setOpaque(false);
        PainelRec.setOpaque(false);
        cmdConfirmar.setBackground(new Color(0, 0, 0, 0));
        cmdCancelar.setBackground(new Color(0, 0, 0, 0));
    }

    /* ======================================= CONFIGURAÇÃO DE LISTENERS ======================================== */
    private void setupListeners() {
        // Após confirmar o código no PainelCod, troca para o PainelRec no CardLayout
        cmdConfirm.addActionListener(e -> {
            java.awt.CardLayout cl = (java.awt.CardLayout) PainelCard.getLayout();
            cl.show(PainelCard, "card2");
            revalidate();
            repaint();
        });
    }

    /* ========================================= CONFIGURAÇÃO DO PARENT APP ========================================= */
    public void setParentApp(LoginApp parentApp) {
        this.parentApp = parentApp;

        // Adicionar listener para mudanças de idioma
        I18nManager.getInstance().addLanguageChangeListener((oldLocale, newLocale) -> {
            // Atualizar textos se necessário
        });
    }

    /**
     * Limpa todos os campos do formulário de recuperação de senha e redefine as mensagens
     */
    public void resetForm() {
        // Limpar campos de texto
        if (txtEmail != null) {
            txtEmail.setText("");
        }
        if (txtSenha1 != null) {
            txtSenha1.setText("");
        }
        if (txtSenha2 != null) {
            txtSenha2.setText("");
        }
        if (txtCod1 != null) {
            txtCod1.setText("");
        }
        if (txtCod2 != null) {
            txtCod2.setText("");
        }
        if (txtCod3 != null) {
            txtCod3.setText("");
        }
        if (txtCod4 != null) {
            txtCod4.setText("");
        }

        // Limpar mensagens de erro/status
        if (lblInfo != null) {
            lblInfo.setText("");
            lblInfo.setVisible(false);
        }

        // Redefinir visibilidade dos painéis se necessário
        if (PainelRec != null) {
            PainelRec.setVisible(false);
        }

        if (PainelCod != null) {
            PainelCod.setVisible(false);
        }

        // Voltar o foco para o campo de e-mail
        if (txtEmail != null) {
            txtEmail.requestFocusInWindow();
        }
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    /**
     * Este método é chamado dentro do construtor para inicializar o formulário. AVISO: NÃO modifique este código. O
     * conteúdo deste método é sempre regenerado pelo Editor de Formulários.
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
        PainelCard = new javax.swing.JPanel();
        PainelCod = new javax.swing.JPanel();
        txtCod1 = new br.com.warrick.biblioteca.swing.WTextField();
        txtCod2 = new br.com.warrick.biblioteca.swing.WTextField();
        txtCod3 = new br.com.warrick.biblioteca.swing.WTextField();
        txtCod4 = new br.com.warrick.biblioteca.swing.WTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmdConfirm = new br.com.warrick.biblioteca.swing.WButton();
        cmdCancela = new br.com.warrick.biblioteca.swing.WButton();
        PainelRec = new javax.swing.JPanel();
        txtSenha1 = new br.com.warrick.biblioteca.swing.WPasswordField();
        txtSenha2 = new br.com.warrick.biblioteca.swing.WPasswordField();
        cmdCancelar = new br.com.warrick.biblioteca.swing.WButton();
        cmdConfirmar = new br.com.warrick.biblioteca.swing.WButton();
        lblFundo = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloInf.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloInf.setText("Perdeu sua senha? Sem Problema!");
        add(lblTituloInf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 320, -1));

        lblTituloInf1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloInf1.setText("Podemos de Ajudar, é Rapidinho!");
        add(lblTituloInf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 320, -1));

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

        PainelCard.setLayout(new java.awt.CardLayout());

        PainelCod.setBackground(new java.awt.Color(204, 153, 0));
        PainelCod.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCod1.setForeground(new java.awt.Color(255, 255, 255));
        txtCod1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCod1.setDragEnabled(true);
        txtCod1.setHoverColor(new java.awt.Color(218, 165, 4));
        txtCod1.setLabelText("LAD");
        txtCod1.setLineColor(new java.awt.Color(218, 165, 4));
        txtCod1.setSelectionColor(new java.awt.Color(171, 122, 24));
        PainelCod.add(txtCod1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 40, 40));

        txtCod2.setForeground(new java.awt.Color(255, 255, 255));
        txtCod2.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCod2.setDragEnabled(true);
        txtCod2.setHoverColor(new java.awt.Color(218, 165, 4));
        txtCod2.setLabelText("LAD");
        txtCod2.setLineColor(new java.awt.Color(218, 165, 4));
        txtCod2.setSelectionColor(new java.awt.Color(171, 122, 24));
        PainelCod.add(txtCod2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 40, 40));

        txtCod3.setForeground(new java.awt.Color(255, 255, 255));
        txtCod3.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCod3.setDragEnabled(true);
        txtCod3.setHoverColor(new java.awt.Color(218, 165, 4));
        txtCod3.setLabelText("LAD");
        txtCod3.setLineColor(new java.awt.Color(218, 165, 4));
        txtCod3.setSelectionColor(new java.awt.Color(171, 122, 24));
        PainelCod.add(txtCod3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 40, 40));

        txtCod4.setForeground(new java.awt.Color(255, 255, 255));
        txtCod4.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCod4.setDragEnabled(true);
        txtCod4.setHoverColor(new java.awt.Color(218, 165, 4));
        txtCod4.setLabelText("LAD");
        txtCod4.setLineColor(new java.awt.Color(218, 165, 4));
        txtCod4.setSelectionColor(new java.awt.Color(171, 122, 24));
        PainelCod.add(txtCod4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 40, 40));

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

        cmdConfirm.setForeground(new java.awt.Color(0, 102, 255));
        cmdConfirm.setText("CONFIRMAR");
        cmdConfirm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        PainelCod.add(cmdConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 170, 60));

        cmdCancela.setForeground(new java.awt.Color(102, 0, 0));
        cmdCancela.setText("CANCELAR");
        cmdCancela.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cmdCancela.setHoverColor(new java.awt.Color(204, 0, 0));
        cmdCancela.setLineColor(new java.awt.Color(102, 0, 0));
        cmdCancela.setPressedTextColor(new java.awt.Color(102, 0, 0));
        PainelCod.add(cmdCancela, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 170, 60));

        PainelCard.add(PainelCod, "card3");

        PainelRec.setBackground(new java.awt.Color(255, 255, 255));

        txtSenha1.setForeground(new java.awt.Color(218, 165, 4));
        txtSenha1.setCaretColor(new java.awt.Color(255, 255, 255));
        txtSenha1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtSenha1.setHoverColor(new java.awt.Color(218, 202, 26));
        txtSenha1.setLabelText("DIGITE A NOVA SENHA");
        txtSenha1.setLineColor(new java.awt.Color(218, 165, 4));
        txtSenha1.setSelectionColor(new java.awt.Color(171, 122, 24));

        txtSenha2.setForeground(new java.awt.Color(218, 165, 4));
        txtSenha2.setCaretColor(new java.awt.Color(255, 255, 255));
        txtSenha2.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtSenha2.setHoverColor(new java.awt.Color(218, 202, 26));
        txtSenha2.setLabelText("REPETIR NOVA SENHA");
        txtSenha2.setLineColor(new java.awt.Color(218, 165, 4));
        txtSenha2.setSelectionColor(new java.awt.Color(171, 122, 24));
        txtSenha2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenha2ActionPerformed(evt);
            }
        });

        cmdCancelar.setForeground(new java.awt.Color(153, 0, 0));
        cmdCancelar.setText("CANCELAR");
        cmdCancelar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        cmdCancelar.setHoverColor(new java.awt.Color(255, 0, 0));
        cmdCancelar.setLineColor(new java.awt.Color(255, 0, 0));
        cmdCancelar.setPressedTextColor(new java.awt.Color(132, 0, 0));

        cmdConfirmar.setForeground(new java.awt.Color(0, 153, 255));
        cmdConfirmar.setText("CONFIRMAR");
        cmdConfirmar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        cmdConfirmar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout PainelRecLayout = new javax.swing.GroupLayout(PainelRec);
        PainelRec.setLayout(PainelRecLayout);
        PainelRecLayout.setHorizontalGroup(
            PainelRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelRecLayout.createSequentialGroup()
                .addGroup(PainelRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelRecLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(cmdConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(cmdCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelRecLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelRecLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSenha2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        PainelRecLayout.setVerticalGroup(
            PainelRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelRecLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(txtSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(txtSenha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(PainelRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        PainelCard.add(PainelRec, "card2");

        add(PainelCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 410, 350));

        lblFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/warrick/biblioteca/Imagem/Aberto.png"))); // NOI18N
        lblFundo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lblFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1035, 720));
    }// </editor-fold>//GEN-END:initComponents

    private void txtSenha2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenha2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenha2ActionPerformed


    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelCard;
    private javax.swing.JPanel PainelCod;
    private javax.swing.JPanel PainelRec;
    private br.com.warrick.biblioteca.swing.WButton cmdCancela;
    private br.com.warrick.biblioteca.swing.WButton cmdCancelar;
    private br.com.warrick.biblioteca.swing.WButton cmdConfirm;
    private br.com.warrick.biblioteca.swing.WButton cmdConfirmar;
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
    private javax.swing.JLabel lblTituloInf;
    private javax.swing.JLabel lblTituloInf1;
    private javax.swing.JLabel lblTituloRecuperacao;
    private br.com.warrick.biblioteca.swing.WTextField txtCod1;
    private br.com.warrick.biblioteca.swing.WTextField txtCod2;
    private br.com.warrick.biblioteca.swing.WTextField txtCod3;
    private br.com.warrick.biblioteca.swing.WTextField txtCod4;
    private br.com.warrick.biblioteca.swing.WPasswordField txtSenha1;
    private br.com.warrick.biblioteca.swing.WPasswordField txtSenha2;
    // End of variables declaration//GEN-END:variables
}
