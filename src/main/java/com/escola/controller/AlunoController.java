package com.escola.controller;

import com.escola.model.Aluno;
import com.escola.service.AlunoService;

import java.sql.SQLException;
import java.util.List;

public class AlunoController {

    private final AlunoService alunoService = new AlunoService();

    public Aluno cadastrar(String nome, String email, String telefone) {
        try {
            Aluno a = alunoService.cadastrar(nome, email, telefone);
            System.out.println("[OK] Aluno cadastrado: " + a);
            return a;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listar() {
        try {
            List<Aluno> lista = alunoService.listarTodos();
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhum aluno cadastrado."); return; }
            System.out.println("=== Alunos ===");
            lista.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void remover(int id) {
        try {
            alunoService.remover(id);
            System.out.println("[OK] Aluno id=" + id + " removido.");
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }
}
