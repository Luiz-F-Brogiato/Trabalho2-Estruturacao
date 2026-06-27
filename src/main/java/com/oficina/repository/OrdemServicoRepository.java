package com.oficina.repository;

import com.oficina.model.OrdemServico;
import com.oficina.model.OrdemServico.Status;
import com.oficina.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    public OrdemServico salvar(OrdemServico os) throws SQLException {
        String sql = "INSERT INTO ordem_servico (id_veiculo, descricao, valor, status) VALUES (?, ?, ?, ?::status_os) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setDouble(3, os.getValor());
            stmt.setString(4, os.getStatus().name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) os.setId(rs.getInt("id"));
        }
        return os;
    }

    public Optional<OrdemServico> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        }
        return Optional.empty();
    }

    public List<OrdemServico> listarTodas() throws SQLException {
        String sql = "SELECT * FROM ordem_servico ORDER BY id DESC";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<OrdemServico> buscarPorVeiculo(int idVeiculo) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE id_veiculo = ? ORDER BY id DESC";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(OrdemServico os) throws SQLException {
        String sql = "UPDATE ordem_servico SET id_veiculo = ?, descricao = ?, valor = ?, status = ?::status_os WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setDouble(3, os.getValor());
            stmt.setString(4, os.getStatus().name());
            stmt.setInt(5, os.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private OrdemServico mapear(ResultSet rs) throws SQLException {
        return new OrdemServico(
            rs.getInt("id"), rs.getInt("id_veiculo"),
            rs.getString("descricao"), rs.getDouble("valor"),
            Status.valueOf(rs.getString("status"))
        );
    }
}
