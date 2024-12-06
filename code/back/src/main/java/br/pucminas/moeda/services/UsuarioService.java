package br.pucminas.moeda.services;

import br.pucminas.moeda.dto.transacao.TransacaoDto;
import br.pucminas.moeda.dto.usuario.RespostaLoginDto;
import br.pucminas.moeda.dto.usuario.UsuarioDto;
import br.pucminas.moeda.dto.usuario.UsuarioLoginDto;
import br.pucminas.moeda.dto.usuario.UsuarioSimplesDto;
import br.pucminas.moeda.models.Transacao;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public UsuarioDto obterUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(email).orElseThrow();

        UsuarioDto usuarioAutenticadoDto = switch (usuarioAutenticado) {
            case var aluno when aluno instanceof Aluno -> objectMapper.convertValue(usuarioAutenticado, UsuarioDto.class);
            case var empresa when empresa instanceof EmpresaParceira -> objectMapper.convertValue(usuarioAutenticado, UsuarioDto.class);
            case var professor when professor instanceof Professor -> objectMapper.convertValue(usuarioAutenticado, UsuarioDto.class);
            default -> throw new IllegalArgumentException("Invalid user type");
        };

        Set<Long> idsUsuariosEnvolvidos = new HashSet<>();
        usuarioAutenticado.getTransacoesEnviadas().stream()
                .map(Transacao::getPara)
                .map(Usuario::getId)
                .forEach(idsUsuariosEnvolvidos::add);
        usuarioAutenticado.getTransacoesRecebidas().stream()
                .map(Transacao::getDe)
                .map(Usuario::getId)
                .forEach(idsUsuariosEnvolvidos::add);

        Map<Long, UsuarioSimplesDto> usuariosEnvolvidos = usuarioRepository.findAllById(idsUsuariosEnvolvidos)
                .stream()
                .map(u -> objectMapper.convertValue(u, UsuarioSimplesDto.class))
                .collect(Collectors.toMap(UsuarioSimplesDto::getId, u -> u));

        usuarioAutenticadoDto.setTransacoesEnviadas(
                usuarioAutenticado.getTransacoesEnviadas().stream()
                        .map(transacao -> {
                            TransacaoDto transacaoDto = objectMapper.convertValue(transacao, TransacaoDto.class);
                            transacaoDto.setPara(usuariosEnvolvidos.get(transacao.getPara().getId()));
                            return transacaoDto;
                        })
                        .toList()
        );

        usuarioAutenticadoDto.setTransacoesRecebidas(
                usuarioAutenticado.getTransacoesRecebidas().stream()
                        .map(transacao -> {
                            TransacaoDto transacaoDto = objectMapper.convertValue(transacao, TransacaoDto.class);
                            transacaoDto.setDe(usuariosEnvolvidos.get(transacao.getDe().getId()));
                            return transacaoDto;
                        }).toList()
        );

        return usuarioAutenticadoDto;
    }

    public RespostaLoginDto autenticarUsuario(UsuarioLoginDto usuarioLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioLoginDto.getEmail(),
                        usuarioLoginDto.getSenha()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(usuarioLoginDto.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(usuario);

        RespostaLoginDto loginDto = new RespostaLoginDto();
        loginDto.setToken(jwtToken);
        loginDto.setExpiraEm(jwtService.getExpirationTime());

        return loginDto;
    }
}
