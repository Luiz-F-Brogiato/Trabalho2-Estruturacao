package com.clinica.repository;

import com.clinica.model.Animal;
import com.clinica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepository {

    public Animal salvar(Animal animal) throws SQLException {
        String sql = "INSERT INTO animal (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdTutor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                animal.setId(rs.getInt("id"));
            }
        }
        return animal;
    }

    public Optional<Animal> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM animal WHERE id = ?";
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

    public List<Animal> listarTodos() throws SQLException {
        String sql = "SELECT * FROM animal ORDER BY nome";
        List<Animal> animais = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                animais.add(mapear(rs));
            }
        }
        return animais;
    }

    public List<Animal> buscarPorTutor(int idTutor) throws SQLException {
        String sql = "SELECT * FROM animal WHERE id_tutor = ? ORDER BY nome";
        List<Animal> animais = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTutor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                animais.add(mapear(rs));
            }
        }
        return animais;
    }

    public void atualizar(Animal animal) throws SQLException {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, id_tutor = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdTutor());
            stmt.setInt(5, animal.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Animal mapear(ResultSet rs) throws SQLException {
        return new Animal(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("especie"),
            rs.getString("raca"),
            rs.getInt("id_tutor")
        );
    }
}
