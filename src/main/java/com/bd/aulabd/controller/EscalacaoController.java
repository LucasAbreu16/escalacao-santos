package com.bd.aulabd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bd.aulabd.model.Escalacao;
import com.bd.aulabd.model.EscalacaoService;
import com.bd.aulabd.model.Formacao;
import com.bd.aulabd.model.Jogador;
import com.bd.aulabd.model.JogadorDAO;
import com.bd.aulabd.model.Posicao;
import com.bd.aulabd.model.Selecao;
import com.bd.aulabd.model.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EscalacaoController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/escalacoes")
    public String listar(Model model, HttpSession session) {
        Integer usuarioId = usuarioId(session);
        if (usuarioId == null) return "redirect:/login";

        EscalacaoService es = context.getBean(EscalacaoService.class);
        List<Escalacao> lista = es.obterEscalacoesPorUsuario(usuarioId);
        model.addAttribute("escalacoes", lista);
        return "escalacoes";
    }

    @GetMapping("/escalacoes/nova")
    public String formNova(@RequestParam(required = false) String selecao,
                           Model model,
                           HttpSession session) {
        if (usuarioId(session) == null) return "redirect:/login";

        Selecao selecaoEnum = Selecao.fromCodigo(selecao);
        if (selecaoEnum == null) {
            model.addAttribute("selecoes", Selecao.values());
            return "escalacao-selecao";
        }

        preencherForm(model, new Escalacao(), new ArrayList<>(), selecaoEnum, null);
        return "escalacao-form";
    }

    @PostMapping("/escalacoes/nova")
    public String criar(@RequestParam String nome,
                        @RequestParam String formacao,
                        @RequestParam String selecao,
                        @RequestParam(required = false, name = "jogadorIds") List<Integer> jogadorIds,
                        Model model,
                        HttpSession session) {
        Integer usuarioId = usuarioId(session);
        if (usuarioId == null) return "redirect:/login";

        Selecao selecaoEnum = Selecao.fromCodigo(selecao);
        if (selecaoEnum == null) {
            model.addAttribute("erro", "Seleção inválida.");
            model.addAttribute("selecoes", Selecao.values());
            return "escalacao-selecao";
        }

        EscalacaoService es = context.getBean(EscalacaoService.class);

        try {
            es.criarEscalacao(usuarioId, nome, formacao, selecaoEnum, jogadorIds);
            return "redirect:/escalacoes";
        } catch (IllegalArgumentException e) {
            preencherForm(model, new Escalacao(), jogadorIds, selecaoEnum, e.getMessage());
            model.addAttribute("nome", nome);
            model.addAttribute("formacaoSelecionada", formacao);
            return "escalacao-form";
        }
    }

    @GetMapping("/escalacoes/{id}/editar")
    public String formEditar(@PathVariable int id, Model model, HttpSession session) {
        Integer usuarioId = usuarioId(session);
        if (usuarioId == null) return "redirect:/login";

        EscalacaoService es = context.getBean(EscalacaoService.class);
        Escalacao esc = es.obterEscalacao(id, usuarioId);
        List<Integer> selecionados = es.obterIdsJogadoresDaEscalacao(id);
        preencherForm(model, esc, selecionados, esc.getSelecao(), null);
        model.addAttribute("editando", true);
        return "escalacao-form";
    }

    @PostMapping("/escalacoes/{id}/editar")
    public String editar(@PathVariable int id,
                         @RequestParam String nome,
                         @RequestParam String formacao,
                         @RequestParam String selecao,
                         @RequestParam(required = false, name = "jogadorIds") List<Integer> jogadorIds,
                         Model model,
                         HttpSession session) {
        Integer usuarioId = usuarioId(session);
        if (usuarioId == null) return "redirect:/login";

        Selecao selecaoEnum = Selecao.fromCodigo(selecao);
        EscalacaoService es = context.getBean(EscalacaoService.class);

        try {
            if (selecaoEnum == null) {
                throw new IllegalArgumentException("Seleção inválida.");
            }
            es.atualizarEscalacao(usuarioId, id, nome, formacao, selecaoEnum, jogadorIds);
            return "redirect:/escalacoes";
        } catch (IllegalArgumentException e) {
            Escalacao esc = es.obterEscalacao(id, usuarioId);
            Selecao sel = selecaoEnum != null ? selecaoEnum : esc.getSelecao();
            preencherForm(model, esc, jogadorIds, sel, e.getMessage());
            model.addAttribute("editando", true);
            return "escalacao-form";
        }
    }

    @PostMapping("/escalacoes/{id}/excluir")
    public String excluir(@PathVariable int id, HttpSession session) {
        Integer usuarioId = usuarioId(session);
        if (usuarioId == null) return "redirect:/login";

        EscalacaoService es = context.getBean(EscalacaoService.class);
        es.excluirEscalacao(usuarioId, id);
        return "redirect:/escalacoes";
    }

    private Integer usuarioId(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return null;
        UsuarioService us = context.getBean(UsuarioService.class);
        return us.obterIdPorUsername(username);
    }

    private void preencherForm(Model model, Escalacao esc, List<Integer> selecionados, Selecao selecao, String erro) {
        JogadorDAO jdao = context.getBean(JogadorDAO.class);
        List<Jogador> jogadores = jdao.obterPorSelecao(selecao);

        if (esc.getSelecao() == null) {
            esc.setSelecao(selecao);
        }

        model.addAttribute("jogadores", jogadores);
        model.addAttribute("selecionados", selecionados == null ? new ArrayList<>() : selecionados);
        model.addAttribute("esc", esc);
        model.addAttribute("selecaoAtual", selecao);
        model.addAttribute("editando", false);
        model.addAttribute("formacoes", Formacao.values());
        model.addAttribute("posicoes", Posicao.values());
        if (erro != null) model.addAttribute("erro", erro);
    }
}
