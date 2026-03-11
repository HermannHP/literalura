# 📚 LiterAlura — Catálogo de Livros

Desafio Back-End da formação Java da [Alura](https://www.alura.com.br/) em parceria com a Oracle (ONE).

Aplicação de linha de comando que consome a API [Gutendex](https://gutendex.com/) para buscar livros, salvar no banco de dados e permitir consultas diversas.

---

## ✅ Funcionalidades

- 🔍 Buscar livro por título (via API Gutendex)
- 📖 Listar todos os livros registrados
- 👤 Listar todos os autores registrados
- 🗓️ Listar autores vivos em determinado ano
- 🌍 Listar livros por idioma
- 🏆 Top 10 livros mais baixados

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.11 |
| Spring Data JPA | — |
| PostgreSQL | 18 |
| Jackson | — |
| Maven | — |

---

## ▶️ Como rodar o projeto

### Pré-requisitos

- Java 21 instalado
- PostgreSQL instalado e rodando
- Maven instalado (ou usar o wrapper do projeto)

### 1. Clone o repositório

```bash
git clone https://github.com/hermann/literalura.git
cd literalura
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE literalura;
```

### 3. Configure o `application.properties`

Em `src/main/resources/application.properties`, preencha com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.threads.virtual.enabled=true
```

### 4. Execute o projeto

```bash
./mvnw spring-boot:run
```

O menu será exibido no terminal automaticamente.

---

## 📋 Menu da aplicação

```
╔══════════════════════════════════╗
       📚  L I T E R A L U R A
╚══════════════════════════════════╝
 1 - Buscar livro por título
 2 - Listar livros registrados
 3 - Listar autores registrados
 4 - Listar autores vivos em determinado ano
 5 - Listar livros por idioma
 6 - Top 10 livros mais baixados
 0 - Sair
```

---

## 🗂️ Estrutura do projeto

```
src/main/java/com/hermann/literalura/
├── model/
│   ├── Livro.java
│   ├── Autor.java
│   ├── DadosLivro.java
│   ├── DadosAutor.java
│   └── DadosResposta.java
├── repository/
│   ├── LivroRepository.java
│   └── AutorRepository.java
├── service/
│   ├── ConsumoApi.java
│   └── ConverteDados.java
├── principal/
│   └── Principal.java
└── LiteraluraApplication.java
```

---
<img width="410" height="410" alt="image" src="https://github.com/user-attachments/assets/56ab174c-600c-443f-8193-7c17beec8cd1" />


Desenvolvido por **Hermann** como parte do Challenge Back-End Java — Alura + Oracle ONE.
