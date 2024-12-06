package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.professor.AtualizarProfessorDto;
import br.pucminas.moeda.dto.professor.CriarProfessorDto;
import br.pucminas.moeda.dto.professor.ProfessorDto;
import br.pucminas.moeda.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ProfessorDto criarProfessor(@RequestBody CriarProfessorDto criarProfessorDto) {
        return professorService.criarProfessor(criarProfessorDto);
    }

    @GetMapping("/{id}")
    public ProfessorDto obterProfessor(@PathVariable Long id) {
        return professorService.obterProfessor(id);
    }

    @PutMapping("/{id}")
    public ProfessorDto atualizarProfessor(@PathVariable Long id, @RequestBody AtualizarProfessorDto atualizarProfessorDto) {
        return professorService.atualizarProfessor(id, atualizarProfessorDto);
    }

    @DeleteMapping("/{id}")
    public void deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
    }
}
