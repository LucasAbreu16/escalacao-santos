package com.bd.aulabd.model;



import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import java.util.Map;



import javax.sql.DataSource;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;



import jakarta.annotation.PostConstruct;



@Repository

public class JogadorDAO {



    @Autowired

    DataSource dataSource;



    JdbcTemplate jdbc;



    @PostConstruct

    private void initialize() {

        jdbc = new JdbcTemplate(dataSource);

    }



    public List<Jogador> obterPorSelecao(Selecao selecao) {

        String sql = "SELECT * FROM jogador WHERE selecao = ? ORDER BY posicao, nome";

        List<Map<String, Object>> listaRegistros = jdbc.queryForList(sql, selecao.name());

        ArrayList<Jogador> aux = new ArrayList<>();

        for (Map<String, Object> registro : listaRegistros) {

            aux.add(Jogador.converterRegistros(registro));

        }

        return aux;

    }



    public List<Jogador> obterPorIds(List<Integer> ids) {

        if (ids == null || ids.isEmpty()) return Collections.emptyList();



        StringBuilder sb = new StringBuilder("SELECT * FROM jogador WHERE id IN (");

        for (int i = 0; i < ids.size(); i++) {

            sb.append("?");

            if (i < ids.size() - 1) sb.append(",");

        }

        sb.append(") ORDER BY posicao, nome");



        Object[] params = ids.toArray();

        List<Map<String, Object>> listaRegistros = jdbc.queryForList(sb.toString(), params);

        ArrayList<Jogador> aux = new ArrayList<>();

        for (Map<String, Object> registro : listaRegistros) {

            aux.add(Jogador.converterRegistros(registro));

        }

        return aux;

    }

}



