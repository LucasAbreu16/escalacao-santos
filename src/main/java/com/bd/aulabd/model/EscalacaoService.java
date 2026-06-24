
package com.bd.aulabd.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EscalacaoService {

    @Autowired
    EscalacaoDAO escdao;

    @Autowired
    JogadorDAO jdao;

    public List<Escalacao> obterEscalacoesPorUsuario(int usuarioId) {
        return escdao.obterEscalacoesPorUsuario(usuarioId);
    }

    public Escalacao obterEscalacao(int id, int usuarioId) {
        return escdao.obterEscalacao(id, usuarioId);
    }

    public List<Integer> obterIdsJogadoresDaEscalacao(int escalacaoId) {
        return escdao.obterIdsJogadoresDaEscalacao(escalacaoId);
    }
    
    //Garante que salvar escalação + salvar jogadores acontece tudo junto ou nada.
    @Transactional
    public int criarEscalacao(int usuarioId, String nome, String formacao, Selecao selecao, List<Integer> jogadorIds) {
        validar(usuarioId, nome, formacao, selecao, jogadorIds, true);

        Escalacao esc = new Escalacao(usuarioId, nome.trim(), formacao, selecao);
        int id = escdao.inserirEscalacao(esc);
        escdao.substituirJogadores(id, jogadorIds);
        return id;
    }
    
    //Garante que salvar escalação + salvar jogadores acontece tudo junto ou nada.
    @Transactional
    public void atualizarEscalacao(int usuarioId, int escalacaoId, String nome, String formacao, Selecao selecao, List<Integer> jogadorIds) {
        validar(usuarioId, nome, formacao, selecao, jogadorIds, false);

        Escalacao novo = new Escalacao(usuarioId, nome.trim(), formacao, selecao);
        escdao.atualizarEscalacao(escalacaoId, usuarioId, novo);
        escdao.substituirJogadores(escalacaoId, jogadorIds);
    }

    public void excluirEscalacao(int usuarioId, int escalacaoId) {
        escdao.excluirEscalacao(escalacaoId, usuarioId);
    }

    private void validar(int usuarioId, String nome, String formacao, Selecao selecao, List<Integer> jogadorIds, boolean isCriacao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da escalação é obrigatório.");
        }
        if (selecao == null) {
            throw new IllegalArgumentException("Seleção é obrigatória.");
        }
        Formacao f = Formacao.fromCodigo(formacao);
        if (f == null) {
            throw new IllegalArgumentException("Formação inválida.");
        }

        if (jogadorIds == null) jogadorIds = new ArrayList<>();

        Set<Integer> unique = new HashSet<>(jogadorIds);
        if (unique.size() != jogadorIds.size()) {
            throw new IllegalArgumentException("Não é permitido repetir jogador na mesma escalação.");
        }

        if (jogadorIds.size() != 11) {
            throw new IllegalArgumentException("Você deve selecionar exatamente 11 jogadores.");
        }

        if (isCriacao) {
            int qtd = escdao.contarPorUsuario(usuarioId);
            if (qtd >= 5) {
                throw new IllegalArgumentException("Limite de 5 escalações atingido.");
            }
        }

        List<Jogador> jogadores = jdao.obterPorIds(jogadorIds);
        if (jogadores.size() != 11) {
            throw new IllegalArgumentException("Seleção contém jogador inválido.");
        }

        int goleiros = 0;
        int laterais = 0;
        int zagueiros = 0;
        int meios = 0;
        int atacantes = 0;

        for (Jogador j : jogadores) {
            if (j.getSelecao() != selecao) {
                throw new IllegalArgumentException("Todos os jogadores devem ser da seleção " + selecao.getLabel() + ".");
            }
            if (j.getPosicao() == Posicao.GOLEIRO) goleiros++;
            else if (j.getPosicao() == Posicao.LATERAL) laterais++;
            else if (j.getPosicao() == Posicao.ZAGUEIRO) zagueiros++;
            else if (j.getPosicao() == Posicao.MEIO_CAMPISTA) meios++;
            else if (j.getPosicao() == Posicao.ATACANTE) atacantes++;
        }

        if (goleiros != f.getGoleiros()
            || laterais != f.getLaterais()
            || zagueiros != f.getZagueiros()
            || meios != f.getMeioCampistas()
            || atacantes != f.getAtacantes()) {
            throw new IllegalArgumentException("A quantidade de jogadores por posição não é válida com a formação escolhida.");
        }
    }

}
