package com.oficina.controller;

import com.oficina.model.OrdemServico;
import com.oficina.service.OrdemServicoService;

import java.sql.SQLException;
import java.util.List;

public class OrdemServicoController {

    private final OrdemServicoService osService = new OrdemServicoService();

    public OrdemServico abrir(int idVeiculo, String descricao, double valor) {
        try {
            OrdemServico os = osService.abrir(idVeiculo, descricao, valor);
            System.out.println("[OK] Ordem de serviço aberta: " + os);
            return os;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void concluir(int id) {
        try {
            OrdemServico os = osService.concluir(id);
            System.out.println("[OK] OS concluída: " + os);
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void listarTodas() {
        try {
            List<OrdemServico> lista = osService.listarTodas();
            if (lista.isEmpty()) { System.out.println("[INFO] Nenhuma OS registrada."); return; }
            System.out.println("=== Ordens de Serviço ===");
            lista.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void historicoPorVeiculo(int idVeiculo) {
        try {
            List<OrdemServico> lista = osService.historicoPorVeiculo(idVeiculo);
            if (lista.isEmpty()) { System.out.println("[INFO] Veículo id=" + idVeiculo + " não possui OS."); return; }
            System.out.println("=== Histórico do Veículo id=" + idVeiculo + " ===");
            lista.forEach(System.out::println);
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }

    public void remover(int id) {
        try {
            osService.remover(id);
            System.out.println("[OK] OS id=" + id + " removida.");
        } catch (IllegalArgumentException | SQLException e) { System.out.println("[ERRO] " + e.getMessage()); }
    }
}
