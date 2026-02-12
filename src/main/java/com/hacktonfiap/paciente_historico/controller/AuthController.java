package com.hacktonfiap.paciente_historico.controller;

import com.hacktonfiap.paciente_historico.dto.LoginRequest;
import com.hacktonfiap.paciente_historico.dto.LoginResponse;
import com.hacktonfiap.paciente_historico.entity.Usuario;
import com.hacktonfiap.paciente_historico.security.JwtService;
import com.hacktonfiap.paciente_historico.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {

        Usuario user = usuarioService.buscarPorEmail(request.email());

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if (!passwordEncoder.matches(request.senha(), user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}
