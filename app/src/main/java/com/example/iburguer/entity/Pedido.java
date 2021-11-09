package com.example.iburguer.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Pedido {
    private String id;
    private Hamburgueria hamburgueria;
    private Cliente cliente;
    private float valor;
    private Endereco endereco;
    private List<Items_pedido> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String pegaData(){
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String aux = formataData.format(data);
        String hora = String.valueOf(Calendar.getInstance().getTime().getHours()+":"+Calendar.getInstance().getTime().getMinutes());


        return aux +" "+hora;



    }

    public static String converteSituacao(com.example.iburguer.Model.Pedido ped){
        String sit = "";
        if (ped.getSituacao().equalsIgnoreCase("AGUARDANDO APROVACAO")){
            return "Aguardando Aprovação";
        }else if(ped.getSituacao().equalsIgnoreCase("EM PRODUCAO")){
            return "Em Produção";
        }else if(ped.getSituacao().equalsIgnoreCase("ENVIADO")){
            return "Enviado";
        }else if(ped.getSituacao().equalsIgnoreCase("FINALIZADO")){
            return "Finalizado";
        }


        return sit;
    }
}
