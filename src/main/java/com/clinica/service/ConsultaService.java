package com.clinica.service;

import com.clinica.model.Consulta;
import com.clinica.repository.AnimalRepository;
import com.clinica.repository.ConsultaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository consultaRepository = new ConsultaRepository();
    private final AnimalRepository animalRepository     = new AnimalRepository();

    public Consulta registrar(int idAnimal, LocalDate data, String motivo, double valor) throws SQLException {
        // RN: não registrar consulta para animal não cadastrado
        animalRepository.buscarPorId(idAnimal)
            .orElseThrow(() -> new IllegalArgumentException(
                "Animal não encontrado (id=" + idAnimal + "). Cadastre o animal antes da consulta."));

        // RN: valor não pode ser negativo
        if (valor < 0)
            throw new IllegalArgumentException("O valor da consulta não pode ser negativo.");

        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("O motivo da consulta é obrigatório.");

        LocalDate dataConsulta = (data != null) ? data : LocalDate.now();

        Consulta consulta = new Consulta(idAnimal, dataConsulta, motivo.trim(), valor);
        return consultaRepository.salvar(consulta);
    }

    public Consulta buscarPorId(int id) throws SQLException {
        return consultaRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada: id=" + id));
    }

    public List<Consulta> listarTodas() throws SQLException {
        return consultaRepository.listarTodas();
    }

    // RN: histórico de atendimentos de um animal específico
    public List<Consulta> historicoPorAnimal(int idAnimal) throws SQLException {
        animalRepository.buscarPorId(idAnimal)
            .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado: id=" + idAnimal));
        return consultaRepository.buscarPorAnimal(idAnimal);
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        consultaRepository.deletar(id);
    }
}
