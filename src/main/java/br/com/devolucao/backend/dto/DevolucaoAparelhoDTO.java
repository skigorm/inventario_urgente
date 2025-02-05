package br.com.devolucao.backend.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevolucaoAparelhoDTO {
    private String codigoAparelho;
    private boolean aprovado;
    private LocalDateTime dataDevolucao;
    private String nrContrato;


    public DevolucaoAparelhoDTO(String codigoAparelho, Boolean aprovado,String nrContrato) {
        this.codigoAparelho = codigoAparelho;
        this.aprovado = aprovado;
        this.nrContrato = nrContrato;
    }
}