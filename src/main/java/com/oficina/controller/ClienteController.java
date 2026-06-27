package com.oficina.controller;

import com.oficina.model.Cliente;
import com.oficina.model.Veiculo;
import com.oficina.service.ClienteService;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private final ClienteService clienteService = new ClienteService();

    public Cliente cadastrar(String nome, String telefone) {
        try {
            Cliente c = clienteService.cadastrar(nome, telefone);
            System.out.println("[OK] Cliente cadastrado: " + c);
            return c;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listar() {
        try {
            List<Cliente> lista = clienteService.listarTodos();
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhum cliente cadastrado."); return; }
            System.out.println("=== Clientes ===");
            lista.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void listarVeiculosDoCliente(int idCliente) {
        try {
            List<Veiculo> lista = clienteService.listarVeiculosDoCliente(idCliente);
            if (lista.isEmpty()) { System.out.println("[INFO] Cliente id=" + idCliente + " não possui veículos."); return; }
            System.out.println("=== Veículos do Cliente id=" + idCliente + " ===");
            lista.forEach(System.out::println);
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void remover(int id) {
        try {
            clienteService.remover(id);
            System.out.println("[OK] Cliente id=" + id + " removido.");
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }
}
