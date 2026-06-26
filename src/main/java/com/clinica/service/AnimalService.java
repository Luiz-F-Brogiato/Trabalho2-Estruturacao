package com.clinica.service;

import com.clinica.model.Animal;
import com.clinica.repository.AnimalRepository;
import com.clinica.repository.TutorRepository;

import java.sql.SQLException;
import java.util.List;

public class AnimalService {

    private final AnimalRepository animalRepository = new AnimalRepository();
    private final TutorRepository tutorRepository   = new TutorRepository();

    public Animal cadastrar(String nome, String especie, String raca, int idTutor) throws SQLException {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do animal é obrigatório.");
        if (especie == null || especie.isBlank())
            throw new IllegalArgumentException("Espécie do animal é obrigatória.");

        // RN: animal só pode ser cadastrado se o tutor existir
        tutorRepository.buscarPorId(idTutor)
            .orElseThrow(() -> new IllegalArgumentException(
                "Tutor não encontrado (id=" + idTutor + "). Cadastre o tutor antes do animal."));

        Animal animal = new Animal(nome.trim(), especie.trim(), raca, idTutor);
        return animalRepository.salvar(animal);
    }

    public Animal buscarPorId(int id) throws SQLException {
        return animalRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado: id=" + id));
    }

    public List<Animal> listarTodos() throws SQLException {
        return animalRepository.listarTodos();
    }

    public Animal atualizar(int id, String nome, String especie, String raca) throws SQLException {
        Animal animal = buscarPorId(id);
        if (nome != null && !nome.isBlank())     animal.setNome(nome.trim());
        if (especie != null && !especie.isBlank()) animal.setEspecie(especie.trim());
        if (raca != null)                          animal.setRaca(raca);
        animalRepository.atualizar(animal);
        return animal;
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        animalRepository.deletar(id);
    }
}
