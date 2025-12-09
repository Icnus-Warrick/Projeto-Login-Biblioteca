# 🔐 Sistema de Login - Biblioteca

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

Sistema de autenticação moderno para bibliotecas, desenvolvido em Java com Swing e FlatLaf. Oferece uma experiência de usuário fluida com animações suaves, validações em tempo real e suporte a múltiplos idiomas.

## 🚀 Recursos Principais

- 🔐 **Autenticação Segura**
  - Validação em tempo real
  - Mensagens de erro específicas por campo
  - Recuperação de senha segura
  - Bloqueio após tentativas falhas

- 🎨 **Interface Moderna**
  - Animações suaves e fluidas
  - Feedback visual imediato
  - Design responsivo e acessível
  - Temas claro/escuro

- 🌐 **Internacionalização**
  - Suporte a múltiplos idiomas
  - Troca em tempo real
  - Mensagens localizadas
  - Fácil adição de novos idiomas

- ⚙️ **Configurações**
  - Personalização de temas
  - Gerenciamento de preferências
  - Configurações salvas localmente

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 17+
- **Interface Gráfica**: Java Swing, FlatLaf
- **Banco de Dados**: SQLite com JDBC
- **Gerenciamento de Dependências**: Maven
- **Bibliotecas Principais**:
  - FlatLaf: Temas modernos para Swing
  - I18n: Sistema de internacionalização
  - SwingX: Componentes avançados de UI
  - JUnit: Testes unitários
  - Log4j2: Sistema de logging

## 🏗️ Estrutura do Projeto

```
src/main/java/br/com/warrick/biblioteca/
├── Configuracao/        # Configurações da aplicação
│   ├── ConfiguracaoCapa.java
│   └── ConfiguracaoIdioma.java
│
├── controller/          # Controladores (MVC)
│   ├── LivroController.java
│   └── UsuarioController.java
│
├── persistence/         # Camada de persistência
│   ├── config/          # Configurações de banco de dados
│   │   └── ConnectionFactory.java
│   │
│   ├── dao/             # Data Access Objects
│   │   ├── LivroDAO.java
│   │   ├── LivroDAOImpl.java
│   │   ├── UsuarioDAO.java
│   │   └── UsuarioDAOImpl.java
│   │
│   ├── exception/       # Exceções de persistência
│   │   └── DAOException.java
│   │
│   └── model/           # Modelos de domínio
│       ├── Categoria.java
│       ├── EstiloLivro.java
│       ├── Livro.java
│       └── Usuario.java
│
├── service/             # Lógica de negócios
│   ├── LivroService.java
│   └── UsuarioService.java
│
├── swing/               # Componentes Swing personalizados
│   ├── WButton.java
│   ├── WPasswordField.java
│   └── WTextField.java
│
├── uihelper/            # Auxiliares de interface
│   └── DialogHelper.java
│
├── uimanager/           # Gerenciamento de UI
│   └── ThemeManager.java
│
├── util/                # Utilitários gerais
│   ├── I18nManager.java # Gerenciador de internacionalização
│   ├── ImageLoader.java # Carregador de imagens
│   └── StringUtils.java # Utilitários para strings
│
└── view/                # Telas da aplicação
    └── login/           # Telas de autenticação
        ├── LoginApp.java
        ├── LoginFrente.java
        ├── LoginPortas.java

## 📋 Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- Git (opcional, apenas para desenvolvimento)
- Conexão com a internet (para baixar dependências)

## 🚀 Instalação e Execução

1. **Clone o repositório**:
   ```bash
   git clone [URL_DO_REPOSITÓRIO]
   cd projeto-login-biblioteca
   ```

2. **Compile o projeto**:
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**:
   ```bash
   mvn exec:java -Dexec.mainClass="br.com.warrick.biblioteca.view.login.LoginApp"
   ```

   Ou use o wrapper do Maven:
   ```bash
   # Linux/Mac
   ./mvnw exec:java -Dexec.mainClass="br.com.warrick.biblioteca.view.login.LoginApp"
   
   # Windows
   mvnw.cmd exec:java -Dexec.mainClass="br.com.warrick.biblioteca.view.login.LoginApp"
   ```

## 🌐 Suporte a Múltiplos Idiomas

O sistema suporta os seguintes idiomas:
- Português do Brasil (padrão)
- Inglês (EUA)

### Como adicionar um novo idioma:
1. Crie uma cópia do arquivo `messages.properties` com o sufixo do locale (ex: `messages_es_ES.properties`)
2. Traduza todas as mensagens para o novo idioma
3. Atualize o `I18nManager` para incluir o novo locale

## 🎨 Interface do Usuário

A interface foi projetada para ser intuitiva e agradável, com foco na experiência do usuário:

- **Animações suaves** para transições entre telas
- **Validação em tempo real** dos campos
- **Feedback visual** claro para ações
- **Responsividade** para diferentes telas
- **Temas personalizáveis** (claro/escuro)

## 🤝 Contribuição

Contribuições são bem-vindas! Siga estes passos:

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/NovaFuncionalidade`)
3. Adicione suas mudanças (`git add .`)
4. Comite suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
5. Faça o Push da Branch (`git push origin feature/NovaFuncionalidade`)
6. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## ✨ Créditos

- Desenvolvido por [Seu Nome]
- Ícones por [Fonte dos Ícones]
- Inspirado em [Projeto/Fonte de Inspiração]

## 📞 Suporte

Para suporte, por favor abra uma issue no repositório ou entre em contato com a equipe de desenvolvimento.

## 🙏 Agradecimentos

Agradecimentos a todos os colaboradores e projetos de código aberto que tornaram este projeto possível.

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
