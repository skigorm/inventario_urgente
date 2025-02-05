package br.com.devolucao.backend.inv_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String local;
    private String email;
}
