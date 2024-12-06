package br.pucminas.moeda.services;

import br.pucminas.moeda.dto.professor.AtualizarProfessorDto;
import br.pucminas.moeda.dto.professor.CriarProfessorDto;
import br.pucminas.moeda.dto.professor.ProfessorDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import br.pucminas.moeda.models.Professor;
import br.pucminas.moeda.repositories.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorDto criarProfessor(CriarProfessorDto criarProfessorDto) {
        Professor professor = objectMapper.convertValue(criarProfessorDto, Professor.class);
        professor.setTipo(TipoUsuario.PROFESSOR);
        professor.setSenha(passwordEncoder.encode(criarProfessorDto.getSenha()));
        professor = professorRepository.save(professor);
        return objectMapper.convertValue(professor, ProfessorDto.class);
    }

    public ProfessorDto obterProfessor(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow();
        return objectMapper.convertValue(professor, ProfessorDto.class);
    }

    public ProfessorDto atualizarProfessor(Long id, AtualizarProfessorDto atualizarProfessorDto) {
        Professor professor = professorRepository.findById(id).orElseThrow();
        professor.setNome(atualizarProfessorDto.getNome());
        professor.setEmail(atualizarProfessorDto.getEmail());
        professor.setSenha(passwordEncoder.encode(atualizarProfessorDto.getSenha()));
        professor.setDepartamento(atualizarProfessorDto.getDepartamento());
        professor = professorRepository.save(professor);
        return objectMapper.convertValue(professor, ProfessorDto.class);
    }

    public void deletarProfessor(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow();
        professorRepository.delete(professor);
    }
}
