package com.clinica.model;

import java.time.LocalDate;

public class Consulta {

    private int id;
    private int idAnimal;
    private LocalDate data;
    private String motivo;
    private double valor;

    public Consulta() {}

    public Consulta(int idAnimal, LocalDate data, String motivo, double valor) {
        this.idAnimal = idAnimal;
        this.data     = data;
        this.motivo   = motivo;
        this.valor    = valor;
    }

    public Consulta(int id, int idAnimal, LocalDate data, String motivo, double valor) {
        this.id       = id;
        this.idAnimal = idAnimal;
        this.data     = data;
        this.motivo   = motivo;
        this.valor    = valor;
    }

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public int getIdAnimal()             { return idAnimal; }
    public void setIdAnimal(int idAnimal){ this.idAnimal = idAnimal; }

    public LocalDate getData()               { return data; }
    public void setData(LocalDate data)      { this.data = data; }

    public String getMotivo()                { return motivo; }
    public void setMotivo(String motivo)     { this.motivo = motivo; }

    public double getValor()                 { return valor; }
    public void setValor(double valor)       { this.valor = valor; }

    @Override
    public String toString() {
        return "Consulta{id=" + id + ", idAnimal=" + idAnimal + ", data=" + data +
               ", motivo='" + motivo + "', valor=" + valor + "}";
    }
}
