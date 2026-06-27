package com.oficina;

import com.oficina.controller.ClienteController;
import com.oficina.controller.OrdemServicoController;
import com.oficina.controller.VeiculoController;
import com.oficina.model.Cliente;
import com.oficina.model.Veiculo;

public class Main {

    public static void main(String[] args) {

        ClienteController clienteController   = new ClienteController();
        VeiculoController veiculoController   = new VeiculoController();
        OrdemServicoController osController   = new OrdemServicoController();

        System.out.println("========================================");
        System.out.println("  SISTEMA - OFICINA MECÂNICA");
        System.out.println("========================================\n");

        // ── 1. Cadastro de Clientes ───────────────────────────────────────────
        System.out.println("--- Cadastrando clientes ---");
        Cliente c1 = clienteController.cadastrar("Carlos Pereira", "44988880001");
        Cliente c2 = clienteController.cadastrar("Ana Lima",       "44988880002");

        System.out.println("\n-- Tentativa inválida (sem telefone) --");
        clienteController.cadastrar("Sem Telefone", "");

        // ── 2. Listagem de clientes ───────────────────────────────────────────
        System.out.println("\n--- Listando todos os clientes ---");
        clienteController.listar();

        // ── 3. Cadastro de Veículos ───────────────────────────────────────────
        System.out.println("\n--- Cadastrando veículos ---");
        Veiculo v1 = veiculoController.cadastrar("ABC1234", "Fiat Uno",      2018, c1.getId());
        Veiculo v2 = veiculoController.cadastrar("DEF5678", "VW Gol",        2020, c1.getId());
        Veiculo v3 = veiculoController.cadastrar("GHI9012", "Honda Civic",   2022, c2.getId());

        System.out.println("\n-- Tentativa com cliente inexistente --");
        veiculoController.cadastrar("XYZ0000", "Fantasma", 2000, 9999);

        // ── 4. Ver veículos do cliente ────────────────────────────────────────
        System.out.println("\n--- Veículos de " + c1.getNome() + " ---");
        clienteController.listarVeiculosDoCliente(c1.getId());

        // ── 5. Abrir Ordens de Serviço (o movimento) ─────────────────────────
        System.out.println("\n--- Abrindo ordens de serviço ---");
        osController.abrir(v1.getId(), "Troca de óleo e filtro",        250.00);
        osController.abrir(v1.getId(), "Freios dianteiros desgastados", 480.00);
        osController.abrir(v2.getId(), "Barulho no câmbio",             350.00);
        osController.abrir(v3.getId(), "Revisão completa 30.000 km",    890.00);

        System.out.println("\n-- Tentativa com valor negativo --");
        osController.abrir(v1.getId(), "Alinhamento", -50.00);

        System.out.println("\n-- Tentativa com veículo inexistente --");
        osController.abrir(9999, "OS fantasma", 100.00);

        // ── 6. Concluir uma OS ────────────────────────────────────────────────
        System.out.println("\n--- Concluindo OS id=1 ---");
        osController.concluir(1);

        // ── 7. Histórico do veículo ───────────────────────────────────────────
        System.out.println("\n--- Histórico do veículo " + v1.getPlaca() + " ---");
        osController.historicoPorVeiculo(v1.getId());

        // ── 8. Listagem geral ─────────────────────────────────────────────────
        System.out.println("\n--- Todas as ordens de serviço ---");
        osController.listarTodas();

        System.out.println("\n========================================");
        System.out.println("  FIM DA SIMULAÇÃO");
        System.out.println("========================================");
    }
}
