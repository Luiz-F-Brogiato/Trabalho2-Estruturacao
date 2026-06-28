package com.escola;

import com.escola.controller.AlunoController;
import com.escola.controller.CursoController;
import com.escola.controller.MatriculaController;
import com.escola.model.Aluno;
import com.escola.model.Curso;

public class Main {

    public static void main(String[] args) {

        AlunoController alunoController         = new AlunoController();
        CursoController cursoController         = new CursoController();
        MatriculaController matriculaController = new MatriculaController();

        System.out.println("========================================");
        System.out.println("  SISTEMA - ESCOLA DE CURSOS LIVRES");
        System.out.println("========================================\n");

        // ── 1. Cadastro de Alunos ─────────────────────────────────────────────
        System.out.println("--- Cadastrando alunos ---");
        Aluno a1 = alunoController.cadastrar("Lucas Ferreira",  "lucas@email.com",  "44977770001");
        Aluno a2 = alunoController.cadastrar("Beatriz Costa",   "bea@email.com",    "44977770002");
        Aluno a3 = alunoController.cadastrar("Rafael Mendes",   "rafael@email.com", "44977770003");

        System.out.println("\n-- Tentativa inválida (sem e-mail) --");
        alunoController.cadastrar("Sem Email", "", "44900000000");

        // ── 2. Cadastro de Cursos ─────────────────────────────────────────────
        System.out.println("\n--- Cadastrando cursos ---");
        Curso c1 = cursoController.cadastrar("Java Básico",      "Fundamentos da linguagem Java",   40, 3);
        Curso c2 = cursoController.cadastrar("Banco de Dados",   "SQL e modelagem relacional",      30, 2);
        Curso c3 = cursoController.cadastrar("Excel Avançado",   "Fórmulas, tabelas e dashboards",  20, 1);

        System.out.println("\n--- Listando cursos ---");
        cursoController.listar();

        // ── 3. Matrículas (o movimento) ───────────────────────────────────────
        System.out.println("\n--- Realizando matrículas ---");
        matriculaController.matricular(a1.getId(), c1.getId(), 350.00);
        matriculaController.matricular(a2.getId(), c1.getId(), 350.00);
        matriculaController.matricular(a3.getId(), c1.getId(), 350.00);
        matriculaController.matricular(a1.getId(), c2.getId(), 280.00);
        matriculaController.matricular(a2.getId(), c2.getId(), 280.00);
        matriculaController.matricular(a1.getId(), c3.getId(), 150.00);

        // ── 4. Tentativas inválidas ───────────────────────────────────────────
        System.out.println("\n-- Tentativa: matrícula duplicada --");
        matriculaController.matricular(a1.getId(), c1.getId(), 350.00);

        System.out.println("\n-- Tentativa: curso sem vagas (Excel Avançado já está cheio) --");
        matriculaController.matricular(a2.getId(), c3.getId(), 150.00);

        System.out.println("\n-- Tentativa: valor negativo --");
        matriculaController.matricular(a3.getId(), c2.getId(), -100.00);

        System.out.println("\n-- Tentativa: aluno inexistente --");
        matriculaController.matricular(9999, c1.getId(), 350.00);

        System.out.println("\n-- Tentativa: curso inexistente --");
        matriculaController.matricular(a1.getId(), 9999, 350.00);

        // ── 5. Consultas ──────────────────────────────────────────────────────
        System.out.println("\n--- Alunos matriculados em '" + c1.getNome() + "' ---");
        matriculaController.alunosPorCurso(c1.getId());

        System.out.println("\n--- Cursos de " + a1.getNome() + " ---");
        matriculaController.cursosPorAluno(a1.getId());

        System.out.println("\n--- Todas as matrículas ---");
        matriculaController.listarTodas();

        System.out.println("\n--- Cursos atualizados (vagas disponíveis) ---");
        cursoController.listar();

        System.out.println("\n========================================");
        System.out.println("  FIM DA SIMULAÇÃO");
        System.out.println("========================================");
    }
}
