
package com.bd.aulabd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class EscalacaoDAO {

    @Autowired
	DataSource dataSource;
	
	JdbcTemplate jdbc;
	
	@PostConstruct
	private void initialize() {
		jdbc = new JdbcTemplate(dataSource);
	}
	
    public int inserirEscalacao(Escalacao esc){
        String sql = "INSERT INTO escalacao(usuario_id, nome, formacao, criado_em) VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, esc.getUsuarioId());
            ps.setString(2, esc.getNome());
            ps.setString(3, esc.getFormacao());
            ps.setObject(4, esc.getCriadoEm());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null && keyHolder.getKeys() != null && keyHolder.getKeys().get("id") != null) {
            key = (Number) keyHolder.getKeys().get("id");
        }
        if (key == null) {
            throw new IllegalStateException("Não foi possível obter o ID da escalação criada.");
        }
        return key.intValue();
    }

    public void atualizarEscalacao(int id, int usuarioId, Escalacao novo){
        String sql = "UPDATE escalacao SET nome = ?, formacao = ? where id = ? AND usuario_id = ?";
        Object[] obj = new Object[4];
        obj[0] = novo.getNome();
        obj[1] = novo.getFormacao();
        obj[2] = id;
        obj[3] = usuarioId;
        jdbc.update(sql,obj);
    }

    public Escalacao obterEscalacao(int id, int usuarioId){
        String sql = "SELECT * FROM escalacao where id=? AND usuario_id=?";
        return Escalacao
            .converterRegistros((Map<String,Object>) jdbc.queryForMap(sql,id, usuarioId));

    }

    public int contarPorUsuario(int usuarioId) {
        String sql = "SELECT COUNT(*) FROM escalacao WHERE usuario_id = ?";
        Integer qtd = jdbc.queryForObject(sql, Integer.class, usuarioId);
        return qtd == null ? 0 : qtd;
    }

    public List<Escalacao> obterEscalacoesPorUsuario(int usuarioId){
        String sql = "SELECT * FROM escalacao WHERE usuario_id = ? ORDER BY criado_em DESC, id DESC";
        List<Map<String,Object>> listaRegistros = jdbc.queryForList(sql, usuarioId);
        ArrayList<Escalacao> aux = new ArrayList<>();
        for(Map<String,Object> registro : listaRegistros){
            aux.add(Escalacao.converterRegistros(registro));
        }
        return aux;
    }

    public void excluirEscalacao(int id, int usuarioId) {
        String sql = "DELETE FROM escalacao WHERE id = ? AND usuario_id = ?";
        jdbc.update(sql, id, usuarioId);
    }

    public void substituirJogadores(int escalacaoId, List<Integer> jogadorIds) {
        String del = "DELETE FROM escalacao_jogador WHERE escalacao_id = ?";
        jdbc.update(del, escalacaoId);

        if (jogadorIds == null || jogadorIds.isEmpty()) return;

        String ins = "INSERT INTO escalacao_jogador(escalacao_id, jogador_id) VALUES(?,?)";
        for (Integer jogadorId : jogadorIds) {
            jdbc.update(ins, escalacaoId, jogadorId);
        }
    }

    public List<Integer> obterIdsJogadoresDaEscalacao(int escalacaoId) {
        String sql = "SELECT jogador_id FROM escalacao_jogador WHERE escalacao_id = ?";
        List<Map<String, Object>> regs = jdbc.queryForList(sql, escalacaoId);
        if (regs == null || regs.isEmpty()) return Collections.emptyList();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Map<String, Object> r : regs) {
            ids.add(((Number) r.get("jogador_id")).intValue());
        }
        return ids;
    }

}
