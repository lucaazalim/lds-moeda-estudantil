package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.empresa_parceira.AtualizarEmpresaParceiraDto;
import br.pucminas.moeda.dto.empresa_parceira.CriarEmpresaParceiraDto;
import br.pucminas.moeda.dto.empresa_parceira.EmpresaParceiraDto;
import br.pucminas.moeda.services.EmpresaParceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas-parceiras")
public class EmpresaParceiraController {

    @Autowired
    private EmpresaParceiraService empresaParceiraService;

    @PostMapping
    public EmpresaParceiraDto criarEmpresaParceira(@RequestBody CriarEmpresaParceiraDto criarEmpresaParceiraDto) {
        return empresaParceiraService.criarEmpresaParceira(criarEmpresaParceiraDto);
    }

    @GetMapping("/{id}")
    public EmpresaParceiraDto obterEmpresaParceira(@PathVariable Long id) {
        return empresaParceiraService.obterEmpresaParceira(id);
    }

    @PutMapping("/{id}")
    public EmpresaParceiraDto atualizarEmpresaParceira(@PathVariable Long id, @RequestBody AtualizarEmpresaParceiraDto atualizarEmpresaParceiraDto) {
        return empresaParceiraService.atualizarEmpresaParceira(id, atualizarEmpresaParceiraDto);
    }

    @DeleteMapping("/{id}")
    public void deletarEmpresaParceira(@PathVariable Long id) {
        empresaParceiraService.deletarEmpresaParceira(id);
    }
}
