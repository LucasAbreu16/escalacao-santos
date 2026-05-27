
package com.bd.aulabd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class ClienteDAO {

    @Autowired
	DataSource dataSource;
	
	JdbcTemplate jdbc;
	
	@PostConstruct
	private void initialize() {
		jdbc = new JdbcTemplate(dataSource);
	}

    public void inserirCliente(Cliente cli){
        String sql = "INSERT INTO Cliente(nome,cpf) VALUES(?,?)";
        Object[] obj = new Object[2];
        obj[0] = cli.getNome();
        obj[1] = cli.getCpf();
        jdbc.update(sql,obj);
    }

    public void atualizarCliente(int id, Cliente novo){
        String sql = "UPDATE cliente SET nome = ?, cpf = ? where id = ?";
        Object[] obj = new Object[3];
        obj[0] = novo.getNome();
        obj[1] = novo.getCpf();
        obj[2] = id;
        jdbc.update(sql,obj);
    }

    public Cliente obterCliente(int id){
        String sql = "SELECT * FROM cliente where id=?";
        return Cliente
            .converterRegistros((Map<String,Object>) jdbc.queryForMap(sql,id));

    }

    public List<Cliente> obterTodosClientes(){
        String sql = "SELECT * FROM cliente";
        List<Map<String,Object>> listaRegistros = jdbc.queryForList(sql);
        ArrayList<Cliente> aux = new ArrayList<>();
        for(Map<String,Object> registro : listaRegistros){
            aux.add(Cliente.converterRegistros(registro));
        }
        return aux;
    }

}
