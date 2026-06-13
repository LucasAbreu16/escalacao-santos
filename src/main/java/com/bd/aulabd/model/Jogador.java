package com.bd.aulabd.model;

import java.util.Map;

public class Jogador {

    private int id;
    private String nome;
    private Posicao posicao;
    private Selecao selecao;

    public Jogador() {}

    public Jogador(int id, String nome, Posicao posicao, Selecao selecao) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.selecao = selecao;
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

    public Selecao getSelecao() {
        return selecao;
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

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public static Jogador converterRegistros(Map<String, Object> registros) {
        int id = ((Number) registros.get("id")).intValue();
        String nome = (String) registros.get("nome");
        String posicao = (String) registros.get("posicao");
        String selecao = (String) registros.get("selecao");
        return new Jogador(id, nome, Posicao.valueOf(posicao), Selecao.valueOf(selecao));
    }
}
