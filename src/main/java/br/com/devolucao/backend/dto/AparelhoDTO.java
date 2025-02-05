package br.com.devolucao.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AparelhoDTO {
    private String codigoAparelho;
    private boolean aprovado;
    private String nrContrato;
}