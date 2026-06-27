package com.oficina.controller;

import com.oficina.model.Veiculo;
import com.oficina.service.VeiculoService;

import java.sql.SQLException;
import java.util.List;

public class VeiculoController {

    private final VeiculoService veiculoService = new VeiculoService();

    public Veiculo cadastrar(String placa, String modelo, int ano, int idCliente) {
        try {
            Veiculo v = veiculoService.cadastrar(placa, modelo, ano, idCliente);
            System.out.println("[OK] Veículo cadastrado: " + v);
            return v;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listar() {
        try {
            List<Veiculo> lista = veiculoService.listarTodos();
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhum veículo cadastrado."); return; }
            System.out.println("=== Veículos ===");
            lista.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void remover(int id) {
        try {
            veiculoService.remover(id);
            System.out.println("[OK] Veículo id=" + id + " removido.");
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }
}
