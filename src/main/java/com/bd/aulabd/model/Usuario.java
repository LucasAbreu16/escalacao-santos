package com.bd.aulabd.model;

import java.util.Map;

public class Usuario {

    private int id;
    private String username;
    private String senha;
    private boolean enabled;

    public Usuario() {}

    public Usuario(String username, String senha, boolean enabled) {
        this.username = username;
        this.senha = senha;
        this.enabled = enabled;
    }

    public Usuario(int id, String username, String senha, boolean enabled) {
        this.id = id;
        this.username = username;
        this.senha = senha;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static Usuario converterRegistros(Map<String, Object> registros) {
        int id = ((Number) registros.get("id")).intValue();
        String username = (String) registros.get("username");
        String senha = (String) registros.get("senha");
        boolean enabled = (boolean) registros.get("enabled");
        return new Usuario(id, username, senha, enabled);
    }
}

