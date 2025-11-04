package br.com.warrick.biblioteca.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderMonitor {

    private static final String PROJECT_NAME = "Biblioteca";
    private static final String AUTHOR = "Warrick";
    private static final Path SRC_PATH = Paths.get("src");

    public static void main(String[] args) {
        System.out.println("🔍 Verificando todas as classes...\n");
        verificarArquivosExistentes();
        System.out.println("\n✅ Verificação concluída!");
    }

    private static void verificarArquivosExistentes() {
        try {
            Files.walk(SRC_PATH)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> !path.endsWith("HeaderMonitor.java")) // Simples e eficaz
                    .forEach(HeaderMonitor::verificarOuAtualizarCabecalho);
        } catch (IOException e) {
            System.err.println("❌ Erro ao percorrer diretórios: " + e.getMessage());
        }
    }

    private static void verificarOuAtualizarCabecalho(Path filePath) {
        try {
            String conteudo = Files.readString(filePath);
            String conteudoOriginal = conteudo;
            
            // Regex melhorado para detectar cabeçalhos existentes
            Pattern cabecalhoPattern = Pattern.compile("^/\\*\\*[\\s\\S]*?\\*/", Pattern.MULTILINE);
            Matcher cabecalhoMatcher = cabecalhoPattern.matcher(conteudo);

            if (cabecalhoMatcher.find()) {
                // Já tem cabeçalho - verifica e atualiza
                String cabecalhoAtual = cabecalhoMatcher.group();
                String novoCabecalho = cabecalhoAtual;

                // 1. Verifica projeto
                if (!novoCabecalho.contains("Projeto:")) {
                    novoCabecalho = novoCabecalho.replaceFirst(
                            "/\\*\\*\n",
                            "/**\n * Projeto: " + PROJECT_NAME + "\n"
                    );
                }

                // 2. Verifica autor
                Pattern authorPattern = Pattern.compile("@author\\s+(.+)");
                Matcher authorMatcher = authorPattern.matcher(novoCabecalho);
                if (authorMatcher.find()) {
                    String autorEncontrado = authorMatcher.group(1).trim();
                    if (!autorEncontrado.equals(AUTHOR)) {
                        novoCabecalho = novoCabecalho.replaceFirst(
                                "@author\\s+.+",
                                "@author " + AUTHOR
                        );
                    }
                } else {
                    // Adiciona autor se não existir
                    novoCabecalho = novoCabecalho.replaceFirst(
                            "\\*/",
                            " * @author " + AUTHOR + "\n */"
                    );
                }

                // 3. Verifica data
                if (!novoCabecalho.contains("@since")) {
                    String data = dataArquivo(filePath);
                    novoCabecalho = novoCabecalho.replaceFirst(
                            "\\*/",
                            " * @since " + data + "\n */"
                    );
                }

                if (!novoCabecalho.equals(cabecalhoAtual)) {
                    conteudo = conteudo.replace(cabecalhoAtual, novoCabecalho);
                    System.out.println("🔄 Atualizado: " + filePath.getFileName());
                }

            } else {
                // Não tem cabeçalho - adiciona
                conteudo = adicionarCabecalho(conteudo, filePath);
                System.out.println("🆕 Cabeçalho criado: " + filePath.getFileName());
            }

            // Salva apenas se houve mudança
            if (!conteudo.equals(conteudoOriginal)) {
                Files.writeString(filePath, conteudo);
            }

        } catch (Exception e) {
            System.err.println("❌ Erro em " + filePath.getFileName() + ": " + e.getMessage());
        }
    }

    private static String adicionarCabecalho(String conteudo, Path filePath) {
        String cabecalho = String.format(
                "/**\n" +
                " * Projeto: %s\n" +
                " * @author %s\n" +
                " * @since %s\n" +
                " */\n",
                PROJECT_NAME, AUTHOR, dataArquivo(filePath)
        );

        // Encontra onde termina package/imports
        String[] linhas = conteudo.split("\n", -1);
        int posicaoInsercao = 0;
        
        for (int i = 0; i < linhas.length; i++) {
            String linha = linhas[i].trim();
            
            // Pula linhas vazias, comentários, package e imports
            if (linha.isEmpty() || 
                linha.startsWith("//") || 
                linha.startsWith("/*") || 
                linha.startsWith("*") ||
                linha.startsWith("package ") || 
                linha.startsWith("import ")) {
                posicaoInsercao = i + 1;
            } else {
                // Primeira linha de código real (class, interface, enum)
                break;
            }
        }

        // Reconstrói o arquivo
        StringBuilder resultado = new StringBuilder();
        
        // Parte antes do cabeçalho (package/imports)
        for (int i = 0; i < posicaoInsercao; i++) {
            resultado.append(linhas[i]).append("\n");
        }
        
        // Adiciona linha em branco se necessário
        if (posicaoInsercao > 0 && !linhas[posicaoInsercao - 1].trim().isEmpty()) {
            resultado.append("\n");
        }
        
        // Cabeçalho
        resultado.append(cabecalho);
        
        // Resto do arquivo
        for (int i = posicaoInsercao; i < linhas.length; i++) {
            resultado.append(linhas[i]);
            if (i < linhas.length - 1) resultado.append("\n");
        }

        return resultado.toString();
    }

    private static String dataArquivo(Path filePath) {
        try {
            FileTime time = Files.getLastModifiedTime(filePath);
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date(time.toMillis()));
        } catch (IOException e) {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        }
    }
}   
