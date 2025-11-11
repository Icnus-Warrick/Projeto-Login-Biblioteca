package br.com.warrick.biblioteca.persistence.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma categoria de livros na biblioteca.
 * Pode ter subcategorias, formando uma hierarquia.
 * 
 * @author Warrick
 * @since 11/11/2025
 */
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nome;
    private String descricao;
    private Color corLombada;
    private Color corBarras;
    private Color corFonte;
    private Categoria categoriaPai;
    private List<Categoria> subcategorias;
    private EstiloLivro estiloPersonalizado;
    
    // Categorias principais - declaradas como nulas inicialmente
    public static final Categoria FICCAO;
    public static final Categoria TECNICO;
    public static final Categoria RELIGIAO;
    public static final Categoria INFANTIL;
    public static final Categoria ACADEMICO;
    public static final Categoria TECNOLOGIA;
    
    // Bloco estático para inicialização das categorias
    static {
        // Inicialização das categorias principais
        FICCAO = new Categoria(1, "Ficção", "Livros de ficção em geral", 
                new Color(75, 0, 130),  // Roxo
                new Color(147, 112, 219), // Roxo claro
                Color.WHITE);
                
        TECNICO = new Categoria(2, "Técnico", "Livros técnicos e didáticos", 
                new Color(0, 0, 139),    // Azul escuro
                new Color(100, 149, 237), // Azul claro
                Color.WHITE);
                
        RELIGIAO = new Categoria(3, "Religião", "Livros religiosos e espirituais",
                new Color(139, 0, 0),    // Vermelho escuro
                new Color(205, 92, 92),   // Vermelho claro
                Color.WHITE);
                
        INFANTIL = new Categoria(4, "Infantil", "Livros infantis", 
                new Color(255, 215, 0),   // Dourado
                new Color(255, 255, 0),   // Amarelo
                Color.BLACK);
                
        ACADEMICO = new Categoria(5, "Acadêmico", "Livros acadêmicos", 
                new Color(0, 100, 0),     // Verde escuro
                new Color(144, 238, 144), // Verde claro
                Color.BLACK);
                
        TECNOLOGIA = new Categoria(6, "Tecnologia", "Livros de tecnologia e informática",
                new Color(47, 79, 79),    // Azul petróleo escuro
                new Color(102, 205, 170), // Azul petróleo claro
                Color.WHITE);
        
        // Inicialização das subcategorias
        Categoria biblia = new Categoria(7, "Bíblia", "Textos bíblicos", 
                new Color(139, 0, 0), new Color(205, 92, 92), Color.WHITE, RELIGIAO);
                
        Categoria teologia = new Categoria(8, "Teologia", "Estudos teológicos", 
                new Color(128, 0, 0), new Color(220, 20, 60), Color.WHITE, RELIGIAO);
                
        Categoria estudoBiblico = new Categoria(9, "Estudo Bíblico", "Guias e comentários bíblicos", 
                new Color(165, 42, 42), new Color(205, 133, 63), Color.WHITE, RELIGIAO);
        
        Categoria programacao = new Categoria(10, "Programação", "Linguagens de programação", 
                new Color(25, 25, 112), new Color(70, 130, 180), Color.WHITE, TECNOLOGIA);
                
        Categoria dados = new Categoria(11, "Ciência de Dados", "Análise de dados e IA", 
                new Color(0, 0, 128), new Color(100, 149, 237), Color.WHITE, TECNOLOGIA);
                
        Categoria web = new Categoria(12, "Desenvolvimento Web", "Front-end e back-end", 
                new Color(0, 100, 0), new Color(60, 179, 113), Color.WHITE, TECNOLOGIA);
        
        Categoria ficcaoCientifica = new Categoria(13, "Ficção Científica", "Ficção científica", 
                new Color(72, 61, 139), new Color(123, 104, 238), Color.WHITE, FICCAO);
                
        Categoria fantasia = new Categoria(14, "Fantasia", "Literatura fantástica", 
                new Color(85, 26, 139), new Color(147, 112, 219), Color.WHITE, FICCAO);
        
        Categoria engenharia = new Categoria(15, "Engenharia", "Engenharias em geral", 
                new Color(0, 0, 128), new Color(65, 105, 225), Color.WHITE, TECNICO);
                
        Categoria saude = new Categoria(16, "Saúde", "Medicina e saúde", 
                new Color(139, 0, 0), new Color(255, 99, 71), Color.WHITE, TECNICO);
        
        // Inicializa as listas de subcategorias
        RELIGIAO.subcategorias = new ArrayList<>();
        RELIGIAO.subcategorias.add(biblia);
        RELIGIAO.subcategorias.add(teologia);
        RELIGIAO.subcategorias.add(estudoBiblico);
        
        TECNOLOGIA.subcategorias = new ArrayList<>();
        TECNOLOGIA.subcategorias.add(programacao);
        TECNOLOGIA.subcategorias.add(dados);
        TECNOLOGIA.subcategorias.add(web);
        
        FICCAO.subcategorias = new ArrayList<>();
        FICCAO.subcategorias.add(ficcaoCientifica);
        FICCAO.subcategorias.add(fantasia);
        
        TECNICO.subcategorias = new ArrayList<>();
        TECNICO.subcategorias.add(engenharia);
        TECNICO.subcategorias.add(saude);
    }
    
    public static final Categoria BIOGRAFIA = new Categoria(3, "Biografia", "Biografias e memórias", 
            new Color(139, 69, 19),  // Marrom
            new Color(210, 180, 140), // Marrom claro
            Color.BLACK);
    
    public Categoria() {
        this.subcategorias = new ArrayList<>();
    }
    
    public Categoria(Integer id, String nome, String descricao, 
                   Color corLombada, Color corBarras, Color corFonte) {
        this(id, nome, descricao, corLombada, corBarras, corFonte, null);
    }
    
    public Categoria(Integer id, String nome, String descricao, 
                   Color corLombada, Color corBarras, Color corFonte, Categoria categoriaPai) {
        this();
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.corLombada = corLombada;
        this.corBarras = corBarras;
        this.corFonte = corFonte;
        this.categoriaPai = categoriaPai;
        this.estiloPersonalizado = EstiloLivro.criarEstiloPadrao(this);
    }
    
    /**
     * Define um estilo personalizado para esta categoria.
     * Se null for passado, o estilo padrão será restaurado.
     */
    public void setEstiloPersonalizado(EstiloLivro estilo) {
        this.estiloPersonalizado = (estilo != null) ? 
            EstiloLivro.criarEstiloPersonalizado(this, estilo) : 
            EstiloLivro.criarEstiloPadrao(this);
    }
    
    /**
     * Retorna o estilo desta categoria.
     * Se um estilo personalizado foi definido, retorna-o.
     * Caso contrário, retorna o estilo padrão baseado nas cores da categoria.
     */
    public EstiloLivro getEstilo() {
        if (estiloPersonalizado != null) {
            return estiloPersonalizado;
        }
        return EstiloLivro.criarEstiloPadrao(this);
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Color getCorLombada() {
        return corLombada;
    }

    public void setCorLombada(Color corLombada) {
        this.corLombada = corLombada;
    }

    public Color getCorBarras() {
        return corBarras;
    }

    public void setCorBarras(Color corBarras) {
        this.corBarras = corBarras;
    }

    public Color getCorFonte() {
        return corFonte;
    }

    public void setCorFonte(Color corFonte) {
        this.corFonte = corFonte;
    }
    
    // Método para obter todas as categorias padrão
    public static Categoria[] getCategoriasPadrao() {
        return new Categoria[]{FICCAO, TECNICO, BIOGRAFIA, INFANTIL, ACADEMICO};
    }
    
    // Métodos para gerenciar hierarquia
    public void adicionarSubcategoria(Categoria subcategoria) {
        if (this.subcategorias == null) {
            this.subcategorias = new ArrayList<>();
        }
        subcategoria.categoriaPai = this;
        this.subcategorias.add(subcategoria);
    }
    
    public List<Categoria> getSubcategorias() {
        return subcategorias != null ? new ArrayList<>(subcategorias) : new ArrayList<>();
    }
    
    public boolean isSubcategoria() {
        return categoriaPai != null;
    }
    
    public Categoria getCategoriaPai() {
        return categoriaPai;
    }
    
    // Método para obter todas as categorias principais
    public static List<Categoria> getCategoriasPrincipais() {
        return List.of(FICCAO, TECNICO, RELIGIAO, INFANTIL, ACADEMICO, TECNOLOGIA);
    }
    
    // Método para buscar uma categoria por ID (incluindo subcategorias)
    public static Categoria buscarPorId(Integer id) {
        if (id == null) return null;
        
        // Busca nas categorias principais
        for (Categoria cat : getCategoriasPrincipais()) {
            if (id.equals(cat.getId())) return cat;
            
            // Busca nas subcategorias
            for (Categoria sub : cat.getSubcategorias()) {
                if (id.equals(sub.getId())) return sub;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        if (isSubcategoria()) {
            return categoriaPai.getNome() + " > " + nome;
        }
        return nome;
    }
}
