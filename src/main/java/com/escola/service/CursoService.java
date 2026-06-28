package com.escola.service;

import com.escola.model.Curso;
import com.escola.repository.CursoRepository;

import java.sql.SQLException;
import java.util.List;

public class CursoService {

    private final CursoRepository cursoRepository = new CursoRepository();

    public Curso cadastrar(String nome, String descricao, int cargaHoraria, int vagasTotais) throws SQLException {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
        if (cargaHoraria <= 0)
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        if (vagasTotais <= 0)
            throw new IllegalArgumentException("Número de vagas deve ser maior que zero.");

        return cursoRepository.salvar(new Curso(nome.trim(), descricao, cargaHoraria, vagasTotais));
    }

    public Curso buscarPorId(int id) throws SQLException {
        return cursoRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: id=" + id));
    }

    public List<Curso> listarTodos() throws SQLException {
        return cursoRepository.listarTodos();
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        cursoRepository.deletar(id);
    }

    // Decrementa vagas disponíveis após matrícula
    public void decrementarVaga(int idCurso) throws SQLException {
        Curso curso = buscarPorId(idCurso);
        curso.setVagasDisponiveis(curso.getVagasDisponiveis() - 1);
        cursoRepository.atualizar(curso);
    }
}
