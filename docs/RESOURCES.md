# 📚 Recursos do Projeto

## Estrutura de Diretórios

```
src/main/resources/
├── Icone/         # Ícones da interface
├── db/            # Banco de dados SQLite
├── Imagem/        # Imagens da aplicação
├── messages/      # Arquivos de internacionalização
└── themes/        # Temas de interface
```

## Uso

### Acesso a Recursos

Use `ClassLoader` para acessar recursos no código:

```java
// Exemplo: Carregar ícone
ImageIcon icon = new ImageIcon(getClass().getResource("/Icone/eye.png"));

// Exemplo: Carregar mensagem
InputStream input = getClass().getResourceAsStream("/messages/messages_pt_BR.properties");
```

## Convenções

- Use nomes em minúsculas com underscores
- Mantenha os arquivos organizados por tipo
- Documente novos recursos neste arquivo

> **Nota:** Consulte a documentação completa em [README_COMPLETO.md](README_COMPLETO.md)
