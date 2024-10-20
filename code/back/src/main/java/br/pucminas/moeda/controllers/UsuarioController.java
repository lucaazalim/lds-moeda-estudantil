package br.pucminas.moeda.controllers;

import br.pucminas.moeda.dto.usuario.RespostaLoginDto;
import br.pucminas.moeda.dto.usuario.UsuarioLoginDto;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.repositories.UsuarioRepository;
import br.pucminas.moeda.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<RespostaLoginDto> authenticate(@RequestBody UsuarioLoginDto usuarioLoginDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioLoginDto.getEmail(),
                        usuarioLoginDto.getSenha()
                )
        );

        Usuario cliente = usuarioRepository.findByEmail(usuarioLoginDto.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(cliente);

        RespostaLoginDto loginDto = new RespostaLoginDto();

        loginDto.setToken(jwtToken);
        loginDto.setExpiraEm(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginDto);

    }

}
