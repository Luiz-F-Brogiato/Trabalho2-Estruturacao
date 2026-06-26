package com.clinica.repository;

import com.clinica.model.Tutor;
import com.clinica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TutorRepository {

    public Tutor salvar(Tutor tutor) throws SQLException {
        String sql = "INSERT INTO tutor (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tutor.setId(rs.getInt("id"));
            }
        }
        return tutor;
    }

    public Optional<Tutor> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapear(rs));
            }
        }
        return Optional.empty();
    }

    public List<Tutor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM tutor ORDER BY nome";
        List<Tutor> tutores = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tutores.add(mapear(rs));
            }
        }
        return tutores;
    }

    public void atualizar(Tutor tutor) throws SQLException {
        String sql = "UPDATE tutor SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            stmt.setInt(4, tutor.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Tutor mapear(ResultSet rs) throws SQLException {
        return new Tutor(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("endereco"),
            rs.getString("telefone")
        );
    }
}
