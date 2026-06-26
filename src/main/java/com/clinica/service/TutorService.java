package com.clinica.service;

import com.clinica.model.Animal;
import com.clinica.model.Tutor;
import com.clinica.repository.AnimalRepository;
import com.clinica.repository.TutorRepository;

import java.sql.SQLException;
import java.util.List;

public class TutorService {

    private final TutorRepository tutorRepository   = new TutorRepository();
    private final AnimalRepository animalRepository = new AnimalRepository();

    public Tutor cadastrar(String nome, String endereco, String telefone) throws SQLException {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do tutor é obrigatório.");
        if (telefone == null || telefone.isBlank())
            throw new IllegalArgumentException("Telefone do tutor é obrigatório.");

        Tutor tutor = new Tutor(nome.trim(), endereco, telefone.trim());
        return tutorRepository.salvar(tutor);
    }

    public Tutor buscarPorId(int id) throws SQLException {
        return tutorRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado: id=" + id));
    }

    public List<Tutor> listarTodos() throws SQLException {
        return tutorRepository.listarTodos();
    }

    public Tutor atualizar(int id, String nome, String endereco, String telefone) throws SQLException {
        Tutor tutor = buscarPorId(id);
        if (nome != null && !nome.isBlank())      tutor.setNome(nome.trim());
        if (endereco != null)                      tutor.setEndereco(endereco);
        if (telefone != null && !telefone.isBlank()) tutor.setTelefone(telefone.trim());
        tutorRepository.atualizar(tutor);
        return tutor;
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id); // garante que existe
        tutorRepository.deletar(id);
    }

    public List<Animal> listarAnimaisDoTutor(int idTutor) throws SQLException {
        buscarPorId(idTutor); // garante que tutor existe
        return animalRepository.buscarPorTutor(idTutor);
    }
}
