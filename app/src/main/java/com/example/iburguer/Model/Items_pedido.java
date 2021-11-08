package com.example.iburguer.Model;

public class Items_pedido {
    private String obsercavao, produtoId;
    private int quantidade;

    public Items_pedido(String obsercavao, String produtoId, int quantidade) {
        this.obsercavao = obsercavao;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public String getObsercavao() {
        return obsercavao;
    }

    public Items_pedido() {
    }

    public void setObsercavao(String obsercavao) {
        this.obsercavao = obsercavao;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
