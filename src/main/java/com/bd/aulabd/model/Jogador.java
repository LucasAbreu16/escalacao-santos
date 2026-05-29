package com.bd.aulabd.model;

import java.util.Map;

public class Jogador {

    private int id;
    private String nome;
    private Posicao posicao;
    private String foto;

    public Jogador() {}

    public Jogador(int id, String nome, Posicao posicao, String foto) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public static Jogador converterRegistros(Map<String, Object> registros) {
        int id = ((Number) registros.get("id")).intValue();
        String nome = (String) registros.get("nome");
        String posicao = (String) registros.get("posicao");
        String foto = (String) registros.get("foto");
        return new Jogador(id, nome, Posicao.valueOf(posicao), foto);
    }
}

