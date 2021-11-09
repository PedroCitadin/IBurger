package com.example.iburguer.Model;

import com.example.iburguer.entity.Cliente;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pedido {
    private String id;
    private String clienteId, enderecoId, hamburgueriaId, situacao, pagamento;
    private float valorTotal;
    private List<Items_pedido> itensPedido;
    private String data_pedido;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pedido() {
    }

    public String getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(String data_pedido) {
        this.data_pedido = data_pedido;
    }

    public Pedido(String clienteId, String enderecoId, String hamburgueriaId, String situacao, String pagamento, float valorTotal, List<Items_pedido> itensPedido, String data_pedido) {
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.hamburgueriaId = hamburgueriaId;
        this.situacao = situacao;
        this.pagamento = pagamento;
        this.valorTotal = valorTotal;
        this.itensPedido = itensPedido;
        this.data_pedido = data_pedido;
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

    @Override
    public String toString() {
        return "R$ "+valorTotal+" - "+situacao+" - "+data_pedido;
    }


    public static Map<String, Object> retornaMap(Pedido ped){
        Map<String, Object> map = new HashMap<>();
        map.put("situacao", ped.getSituacao());

        return map;


    }
}
