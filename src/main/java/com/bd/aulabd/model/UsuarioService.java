package com.bd.aulabd.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioDAO udao;

    public void cadastrarUsuario(String username, String senha) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário é obrigatório.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }
        if (udao.existeUsername(username.trim())) {
            throw new IllegalArgumentException("Usuário já existe.");
        }

        Usuario u = new Usuario(username.trim(), senha, true);
        udao.inserirUsuario(u);
    }

    public boolean autenticar(String username, String senha) {
        if (username == null || senha == null) return false;
        try {
            Usuario u = udao.obterPorUsername(username.trim());
            return u.isEnabled() && u.getSenha().equals(senha);
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario obterPorUsername(String username) {
        return udao.obterPorUsername(username);
    }

    public int obterIdPorUsername(String username) {
        return obterPorUsername(username).getId();
    }
}
