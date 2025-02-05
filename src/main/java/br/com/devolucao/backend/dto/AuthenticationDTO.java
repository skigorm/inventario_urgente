package br.com.devolucao.backend.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object para autenticação de usuário.
 */
@Data
public class AuthenticationDTO {

    /**
     * Login do usuário.
     */
    @NotBlank(message = "Login não pode estar vazio")
    private String login;

    /**
     * Senha do usuário.
     */
    @NotBlank(message = "Senha não pode estar vazia")
    private String password;
}
