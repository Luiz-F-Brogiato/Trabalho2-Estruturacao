package com.clinica;

import com.clinica.controller.AnimalController;
import com.clinica.controller.ConsultaController;
import com.clinica.controller.TutorController;
import com.clinica.model.Animal;
import com.clinica.model.Tutor;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        TutorController tutorController       = new TutorController();
        AnimalController animalController     = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        System.out.println("========================================");
        System.out.println("  SISTEMA - CLÍNICA VETERINÁRIA");
        System.out.println("========================================\n");

        // ── 1. Cadastro de Tutores ────────────────────────────────────────────
        System.out.println("--- Cadastrando tutores ---");
        Tutor tutor1 = tutorController.cadastrar(
                "Maria Oliveira", "Rua das Flores, 100 - Cianorte", "44999990001");
        Tutor tutor2 = tutorController.cadastrar(
                "João Santos", "Av. Paraná, 250 - Cianorte", "44999990002");

        // Tentativa com dados inválidos (deve exibir erro)
        System.out.println("\n-- Tentativa de cadastro inválido (sem nome) --");
        tutorController.cadastrar("", "Rua X", "44988880000");

        // ── 2. Listagem de tutores ────────────────────────────────────────────
        System.out.println("\n--- Listando todos os tutores ---");
        tutorController.listar();

        // ── 3. Cadastro de Animais ────────────────────────────────────────────
        System.out.println("\n--- Cadastrando animais ---");
        Animal animal1 = animalController.cadastrar("Rex",    "Cachorro", "Labrador",         tutor1.getId());
        Animal animal2 = animalController.cadastrar("Mimi",   "Gato",     "Siamês",           tutor1.getId());
        Animal animal3 = animalController.cadastrar("Bolinha","Cachorro",  "Poodle",           tutor2.getId());

        // Tentativa com tutor inexistente (deve exibir erro)
        System.out.println("\n-- Tentativa com tutor inexistente --");
        animalController.cadastrar("Fantasma", "Gato", "SRD", 9999);

        // ── 4. Ver animais de um tutor ────────────────────────────────────────
        System.out.println("\n--- Animais de " + tutor1.getNome() + " ---");
        tutorController.listarAnimaisDoTutor(tutor1.getId());

        // ── 5. Registrar Consultas (o movimento) ─────────────────────────────
        System.out.println("\n--- Registrando consultas ---");
        consultaController.registrar(
                animal1.getId(), LocalDate.now(), "Vacina anual", 120.00);
        consultaController.registrar(
                animal1.getId(), LocalDate.now().minusDays(30), "Otite", 85.50);
        consultaController.registrar(
                animal2.getId(), LocalDate.now(), "Check-up geral", 95.00);

        // Tentativa com valor negativo (deve exibir erro)
        System.out.println("\n-- Tentativa de consulta com valor negativo --");
        consultaController.registrar(animal3.getId(), LocalDate.now(), "Tosse", -50.00);

        // Tentativa com animal inexistente (deve exibir erro)
        System.out.println("\n-- Tentativa de consulta para animal inexistente --");
        consultaController.registrar(9999, LocalDate.now(), "Consulta fantasma", 100.00);

        // ── 6. Histórico do animal ────────────────────────────────────────────
        System.out.println("\n--- Histórico de consultas de " + animal1.getNome() + " ---");
        consultaController.historicoPorAnimal(animal1.getId());

        // ── 7. Listagem geral ─────────────────────────────────────────────────
        System.out.println("\n--- Todas as consultas registradas ---");
        consultaController.listarTodas();

        System.out.println("\n========================================");
        System.out.println("  FIM DA SIMULAÇÃO");
        System.out.println("========================================");
    }
}
