package br.pucminas.moeda.services;

import br.pucminas.moeda.dto.empresa_parceira.AtualizarEmpresaParceiraDto;
import br.pucminas.moeda.dto.empresa_parceira.CriarEmpresaParceiraDto;
import br.pucminas.moeda.dto.empresa_parceira.EmpresaParceiraDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import br.pucminas.moeda.models.EmpresaParceira;
import br.pucminas.moeda.repositories.EmpresaParceiraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpresaParceiraService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpresaParceiraRepository empresaParceiraRepository;

    public EmpresaParceiraDto criarEmpresaParceira(CriarEmpresaParceiraDto criarEmpresaParceiraDto) {
        EmpresaParceira empresaParceira = objectMapper.convertValue(criarEmpresaParceiraDto, EmpresaParceira.class);
        empresaParceira.setTipo(TipoUsuario.EMPRESA_PARCEIRA);
        empresaParceira.setSenha(passwordEncoder.encode(criarEmpresaParceiraDto.getSenha()));
        empresaParceira = empresaParceiraRepository.save(empresaParceira);
        return objectMapper.convertValue(empresaParceira, EmpresaParceiraDto.class);
    }

    public EmpresaParceiraDto obterEmpresaParceira(Long id) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id).orElseThrow();
        return objectMapper.convertValue(empresaParceira, EmpresaParceiraDto.class);
    }

    public EmpresaParceiraDto atualizarEmpresaParceira(Long id, AtualizarEmpresaParceiraDto atualizarEmpresaParceiraDto) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id).orElseThrow();
        empresaParceira.setNome(atualizarEmpresaParceiraDto.getNome());
        empresaParceira.setEmail(atualizarEmpresaParceiraDto.getEmail());
        empresaParceira.setSenha(passwordEncoder.encode(atualizarEmpresaParceiraDto.getSenha()));
        empresaParceira = empresaParceiraRepository.save(empresaParceira);
        return objectMapper.convertValue(empresaParceira, EmpresaParceiraDto.class);
    }

    public void deletarEmpresaParceira(Long id) {
        EmpresaParceira empresaParceira = empresaParceiraRepository.findById(id).orElseThrow();
        empresaParceiraRepository.delete(empresaParceira);
    }
}
