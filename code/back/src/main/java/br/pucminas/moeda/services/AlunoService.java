package br.pucminas.moeda.services;

import br.pucminas.moeda.dto.aluno.AlunoDto;
import br.pucminas.moeda.dto.aluno.AtualizarAlunoDto;
import br.pucminas.moeda.dto.aluno.CriarAlunoDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import br.pucminas.moeda.models.Aluno;
import br.pucminas.moeda.repositories.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AlunoRepository alunoRepository;

    public AlunoDto criarAluno(CriarAlunoDto criarAlunoDto) {
        Aluno aluno = objectMapper.convertValue(criarAlunoDto, Aluno.class);
        aluno.setTipo(TipoUsuario.ALUNO);
        aluno.setSenha(passwordEncoder.encode(criarAlunoDto.getSenha()));
        aluno = alunoRepository.save(aluno);
        return objectMapper.convertValue(aluno, AlunoDto.class);
    }

    public AlunoDto obterAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow();
        return objectMapper.convertValue(aluno, AlunoDto.class);
    }

    public List<AlunoDto> obterTodosAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(aluno -> objectMapper.convertValue(aluno, AlunoDto.class))
                .collect(Collectors.toList());
    }

    public AlunoDto atualizarAluno(Long id, AtualizarAlunoDto atualizarAlunoDto) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow();
        aluno.setNome(atualizarAlunoDto.getNome());
        aluno.setEmail(atualizarAlunoDto.getEmail());
        aluno.setSenha(passwordEncoder.encode(atualizarAlunoDto.getSenha()));
        aluno.setEndereco(atualizarAlunoDto.getEndereco());
        aluno.setCurso(atualizarAlunoDto.getCurso());
        aluno = alunoRepository.save(aluno);
        return objectMapper.convertValue(aluno, AlunoDto.class);
    }

    public void deletarAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow();
        alunoRepository.delete(aluno);
    }
}
