package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.vantagem.AtualizarVantagemDto;
import br.pucminas.moeda.dto.vantagem.CriarVantagemDto;
import br.pucminas.moeda.dto.vantagem.VantagemDto;
import br.pucminas.moeda.models.EmpresaParceira;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.models.Vantagem;
import br.pucminas.moeda.repositories.EmpresaParceiraRepository;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.repositories.VantagemRepository;
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
    private EmpresaParceiraRepository empresaParceiraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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