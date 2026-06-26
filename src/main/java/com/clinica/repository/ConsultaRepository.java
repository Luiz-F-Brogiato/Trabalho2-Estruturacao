package com.clinica.repository;

import com.clinica.model.Consulta;
import com.clinica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaRepository {

    public Consulta salvar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (id_animal, data, motivo, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setDouble(4, consulta.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                consulta.setId(rs.getInt("id"));
            }
        }
        return consulta;
    }

    public Optional<Consulta> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";
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

    public List<Consulta> listarTodas() throws SQLException {
        String sql = "SELECT * FROM consulta ORDER BY data DESC";
        List<Consulta> consultas = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                consultas.add(mapear(rs));
            }
        }
        return consultas;
    }

    public List<Consulta> buscarPorAnimal(int idAnimal) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id_animal = ? ORDER BY data DESC";
        List<Consulta> consultas = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                consultas.add(mapear(rs));
            }
        }
        return consultas;
    }

    public void atualizar(Consulta consulta) throws SQLException {
        String sql = "UPDATE consulta SET id_animal = ?, data = ?, motivo = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setDouble(4, consulta.getValor());
            stmt.setInt(5, consulta.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Consulta mapear(ResultSet rs) throws SQLException {
        return new Consulta(
            rs.getInt("id"),
            rs.getInt("id_animal"),
            rs.getDate("data").toLocalDate(),
            rs.getString("motivo"),
            rs.getDouble("valor")
        );
    }
}
