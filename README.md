# Cenário 2 — Sistema de Oficina Mecânica

## Tabelas identificadas

### cliente
| Campo    | Tipo         | Restrição |
|----------|--------------|-----------|
| id       | SERIAL       | PK        |
| nome     | VARCHAR(100) | NOT NULL  |
| telefone | VARCHAR(20)  | NOT NULL  |

### veiculo
| Campo      | Tipo         | Restrição                    |
|------------|--------------|------------------------------|
| id         | SERIAL       | PK                           |
| placa      | VARCHAR(10)  | NOT NULL                     |
| modelo     | VARCHAR(100) | NOT NULL                     |
| ano        | INTEGER      | NOT NULL                     |
| id_cliente | INTEGER      | NOT NULL — FK → cliente(id)  |

### ordem_servico
| Campo      | Tipo          | Restrição                    |
|------------|---------------|------------------------------|
| id         | SERIAL        | PK                           |
| id_veiculo | INTEGER       | NOT NULL — FK → veiculo(id)  |
| descricao  | VARCHAR(300)  | NOT NULL                     |
| valor      | NUMERIC(10,2) | NOT NULL — CHECK >= 0        |
| status     | status_os     | NOT NULL (ABERTA/CONCLUIDA)  |

---

## Comandos SQL (CREATE TABLE)

```sql
CREATE TYPE status_os AS ENUM ('ABERTA', 'CONCLUIDA');

CREATE TABLE cliente (
    id       SERIAL        PRIMARY KEY,
    nome     VARCHAR(100)  NOT NULL,
    telefone VARCHAR(20)   NOT NULL
);

CREATE TABLE veiculo (
    id          SERIAL        PRIMARY KEY,
    placa       VARCHAR(10)   NOT NULL,
    modelo      VARCHAR(100)  NOT NULL,
    ano         INTEGER       NOT NULL,
    id_cliente  INTEGER       NOT NULL REFERENCES cliente(id) ON DELETE CASCADE
);

CREATE TABLE ordem_servico (
    id          SERIAL         PRIMARY KEY,
    id_veiculo  INTEGER        NOT NULL REFERENCES veiculo(id) ON DELETE CASCADE,
    descricao   VARCHAR(300)   NOT NULL,
    valor       NUMERIC(10,2)  NOT NULL CHECK (valor >= 0),
    status      status_os      NOT NULL DEFAULT 'ABERTA'
);
```

---

## Regras de Negócio

| # | Regra |
|---|-------|
| RN01 | Cliente deve ter obrigatoriamente nome e telefone. |
| RN02 | Um cliente pode ter zero ou mais veículos cadastrados. |
| RN03 | Um veículo pertence a exatamente um cliente; não pode existir sem cliente. |
| RN04 | O veículo deve ter obrigatoriamente placa, modelo e ano. |
| RN05 | Não é permitido abrir OS para um veículo não cadastrado. |
| RN06 | O valor do serviço não pode ser negativo. |
| RN07 | Uma OS deve informar: veículo, descrição do problema e valor. |
| RN08 | Uma OS pode estar ABERTA ou CONCLUIDA. |
| RN09 | É possível consultar todo o histórico de manutenções de um veículo. |

---

## Estrutura do Projeto (MVC)

```
src/main/java/com/oficina/
├── Main.java
├── model/
│   ├── Cliente.java
│   ├── Veiculo.java
│   └── OrdemServico.java
├── repository/
│   ├── ClienteRepository.java
│   ├── VeiculoRepository.java
│   └── OrdemServicoRepository.java
├── service/
│   ├── ClienteService.java
│   ├── VeiculoService.java
│   └── OrdemServicoService.java
├── controller/
│   ├── ClienteController.java
│   ├── VeiculoController.java
│   └── OrdemServicoController.java
└── util/
    └── Conexao.java
```

## Como executar

1. Crie o banco no PostgreSQL:
   ```sql
   CREATE DATABASE oficina_mecanica;
   ```
2. Rode os scripts SQL acima (CREATE TYPE antes do CREATE TABLE).
3. Ajuste usuário/senha em `util/Conexao.java` se necessário.
4. Execute via IntelliJ ou `mvn compile exec:java`.
