package br.com.devolucao.backend.dto;

import br.com.devolucao.backend.enumerated.UserRole;
import lombok.Data;

@Data
public class RegisterDTO {

    private String login;
    private String password;
    private UserRole role;
}
