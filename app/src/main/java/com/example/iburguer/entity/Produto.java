package com.example.iburguer.entity;

public class Produto {
    private long id;
    private String nome, descricao;
    private Float valor;
    private Hamburgueria hamburgueria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Hamburgueria getHamburgueria() {
        return hamburgueria;
    }

    public void setHamburgueria(Hamburgueria hamburgueria) {
        this.hamburgueria = hamburgueria;
    }
}
