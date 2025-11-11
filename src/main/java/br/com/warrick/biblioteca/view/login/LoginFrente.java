package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;
import br.com.warrick.biblioteca.peripherals.*;
import br.com.warrick.biblioteca.view.BibliotecaApp;
import javax.swing.SwingUtilities;

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
        if (lblInfo3 != null) {
            lblInfo3.setText("");
            lblInfo3.setVisible(false);
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
                lblInfo3.setText(I18nManager.msg("login.success"));
                lblInfo3.setForeground(new java.awt.Color(0, 150, 0)); // Verde mais escuro para melhor contraste

                // Iniciar a sequência de login com delays e animação
                if (parentApp != null) {
                    parentApp.iniciarSequenciaLogin();
                }
            } else {
                lblInfo3.setText(I18nManager.msg("login.error"));
                lblInfo3.setForeground(java.awt.Color.RED);
            }
        });

        // Configurar ação do botão sair: encerrar a aplicação
        cmdSair.addActionListener(e -> System.exit(0));
        
        // Configurar listener para o label de recuperação de senha: navegar para tela de recuperação
        lblInfo2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentApp != null) {
                    parentApp.mostrarRecuperacao();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInfo2.setForeground(java.awt.Color.CYAN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblInfo2.setForeground(java.awt.Color.WHITE);
            }
        });
        
        // Configurar listener para o label de registro: navegar para tela de registro
        lblInfo4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentApp != null) {
                    parentApp.mostrarRegistro();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInfo4.setForeground(java.awt.Color.CYAN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblInfo4.setForeground(java.awt.Color.WHITE);
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
        lblInfo2.setText(I18nManager.msg("login.forgot"));
        lblInfo4.setText(I18nManager.msg("login.no_account"));
        
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
    // <editor-fold defaultstate="collapsed" desc="Código Gerado">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo1 = new javax.swing.JLabel();
        lblTitulo2 = new javax.swing.JLabel();
        lblTitulo3 = new javax.swing.JLabel();
        txtUsuario = new br.com.warrick.biblioteca.peripherals.TextFieldLogin();
        txtSenha = new br.com.warrick.biblioteca.peripherals.PasswordFieldLogin();
        lblInfo1 = new javax.swing.JLabel();
        lblInfo2 = new javax.swing.JLabel();
        lblInfo3 = new javax.swing.JLabel();
        cmdLogin = new br.com.warrick.biblioteca.peripherals.WButton();
        cmdSair = new br.com.warrick.biblioteca.peripherals.WButton();
        lblInfo4 = new javax.swing.JLabel();
        lblTitulo4 = new javax.swing.JLabel();
        lblTitulo5 = new javax.swing.JLabel();
        cbbIdioma = new br.com.warrick.biblioteca.peripherals.ComboBox();
        lblFundo = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblTitulo1.setForeground(new java.awt.Color(204, 153, 0));
        lblTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo1.setText("LOGIN");
        add(lblTitulo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 70, 407, 60));

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
        txtUsuario.setCaretColor(new java.awt.Color(255, 204, 51));
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsuario.setLabelText("USUÁRIO");
        txtUsuario.setMinimumSize(new java.awt.Dimension(64, 46));
        add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 360, 50));

        txtSenha.setForeground(new java.awt.Color(255, 204, 0));
        txtSenha.setCaretColor(new java.awt.Color(255, 255, 0));
        txtSenha.setDisabledTextColor(new java.awt.Color(255, 204, 0));
        txtSenha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSenha.setLabelText("SENHA");
        add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 360, 50));

        lblInfo1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblInfo1.setForeground(new java.awt.Color(153, 0, 0));
        lblInfo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo1.setToolTipText("");
        add(lblInfo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 375, 200, 25));

        lblInfo2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblInfo2.setForeground(new java.awt.Color(255, 255, 255));
        lblInfo2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInfo2.setText("Esqueci minha senha...");
        lblInfo2.setToolTipText("");
        add(lblInfo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 375, 200, 25));

        lblInfo3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblInfo3.setForeground(new java.awt.Color(153, 0, 0));
        lblInfo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo3.setToolTipText("");
        add(lblInfo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 415, 407, 30));

        cmdLogin.setForeground(new java.awt.Color(204, 153, 0));
        cmdLogin.setText("LOGIN");
        cmdLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        add(cmdLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 170, 50));

        cmdSair.setForeground(new java.awt.Color(153, 51, 0));
        cmdSair.setText("SAIR");
        cmdSair.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        add(cmdSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 170, 50));

        lblInfo4.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblInfo4.setForeground(new java.awt.Color(255, 255, 255));
        lblInfo4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo4.setText("Não tem conta? Registre-se!");
        lblInfo4.setToolTipText("");
        add(lblInfo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 510, 250, 30));

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
        cbbIdioma.setName(""); // NOI18N
        cbbIdioma.setOpaque(false);
        cbbIdioma.setRequestFocusEnabled(false);
        add(cbbIdioma, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 610, 140, 40));

        lblFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/LivroLogin.png"))); // NOI18N
        add(lblFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.warrick.biblioteca.peripherals.ComboBox cbbIdioma;
    private br.com.warrick.biblioteca.peripherals.WButton cmdLogin;
    private br.com.warrick.biblioteca.peripherals.WButton cmdSair;
    private javax.swing.JLabel lblFundo;
    private javax.swing.JLabel lblInfo1;
    private javax.swing.JLabel lblInfo2;
    private javax.swing.JLabel lblInfo3;
    private javax.swing.JLabel lblInfo4;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblTitulo2;
    private javax.swing.JLabel lblTitulo3;
    private javax.swing.JLabel lblTitulo4;
    private javax.swing.JLabel lblTitulo5;
    private br.com.warrick.biblioteca.peripherals.PasswordFieldLogin txtSenha;
    private br.com.warrick.biblioteca.peripherals.TextFieldLogin txtUsuario;
    // End of variables declaration//GEN-END:variables
}
