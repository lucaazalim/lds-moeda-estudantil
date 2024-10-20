package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.aluno.AlunoDto;
import br.pucminas.moeda.dto.aluno.AtualizarAlunoDto;
import br.pucminas.moeda.dto.aluno.CriarAlunoDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import br.pucminas.moeda.models.Aluno;
import br.pucminas.moeda.repositories.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    public AlunoDto criarAluno(@RequestBody CriarAlunoDto criarAlunoDto) {

        Aluno aluno = objectMapper.convertValue(criarAlunoDto, Aluno.class);

        aluno.setTipo(TipoUsuario.ALUNO);
        aluno.setSenha(passwordEncoder.encode(criarAlunoDto.getSenha()));

        aluno = alunoRepository.save(aluno);

        return objectMapper.convertValue(aluno, AlunoDto.class);

    }

    @GetMapping("/{id}")
    public AlunoDto obterAluno(@PathVariable Long id) {

        Aluno aluno = alunoRepository.findById(id).orElseThrow();

        return objectMapper.convertValue(aluno, AlunoDto.class);

    }

    @PutMapping("/{id}")
    public AlunoDto atualizarAluno(@PathVariable Long id, @RequestBody AtualizarAlunoDto atualizarAlunoDto) {

        Aluno aluno = alunoRepository.findById(id).orElseThrow();

        aluno.setNome(atualizarAlunoDto.getNome());
        aluno.setEmail(atualizarAlunoDto.getEmail());
        aluno.setSenha(passwordEncoder.encode(atualizarAlunoDto.getSenha()));
        aluno.setEndereco(atualizarAlunoDto.getEndereco());
        aluno.setCurso(atualizarAlunoDto.getCurso());

        aluno = alunoRepository.save(aluno);

        return objectMapper.convertValue(aluno, AlunoDto.class);

    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {

        Aluno aluno = alunoRepository.findById(id).orElseThrow();

        alunoRepository.delete(aluno);

    }

}
