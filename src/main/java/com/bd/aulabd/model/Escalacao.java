package com.bd.aulabd.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Escalacao {

    private int id;
    private int usuarioId;
    private String nome; // nome da escalacao
    private String formacao; // "4-4-2", "4-3-3", "4-5-1"
    private LocalDateTime criadoEm; // data de criação da escalacao

    // construtor vazio
    public Escalacao() {} 

    // insert - constructor
    public Escalacao(int usuarioId, String nome, String formacao, LocalDateTime criadoEm) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.formacao = formacao;
        this.criadoEm = criadoEm;
    }
    // select - constructor
    public Escalacao(int id, int usuarioId, String nome, String formacao, LocalDateTime criadoEm) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.formacao = formacao;
        this.criadoEm = criadoEm;
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

    public LocalDateTime getCriadoEm() {
        return criadoEm;
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

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public static Escalacao converterRegistros(Map<String, Object> registros) {
        int id = ((Number) registros.get("id")).intValue();
        int usuarioId = ((Number) registros.get("usuario_id")).intValue();
        String nome = (String) registros.get("nome");
        String formacao = (String) registros.get("formacao");
        java.sql.Timestamp ts = (java.sql.Timestamp) registros.get("criado_em");
        LocalDateTime criadoEm = ts == null ? null : ts.toLocalDateTime();
        return new Escalacao(id, usuarioId, nome, formacao, criadoEm);
    }
}

