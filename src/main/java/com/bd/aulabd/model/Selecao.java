package com.bd.aulabd.model;

public enum Selecao {
    BRASIL("Brasil", "BRA"),
    ESPANHA("Espanha", "ESP"),
    FRANCA("França", "FRA"),
    PORTUGAL("Portugal", "POR"),
    ARGENTINA("Argentina", "ARG"),
    INGLATERRA("Inglaterra", "ENG");

    private final String label;
    private final String sigla;

    Selecao(String label, String sigla) {
        this.label = label;
        this.sigla = sigla;
    }

    public String getLabel() {
        return label;
    }

    public String getSigla() {
        return sigla;
    }

    public static Selecao fromCodigo(String codigo) {
        if (codigo == null) return null;
        try {
            return Selecao.valueOf(codigo.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
