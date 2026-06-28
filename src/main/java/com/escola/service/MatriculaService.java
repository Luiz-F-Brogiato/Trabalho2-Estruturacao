package com.escola.service;

import com.escola.model.Curso;
import com.escola.model.Matricula;
import com.escola.repository.AlunoRepository;
import com.escola.repository.CursoRepository;
import com.escola.repository.MatriculaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MatriculaService {

    private final MatriculaRepository matriculaRepository = new MatriculaRepository();
    private final AlunoRepository alunoRepository         = new AlunoRepository();
    private final CursoRepository cursoRepository         = new CursoRepository();

    public Matricula matricular(int idAluno, int idCurso, double valor) throws SQLException {

        // RN: aluno deve estar cadastrado
        alunoRepository.buscarPorId(idAluno)
            .orElseThrow(() -> new IllegalArgumentException(
                "Aluno não encontrado (id=" + idAluno + "). Cadastre o aluno antes de matricular."));

        // RN: curso deve estar cadastrado
        Curso curso = cursoRepository.buscarPorId(idCurso)
            .orElseThrow(() -> new IllegalArgumentException(
                "Curso não encontrado (id=" + idCurso + "). Cadastre o curso antes de matricular."));

        // RN: não pode matricular o mesmo aluno duas vezes no mesmo curso
        if (matriculaRepository.existeMatricula(idAluno, idCurso))
            throw new IllegalArgumentException(
                "Aluno id=" + idAluno + " já está matriculado no curso id=" + idCurso + ".");

        // RN: não pode matricular se não houver vagas disponíveis
        if (curso.getVagasDisponiveis() <= 0)
            throw new IllegalArgumentException(
                "Curso '" + curso.getNome() + "' não possui vagas disponíveis.");

        // RN: valor não pode ser negativo
        if (valor < 0)
            throw new IllegalArgumentException("O valor da matrícula não pode ser negativo.");

        // Salva matrícula e decrementa vaga
        Matricula matricula = matriculaRepository.salvar(
            new Matricula(idAluno, idCurso, LocalDate.now(), valor));

        curso.setVagasDisponiveis(curso.getVagasDisponiveis() - 1);
        cursoRepository.atualizar(curso);

        return matricula;
    }

    public Matricula buscarPorId(int id) throws SQLException {
        return matriculaRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada: id=" + id));
    }

    public List<Matricula> listarTodas() throws SQLException {
        return matriculaRepository.listarTodas();
    }

    // RN: consultar todos os alunos matriculados em um curso
    public List<Matricula> alunosPorCurso(int idCurso) throws SQLException {
        cursoRepository.buscarPorId(idCurso)
            .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: id=" + idCurso));
        return matriculaRepository.buscarPorCurso(idCurso);
    }

    // RN: consultar todos os cursos em que um aluno está matriculado
    public List<Matricula> cursosPorAluno(int idAluno) throws SQLException {
        alunoRepository.buscarPorId(idAluno)
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: id=" + idAluno));
        return matriculaRepository.buscarPorAluno(idAluno);
    }

    public void cancelar(int id) throws SQLException {
        buscarPorId(id);
        matriculaRepository.deletar(id);
    }
}
