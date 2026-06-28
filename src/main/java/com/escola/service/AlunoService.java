package com.escola.service;

import com.escola.model.Aluno;
import com.escola.repository.AlunoRepository;

import java.sql.SQLException;
import java.util.List;

public class AlunoService {

    private final AlunoRepository alunoRepository = new AlunoRepository();

    public Aluno cadastrar(String nome, String email, String telefone) throws SQLException {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("E-mail do aluno é obrigatório.");
        if (telefone == null || telefone.isBlank())
            throw new IllegalArgumentException("Telefone do aluno é obrigatório.");

        return alunoRepository.salvar(new Aluno(nome.trim(), email.trim(), telefone.trim()));
    }

    public Aluno buscarPorId(int id) throws SQLException {
        return alunoRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: id=" + id));
    }

    public List<Aluno> listarTodos() throws SQLException {
        return alunoRepository.listarTodos();
    }

    public Aluno atualizar(int id, String nome, String email, String telefone) throws SQLException {
        Aluno a = buscarPorId(id);
        if (nome != null && !nome.isBlank())         a.setNome(nome.trim());
        if (email != null && !email.isBlank())       a.setEmail(email.trim());
        if (telefone != null && !telefone.isBlank()) a.setTelefone(telefone.trim());
        alunoRepository.atualizar(a);
        return a;
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        alunoRepository.deletar(id);
    }
}
