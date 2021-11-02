package com.example.iburguer.entity;

import java.util.List;

public class Pedido {
    private long id;
    private Hamburgueria hamburgueria;
    private Cliente cliente;
    private float valor;
    private Endereco endereco;
    private List<Items_pedido> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hamburgueria getHamburgueria() {
        return hamburgueria;
    }

    public void setHamburgueria(Hamburgueria hamburgueria) {
        this.hamburgueria = hamburgueria;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Items_pedido> getItems() {
        return items;
    }

    public void setItems(List<Items_pedido> items) {
        this.items = items;
    }
}
