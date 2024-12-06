package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.usuario.RespostaLoginDto;
import br.pucminas.moeda.dto.usuario.UsuarioDto;
import br.pucminas.moeda.dto.usuario.UsuarioLoginDto;
import br.pucminas.moeda.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public UsuarioDto obterUsuarioAutenticado() {
        return usuarioService.obterUsuarioAutenticado();
    }

    @PostMapping("/login")
    public ResponseEntity<RespostaLoginDto> autenticarUsuario(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        RespostaLoginDto resposta = usuarioService.autenticarUsuario(usuarioLoginDto);
        return ResponseEntity.ok(resposta);
    }
}
