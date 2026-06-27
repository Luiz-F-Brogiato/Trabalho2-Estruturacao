package com.oficina.service;

import com.oficina.model.OrdemServico;
import com.oficina.model.OrdemServico.Status;
import com.oficina.repository.OrdemServicoRepository;
import com.oficina.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;

public class OrdemServicoService {

    private final OrdemServicoRepository osRepository     = new OrdemServicoRepository();
    private final VeiculoRepository veiculoRepository     = new VeiculoRepository();

    public OrdemServico abrir(int idVeiculo, String descricao, double valor) throws SQLException {
        // RN: não abrir OS para veículo não cadastrado
        veiculoRepository.buscarPorId(idVeiculo)
            .orElseThrow(() -> new IllegalArgumentException(
                "Veículo não encontrado (id=" + idVeiculo + "). Cadastre o veículo antes de abrir a OS."));

        // RN: valor não pode ser negativo
        if (valor < 0)
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");

        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("A descrição do problema é obrigatória.");

        return osRepository.salvar(new OrdemServico(idVeiculo, descricao.trim(), valor, Status.ABERTA));
    }

    public OrdemServico buscarPorId(int id) throws SQLException {
        return osRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada: id=" + id));
    }

    public List<OrdemServico> listarTodas() throws SQLException {
        return osRepository.listarTodas();
    }

    // RN: histórico de manutenções de um veículo
    public List<OrdemServico> historicoPorVeiculo(int idVeiculo) throws SQLException {
        veiculoRepository.buscarPorId(idVeiculo)
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado: id=" + idVeiculo));
        return osRepository.buscarPorVeiculo(idVeiculo);
    }

    public OrdemServico concluir(int id) throws SQLException {
        OrdemServico os = buscarPorId(id);
        os.setStatus(Status.CONCLUIDA);
        osRepository.atualizar(os);
        return os;
    }

    public void remover(int id) throws SQLException {
        buscarPorId(id);
        osRepository.deletar(id);
    }
}
