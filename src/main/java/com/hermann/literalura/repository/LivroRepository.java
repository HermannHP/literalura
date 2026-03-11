package com.hermann.literalura.repository;

import com.hermann.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Livro l WHERE l.idioma = :idioma")
    List<Livro> buscarPorIdioma(String idioma);

    List<Livro> findAllByOrderByNumeroDownloadsDesc();
}