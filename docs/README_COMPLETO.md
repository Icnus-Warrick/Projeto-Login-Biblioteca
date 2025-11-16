# 📚 Biblioteca

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

Sistema de gerenciamento de biblioteca com interface moderna, desenvolvido em Java com Swing e FlatLaf. Oferece uma experiência de usuário fluida com animações suaves e design responsivo.

## 🚀 Recursos Principais

- 🔐 Autenticação segura de usuários
- 📚 Gerenciamento completo de acervo de livros
- 🔄 Controle de empréstimos e devoluções
- 🎨 Interface moderna com suporte a temas claro/escuro
- ⚡ Animações fluidas e interativas
- 💾 Banco de dados SQLite integrado
- 📊 Relatórios em PDF

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 17+
- **Interface Gráfica**: Java Swing, FlatLaf
- **Banco de Dados**: SQLite
- **Gerenciamento de Dependências**: Maven
- **Bibliotecas Principais**:
  - PDFBox: Geração e manipulação de PDFs
  - Trident: Para animações suaves
  - TimingFramework: Animações baseadas em tempo
  - FlatLaf: Temas modernos para Swing

## 📋 Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- Conexão com a internet (para baixar dependências)
- SQLite (já incluído nas dependências)

## 🚀 Instalação e Execução

1. **Clone o repositório**:
   ```bash
   git clone [URL_DO_REPOSITORIO]
   cd biblioteca
   ```

2. **Compile o projeto**:
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**:
   ```bash
   mvn exec:java -Dexec.mainClass="br.com.warrick.biblioteca.app.Main"
   ```

   Ou use o wrapper do Maven:
   ```bash
   # Linux/Mac
   ./mvnw exec:java -Dexec.mainClass="br.com.warrick.biblioteca.app.Main"
   
   # Windows
   mvnw.cmd exec:java -Dexec.mainClass="br.com.warrick.biblioteca.app.Main"
   ```

## 📚 Documentação

A documentação completa do projeto está disponível na pasta `docs/`:

- [Guia de Internacionalização (i18n)](./I18N_GUIDE.md) - Documentação detalhada do sistema de múltiplos idiomas
- [Recursos do Projeto](./RESOURCES.md) - Estrutura de diretórios e uso de recursos
- [Resumo Técnico](./RESUMO_I18N.md) - Visão geral e histórico de implementação

## 🌐 Internacionalização

O sistema suporta múltiplos idiomas com troca em tempo real:
- Português do Brasil (padrão)
- Inglês (EUA)

Para adicionar um novo idioma, consulte o [guia de internacionalização](./I18N_GUIDE.md).

## 🏗️ Estrutura do Código Fonte

```
src/main/java/br/com/warrick/biblioteca/
├── Configuracao/           # Configurações da aplicação
│   ├── ConfiguracaoCapa.java
│   └── ConfiguracaoIdioma.java
│
├── controller/             # Controladores (MVC)
│   ├── LivroController.java
│   └── UsuarioController.java
│
├── peripherals/            # Componentes de UI personalizados
│   ├── CheckBoxCustom.java
│   ├── ComboBox.java
│   ├── FormattedTextField.java
│   ├── ModernScrollBarUI.java
│   ├── PasswordField.java
│   ├── PasswordFieldLogin.java
│   ├── ScrollBarCustom.java
│   ├── Tabbed.java
│   ├── TextArea.java
│   ├── TextAreaScroll.java
│   ├── TextField.java
│   ├── TextFieldLogin.java
│   └── WButton.java
│
├── persistence/            # Camada de persistência
│   ├── config/
│   │   └── ConnectionFactory.java
│   │
│   ├── dao/                # Data Access Objects
│   │   ├── LivroDAO.java
│   │   ├── LivroDAOImpl.java
│   │   ├── UsuarioDAO.java
│   │   └── UsuarioDAOImpl.java
│   │
│   ├── exception/          # Exceções de persistência
│   │   └── DAOException.java
│   │
│   └── model/              # Modelos de domínio (entidades)
│       ├── Categoria.java
│       ├── EstiloLivro.java
│       ├── Livro.java
│       └── Usuario.java
│
├── service/                # Lógica de negócios
│   ├── LivroService.java
│   └── UsuarioService.java
│
├── uihelper/               # Auxiliares de UI
│   └── DialogHelper.java
│
├── uimanager/              # Gerenciadores de UI
│   └── ThemeManager.java
│
├── util/                   # Utilitários diversos
│   ├── AnimacaoUtils.java  # Utilitários para animações
│   ├── EstiloLivroManager.java # Gerenciador de estilos de livros
│   ├── HeaderMonitor.java  # Monitoramento de cabeçalhos
│   ├── I18nExample.java    # Exemplo de internacionalização
│   ├── I18nManager.java    # Gerenciador de internacionalização
│   ├── ImageLoader.java    # Carregador de imagens
│   ├── LanguageChangeListener.java # Listener para mudança de idioma
│   ├── LanguageSwitcher.java # Alternador de idiomas
│   └── StringUtils.java    # Utilitários para manipulação de strings
│
└── view/                   # Interfaces gráficas
    │
    ├── BibliotecaApp.form  # Arquivo de formulário do IntelliJ
    │
    ├── BibliotecaApp.java  # Classe principal da aplicação
    │
    ├── app/                # Configuração da aplicação
    │   └── Main.java       # Ponto de entrada
    │
    ├── components/         # Componentes de UI reutilizáveis
    │   ├── cards/          # Componentes de card
    │   ├── dialogs/        # Diálogos personalizados
    │   ├── forms/          # Formulários reutilizáveis
    │   └── tables/         # Tabelas personalizadas
    │
    ├── layouts/            # Layouts da aplicação
    │
    ├── login/              # Telas de login
    │   ├── LoginApp.java
    │   ├── LoginFrente.form
    │   ├── LoginFrente.java
    │   ├── LoginPortas.form
    │   ├── LoginPortas.java
    │   ├── LoginRecupera.form
    │   ├── LoginRecupera.java
    │   ├── LoginTras.form
    │   └── LoginTras.java
    │
    └── screens/            # Telas principais da aplicação
        ├── dashboard/      # Painel principal
        ├── livros/         # Gerenciamento de livros
        └── usuarios/       # Gerenciamento de usuários
```

## 🎨 Interface do Usuário

A aplicação utiliza o FlatLaf para fornecer uma interface moderna e responsiva, com suporte a temas claro e escuro. Os componentes de interface foram personalizados para melhor usabilidade e experiência do usuário.

### Temas Suportados
- **FlatLightLaf**: Tema claro padrão
- **FlatDarkLaf**: Tema escuro
- **FlatMacDarkLaf**: Tema escuro estilo macOS

### Componentes Personalizados
- Botões com efeito hover
- Campos de texto estilizados
- Barras de rolagem modernas
- Diálogos personalizados
- Painéis com bordas arredondadas

## 🤝 Contribuição

Contribuições são bem-vindas! Siga estes passos:

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)
3. Adicione suas mudanças (`git add .`)

## 📞 Suporte

Para suporte, por favor abra uma issue no repositório ou entre em contato com a equipe de desenvolvimento.

## 🙏 Agradecimentos e Créditos

Este projeto utiliza componentes de código aberto e personalizados. Por favor, consulte o arquivo [CREDITS.md](CREDITS.md) para uma lista completa de créditos e atribuições de terceiros.

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 📚 Documentação

A documentação detalhada do projeto está organizada na pasta `docs/`:

- [Documentação de Internacionalização (i18n)](docs/i18n_index.md) - Guia completo sobre o sistema de múltiplos idiomas
- [Guia do Desenvolvedor](docs/dev_guide.md) - Instruções para configurar e contribuir com o projeto
- [Arquitetura](docs/architecture.md) - Visão geral da arquitetura do sistema

## 🧩 Tecnologias e Ferramentas

- [FlatLaf](https://www.formdev.com/flatlaf/) - Look and Feel moderno para Java Swing
- [Maven](https://maven.apache.org/) - Gerenciamento de dependências
- [SQLite](https://www.sqlite.org/) - Banco de dados leve e eficiente

## 👨‍💻 Autor

**Warrick** - Desenvolvedor principal

---

Desenvolvido com ❤️ por Warrick
