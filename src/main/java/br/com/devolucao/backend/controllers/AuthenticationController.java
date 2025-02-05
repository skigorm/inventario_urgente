package br.com.devolucao.backend.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.dto.AuthenticationDTO;
import br.com.devolucao.backend.dto.LoginResponseDTO;
import br.com.devolucao.backend.dto.RegisterDTO;
import br.com.devolucao.backend.infra.security.TokenService;
import br.com.devolucao.backend.repositories.UserRepository;

/**
 * Controlador responsável por gerenciar autenticação e registro de usuários.
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Endpoint para login de usuários.
     *
     * @param data Dados de autenticação (login e senha).
     * @return Resposta com o token JWT e o papel do usuário.
     */

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        var email = ((User) auth.getPrincipal()).getLogin();


        return ResponseEntity.ok(new LoginResponseDTO(token, email, ((User) auth.getPrincipal()).getRole()));
    }

    /**
     * Endpoint para registro de novos usuários.
     *
     * @param data Dados de registro (login, senha, papel).
     * @return Resposta de sucesso ou erro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.getLogin()) != null)
            return ResponseEntity.badRequest().body("Login already exists");

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        User newUser = new User(data.getLogin(), encryptedPassword, data.getRole());

        this.repository.save(newUser);

        return ResponseEntity.ok().body("User registered successfully");
    }
}
