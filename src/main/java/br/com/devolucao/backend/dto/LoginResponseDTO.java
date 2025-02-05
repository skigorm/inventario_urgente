package br.com.devolucao.backend.dto;

import br.com.devolucao.backend.enumerated.UserRole;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private String email;
    private UserRole role;

    public LoginResponseDTO(String token, String email, UserRole role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
}
