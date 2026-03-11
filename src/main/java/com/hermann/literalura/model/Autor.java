package com.hermann.literalura.model;

import com.hermann.literalura.model.Livro;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(DadosAutor dados) {
        this.nome = dados.nome();
        this.anoNascimento = dados.anoNascimento();
        this.anoFalecimento = dados.anoFalecimento();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public List<Livro> getLivros() { return livros; }
    public void setNome(String nome) { this.nome = nome; }
    public void setAnoNascimento(Integer ano) { this.anoNascimento = ano; }
    public void setAnoFalecimento(Integer ano) { this.anoFalecimento = ano; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }

    @Override
    public String toString() {
        // ✨ Java 21 — Text Block para formatar saída
        return """
                
                ╔══════════════════════════╗
                       AUTOR
                ╚══════════════════════════╝
                Nome       : %s
                Nascimento : %s
                Falecimento: %s
                Livros     : %s
                """.formatted(
                nome,
                anoNascimento != null ? anoNascimento : "desconhecido",
                anoFalecimento != null ? anoFalecimento : "ainda vivo",
                livros.stream().map(Livro::getTitulo).toList()
        );
    }
}