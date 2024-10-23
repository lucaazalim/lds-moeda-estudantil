package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.aluno.AlunoDto;
import br.pucminas.moeda.dto.empresa_parceira.EmpresaParceiraDto;
import br.pucminas.moeda.dto.usuario.RespostaLoginDto;
import br.pucminas.moeda.dto.usuario.UsuarioDto;
import br.pucminas.moeda.dto.usuario.UsuarioLoginDto;
import br.pucminas.moeda.models.Aluno;
import br.pucminas.moeda.models.EmpresaParceira;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/me")
    public UsuarioDto getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (usuario instanceof Aluno) {
            return objectMapper.convertValue(usuario, AlunoDto.class);
        } else if (usuario instanceof EmpresaParceira) {
            return objectMapper.convertValue(usuario, EmpresaParceiraDto.class);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<RespostaLoginDto> authenticate(@RequestBody UsuarioLoginDto usuarioLoginDto) {

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

        return ResponseEntity.ok(loginDto);

    }

}
