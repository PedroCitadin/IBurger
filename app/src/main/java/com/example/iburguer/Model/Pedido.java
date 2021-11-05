package com.example.iburguer.Model;

import java.util.List;

public class Pedido {
    private String clienteId, enderecoId, hamburgueriaId, situacao, pagamento;
    private float valorTotal;
    private List<Items_pedido> itensPedido;

    public Pedido(String clienteId, String enderecoId, String hamburgueriaId, String situacao, String pagamento, float valorTotal, List<Items_pedido> itensPedido) {
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.hamburgueriaId = hamburgueriaId;
        this.situacao = situacao;
        this.pagamento = pagamento;
        this.valorTotal = valorTotal;
        this.itensPedido = itensPedido;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getHamburgueriaId() {
        return hamburgueriaId;
    }

    public void setHamburgueriaId(String hamburgueriaId) {
        this.hamburgueriaId = hamburgueriaId;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Items_pedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<Items_pedido> itensPedido) {
        this.itensPedido = itensPedido;
    }
}
