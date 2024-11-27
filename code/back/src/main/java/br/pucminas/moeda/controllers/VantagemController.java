package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.vantagem.AtualizarVantagemDto;
import br.pucminas.moeda.dto.vantagem.CriarVantagemDto;
import br.pucminas.moeda.dto.vantagem.VantagemDto;
import br.pucminas.moeda.models.*;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.repositories.VantagemRepository;
import br.pucminas.moeda.services.EmailService;
import br.pucminas.moeda.services.TransacaoService;
import br.pucminas.moeda.utils.GeradorCupom;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vantagens")
public class VantagemController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/{id}/resgatar")
    public void resgatarVantagem(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();

        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        EmpresaParceira empresaParceira = vantagem.getEmpresaParceira();

        String cupom = GeradorCupom.gerarCupom();

        Transacao transacao = new Transacao(
                vantagem.getCusto(),
                "Resgate de vantagem: " + vantagem.getNome() + " (Cupom: " + cupom + ")",
                usuario,
                empresaParceira
        );

        String mensagemUsuario = String.format(
                "A vantagem \"%s\" foi resgatada com sucesso! Informe o cupom %s à empresa %s para coletá-la.",
                vantagem.getNome(),
                cupom,
                empresaParceira.getNome()
        );

        emailService.sendEmail(new EmailDetails(
                usuario.getEmail(),
                mensagemUsuario,
                "Você resgatou uma vantagem!"
        ));

        String mensagemEmpresaParceira = String.format(
                "O aluno %s resgatou a vantagem \"%s\"! O cupom de resgate é %s.",
                usuario.getNome(),
                vantagem.getNome(),
                cupom
        );

        emailService.sendEmail(new EmailDetails(
                empresaParceira.getEmail(),
                mensagemEmpresaParceira,
                "Um aluno resgatou uma vantagem!"
        ));

        transacaoService.criarTransacao(transacao);

    }

    @PostMapping
    public VantagemDto criarVantagem(@RequestBody CriarVantagemDto criarVantagemDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();

        if (!(usuarioAutenticado instanceof EmpresaParceira empresaParceira)) {
            throw new IllegalArgumentException("Usuário não é empresa parceira");
        }

        Vantagem vantagem = objectMapper.convertValue(criarVantagemDto, Vantagem.class);

        vantagem.setEmpresaParceira(empresaParceira);

        vantagem = vantagemRepository.save(vantagem);
        return objectMapper.convertValue(vantagem, VantagemDto.class);
    }

    @GetMapping("/{id}")
    public VantagemDto obterVantagem(@PathVariable Long id) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        VantagemDto vantagemDto = objectMapper.convertValue(vantagem, VantagemDto.class);
        vantagemDto.setEmpresaParceiraId(vantagem.getEmpresaParceira().getId());
        return vantagemDto;
    }

    @GetMapping
    public List<VantagemDto> obterVantagens() {
        List<Vantagem> vantagens = vantagemRepository.findAll();
        return vantagens.stream()
                .map(vantagem -> {
                    VantagemDto vantagemDto = objectMapper.convertValue(vantagem, VantagemDto.class);
                    vantagemDto.setEmpresaParceiraId(vantagem.getEmpresaParceira().getId());
                    return vantagemDto;
                })
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public VantagemDto atualizarVantagem(@PathVariable Long id, @RequestBody AtualizarVantagemDto atualizarVantagemDto) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        vantagem.setNome(atualizarVantagemDto.getNome());
        vantagem.setDescricao(atualizarVantagemDto.getDescricao());
        vantagem.setFoto(atualizarVantagemDto.getFoto());
        vantagem.setCusto(atualizarVantagemDto.getCusto());
        vantagem = vantagemRepository.save(vantagem);
        return objectMapper.convertValue(vantagem, VantagemDto.class);
    }

    @DeleteMapping("/{id}")
    public void deletarVantagem(@PathVariable Long id) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        vantagemRepository.delete(vantagem);
    }
}