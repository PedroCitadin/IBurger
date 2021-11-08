package com.example.iburguer.entity;

import java.util.HashMap;
import java.util.Map;

public class Cliente {

    private String nome, sobrenome, cpf, email, telefone;

    public Cliente(){}

    public Cliente(String nome, String sobrenome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public static Map<String, Object> retornaMap(Cliente c){
        Map<String, Object> map = new HashMap<>();
        map.put("nome", c.getNome());
        map.put("sobrenome", c.getSobrenome());
        map.put("cpf", c.getCpf());
        map.put("telefone", c.getTelefone());

        return map;


    }
}
