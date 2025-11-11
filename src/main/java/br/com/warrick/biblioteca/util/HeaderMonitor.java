package br.com.warrick.biblioteca.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitário para verificar e atualizar automaticamente os cabeçalhos de arquivos Java
 * Garante a padronização dos comentários de cabeçalho em todo o projeto
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 05/11/2025
 */
public class HeaderMonitor {

    /* ============================================== CONSTANTES ============================================== */
    
    /** Nome do projeto para inclusão nos cabeçalhos */
    private static final String PROJECT_NAME = "Biblioteca";
    
    /** Nome do autor para inclusão nos cabeçalhos */
    private static final String AUTHOR = "Warrick";
    
    /** Caminho raiz do código-fonte */
    private static final Path SRC_PATH = Paths.get("src");

    /* ========================================= MÉTODO PRINCIPAL =========================================== */
    
    /**
     * Ponto de entrada principal para execução do utilitário
     * 
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println("🔍 Verificando todas as classes...\n");
        verificarArquivosExistentes();
        System.out.println("\n✅ Verificação concluída!");
    }

    /* ========================================= MÉTODOS PRIVADOS ========================================== */
    
    /**
     * Percorre recursivamente o diretório de código-fonte em busca de arquivos Java
     * e verifica/atualiza seus cabeçalhos
     */
    private static void verificarArquivosExistentes() {
        try {
            Files.walk(SRC_PATH)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> !path.endsWith("HeaderMonitor.java")) // Ignora o próprio HeaderMonitor
                    .filter(path -> !path.toString().contains("peripherals")) // Ignora todo o pacote peripherals
                    .forEach(HeaderMonitor::verificarOuAtualizarCabecalho);
        } catch (IOException e) {
            System.err.println("❌ Erro ao percorrer diretórios: " + e.getMessage());
        }
    }

    /**
     * Verifica e atualiza o cabeçalho de um arquivo Java
     * 
     * @param filePath Caminho para o arquivo a ser verificado/atualizado
     */
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

    /**
     * Gera o texto padrão para o cabeçalho de um arquivo Java
     * 
     * @return String com o cabeçalho formatado
     */
    private static String gerarCabecalhoPadrao() {
        String cabecalho = String.format(
                "/**\n" +
                " * Projeto: %s\n" +
                " * @author %s\n" +
                " * @since %s\n" +
                " */\n",
                PROJECT_NAME, AUTHOR, dataArquivo(Paths.get(""))
        );

        return cabecalho;
    }

    /**
     * Adiciona um cabeçalho padrão a um arquivo Java
     * 
     * @param conteudo Conteúdo do arquivo
     * @param filePath Caminho do arquivo
     * @return Conteúdo do arquivo com o cabeçalho adicionado
     */
    private static String adicionarCabecalho(String conteudo, Path filePath) {
        String cabecalho = gerarCabecalhoPadrao();

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

    /**
     * Formata a data de modificação do arquivo para exibição
     * 
     * @param filePath Caminho do arquivo
     * @return String com a data formatada
     */
    private static String dataArquivo(Path filePath) {
        try {
            FileTime time = Files.getLastModifiedTime(filePath);
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date(time.toMillis()));
        } catch (IOException e) {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        }
    }
}   
