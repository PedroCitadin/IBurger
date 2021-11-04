package com.example.iburguer.entity;

public class Produto {
    private String id;
    private String nome, descricao;
    private String valor;
    private Hamburgueria hamburgueria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Hamburgueria getHamburgueria() {
        return hamburgueria;
    }

    public void setHamburgueria(Hamburgueria hamburgueria) {
        this.hamburgueria = hamburgueria;
    }
}
