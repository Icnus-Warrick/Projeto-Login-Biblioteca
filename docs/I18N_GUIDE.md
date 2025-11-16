# 📚 Guia de Internacionalização (i18n)

## 🌍 Visão Geral

Documentação completa do sistema de internacionalização (i18n) da aplicação Biblioteca, que permite suporte a múltiplos idiomas de forma simples e eficiente.

## 📋 Sumário

- [📦 Estrutura de Arquivos](#-estrutura-de-arquivos)
- [🚀 Começando Rápido](#-começando-rápido)
- [🔧 Uso Avançado](#-uso-avançado)
- [🌐 Adicionando Novos Idiomas](#-adicionando-novos-idiomas)
- [💡 Boas Práticas](#-boas-práticas)
- [🔍 Referência de Código](#-referência-de-código)
- [❓ Perguntas Frequentes](#-perguntas-frequentes)

## 📦 Estrutura de Arquivos

```
src/main/resources/messages/
├── messages.properties          # Arquivo base (inglês)
├── messages_pt_BR.properties    # Português do Brasil
└── messages_en_US.properties    # Inglês dos EUA
```

## 🚀 Começando Rápido

### 1. Importar o Gerenciador

```java
import br.com.warrick.biblioteca.util.I18nManager;
```

### 2. Obter Mensagens

```java
// Método estático (recomendado)
String titulo = I18nManager.msg("app.title");

// Com parâmetros
String bemVindo = I18nManager.msg("user.welcome", "João");
// Resultado: "Bem-vindo, João!" ou "Welcome, João!"
```

### 3. Trocar Idioma

```java
// Para português
I18nManager.getInstance().setLocale(new Locale("pt", "BR"));

// Para inglês
I18nManager.getInstance().setLocale(Locale.US);
```

## 🔧 Uso Avançado

### Formatação de Números e Datas

```java
// Formatar número
String numero = I18nManager.formatNumber(1000.5);
// Resultado: "1.000,5" (pt_BR) ou "1,000.5" (en_US)

// Formatar data
Date hoje = new Date();
String data = I18nManager.formatDate(hoje);
```

### Tratamento de Exceções

```java
try {
    // Código que pode lançar exceção
} catch (Exception e) {
    String mensagemErro = I18nManager.msg("error.generic", e.getMessage());
    JOptionPane.showMessageDialog(null, mensagemErro);
}
```

## 🌐 Adicionando Novos Idiomas

1. Crie um novo arquivo `messages_XX_YY.properties`
2. Adicione as traduções seguindo o padrão:
   ```properties
   app.title=Meu Aplicativo
   login.button=Entrar
   ```
3. Atualize a classe `I18nManager` para incluir o novo idioma
4. Teste a mudança de idioma na aplicação

## 💡 Boas Práticas

- **Nomes de Chaves**: Use notação em inglês, minúsculas e pontos
  - 👍 `user.profile.title`
  - 👎 `TITULO_DO_PERFIL_DO_USUARIO`

- **Organização**: Agrupe por funcionalidade
  ```properties
  # Login
  login.title=Login
  login.button=Entrar
  
  # Menu
  menu.file=Arquivo
  menu.edit=Editar
  ```

- **Comentários**: Documente o uso das chaves
  ```properties
  # Usado no cabeçalho da aplicação
  app.title=Biblioteca
  ```

## 🔍 Referência de Código

### I18nManager

| Método | Descrição |
|--------|-----------|
| `I18nManager.msg("chave")` | Obtém mensagem traduzida |
| `I18nManager.msg("chave", params)` | Mensagem com parâmetros |
| `I18nManager.formatNumber(value)` | Formata número |
| `I18nManager.formatDate(date)` | Formata data |
| `getInstance().setLocale(locale)` | Muda o idioma |

## ❓ Perguntas Frequentes

### Como adicionar suporte a um novo idioma?
Siga o guia [Adicionando Novos Idiomas](#-adicionando-novos-idiomas).

### O que acontece se uma chave não for encontrada?
O sistema retorna a própria chave entre colchetes, ex: `[chave.inexistente]`.

### Posso usar HTML nas mensagens?
Sim, use `I18nManager.msgHtml()` para mensagens que contenham formatação HTML.

### Como testar um novo idioma?
```java
// No método main ou em um botão de teste
I18nManager.getInstance().setLocale(new Locale("es", "ES"));
JOptionPane.showMessageDialog(null, I18nManager.msg("test.message"));
```

## 📚 Recursos Adicionais

- [Documentação Java ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html)
- [Guia de Localização Java](https://docs.oracle.com/javase/tutorial/i18n/)
- [Códigos de Idioma ISO 639-1](https://www.loc.gov/standards/iso639-2/php/code_list.php)
