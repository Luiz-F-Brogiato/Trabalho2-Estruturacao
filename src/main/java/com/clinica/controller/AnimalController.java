package com.clinica.controller;

import com.clinica.model.Animal;
import com.clinica.service.AnimalService;

import java.sql.SQLException;
import java.util.List;

public class AnimalController {

    private final AnimalService animalService = new AnimalService();

    public Animal cadastrar(String nome, String especie, String raca, int idTutor) {
        try {
            Animal animal = animalService.cadastrar(nome, especie, raca, idTutor);
            System.out.println("[OK] Animal cadastrado: " + animal);
            return animal;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listar() {
        try {
            List<Animal> animais = animalService.listarTodos();
            if (animais.isEmpty()) {
                System.out.println("[INFO] Nenhum animal cadastrado.");
            } else {
                System.out.println("=== Animais ===");
                animais.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void atualizar(int id, String nome, String especie, String raca) {
        try {
            Animal animal = animalService.atualizar(id, nome, especie, raca);
            System.out.println("[OK] Animal atualizado: " + animal);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            animalService.remover(id);
            System.out.println("[OK] Animal id=" + id + " removido.");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }
}
