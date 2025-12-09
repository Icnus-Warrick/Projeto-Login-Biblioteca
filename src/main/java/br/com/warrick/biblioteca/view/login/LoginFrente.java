package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;

/**
 * Painel de login da aplicação
 * Gerencia a autenticação do usuário e navegação para registro
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class LoginFrente extends javax.swing.JPanel {

    /* ============================================== VARIÁVEIS DE INSTÂNCIA =========================================== */
    private LoginApp parentApp;

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public LoginFrente() {
        initComponents();
        setOpaque(false);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        setupListeners();
        atualizarTexto();
    }

    /* ========================================= CONSTRUTOR COM PARÂMETRO ========================================== */
    public void setParentApp(LoginApp parentApp) {
        this.parentApp = parentApp;
    }
    
    /**
     * Limpa todos os campos do formulário de login e redefine as mensagens
     */
    public void resetForm() {
        // Limpar campos de texto
        txtUsuario.setText("");
        txtSenha.setText("");
        
        // Limpar mensagens de erro/sucesso
        if (lblInfo1 != null) {
            lblInfo1.setText("");
            lblInfo1.setVisible(false);
        }
        
        // Voltar o foco para o campo de usuário
        txtUsuario.requestFocusInWindow();
    }

    /* ========================================= CONFIGURAÇÃO DOS LISTENERS ========================================= */
    private void setupListeners() {
        // Configurar listeners após initComponents para evitar interferência no código gerado automaticamente
        // Configurar ação do botão de login: validar credenciais e iniciar aplicação
        // No método setupListeners(), substitua o código do cmdLogin.addActionListener:

        cmdLogin.addActionListener(e -> {
            // Obter o nome de usuário inserido, removendo espaços em branco
            String usuario = txtUsuario.getText().trim();
            // Obter a senha inserida como string
            String senha = new String(txtSenha.getPassword());

            // Usar controller para login
            br.com.warrick.biblioteca.controller.UsuarioController controller
                    = new br.com.warrick.biblioteca.controller.UsuarioController();

            if (controller.fazerLogin(usuario, senha)) {
                lblInfo1.setText(I18nManager.msg("login.success"));
                lblInfo1.setForeground(new java.awt.Color(0, 150, 0)); // Verde mais escuro para melhor contraste

                // Iniciar a sequência de login com delays e animação
                if (parentApp != null) {
                    parentApp.iniciarSequenciaLogin();
                }
            } else {
                lblInfo1.setText(I18nManager.msg("login.error"));
                lblInfo1.setForeground(java.awt.Color.RED);
            }
        });

        // Configurar ação do botão sair: encerrar a aplicação
        cmdSair.addActionListener(e -> System.exit(0));
        
        // Configurar listener para o label de recuperação de senha: navegar para tela de recuperação
        lblcmdOut1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentApp != null) {
                    parentApp.mostrarRecuperacao();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblcmdOut1.setForeground(java.awt.Color.CYAN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblcmdOut1.setForeground(java.awt.Color.WHITE);
            }
        });
        
        // Configurar listener para o label de registro: navegar para tela de registro
        lblcmdOut2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentApp != null) {
                    parentApp.mostrarRegistro();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblcmdOut2.setForeground(java.awt.Color.CYAN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblcmdOut2.setForeground(java.awt.Color.WHITE);
            }
        });
        
        // Configurar listener para o combobox de idioma
        cbbIdioma.addActionListener(e -> {
           int index = cbbIdioma.getSelectedIndex();
           if(index==0){
               I18nManager.getInstance().setLocale(I18nManager.LOCALE_PT_BR);               
           }else{
               I18nManager.getInstance().setLocale(I18nManager.LOCALE_EN_US);
           }           
        });
        
        I18nManager.getInstance().addLanguageChangeListener((oldLocale,newLocale) ->{
            atualizarTexto();
        });
    }
    
    /* ========================================= ATUALIZAÇÃO DE TEXTO ============================================== */
    private void atualizarTexto() {
        // Atualizar labels de título
        lblTitulo1.setText(I18nManager.msg("login.title"));
        lblTitulo2.setText(I18nManager.msg("login.quote1"));
        lblTitulo3.setText(I18nManager.msg("login.quote2"));
        lblTitulo4.setText(I18nManager.msg("login.quote3"));
        lblTitulo5.setText(I18nManager.msg("login.quote4"));
        
        // Atualizar campos de texto
        txtUsuario.setLabelText(I18nManager.msg("login.username").toUpperCase());
        txtSenha.setLabelText(I18nManager.msg("login.password").toUpperCase());
        
        // Atualizar botões
        cmdLogin.setText(I18nManager.msg("login.button"));
        cmdSair.setText(I18nManager.msg("login.exit"));
        
        // Atualizar labels de informação
        lblcmdOut1.setText(I18nManager.msg("login.forgot"));
        lblcmdOut2.setText(I18nManager.msg("login.no_account"));
        
        // Atualizar ComboBox de idioma
        cbbIdioma.setLabelText(I18nManager.msg("settings.language"));
        
        // Atualizar itens do ComboBox mantendo a seleção atual
        int selectedIndex = cbbIdioma.getSelectedIndex();
        cbbIdioma.removeAllItems();
        cbbIdioma.addItem("Português (BR)");
        cbbIdioma.addItem("English (US)");
        cbbIdioma.setSelectedIndex(selectedIndex);
        
        revalidate();
        repaint();
    }
    
    /* ========================================= CONFIGURAÇÃO DE IDIOMA ============================================ */
    private void setupIdioma() {
        // Popular ComboBox com os mesmos valores usados em atualizarTexto()
        cbbIdioma.addItem("Português (BR)");
        cbbIdioma.addItem("English (US)");
        
        // Seleciona idioma atual
        if(I18nManager.getInstance().isPortuguese()){
            cbbIdioma.setSelectedIndex(0);
        }else{
            cbbIdioma.setSelectedIndex(1);
        }
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    /**
     * Este método é chamado dentro do construtor para inicializar o formulário.
     * AVISO: NÃO modifique este código. O conteúdo deste método é sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo1 = new br.com.warrick.biblioteca.swing.WLabel();
        lblTitulo2 = new javax.swing.JLabel();
        lblTitulo3 = new javax.swing.JLabel();
        txtUsuario = new br.com.warrick.biblioteca.swing.WTextField();
        txtSenha = new br.com.warrick.biblioteca.swing.WPasswordField();
        ccbLembrar = new br.com.warrick.biblioteca.swing.WCheckBox();
        lblcmdOut1 = new br.com.warrick.biblioteca.swing.WLabel();
        lblInfo1 = new javax.swing.JLabel();
        cmdLogin = new br.com.warrick.biblioteca.swing.WButton();
        cmdSair = new br.com.warrick.biblioteca.swing.WButton();
        lblcmdOut2 = new br.com.warrick.biblioteca.swing.WLabel();
        lblTitulo4 = new javax.swing.JLabel();
        lblTitulo5 = new javax.swing.JLabel();
        cbbIdioma = new br.com.warrick.biblioteca.swing.WComboBox();
        lblFundo = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo1.setForeground(new java.awt.Color(204, 153, 0));
        lblTitulo1.setHorizontalAlignment(0);
        lblTitulo1.setText("LOGIN");
        lblTitulo1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblTitulo1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblTitulo1.setLineColor(new java.awt.Color(204, 153, 0));
        lblTitulo1.setLineSpacing(0);
        add(lblTitulo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 40, 407, 80));

        lblTitulo2.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lblTitulo2.setForeground(new java.awt.Color(204, 153, 0));
        lblTitulo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo2.setText("Um bom livro não se abre sozinho…");
        lblTitulo2.setToolTipText("");
        add(lblTitulo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 135, 407, 20));

        lblTitulo3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo3.setForeground(new java.awt.Color(221, 171, 20));
        lblTitulo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo3.setText("Mas com um clique, ele ganha vida.");
        lblTitulo3.setToolTipText("");
        add(lblTitulo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 160, 407, 30));

        txtUsuario.setForeground(new java.awt.Color(255, 204, 0));
        txtUsuario.setHoverColor(new java.awt.Color(218, 189, 32));
        txtUsuario.setLabelText("USUÁRIO");
        txtUsuario.setLineColor(new java.awt.Color(218, 165, 4));
        txtUsuario.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 360, 50));

        txtSenha.setCaretColor(new java.awt.Color(255, 255, 0));
        txtSenha.setDisabledTextColor(new java.awt.Color(255, 204, 0));
        txtSenha.setLabelText("SENHA");
        txtSenha.setLineColor(new java.awt.Color(218, 165, 4));
        txtSenha.setSelectionColor(new java.awt.Color(171, 122, 24));
        add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 360, 50));

        ccbLembrar.setText("Lembrar da Senha.");
        add(ccbLembrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 170, -1));

        lblcmdOut1.setForeground(new java.awt.Color(255, 255, 255));
        lblcmdOut1.setHorizontalAlignment(11);
        lblcmdOut1.setText("Esqueci minha senha...");
        lblcmdOut1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblcmdOut1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        add(lblcmdOut1, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 378, 175, 30));

        lblInfo1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblInfo1.setForeground(new java.awt.Color(153, 0, 0));
        lblInfo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo1.setToolTipText("");
        add(lblInfo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 415, 407, 30));

        cmdLogin.setForeground(new java.awt.Color(204, 153, 0));
        cmdLogin.setText("LOGIN");
        cmdLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        add(cmdLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 170, 50));

        cmdSair.setForeground(new java.awt.Color(153, 0, 0));
        cmdSair.setText("SAIR");
        cmdSair.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        add(cmdSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 170, 50));

        lblcmdOut2.setForeground(new java.awt.Color(255, 255, 255));
        lblcmdOut2.setHorizontalAlignment(0);
        lblcmdOut2.setText("Não tem conta? Registre-se!");
        lblcmdOut2.setVerticalAlignment(1);
        lblcmdOut2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        add(lblcmdOut2, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 515, 250, 30));

        lblTitulo4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo4.setForeground(new java.awt.Color(255, 204, 0));
        lblTitulo4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo4.setText("Uma biblioteca não é só de livros…");
        lblTitulo4.setToolTipText("");
        add(lblTitulo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 550, 407, 40));

        lblTitulo5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo5.setForeground(new java.awt.Color(255, 204, 0));
        lblTitulo5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo5.setText("é de histórias que se conectam.");
        lblTitulo5.setToolTipText("");
        add(lblTitulo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 580, 407, 40));

        cbbIdioma.setForeground(new java.awt.Color(255, 204, 0));
        cbbIdioma.setLabelText("IDIOMA");
        cbbIdioma.setLightWeightPopupEnabled(false);
        cbbIdioma.setLineColor(new java.awt.Color(218, 165, 4));
        cbbIdioma.setRequestFocusEnabled(false);
        add(cbbIdioma, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 610, 200, -1));

        lblFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/LivroLogin.png"))); // NOI18N
        add(lblFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.warrick.biblioteca.swing.WComboBox cbbIdioma;
    private br.com.warrick.biblioteca.swing.WCheckBox ccbLembrar;
    private br.com.warrick.biblioteca.swing.WButton cmdLogin;
    private br.com.warrick.biblioteca.swing.WButton cmdSair;
    private javax.swing.JLabel lblFundo;
    private javax.swing.JLabel lblInfo1;
    private br.com.warrick.biblioteca.swing.WLabel lblTitulo1;
    private javax.swing.JLabel lblTitulo2;
    private javax.swing.JLabel lblTitulo3;
    private javax.swing.JLabel lblTitulo4;
    private javax.swing.JLabel lblTitulo5;
    private br.com.warrick.biblioteca.swing.WLabel lblcmdOut1;
    private br.com.warrick.biblioteca.swing.WLabel lblcmdOut2;
    private br.com.warrick.biblioteca.swing.WPasswordField txtSenha;
    private br.com.warrick.biblioteca.swing.WTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
