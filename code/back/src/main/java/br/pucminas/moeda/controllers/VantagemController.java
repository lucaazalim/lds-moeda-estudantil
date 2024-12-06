package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.vantagem.AtualizarVantagemDto;
import br.pucminas.moeda.dto.vantagem.CriarVantagemDto;
import br.pucminas.moeda.dto.vantagem.VantagemDto;
import br.pucminas.moeda.services.VantagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vantagens")
public class VantagemController {

    @Autowired
    private VantagemService vantagemService;

    @PostMapping("/{id}/resgatar")
    public void resgatarVantagem(@PathVariable Long id) {
        vantagemService.resgatarVantagem(id);
    }

    @PostMapping
    public VantagemDto criarVantagem(@RequestBody CriarVantagemDto criarVantagemDto) {
        return vantagemService.criarVantagem(criarVantagemDto);
    }

    @GetMapping("/{id}")
    public VantagemDto obterVantagem(@PathVariable Long id) {
        return vantagemService.obterVantagem(id);
    }

    @GetMapping
    public List<VantagemDto> obterVantagens() {
        return vantagemService.obterVantagens();
    }

    @PutMapping("/{id}")
    public VantagemDto atualizarVantagem(@PathVariable Long id, @RequestBody AtualizarVantagemDto atualizarVantagemDto) {
        return vantagemService.atualizarVantagem(id, atualizarVantagemDto);
    }

    @DeleteMapping("/{id}")
    public void deletarVantagem(@PathVariable Long id) {
        vantagemService.deletarVantagem(id);
    }
}
