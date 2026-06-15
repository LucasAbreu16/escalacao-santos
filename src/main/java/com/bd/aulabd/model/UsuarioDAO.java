package com.bd.aulabd.model;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class UsuarioDAO {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ?";
        Integer qtd = jdbc.queryForObject(sql, Integer.class, username);
        return qtd != null && qtd > 0;
    }

    public void inserirUsuario(Usuario u) {
        String sql = "INSERT INTO usuario(username, senha) VALUES(?,?)";
        Object[] obj = new Object[2];
        obj[0] = u.getUsername();
        obj[1] = u.getSenha();
        jdbc.update(sql, obj);
    }

    public Usuario obterPorUsername(String username) {
        String sql = "SELECT * FROM usuario WHERE username = ?";
        return Usuario.converterRegistros((Map<String, Object>) jdbc.queryForMap(sql, username));
    }
}

