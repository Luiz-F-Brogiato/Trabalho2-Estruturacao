package com.oficina.service;

import com.oficina.model.Cliente;
import com.oficina.model.Veiculo;
import com.oficina.repository.ClienteRepository;
import com.oficina.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public Cliente cadastrar(String nome, String telefone) throws SQLException {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        if (telefone == null || telefone.isBlank())
            throw new IllegalArgumentException("Telefone do cliente é obrigatório.");
        return clienteRepository.salvar(new Cliente(nome.trim(), telefone.trim()));
    }

    public Cliente buscarPorId(int id) throws SQLException {
        return clienteRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: id=" + id));
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteRepository.listarTodos();
    }

    public Cliente atualizar(int id, String nome, String telefone) throws SQLException {
        Cliente c = buscarPorId(id);
        if (nome != null && !nome.isBlank())         c.setNome(nome.trim());
        if (telefone != null && !telefone.isBlank()) c.setTelefone(telefone.trim());
        clienteRepository.atualizar(c);
        return c;
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        clienteRepository.deletar(id);
    }

    public List<Veiculo> listarVeiculosDoCliente(int idCliente) throws SQLException {
        buscarPorId(idCliente);
        return veiculoRepository.buscarPorCliente(idCliente);
    }
}
