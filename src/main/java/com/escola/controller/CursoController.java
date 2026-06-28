package com.escola.controller;

import com.escola.model.Curso;
import com.escola.service.CursoService;

import java.sql.SQLException;
import java.util.List;

public class CursoController {

    private final CursoService cursoService = new CursoService();

    public Curso cadastrar(String nome, String descricao, int cargaHoraria, int vagasTotais) {
        try {
            Curso c = cursoService.cadastrar(nome, descricao, cargaHoraria, vagasTotais);
            System.out.println("[OK] Curso cadastrado: " + c);
            return c;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listar() {
        try {
            List<Curso> lista = cursoService.listarTodos();
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhum curso cadastrado."); return; }
            System.out.println("=== Cursos ===");
            lista.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void remover(int id) {
        try {
            cursoService.remover(id);
            System.out.println("[OK] Curso id=" + id + " removido.");
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }
}
