package br.com.devolucao.backend.inv_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeituraRequest {
    private Long usuarioId;
    private String material;
    private String centro;
    private String deposito;
    private String lote;
    private String local;
    private int quantidade;
}
