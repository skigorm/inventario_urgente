package br.com.devolucao.backend.inv_controllers;

import br.com.devolucao.backend.inv_domain.InvUsuario;
import br.com.devolucao.backend.inv_dto.LoginRequest;
import br.com.devolucao.backend.inv_dto.LoginResponseDTO;
import br.com.devolucao.backend.inv_repositories.InvUsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller para gerenciar autenticação e login.
 */
@RestController
@RequestMapping("/inv_auth")
public class InvAuthController {

    @Autowired
    private InvUsuarioRepository usuarioRepository;

    /**
     * Endpoint para autenticação de usuário.
     * 
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return Informações do usuário autenticado
     */
    @Operation(summary = "Autentica um usuário com base em email e senha", description = "Retorna informações de login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<InvUsuario> usuario = usuarioRepository.findByEmailAndSenha    (loginRequest.getEmail(), loginRequest.getSenha());
        
        if (usuario.isPresent()) {
            InvUsuario user = usuario.get();
            return ResponseEntity.ok(new LoginResponseDTO(user.getId(), user.getNome(), user. getLocal(), user.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body  ("Credenciais inválidas");
        }
    }

}
