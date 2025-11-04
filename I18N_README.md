# Sistema de Internacionalização (i18n)

## 📚 Visão Geral

O sistema de internacionalização permite que a aplicação suporte múltiplos idiomas de forma simples e eficiente.

## 🌍 Idiomas Suportados

- **Português (Brasil)** - `pt_BR` (padrão)
- **English (USA)** - `en_US`

## 📁 Estrutura de Arquivos

```
src/main/resources/
├── messages.properties           # Arquivo padrão (fallback)
├── messages_pt_BR.properties     # Português do Brasil
└── messages_en_US.properties     # Inglês dos EUA
```

## 🚀 Como Usar

### 1. Importar o I18nManager

```java
import br.com.warrick.biblioteca.util.I18nManager;
```

### 2. Obter mensagens traduzidas

#### Método 1: Usando método estático (recomendado)
```java
String titulo = I18nManager.msg("app.title");
String mensagem = I18nManager.msg("login.error.invalid");
```

#### Método 2: Usando instância
```java
I18nManager i18n = I18nManager.getInstance();
String titulo = i18n.getMessage("app.title");
```

#### Método 3: Com parâmetros
```java
String mensagem = I18nManager.msg("user.welcome", "João");
// Resultado: "Bem-vindo, João!" ou "Welcome, João!"
```

### 3. Trocar idioma

```java
I18nManager i18n = I18nManager.getInstance();

// Definir para inglês
i18n.setLocale(I18nManager.LOCALE_EN_US);

// Definir para português
i18n.setLocale(I18nManager.LOCALE_PT_BR);

// Alternar entre idiomas
i18n.toggleLanguage();
```

### 4. Verificar idioma atual

```java
I18nManager i18n = I18nManager.getInstance();

if (i18n.isPortuguese()) {
    // Código específico para português
}

if (i18n.isEnglish()) {
    // Código específico para inglês
}

Locale current = i18n.getCurrentLocale();
```

## 🎨 Componente de Seleção de Idioma

Use o componente `LanguageSwitcher` para adicionar um seletor de idioma à interface:

```java
import br.com.warrick.biblioteca.util.LanguageSwitcher;

// Adicionar ao seu painel
LanguageSwitcher switcher = new LanguageSwitcher();
panel.add(switcher);
```

## ➕ Adicionar Novas Mensagens

1. Abra os arquivos `.properties` em `src/main/resources/`
2. Adicione a chave e valor em todos os idiomas:

**messages_pt_BR.properties:**
```properties
minha.nova.mensagem=Olá, Mundo!
```

**messages_en_US.properties:**
```properties
minha.nova.mensagem=Hello, World!
```

3. Use no código:
```java
String msg = I18nManager.msg("minha.nova.mensagem");
```

## 🔑 Chaves Disponíveis

### Aplicação
- `app.title` - Título da aplicação
- `app.loading.title` - Título da tela de carregamento
- `app.success` - Mensagem de sucesso

### Login
- `login.title` - Título do login
- `login.username` - Campo de usuário
- `login.password` - Campo de senha
- `login.button` - Botão de login
- `login.error.empty` - Erro de campos vazios
- `login.error.invalid` - Erro de credenciais inválidas

### Animação
- `animation.loading` - Carregando
- `animation.opening.doors` - Abrindo portas
- `animation.completed` - Animação concluída

### Erros
- `error.title` - Título de erro
- `error.database` - Erro de banco de dados
- `error.image.not.found` - Imagem não encontrada
- `error.generic` - Erro genérico

### Botões
- `button.ok` - OK
- `button.cancel` - Cancelar
- `button.save` - Salvar
- `button.delete` - Excluir
- `button.edit` - Editar
- `button.close` - Fechar

### Configurações
- `settings.title` - Configurações
- `settings.language` - Idioma
- `settings.theme` - Tema

## 💾 Persistência

O idioma selecionado é **automaticamente salvo** nas preferências do usuário e será carregado na próxima vez que a aplicação for iniciada.

## 🔧 Configuração Avançada

### Adicionar novo idioma

1. Crie um novo arquivo: `messages_[idioma]_[PAÍS].properties`
   - Exemplo: `messages_es_ES.properties` (Espanhol)

2. Adicione o locale na classe `I18nManager`:
```java
public static final Locale LOCALE_ES_ES = new Locale("es", "ES");
```

3. Atualize o `LanguageSwitcher` para incluir o novo idioma.

## 📝 Boas Práticas

1. **Use chaves descritivas**: `login.button.submit` em vez de `btn1`
2. **Organize por contexto**: Use prefixos como `login.`, `error.`, `button.`
3. **Mantenha consistência**: Traduza todas as chaves em todos os idiomas
4. **Evite hardcoding**: Sempre use i18n para textos visíveis ao usuário
5. **Teste em todos os idiomas**: Verifique se as traduções fazem sentido

## 🐛 Troubleshooting

### Mensagem aparece como `!chave!`
- A chave não existe nos arquivos `.properties`
- Verifique se a chave está escrita corretamente

### Idioma não muda
- Reinicie a aplicação após trocar o idioma
- Verifique se o arquivo `.properties` está no classpath

### Caracteres especiais aparecem errados
- Os arquivos `.properties` devem estar em UTF-8
- Use escape para caracteres especiais: `\u00E1` para `á`

## 📞 Suporte

Para adicionar novas traduções ou reportar problemas, entre em contato com a equipe de desenvolvimento.
