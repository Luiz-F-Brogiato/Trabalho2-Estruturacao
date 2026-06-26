package com.clinica.controller;

import com.clinica.model.Animal;
import com.clinica.model.Tutor;
import com.clinica.service.TutorService;

import java.sql.SQLException;
import java.util.List;

public class TutorController {

    private final TutorService tutorService = new TutorService();

    public Tutor cadastrar(String nome, String endereco, String telefone) {
        try {
            Tutor tutor = tutorService.cadastrar(nome, endereco, telefone);
            System.out.println("[OK] Tutor cadastrado: " + tutor);
            return tutor;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
            return null;
        }
    }

    public void listar() {
        try {
            List<Tutor> tutores = tutorService.listarTodos();
            if (tutores.isEmpty()) {
                System.out.println("[INFO] Nenhum tutor cadastrado.");
            } else {
                System.out.println("=== Tutores ===");
                tutores.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void listarAnimaisDoTutor(int idTutor) {
        try {
            List<Animal> animais = tutorService.listarAnimaisDoTutor(idTutor);
            if (animais.isEmpty()) {
                System.out.println("[INFO] Tutor id=" + idTutor + " não possui animais cadastrados.");
            } else {
                System.out.println("=== Animais do Tutor id=" + idTutor + " ===");
                animais.forEach(System.out::println);
            }
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void atualizar(int id, String nome, String endereco, String telefone) {
        try {
            Tutor tutor = tutorService.atualizar(id, nome, endereco, telefone);
            System.out.println("[OK] Tutor atualizado: " + tutor);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            tutorService.remover(id);
            System.out.println("[OK] Tutor id=" + id + " removido.");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }
}
