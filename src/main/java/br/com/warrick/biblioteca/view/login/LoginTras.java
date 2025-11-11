package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;

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

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public LoginTras() {
        this(null);
    }

    /* ========================================= CONSTRUTOR COM PARÂMETRO ========================================== */
    public LoginTras(LoginApp parentApp) {
        this.parentApp = parentApp;
        initComponents();
        setOpaque(false);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setupListeners();
        setupValidation();
        atualizarTexto();
        
        // Adicionar listener para mudanças de idioma
        I18nManager.getInstance().addLanguageChangeListener((oldLocale, newLocale) -> {
            atualizarTexto();
        });
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
        br.com.warrick.biblioteca.model.Usuario usuarioExistente = controller.buscarUsuarioPorUsername(usuario);

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
    // <editor-fold defaultstate="collapsed" desc="Código Gerado">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTituloR = new javax.swing.JLabel();
        txtNome = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        txtUsuarioR = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        lblConf = new javax.swing.JLabel();
        lblInfR1 = new javax.swing.JLabel();
        txtEmail = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        txtSenhaR = new br.com.warrick.biblioteca.peripherals.PasswordFieldLogin();
        txtSenhaRC = new br.com.warrick.biblioteca.peripherals.PasswordFieldLogin();
        cmdLogin = new br.com.warrick.biblioteca.peripherals.WButton();
        cmdSair = new br.com.warrick.biblioteca.peripherals.WButton();
        lblInfR2 = new javax.swing.JLabel();
        lblInfR3 = new javax.swing.JLabel();
        lblFundoT = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(528, 704));
        setPreferredSize(new java.awt.Dimension(528, 704));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloR.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTituloR.setForeground(new java.awt.Color(204, 153, 0));
        lblTituloR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloR.setText("REGISTRE-SE");
        add(lblTituloR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 410, 25));

        txtNome.setForeground(new java.awt.Color(255, 204, 0));
        txtNome.setCaretColor(new java.awt.Color(255, 204, 51));
        txtNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNome.setLabelText("NOME");
        txtNome.setMinimumSize(new java.awt.Dimension(64, 46));
        add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 140, 340, 50));

        txtUsuarioR.setForeground(new java.awt.Color(255, 204, 0));
        txtUsuarioR.setCaretColor(new java.awt.Color(255, 204, 51));
        txtUsuarioR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsuarioR.setLabelText("USUÁRIO");
        txtUsuarioR.setMinimumSize(new java.awt.Dimension(64, 46));
        add(txtUsuarioR, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 220, 340, 50));

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
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setLabelText("E-MAIL");
        txtEmail.setMinimumSize(new java.awt.Dimension(64, 46));
        add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 300, 340, 50));

        txtSenhaR.setForeground(new java.awt.Color(255, 204, 0));
        txtSenhaR.setCaretColor(new java.awt.Color(255, 255, 0));
        txtSenhaR.setDisabledTextColor(new java.awt.Color(255, 204, 0));
        txtSenhaR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenhaR.setLabelText("SENHA");
        add(txtSenhaR, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 380, 340, 50));

        txtSenhaRC.setForeground(new java.awt.Color(255, 204, 0));
        txtSenhaRC.setCaretColor(new java.awt.Color(255, 255, 0));
        txtSenhaRC.setDisabledTextColor(new java.awt.Color(255, 204, 0));
        txtSenhaRC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenhaRC.setLabelText("CONFIRMA SENHA");
        add(txtSenhaRC, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 460, 340, 50));

        cmdLogin.setForeground(new java.awt.Color(51, 102, 255));
        cmdLogin.setText("REGISTRAR");
        cmdLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        add(cmdLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 550, 165, 50));

        cmdSair.setForeground(new java.awt.Color(153, 51, 0));
        cmdSair.setText("SAIR");
        cmdSair.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        add(cmdSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 165, 50));

        lblInfR2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInfR2.setForeground(new java.awt.Color(0, 204, 51));
        lblInfR2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfR2.setText("Usuário Registrado!");
        add(lblInfR2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 515, 340, 25));

        lblInfR3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblInfR3.setForeground(new java.awt.Color(255, 255, 255));
        lblInfR3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfR3.setText("Já tem conta? Faça login!");
        add(lblInfR3, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 610, 240, 25));

        lblFundoT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/LivroLoginT.png"))); // NOI18N
        add(lblFundoT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.warrick.biblioteca.peripherals.WButton cmdLogin;
    private br.com.warrick.biblioteca.peripherals.WButton cmdSair;
    private javax.swing.JLabel lblConf;
    private javax.swing.JLabel lblFundoT;
    private javax.swing.JLabel lblInfR1;
    private javax.swing.JLabel lblInfR2;
    private javax.swing.JLabel lblInfR3;
    private javax.swing.JLabel lblTituloR;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtEmail;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtNome;
    private br.com.warrick.biblioteca.peripherals.PasswordFieldLogin txtSenhaR;
    private br.com.warrick.biblioteca.peripherals.PasswordFieldLogin txtSenhaRC;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtUsuarioR;
    // End of variables declaration//GEN-END:variables
}
