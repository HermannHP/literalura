package com.hermann.literalura.principal;

import com.hermann.literalura.model.Autor;
import com.hermann.literalura.model.DadosResposta;
import com.hermann.literalura.model.DadosLivro;
import com.hermann.literalura.model.Livro;
import com.hermann.literalura.repository.AutorRepository;
import com.hermann.literalura.repository.LivroRepository;
import com.hermann.literalura.service.ConsumoApi;
import com.hermann.literalura.service.ConverteDados;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibirMenu() {
        var opcao = -1;
        while (opcao != 0) {
            // ✨ Java 21 — Text Block
            System.out.println("""
                    \n╔══════════════════════════════════╗
                           📚  L I T E R A L U R A
                    ╚══════════════════════════════════╝
                     1 - Buscar livro por título
                     2 - Listar livros registrados
                     3 - Listar autores registrados
                     4 - Listar autores vivos em determinado ano
                     5 - Listar livros por idioma
                     6 - Top 10 livros mais baixados
                     0 - Sair
                    """);
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um número válido.");
                continue;
            }

            // ✨ Java 21 — Pattern Matching no switch (switch expression)
            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosNoAno();
                case 5 -> listarLivrosPorIdioma();
                case 6 -> top10MaisBaixados();
                case 0 -> System.out.println("👋 Até mais!");
                default -> System.out.println("⚠️  Opção inválida!");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título: ");
        var titulo = scanner.nextLine().trim();

        if (titulo.isBlank()) {
            System.out.println("⚠️  Título não pode ser vazio.");
            return;
        }

        var url = URL_BASE + titulo.replace(" ", "+");
        var json = consumoApi.obterDados(url);
        var resposta = conversor.obterDados(json, DadosResposta.class);

        if (resposta.resultados().isEmpty()) {
            System.out.println("❌ Nenhum livro encontrado para: " + titulo);
            return;
        }

        var dadosLivro = resposta.resultados().getFirst(); // ✨ Java 21 — getFirst()

        Optional<Livro> livroExistente = livroRepository.findByTituloIgnoreCase(dadosLivro.titulo());
        if (livroExistente.isPresent()) {
            System.out.println("⚠️  Livro já cadastrado:" + livroExistente.get());
            return;
        }

        Autor autor = null;
        if (!dadosLivro.autores().isEmpty()) {
            var dadosAutor = dadosLivro.autores().getFirst();
            autor = autorRepository.findByNomeIgnoreCase(dadosAutor.nome())
                    .orElseGet(() -> autorRepository.save(new Autor(dadosAutor)));
        }

        var livro = new Livro(dadosLivro, autor);
        livroRepository.save(livro);
        System.out.println("✅ Livro salvo!" + livro);
    }

    private void listarLivrosRegistrados() {
        var livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("📭 Nenhum livro registrado ainda.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("📭 Nenhum autor registrado ainda.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosNoAno() {
        System.out.print("Digite o ano: ");
        try {
            var ano = Integer.parseInt(scanner.nextLine().trim());
            var autores = autorRepository.buscarAutoresVivosNoAno(ano);
            if (autores.isEmpty()) {
                System.out.println("📭 Nenhum autor encontrado para o ano " + ano);
            } else {
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Ano inválido.");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Idiomas disponíveis:
                  en - Inglês
                  pt - Português
                  es - Espanhol
                  fr - Francês
                  de - Alemão
                """);
        System.out.print("Digite o código do idioma: ");
        var idioma = scanner.nextLine().trim().toLowerCase();
        var livros = livroRepository.buscarPorIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("📭 Nenhum livro nesse idioma.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void top10MaisBaixados() {
        var top10 = livroRepository.findAllByOrderByNumeroDownloadsDesc()
                .stream()
                .limit(10)
                .toList();
        if (top10.isEmpty()) {
            System.out.println("📭 Nenhum livro registrado ainda.");
        } else {
            System.out.println("\n🏆 TOP 10 MAIS BAIXADOS:");
            top10.forEach(System.out::println);
        }
    }
}