package com.example.iburguer.entity;

import java.util.List;

public class Items_pedido {
    private Produto produto;
    private int quantidade;
    private String observacao;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


    public static void conversao(List<Items_pedido> carrinho, List<String> listaP, List<Integer> listaQ, List<String> listaO){
        for (Items_pedido it : carrinho){
            listaP.add(it.getProduto().getId());
            listaQ.add(it.getQuantidade());
            listaO.add(it.getObservacao());
        }

    }
    public static void conversaoReversa(List<Items_pedido> carrinho, List<String> listaP, List<Integer> listaQ, List<String> listaO){
        for (int i = 0; i<listaP.size(); i++){
            Items_pedido it = new Items_pedido();
            it.setProduto(new Produto(listaP.get(i)));
            it.setQuantidade(listaQ.get(i));
            it.setObservacao(listaO.get(i));

            carrinho.add(it);
        }
    }

    @Override
    public String toString() {
        return produto.getNome()+"\n"+quantidade;
    }
}
