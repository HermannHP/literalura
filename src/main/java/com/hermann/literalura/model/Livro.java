package com.hermann.literalura.model;

import jakarta.persistence.*;
import com.hermann.literalura.model.Autor;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dados, Autor autor) {
        this.titulo = dados.titulo();
        this.idioma = dados.idiomas().isEmpty() ? "desconhecido" : dados.idiomas().getFirst(); // ✨ Java 21 — getFirst()
        this.numeroDownloads = dados.numeroDownloads();
        this.autor = autor;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDownloads() { return numeroDownloads; }
    public Autor getAutor() { return autor; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public void setNumeroDownloads(Integer n) { this.numeroDownloads = n; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return """
                
                ╔══════════════════════════╗
                       LIVRO
                ╚══════════════════════════╝
                Título  : %s
                Autor   : %s
                Idioma  : %s
                Downloads: %d
                """.formatted(
                titulo,
                autor != null ? autor.getNome() : "desconhecido",
                idioma,
                numeroDownloads != null ? numeroDownloads : 0
        );
    }
}