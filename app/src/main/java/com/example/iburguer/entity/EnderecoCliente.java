package com.example.iburguer.entity;

public class EnderecoCliente {
    private String rua, numero, cidade, enderecoId;

    public EnderecoCliente(){}

    public EnderecoCliente(String rua, String numero, String cidade, String enderecoId) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoId = enderecoId;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }
}
