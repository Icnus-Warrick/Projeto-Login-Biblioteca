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

## 🏗️ Estrutura do Projeto

```
src/main/java/br/com/warrick/biblioteca/
├── app/            # Ponto de entrada da aplicação
│   ├── Main.java   # Classe principal
│   └── App.java    # Configuração da aplicação
├── controller/     # Controladores (MVC)
│   └── UsuarioController.java
├── dao/            # Acesso a dados
│   └── UsuarioDAO.java
├── model/          # Modelos de domínio
│   └── Usuario.java
├── peripherals/    # Componentes de UI personalizados
│   ├── TextField.java
│   ├── PasswordField.java
│   └── ...
├── service/        # Lógica de negócios
│   └── UsuarioService.java
├── util/           # Utilitários
│   ├── DatabaseManager.java
│   ├── ImageLoader.java
│   └── ...
└── view/           # Interfaces gráficas
    ├── LApp.java
    ├── login/
    └── ...
```

## 🎨 Interface do Usuário

A aplicação utiliza o FlatLaf para fornecer uma interface moderna e responsiva, com suporte a temas claro e escuro. Os componentes de interface foram personalizados para melhor usabilidade e experiência do usuário.

## 🤝 Contribuição

Contribuições são bem-vindas! Siga estes passos:

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)
3. Adicione suas mudanças (`git add .`)
5. Faça o Push para a Branch (`git push origin feature/AmazingFeature`)
6. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para obter mais detalhes.

## 🙏 Agradecimentos e Créditos

Este projeto utiliza componentes de código aberto e personalizados. Por favor, consulte o arquivo [CREDITS.md](CREDITS.md) para uma lista completa de créditos e atribuições de terceiros.

## 🧩 Tecnologias e Ferramentas

- [FlatLaf](https://www.formdev.com/flatlaf/) - Look and Feel moderno para Java Swing
- [Maven](https://maven.apache.org/) - Gerenciamento de dependências
- [SQLite](https://www.sqlite.org/) - Banco de dados leve e eficiente

## 👨‍💻 Autor

**Warrick** - Desenvolvedor principal

---

Desenvolvido com ❤️ por Warrick
