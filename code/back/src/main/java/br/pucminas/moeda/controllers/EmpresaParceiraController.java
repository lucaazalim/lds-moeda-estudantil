package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.empresa_parceira.EmpresaParceiraDto;
import br.pucminas.moeda.dto.empresa_parceira.AtualizarEmpresaParceiraDto;
import br.pucminas.moeda.dto.empresa_parceira.CriarEmpresaParceiraDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import br.pucminas.moeda.models.EmpresaParceira;
import br.pucminas.moeda.repositories.EmpresaParceiraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas-parceiras")
public class EmpresaParceiraController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpresaParceiraRepository empresaParceiraRepository;

    @PostMapping
    public EmpresaParceiraDto criarEmpresaParceira(@RequestBody CriarEmpresaParceiraDto criarEmpresaParceiraDto) {

        EmpresaParceira empresaParceira = objectMapper.convertValue(criarEmpresaParceiraDto, EmpresaParceira.class);

        empresaParceira.setTipo(TipoUsuario.ALUNO);
        empresaParceira.setSenha(passwordEncoder.encode(criarEmpresaParceiraDto.getSenha()));

        empresaParceira = empresaParceiraRepository.save(empresaParceira);

        return objectMapper.convertValue(empresaParceira, EmpresaParceiraDto.class);

    }

    @GetMapping("/{id}")
    public EmpresaParceiraDto obterEmpresaParceira(@PathVariable Long id) {

        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id).orElseThrow();

        return objectMapper.convertValue(empresaParceira, EmpresaParceiraDto.class);

    }

    @PutMapping("/{id}")
    public EmpresaParceiraDto atualizarEmpresaParceira(@PathVariable Long id, @RequestBody AtualizarEmpresaParceiraDto atualizarEmpresaParceiraDto) {

        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id).orElseThrow();

        empresaParceira.setNome(atualizarEmpresaParceiraDto.getNome());
        empresaParceira.setEmail(atualizarEmpresaParceiraDto.getEmail());
        empresaParceira.setSenha(passwordEncoder.encode(atualizarEmpresaParceiraDto.getSenha()));

        empresaParceira = empresaParceiraRepository.save(empresaParceira);

        return objectMapper.convertValue(empresaParceira, EmpresaParceiraDto.class);

    }

    @DeleteMapping("/{id}")
    public void deletarEmpresaParceira(@PathVariable Long id) {

        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id).orElseThrow();

        empresaParceiraRepository.delete(empresaParceira);

    }

}
