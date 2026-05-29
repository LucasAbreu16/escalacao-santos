package com.bd.aulabd.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioDAO udao;

    @Autowired
    PasswordEncoder passwordEncoder;

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

        String hash = passwordEncoder.encode(senha);
        Usuario u = new Usuario(username.trim(), hash, true);
        udao.inserirUsuario(u);
        udao.inserirRolePadrao(u.getUsername());
    }

    public Usuario obterPorUsername(String username) {
        return udao.obterPorUsername(username);
    }

    public int obterIdPorUsername(String username) {
        return obterPorUsername(username).getId();
    }
}

