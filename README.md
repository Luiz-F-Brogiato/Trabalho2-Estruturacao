# Cenário 1 — Sistema de Clínica Veterinária

## Tabelas identificadas

### tutor
| Campo    | Tipo         | Restrição     |
|----------|--------------|---------------|
| id       | SERIAL       | PK            |
| nome     | VARCHAR(100) | NOT NULL      |
| endereco | VARCHAR(200) |               |
| telefone | VARCHAR(20)  | NOT NULL      |

### animal
| Campo    | Tipo         | Restrição                  |
|----------|--------------|----------------------------|
| id       | SERIAL       | PK                         |
| nome     | VARCHAR(100) | NOT NULL                   |
| especie  | VARCHAR(50)  | NOT NULL                   |
| raca     | VARCHAR(50)  |                            |
| id_tutor | INTEGER      | NOT NULL — FK → tutor(id)  |

### consulta
| Campo     | Tipo          | Restrição                   |
|-----------|---------------|-----------------------------|
| id        | SERIAL        | PK                          |
| id_animal | INTEGER       | NOT NULL — FK → animal(id)  |
| data      | DATE          | NOT NULL                    |
| motivo    | VARCHAR(200)  | NOT NULL                    |
| valor     | NUMERIC(10,2) | NOT NULL — CHECK >= 0       |

---

## Comandos SQL (CREATE TABLE)

```sql
CREATE TABLE tutor (
    id       SERIAL        PRIMARY KEY,
    nome     VARCHAR(100)  NOT NULL,
    endereco VARCHAR(200),
    telefone VARCHAR(20)   NOT NULL
);

CREATE TABLE animal (
    id        SERIAL        PRIMARY KEY,
    nome      VARCHAR(100)  NOT NULL,
    especie   VARCHAR(50)   NOT NULL,
    raca      VARCHAR(50),
    id_tutor  INTEGER       NOT NULL REFERENCES tutor(id) ON DELETE CASCADE
);

CREATE TABLE consulta (
    id        SERIAL         PRIMARY KEY,
    id_animal INTEGER        NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
    data      DATE           NOT NULL,
    motivo    VARCHAR(200)   NOT NULL,
    valor     NUMERIC(10,2)  NOT NULL CHECK (valor >= 0)
);
```

---

## Regras de Negócio

| # | Regra |
|---|-------|
| RN01 | Um tutor deve ter obrigatoriamente nome e telefone. |
| RN02 | Um tutor pode ter zero ou mais animais cadastrados. |
| RN03 | Um animal pertence a exatamente um tutor; não pode existir sem tutor. |
| RN04 | O animal deve ter obrigatoriamente nome e espécie. |
| RN05 | Não é permitido registrar uma consulta para um animal que não esteja cadastrado. |
| RN06 | O valor de uma consulta não pode ser negativo. |
| RN07 | Uma consulta deve informar: animal, data, motivo e valor. |
| RN08 | É possível consultar todo o histórico de atendimentos de um animal específico. |
| RN09 | É possível listar todos os animais de um tutor específico. |

---

## Estrutura do Projeto (MVC)

```
src/main/java/com/clinica/
├── Main.java
├── model/
│   ├── Tutor.java
│   ├── Animal.java
│   └── Consulta.java
├── repository/
│   ├── TutorRepository.java
│   ├── AnimalRepository.java
│   └── ConsultaRepository.java
├── service/
│   ├── TutorService.java
│   ├── AnimalService.java
│   └── ConsultaService.java
├── controller/
│   ├── TutorController.java
│   ├── AnimalController.java
│   └── ConsultaController.java
└── util/
    └── Conexao.java
```

## Como executar

1. Crie o banco no PostgreSQL:
   ```sql
   CREATE DATABASE clinica_veterinaria;
   ```
2. Rode os scripts `CREATE TABLE` acima no banco criado.
3. Ajuste usuário/senha em `util/Conexao.java` se necessário (padrão: `postgres`/`postgres`).
4. Execute via IntelliJ ou `mvn compile exec:java`.
