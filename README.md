# Cenário 3 — Sistema de Escola de Cursos Livres

## Tabelas identificadas

### aluno
| Campo    | Tipo         | Restrição |
|----------|--------------|-----------|
| id       | SERIAL       | PK        |
| nome     | VARCHAR(100) | NOT NULL  |
| email    | VARCHAR(150) | NOT NULL  |
| telefone | VARCHAR(20)  | NOT NULL  |

### curso
| Campo              | Tipo         | Restrição |
|--------------------|--------------|-----------|
| id                 | SERIAL       | PK        |
| nome               | VARCHAR(100) | NOT NULL  |
| descricao          | VARCHAR(300) |           |
| carga_horaria      | INTEGER      | NOT NULL  |
| vagas_totais       | INTEGER      | NOT NULL  |
| vagas_disponiveis  | INTEGER      | NOT NULL  |

### matricula
| Campo          | Tipo          | Restrição                  |
|----------------|---------------|----------------------------|
| id             | SERIAL        | PK                         |
| id_aluno       | INTEGER       | NOT NULL — FK → aluno(id)  |
| id_curso       | INTEGER       | NOT NULL — FK → curso(id)  |
| data_matricula | DATE          | NOT NULL                   |
| valor          | NUMERIC(10,2) | NOT NULL — CHECK >= 0      |

---

## Comandos SQL (CREATE TABLE)

```sql
CREATE TABLE aluno (
    id       SERIAL        PRIMARY KEY,
    nome     VARCHAR(100)  NOT NULL,
    email    VARCHAR(150)  NOT NULL,
    telefone VARCHAR(20)   NOT NULL
);

CREATE TABLE curso (
    id                 SERIAL        PRIMARY KEY,
    nome               VARCHAR(100)  NOT NULL,
    descricao          VARCHAR(300),
    carga_horaria      INTEGER       NOT NULL CHECK (carga_horaria > 0),
    vagas_totais       INTEGER       NOT NULL CHECK (vagas_totais > 0),
    vagas_disponiveis  INTEGER       NOT NULL CHECK (vagas_disponiveis >= 0)
);

CREATE TABLE matricula (
    id              SERIAL         PRIMARY KEY,
    id_aluno        INTEGER        NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    id_curso        INTEGER        NOT NULL REFERENCES curso(id) ON DELETE CASCADE,
    data_matricula  DATE           NOT NULL,
    valor           NUMERIC(10,2)  NOT NULL CHECK (valor >= 0),
    UNIQUE (id_aluno, id_curso)
);
```

---

## Regras de Negócio

| # | Regra |
|---|-------|
| RN01 | Aluno deve ter obrigatoriamente nome, e-mail e telefone. |
| RN02 | Curso deve ter obrigatoriamente nome, carga horária e número de vagas. |
| RN03 | Não é permitido matricular um aluno que não esteja cadastrado. |
| RN04 | Não é permitido matricular em um curso que não esteja cadastrado. |
| RN05 | O mesmo aluno não pode ser matriculado duas vezes no mesmo curso. |
| RN06 | Não é permitido matricular em curso sem vagas disponíveis. |
| RN07 | O valor da matrícula não pode ser negativo. |
| RN08 | Ao realizar uma matrícula, o número de vagas disponíveis do curso é decrementado. |
| RN09 | É possível consultar todos os alunos matriculados em um curso. |
| RN10 | É possível consultar todos os cursos em que um aluno está matriculado. |

---

## Estrutura do Projeto (MVC)

```
src/main/java/com/escola/
├── Main.java
├── model/
│   ├── Aluno.java
│   ├── Curso.java
│   └── Matricula.java
├── repository/
│   ├── AlunoRepository.java
│   ├── CursoRepository.java
│   └── MatriculaRepository.java
├── service/
│   ├── AlunoService.java
│   ├── CursoService.java
│   └── MatriculaService.java
├── controller/
│   ├── AlunoController.java
│   ├── CursoController.java
│   └── MatriculaController.java
└── util/
    └── Conexao.java
```

## Como executar

1. Crie o banco no PostgreSQL:
   ```sql
   CREATE DATABASE escola_cursos;
   ```
2. Rode os scripts SQL acima.
3. Ajuste usuário/senha em `util/Conexao.java` se necessário.
4. Execute via IntelliJ ou `mvn compile exec:java`.
