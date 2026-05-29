package com.bd.aulabd.model;

public enum Formacao {
    F442("4-4-2", 1, 2, 2, 4, 2),
    F433("4-3-3", 1, 2, 2, 3, 3),
    F451("4-5-1", 1, 2, 2, 5, 1);

    private final String codigo;
    private final int goleiros;
    private final int laterais;
    private final int zagueiros;
    private final int meioCampistas;
    private final int atacantes;

    Formacao(String codigo, int goleiros, int laterais, int zagueiros, int meioCampistas, int atacantes) {
        this.codigo = codigo;
        this.goleiros = goleiros;
        this.laterais = laterais;
        this.zagueiros = zagueiros;
        this.meioCampistas = meioCampistas;
        this.atacantes = atacantes;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getGoleiros() {
        return goleiros;
    }

    public int getLaterais() {
        return laterais;
    }

    public int getZagueiros() {
        return zagueiros;
    }

    public int getMeioCampistas() {
        return meioCampistas;
    }

    public int getAtacantes() {
        return atacantes;
    }

    public static Formacao fromCodigo(String codigo) {
        if (codigo == null) return null;
        for (Formacao f : values()) {
            if (f.codigo.equals(codigo)) return f;
        }
        return null;
    }
}

