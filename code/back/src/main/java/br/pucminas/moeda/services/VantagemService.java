package br.pucminas.moeda.services;

import br.pucminas.moeda.dto.vantagem.AtualizarVantagemDto;
import br.pucminas.moeda.dto.vantagem.CriarVantagemDto;
import br.pucminas.moeda.dto.vantagem.VantagemDto;
import br.pucminas.moeda.models.*;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.repositories.VantagemRepository;
import br.pucminas.moeda.utils.GeradorCupom;
import br.pucminas.moeda.services.EmailService;
import br.pucminas.moeda.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VantagemService {

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

    public void resgatarVantagem(Long id) {
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

        enviarEmailsResgate(usuario, vantagem, empresaParceira, cupom);
        transacaoService.criarTransacao(transacao);
    }

    private void enviarEmailsResgate(Usuario usuario, Vantagem vantagem, EmpresaParceira empresaParceira, String cupom) {
        String mensagemUsuario = String.format(
                "A vantagem \"%s\" foi resgatada com sucesso! Informe o cupom %s à empresa %s para coletá-la.",
                vantagem.getNome(), cupom, empresaParceira.getNome()
        );

        emailService.sendEmail(new EmailDetails(
                usuario.getEmail(), mensagemUsuario, "Você resgatou uma vantagem!"
        ));

        String mensagemEmpresaParceira = String.format(
                "O aluno %s resgatou a vantagem \"%s\"! O cupom de resgate é %s.",
                usuario.getNome(), vantagem.getNome(), cupom
        );

        emailService.sendEmail(new EmailDetails(
                empresaParceira.getEmail(), mensagemEmpresaParceira, "Um aluno resgatou uma vantagem!"
        ));
    }

    public VantagemDto criarVantagem(CriarVantagemDto criarVantagemDto) {
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

    public VantagemDto obterVantagem(Long id) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        VantagemDto vantagemDto = objectMapper.convertValue(vantagem, VantagemDto.class);
        vantagemDto.setEmpresaParceiraId(vantagem.getEmpresaParceira().getId());
        return vantagemDto;
    }

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

    public VantagemDto atualizarVantagem(Long id, AtualizarVantagemDto atualizarVantagemDto) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        vantagem.setNome(atualizarVantagemDto.getNome());
        vantagem.setDescricao(atualizarVantagemDto.getDescricao());
        vantagem.setFoto(atualizarVantagemDto.getFoto());
        vantagem.setCusto(atualizarVantagemDto.getCusto());
        vantagem = vantagemRepository.save(vantagem);
        return objectMapper.convertValue(vantagem, VantagemDto.class);
    }

    public void deletarVantagem(Long id) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        vantagemRepository.delete(vantagem);
    }
}
