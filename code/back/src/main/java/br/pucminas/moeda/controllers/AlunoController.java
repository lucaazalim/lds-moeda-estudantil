package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.aluno.AlunoDto;
import br.pucminas.moeda.dto.aluno.AtualizarAlunoDto;
import br.pucminas.moeda.dto.aluno.CriarAlunoDto;
import br.pucminas.moeda.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public AlunoDto criarAluno(@RequestBody CriarAlunoDto criarAlunoDto) {
        return alunoService.criarAluno(criarAlunoDto);
    }

    @GetMapping("/{id}")
    public AlunoDto obterAluno(@PathVariable Long id) {
        return alunoService.obterAluno(id);
    }

    @GetMapping
    public List<AlunoDto> obterTodosAlunos() {
        return alunoService.obterTodosAlunos();
    }

    @PutMapping("/{id}")
    public AlunoDto atualizarAluno(@PathVariable Long id, @RequestBody AtualizarAlunoDto atualizarAlunoDto) {
        return alunoService.atualizarAluno(id, atualizarAlunoDto);
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
    }
}
