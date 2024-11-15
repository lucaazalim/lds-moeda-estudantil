package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.professor.ProfessorDto;
import br.pucminas.moeda.dto.professor.AtualizarProfessorDto;
import br.pucminas.moeda.dto.professor.CriarProfessorDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import br.pucminas.moeda.models.Professor;
import br.pucminas.moeda.repositories.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping
    public ProfessorDto criarProfessor(@RequestBody CriarProfessorDto criarProfessorDto) {

        Professor professor = objectMapper.convertValue(criarProfessorDto, Professor.class);

        professor.setTipo(TipoUsuario.PROFESSOR);
        professor.setSenha(passwordEncoder.encode(criarProfessorDto.getSenha()));

        professor = professorRepository.save(professor);

        return objectMapper.convertValue(professor, ProfessorDto.class);

    }

    @GetMapping("/{id}")
    public ProfessorDto obterProfessor(@PathVariable Long id) {

        Professor professor = professorRepository.findById(id).orElseThrow();

        return objectMapper.convertValue(professor, ProfessorDto.class);

    }

    @PutMapping("/{id}")
    public ProfessorDto atualizarProfessor(@PathVariable Long id, @RequestBody AtualizarProfessorDto atualizarProfessorDto) {

        Professor professor = professorRepository.findById(id).orElseThrow();

        professor.setNome(atualizarProfessorDto.getNome());
        professor.setEmail(atualizarProfessorDto.getEmail());
        professor.setSenha(passwordEncoder.encode(atualizarProfessorDto.getSenha()));
        professor.setDepartamento(atualizarProfessorDto.getDepartamento());

        professor = professorRepository.save(professor);

        return objectMapper.convertValue(professor, ProfessorDto.class);

    }

    @DeleteMapping("/{id}")
    public void deletarProfessor(@PathVariable Long id) {

        Professor professor = professorRepository.findById(id).orElseThrow();

        professorRepository.delete(professor);

    }

}