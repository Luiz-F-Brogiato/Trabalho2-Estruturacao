package com.oficina.service;

import com.oficina.model.Veiculo;
import com.oficina.repository.ClienteRepository;
import com.oficina.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;

public class VeiculoService {

    private final VeiculoRepository veiculoRepository = new VeiculoRepository();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    public Veiculo cadastrar(String placa, String modelo, int ano, int idCliente) throws SQLException {
        if (placa == null || placa.isBlank())
            throw new IllegalArgumentException("Placa do veículo é obrigatória.");
        if (modelo == null || modelo.isBlank())
            throw new IllegalArgumentException("Modelo do veículo é obrigatório.");

        clienteRepository.buscarPorId(idCliente)
            .orElseThrow(() -> new IllegalArgumentException(
                "Cliente não encontrado (id=" + idCliente + "). Cadastre o cliente antes do veículo."));

        return veiculoRepository.salvar(new Veiculo(placa.trim().toUpperCase(), modelo.trim(), ano, idCliente));
    }

    public Veiculo buscarPorId(int id) throws SQLException {
        return veiculoRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado: id=" + id));
    }

    public List<Veiculo> listarTodos() throws SQLException {
        return veiculoRepository.listarTodos();
    }

    public Veiculo atualizar(int id, String placa, String modelo, int ano) throws SQLException {
        Veiculo v = buscarPorId(id);
        if (placa != null && !placa.isBlank())   v.setPlaca(placa.trim().toUpperCase());
        if (modelo != null && !modelo.isBlank()) v.setModelo(modelo.trim());
        if (ano > 0)                             v.setAno(ano);
        veiculoRepository.atualizar(v);
        return v;
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        veiculoRepository.deletar(id);
    }
}
