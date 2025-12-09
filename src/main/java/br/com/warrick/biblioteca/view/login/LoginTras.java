package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;
import java.awt.Color;

/**
 * Painel de registro de usuário da aplicação
 * Gerencia o cadastro de novos usuários e validação de dados
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginTras extends javax.swing.JPanel {

    /* ============================================== VARIÁVEIS DE INSTÂNCIA =========================================== */
    private LoginApp parentApp;

    public LoginTras() {
        initComponents();
        setOpaque(false);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setupListeners();
        configComponentes();
        atualizarTexto();
    }
    
    /* ================================================ CONFIGURAÇÕES =============================================== */
    private void configComponentes(){
        cmdLogin.setBackground(new Color(0,0,0,0));
        cmdSair.setBackground(new Color(0,0,0,0));
    }    

    /* ========================================= CONSTRUTOR COM PARÂMETRO ========================================== */
    public void setParentApp(LoginApp parentApp) {
        this.parentApp = parentApp;

        // Adicionar listener para mudanças de idioma
        I18nManager.getInstance().addLanguageChangeListener((oldLocale, newLocale) -> {
            atualizarTexto();
        });
    }

    /**
     * Limpa todos os campos do formulário de registro e redefine as mensagens
     */
    public void resetForm() {
        // Limpar campos de texto
        txtNome.setText("");
        txtUsuarioR.setText("");
        txtEmail.setText("");
        txtSenhaR.setText("");
        txtSenhaRC.setText("");

        // Redefinir mensagens e estados
        if (lblConf != null) lblConf.setVisible(false);
        if (lblInfR1 != null) {
            lblInfR1.setText("");
            lblInfR1.setVisible(false);
        }
        if (lblInfR2 != null) {
            lblInfR2.setText("");
            lblInfR2.setVisible(false);
        }

        // Voltar o foco para o primeiro campo
        txtNome.requestFocusInWindow();
    }

    /* ========================================= CONFIGURAÇÃO DOS LISTENERS ========================================= */
    private void setupListeners() {
        // Adicionar listeners após initComponents para não interferir no código gerado
        cmdLogin.addActionListener(e -> {
            registrarUsuario();
        });

        cmdSair.addActionListener(e -> System.exit(0));

        // Listener para verificar usuário em tempo real
        txtUsuarioR.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                verificarUsuarioExistente();
            }
        });

        // Link para voltar ao login
        lblInfR3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentApp != null) {
                    parentApp.mostrarLogin();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInfR3.setForeground(java.awt.Color.CYAN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblInfR3.setForeground(java.awt.Color.WHITE);
            }
        });
    }

    /* ========================================= CONFIGURAÇÃO DAS VALIDAÇÕES ======================================== */
    private void setupValidation() {
        // Inicialmente esconder mensagens de validação
        lblConf.setVisible(false);
        lblInfR1.setVisible(false);
        lblInfR2.setVisible(false);

        // Configurar fonte e cor padrão para as mensagens
        lblInfR1.setFont(new java.awt.Font("Segoe UI", 0, 12));
        lblInfR2.setFont(new java.awt.Font("Segoe UI", 0, 14));
    }

    /* ========================================= REGISTRO DE USUÁRIO =============================================== */
    private void registrarUsuario() {
        String nome = txtNome.getText().trim();
        String usuario = txtUsuarioR.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenhaR.getPassword());
        String senhaConfirmacao = new String(txtSenhaRC.getPassword());

        // Validações
        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
            mostrarMensagem(I18nManager.msg("register.error.empty"), java.awt.Color.RED);
            return;
        }

        if (!senha.equals(senhaConfirmacao)) {
            mostrarMensagem(I18nManager.msg("register.error.password_mismatch"), java.awt.Color.RED);
            return;
        }

        if (!email.isEmpty() && (!email.contains("@") || !email.contains("."))) {
            mostrarMensagem(I18nManager.msg("register.error.invalid_email"), java.awt.Color.RED);
            return;
        }

        // Usar controller para registrar
        br.com.warrick.biblioteca.controller.UsuarioController controller = new br.com.warrick.biblioteca.controller.UsuarioController();
        if (controller.cadastrarUsuario(nome, usuario, senha, email, "")) { // estilo preferido vazio por enquanto
            mostrarMensagem(I18nManager.msg("register.success"), new java.awt.Color(0, 150, 0));
            limparCampos();
        } else {
            mostrarMensagem(I18nManager.msg("register.error.generic"), java.awt.Color.RED);
        }
    }

    /* ========================================= VERIFICAÇÃO DE USUÁRIO ============================================ */
    private void verificarUsuarioExistente() {
        String usuario = txtUsuarioR.getText().trim();
        if (usuario.isEmpty()) {
            lblConf.setVisible(false);
            lblInfR1.setVisible(false);
            return;
        }

        br.com.warrick.biblioteca.controller.UsuarioController controller = new br.com.warrick.biblioteca.controller.UsuarioController();
        br.com.warrick.biblioteca.persistence.model.Usuario usuarioExistente = controller.buscarUsuarioPorUsername(usuario);

        if (usuarioExistente != null) {
            lblConf.setText("X");
            lblConf.setForeground(java.awt.Color.RED);
            lblConf.setVisible(true);
            lblInfR1.setText(I18nManager.msg("register.unavailable"));
            lblInfR1.setForeground(java.awt.Color.RED);
            lblInfR1.setVisible(true);
        } else {
            lblConf.setText("V");
            lblConf.setForeground(new java.awt.Color(0, 150, 0));
            lblConf.setVisible(true);
            lblInfR1.setText(I18nManager.msg("register.available"));
            lblInfR1.setForeground(new java.awt.Color(0, 150, 0));
            lblInfR1.setVisible(true);
        }
    }

    /* ========================================= MÉTODOS AUXILIARES ================================================ */
    private void mostrarMensagem(String mensagem, java.awt.Color cor) {
        lblInfR2.setText(mensagem);
        lblInfR2.setForeground(cor);
        lblInfR2.setVisible(true);
    }

    // Limpa todos os campos do formulário
    private void limparCampos() {
        txtNome.setText("");
        txtUsuarioR.setText("");
        txtEmail.setText("");
        txtSenhaR.setText("");
        txtSenhaRC.setText("");
        lblConf.setVisible(false);
        lblInfR1.setVisible(false);
        lblInfR2.setVisible(false);
    }

    /* ========================================= ATUALIZAÇÃO DE TEXTO ============================================== */
    private void atualizarTexto() {
        // Atualizar título
        lblTituloR.setText(I18nManager.msg("register.title"));

        // Atualizar campos de texto
        txtNome.setLabelText(I18nManager.msg("register.name"));
        txtUsuarioR.setLabelText(I18nManager.msg("register.user"));
        txtEmail.setLabelText(I18nManager.msg("register.email"));
        txtSenhaR.setLabelText(I18nManager.msg("register.password"));
        txtSenhaRC.setLabelText(I18nManager.msg("register.confirm_password"));

        // Atualizar botões
        cmdLogin.setText(I18nManager.msg("register.button"));
        cmdSair.setText(I18nManager.msg("login.exit"));

        // Atualizar link de voltar
        lblInfR3.setText(I18nManager.msg("register.back"));

        // Forçar redesenho
        revalidate();
        repaint();
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    /**
     * Este método é chamado dentro do construtor para inicializar o formulário.
     * AVISO: NÃO modifique este código. O conteúdo deste método é sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTituloR = new br.com.warrick.biblioteca.swing.WLabel();
        txtNome = new br.com.warrick.biblioteca.swing.WTextField();
        txtUsuarioR = new br.com.warrick.biblioteca.swing.WTextField();
        lblConf = new javax.swing.JLabel();
        lblInfR1 = new javax.swing.JLabel();
        txtEmail = new br.com.warrick.biblioteca.swing.WTextField();
        txtSenhaR = new br.com.warrick.biblioteca.swing.WPasswordField();
        txtSenhaRC = new br.com.warrick.biblioteca.swing.WPasswordField();
        cmdLogin = new br.com.warrick.biblioteca.swing.WButton();
        cmdSair = new br.com.warrick.biblioteca.swing.WButton();
        lblInfR2 = new javax.swing.JLabel();
        lblInfR3 = new br.com.warrick.biblioteca.swing.WLabel();
        lblFundoT = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(528, 704));
        setPreferredSize(new java.awt.Dimension(528, 704));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloR.setForeground(new java.awt.Color(204, 153, 0));
        lblTituloR.setHorizontalAlignment(0);
        lblTituloR.setText("REGISTRE-SE");
        lblTituloR.setToolTipText("");
        lblTituloR.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTituloR.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTituloR.setLineColor(new java.awt.Color(204, 153, 0));
        add(lblTituloR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 45, 410, 50));

        txtNome.setForeground(new java.awt.Color(255, 204, 0));
        txtNome.setCaretColor(new java.awt.Color(255, 204, 51));
        txtNome.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNome.setHoverColor(new java.awt.Color(218, 225, 27));
        txtNome.setLabelText("NOME");
        txtNome.setLineColor(new java.awt.Color(218, 165, 4));
        txtNome.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 130, 340, -1));

        txtUsuarioR.setForeground(new java.awt.Color(255, 204, 0));
        txtUsuarioR.setCaretColor(new java.awt.Color(255, 204, 51));
        txtUsuarioR.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtUsuarioR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsuarioR.setHoverColor(new java.awt.Color(218, 225, 27));
        txtUsuarioR.setLabelText("USUÁRIO");
        txtUsuarioR.setLineColor(new java.awt.Color(218, 165, 4));
        txtUsuarioR.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtUsuarioR, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 220, 340, -1));

        lblConf.setForeground(new java.awt.Color(51, 204, 0));
        lblConf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConf.setText("V");
        lblConf.setToolTipText("");
        add(lblConf, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 40, 50));

        lblInfR1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInfR1.setForeground(new java.awt.Color(0, 204, 51));
        lblInfR1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfR1.setText("Nome de Usuário Já Registrado!");
        add(lblInfR1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 270, 340, 25));

        txtEmail.setForeground(new java.awt.Color(255, 204, 0));
        txtEmail.setCaretColor(new java.awt.Color(255, 204, 51));
        txtEmail.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setHoverColor(new java.awt.Color(218, 225, 27));
        txtEmail.setLabelText("E-MAIL");
        txtEmail.setLineColor(new java.awt.Color(218, 165, 4));
        txtEmail.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 300, 340, -1));

        txtSenhaR.setForeground(new java.awt.Color(255, 204, 0));
        txtSenhaR.setCaretColor(new java.awt.Color(255, 255, 255));
        txtSenhaR.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtSenhaR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenhaR.setHoverColor(new java.awt.Color(255, 204, 0));
        txtSenhaR.setLabelText("SENHA");
        txtSenhaR.setLineColor(new java.awt.Color(218, 165, 4));
        txtSenhaR.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtSenhaR, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 380, 340, -1));

        txtSenhaRC.setForeground(new java.awt.Color(255, 204, 0));
        txtSenhaRC.setCaretColor(new java.awt.Color(255, 255, 255));
        txtSenhaRC.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtSenhaRC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenhaRC.setHoverColor(new java.awt.Color(255, 204, 0));
        txtSenhaRC.setLabelText("CONFIRMA SENHA");
        txtSenhaRC.setLineColor(new java.awt.Color(218, 165, 4));
        txtSenhaRC.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtSenhaRC, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 460, 340, -1));

        cmdLogin.setForeground(new java.awt.Color(51, 102, 255));
        cmdLogin.setText("REGISTRAR");
        cmdLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cmdLogin.setLineColor(new java.awt.Color(0, 120, 212));
        cmdLogin.setPressedTextColor(new java.awt.Color(0, 120, 212));
        add(cmdLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 550, 165, -1));

        cmdSair.setForeground(new java.awt.Color(153, 51, 0));
        cmdSair.setText("SAIR");
        cmdSair.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cmdSair.setHoverColor(new java.awt.Color(213, 0, 0));
        cmdSair.setLineColor(new java.awt.Color(192, 0, 0));
        cmdSair.setPressedTextColor(new java.awt.Color(169, 0, 0));
        add(cmdSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 165, -1));

        lblInfR2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInfR2.setForeground(new java.awt.Color(0, 204, 51));
        lblInfR2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfR2.setText("Usuário Registrado!");
        add(lblInfR2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 515, 340, 25));

        lblInfR3.setForeground(new java.awt.Color(255, 255, 255));
        lblInfR3.setHorizontalAlignment(0);
        lblInfR3.setText("Já tem conta? Faça login!");
        lblInfR3.setToolTipText("");
        lblInfR3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblInfR3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblInfR3.setLineColor(new java.awt.Color(204, 153, 0));
        lblInfR3.setLineSpacing(0);
        add(lblInfR3, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 610, 240, 50));

        lblFundoT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/LivroLoginT.png"))); // NOI18N
        add(lblFundoT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.warrick.biblioteca.swing.WButton cmdLogin;
    private br.com.warrick.biblioteca.swing.WButton cmdSair;
    private javax.swing.JLabel lblConf;
    private javax.swing.JLabel lblFundoT;
    private javax.swing.JLabel lblInfR1;
    private javax.swing.JLabel lblInfR2;
    private br.com.warrick.biblioteca.swing.WLabel lblInfR3;
    private br.com.warrick.biblioteca.swing.WLabel lblTituloR;
    private br.com.warrick.biblioteca.swing.WTextField txtEmail;
    private br.com.warrick.biblioteca.swing.WTextField txtNome;
    private br.com.warrick.biblioteca.swing.WPasswordField txtSenhaR;
    private br.com.warrick.biblioteca.swing.WPasswordField txtSenhaRC;
    private br.com.warrick.biblioteca.swing.WTextField txtUsuarioR;
    // End of variables declaration//GEN-END:variables
}
