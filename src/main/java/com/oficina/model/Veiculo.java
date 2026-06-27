package com.oficina.model;

public class Veiculo {

    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private int idCliente;

    public Veiculo() {}

    public Veiculo(String placa, String modelo, int ano, int idCliente) {
        this.placa     = placa;
        this.modelo    = modelo;
        this.ano       = ano;
        this.idCliente = idCliente;
    }

    public Veiculo(int id, String placa, String modelo, int ano, int idCliente) {
        this.id        = id;
        this.placa     = placa;
        this.modelo    = modelo;
        this.ano       = ano;
        this.idCliente = idCliente;
    }

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public String getPlaca()             { return placa; }
    public void setPlaca(String placa)   { this.placa = placa; }

    public String getModelo()                { return modelo; }
    public void setModelo(String modelo)     { this.modelo = modelo; }

    public int getAno()                  { return ano; }
    public void setAno(int ano)          { this.ano = ano; }

    public int getIdCliente()                    { return idCliente; }
    public void setIdCliente(int idCliente)      { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Veiculo{id=" + id + ", placa='" + placa + "', modelo='" + modelo +
               "', ano=" + ano + ", idCliente=" + idCliente + "}";
    }
}
