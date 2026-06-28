package com.escola.repository;

import com.escola.model.Matricula;
import com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaRepository {

    public Matricula salvar(Matricula matricula) throws SQLException {
        String sql = "INSERT INTO matricula (id_aluno, id_curso, data_matricula, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getIdAluno());
            stmt.setInt(2, matricula.getIdCurso());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setDouble(4, matricula.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) matricula.setId(rs.getInt("id"));
        }
        return matricula;
    }

    public Optional<Matricula> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM matricula WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        }
        return Optional.empty();
    }

    public List<Matricula> listarTodas() throws SQLException {
        String sql = "SELECT * FROM matricula ORDER BY data_matricula DESC";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // Todos os alunos matriculados em um curso
    public List<Matricula> buscarPorCurso(int idCurso) throws SQLException {
        String sql = "SELECT * FROM matricula WHERE id_curso = ? ORDER BY data_matricula DESC";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // Todos os cursos em que um aluno está matriculado
    public List<Matricula> buscarPorAluno(int idAluno) throws SQLException {
        String sql = "SELECT * FROM matricula WHERE id_aluno = ? ORDER BY data_matricula DESC";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // RN: verificar se aluno já está matriculado no curso
    public boolean existeMatricula(int idAluno, int idCurso) throws SQLException {
        String sql = "SELECT 1 FROM matricula WHERE id_aluno = ? AND id_curso = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM matricula WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Matricula mapear(ResultSet rs) throws SQLException {
        return new Matricula(
            rs.getInt("id"), rs.getInt("id_aluno"), rs.getInt("id_curso"),
            rs.getDate("data_matricula").toLocalDate(), rs.getDouble("valor")
        );
    }
}
