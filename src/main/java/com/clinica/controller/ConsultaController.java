package com.clinica.controller;

import com.clinica.model.Consulta;
import com.clinica.service.ConsultaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultaController {

    private final ConsultaService consultaService = new ConsultaService();

    public Consulta registrar(int idAnimal, LocalDate data, String motivo, double valor) {
        try {
            Consulta consulta = consultaService.registrar(idAnimal, data, motivo, valor);
            System.out.println("[OK] Consulta registrada: " + consulta);
            return consulta;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listarTodas() {
        try {
            List<Consulta> consultas = consultaService.listarTodas();
            if (consultas.isEmpty()) {
                System.out.println("[INFO] Nenhuma consulta registrada.");
            } else {
                System.out.println("=== Consultas ===");
                consultas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void historicoPorAnimal(int idAnimal) {
        try {
            List<Consulta> consultas = consultaService.historicoPorAnimal(idAnimal);
            if (consultas.isEmpty()) {
                System.out.println("[INFO] Animal id=" + idAnimal + " não possui consultas.");
            } else {
                System.out.println("=== Histórico do Animal id=" + idAnimal + " ===");
                consultas.forEach(System.out::println);
            }
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            consultaService.remover(id);
            System.out.println("[OK] Consulta id=" + id + " removida.");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }
}
