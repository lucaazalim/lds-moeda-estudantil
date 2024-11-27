package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.transacao.CriarTransacaoDto;
import br.pucminas.moeda.dto.transacao.TransacaoDto;
import br.pucminas.moeda.models.Transacao;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public TransacaoDto criarTransacao(@RequestBody CriarTransacaoDto criarTransacaoDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario de = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();
        Usuario para = usuarioRepository.findById(criarTransacaoDto.getParaId()).orElseThrow();

        Transacao transacao = objectMapper.convertValue(criarTransacaoDto, Transacao.class);

        transacao.setDe(de);
        transacao.setPara(para);

        transacaoService.criarTransacao(transacao);

        return objectMapper.convertValue(transacao, TransacaoDto.class);

    }

}
