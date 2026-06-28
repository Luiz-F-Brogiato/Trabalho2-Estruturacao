package com.escola.controller;

import com.escola.model.Matricula;
import com.escola.service.MatriculaService;

import java.sql.SQLException;
import java.util.List;

public class MatriculaController {

    private final MatriculaService matriculaService = new MatriculaService();

    public Matricula matricular(int idAluno, int idCurso, double valor) {
        try {
            Matricula m = matriculaService.matricular(idAluno, idCurso, valor);
            System.out.println("[OK] Matrícula realizada: " + m);
            return m;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listarTodas() {
        try {
            List<Matricula> lista = matriculaService.listarTodas();
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhuma matrícula registrada."); return; }
            System.out.println("=== Matrículas ===");
            lista.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void alunosPorCurso(int idCurso) {
        try {
            List<Matricula> lista = matriculaService.alunosPorCurso(idCurso);
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhum aluno matriculado no curso id=" + idCurso + "."); return; }
            System.out.println("=== Alunos matriculados no Curso id=" + idCurso + " ===");
            lista.forEach(System.out::println);
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void cursosPorAluno(int idAluno) {
        try {
            List<Matricula> lista = matriculaService.cursosPorAluno(idAluno);
            if (lista.isEmpty()) { System.out.println("[INFO] Aluno id=" + idAluno + " não possui matrículas."); return; }
            System.out.println("=== Cursos do Aluno id=" + idAluno + " ===");
            lista.forEach(System.out::println);
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void cancelar(int id) {
        try {
            matriculaService.cancelar(id);
            System.out.println("[OK] Matrícula id=" + id + " cancelada.");
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }
}
