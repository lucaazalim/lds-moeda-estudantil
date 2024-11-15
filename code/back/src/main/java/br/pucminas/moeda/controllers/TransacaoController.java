package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.transacao.CriarTransacaoDto;
import br.pucminas.moeda.dto.transacao.TransacaoDto;
import br.pucminas.moeda.models.Transacao;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.repositories.TransacaoRepository;
import br.pucminas.moeda.repositories.UsuarioRepository;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public TransacaoDto criarTransacao(@RequestBody CriarTransacaoDto criarTransacaoDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(email).orElseThrow();

        if (usuarioAutenticado.getId() == criarTransacaoDto.getParaId()) {
            throw new IllegalArgumentException("De e para não podem ser iguais");
        }

        Transacao transacao = objectMapper.convertValue(criarTransacaoDto, Transacao.class);

        Usuario de = usuarioRepository.findById(usuarioAutenticado.getId()).orElseThrow();
        Usuario para = usuarioRepository.findById(criarTransacaoDto.getParaId()).orElseThrow();

        transacao.setDe(de);
        transacao.setPara(para);

        if (de.getSaldo() < criarTransacaoDto.getQuantidade()) {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }

        de.setSaldo(de.getSaldo() - criarTransacaoDto.getQuantidade());
        para.setSaldo(para.getSaldo() + criarTransacaoDto.getQuantidade());

        usuarioRepository.save(de);
        usuarioRepository.save(para);
        transacaoRepository.save(transacao);

        return objectMapper.convertValue(transacao, TransacaoDto.class);

    }

}
