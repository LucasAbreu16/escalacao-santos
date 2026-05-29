package com.bd.aulabd.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bd.aulabd.model.Escalacao;
import com.bd.aulabd.model.EscalacaoService;
import com.bd.aulabd.model.Formacao;
import com.bd.aulabd.model.Jogador;
import com.bd.aulabd.model.JogadorDAO;
import com.bd.aulabd.model.Posicao;
import com.bd.aulabd.model.UsuarioService;

@Controller
public class EscalacaoController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/escalacoes")
    public String listar(Model model, Principal principal) {
        int usuarioId = usuarioId(principal);
        EscalacaoService es = context.getBean(EscalacaoService.class);
        List<Escalacao> lista = es.obterEscalacoesPorUsuario(usuarioId);
        model.addAttribute("escalacoes", lista);
        return "escalacoes";
    }

    @GetMapping("/escalacoes/nova")
    public String formNova(Model model) {
        preencherForm(model, new Escalacao(), new ArrayList<>(), null);
        return "escalacao-form";
    }

    @PostMapping("/escalacoes/nova")
    public String criar(@RequestParam String nome,
                        @RequestParam String formacao,
                        @RequestParam(required = false, name = "jogadorIds") List<Integer> jogadorIds,
                        Model model,
                        Principal principal) {
        int usuarioId = usuarioId(principal);
        EscalacaoService es = context.getBean(EscalacaoService.class);

        try {
            es.criarEscalacao(usuarioId, nome, formacao, jogadorIds);
            return "redirect:/escalacoes";
        } catch (IllegalArgumentException e) {
            preencherForm(model, new Escalacao(), jogadorIds, e.getMessage());
            model.addAttribute("nome", nome);
            model.addAttribute("formacaoSelecionada", formacao);
            return "escalacao-form";
        }
    }

    @GetMapping("/escalacoes/{id}/editar")
    public String formEditar(@PathVariable int id, Model model, Principal principal) {
        int usuarioId = usuarioId(principal);
        EscalacaoService es = context.getBean(EscalacaoService.class);
        Escalacao esc = es.obterEscalacao(id, usuarioId);
        List<Integer> selecionados = es.obterIdsJogadoresDaEscalacao(id);
        preencherForm(model, esc, selecionados, null);
        model.addAttribute("editando", true);
        return "escalacao-form";
    }

    @PostMapping("/escalacoes/{id}/editar")
    public String editar(@PathVariable int id,
                         @RequestParam String nome,
                         @RequestParam String formacao,
                         @RequestParam(required = false, name = "jogadorIds") List<Integer> jogadorIds,
                         Model model,
                         Principal principal) {
        int usuarioId = usuarioId(principal);
        EscalacaoService es = context.getBean(EscalacaoService.class);
        try {
            es.atualizarEscalacao(usuarioId, id, nome, formacao, jogadorIds);
            return "redirect:/escalacoes";
        } catch (IllegalArgumentException e) {
            Escalacao esc = es.obterEscalacao(id, usuarioId);
            preencherForm(model, esc, jogadorIds, e.getMessage());
            model.addAttribute("editando", true);
            return "escalacao-form";
        }
    }

    @PostMapping("/escalacoes/{id}/excluir")
    public String excluir(@PathVariable int id, Principal principal) {
        int usuarioId = usuarioId(principal);
        EscalacaoService es = context.getBean(EscalacaoService.class);
        es.excluirEscalacao(usuarioId, id);
        return "redirect:/escalacoes";
    }

    private int usuarioId(Principal principal) {
        UsuarioService us = context.getBean(UsuarioService.class);
        return us.obterIdPorUsername(principal.getName());
    }

    private void preencherForm(Model model, Escalacao esc, List<Integer> selecionados, String erro) {
        JogadorDAO jdao = context.getBean(JogadorDAO.class);
        List<Jogador> jogadores = jdao.obterTodos();

        model.addAttribute("jogadores", jogadores);
        model.addAttribute("selecionados", selecionados == null ? new ArrayList<>() : selecionados);
        model.addAttribute("esc", esc);
        model.addAttribute("formacoes", Formacao.values());
        model.addAttribute("posicoes", Posicao.values());
        if (erro != null) model.addAttribute("erro", erro);
    }
}

