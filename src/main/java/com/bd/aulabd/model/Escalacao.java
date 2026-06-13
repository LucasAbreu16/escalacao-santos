package com.bd.aulabd.model;

import java.util.Map;

public class Escalacao {

    private int id;
    private int usuarioId;
    private String nome; // nome da escalacao
    private String formacao; // "4-4-2", "4-3-3", "4-5-1"
    private Selecao selecao;

    // construtor vazio
    public Escalacao() {}

    // insert - constructor
    public Escalacao(int usuarioId, String nome, String formacao, Selecao selecao) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.formacao = formacao;
        this.selecao = selecao;
    }

    // select - constructor
    public Escalacao(int id, int usuarioId, String nome, String formacao, Selecao selecao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.formacao = formacao;
        this.selecao = selecao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getFormacao() {
        return formacao;
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

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
    }

    public static Escalacao converterRegistros(Map<String, Object> registros) {
        int id = ((Number) registros.get("id")).intValue();
        int usuarioId = ((Number) registros.get("usuario_id")).intValue();
        String nome = (String) registros.get("nome");
        String formacao = (String) registros.get("formacao");
        String selecao = (String) registros.get("selecao");
        return new Escalacao(id, usuarioId, nome, formacao, Selecao.valueOf(selecao));
    }
}
