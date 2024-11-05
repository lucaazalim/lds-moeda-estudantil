package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.vantagem.AtualizarVantagemDto;
import br.pucminas.moeda.dto.vantagem.CriarVantagemDto;
import br.pucminas.moeda.dto.vantagem.VantagemDto;
import br.pucminas.moeda.models.Vantagem;
import br.pucminas.moeda.repositories.VantagemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public VantagemDto criarVantagem(@RequestBody CriarVantagemDto criarVantagemDto) {
        Vantagem vantagem = objectMapper.convertValue(criarVantagemDto, Vantagem.class);
        vantagem = vantagemRepository.save(vantagem);
        return objectMapper.convertValue(vantagem, VantagemDto.class);
    }

    @GetMapping("/{id}")
    public VantagemDto obterVantagem(@PathVariable Long id) {
        Vantagem vantagem = vantagemRepository.findById(id).orElseThrow();
        return objectMapper.convertValue(vantagem, VantagemDto.class);
    }

    @GetMapping
    public List<VantagemDto> obterVantagens() {
        List<Vantagem> vantagens = vantagemRepository.findAll();
        return vantagens.stream()
                .map(vantagem -> objectMapper.convertValue(vantagem, VantagemDto.class))
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