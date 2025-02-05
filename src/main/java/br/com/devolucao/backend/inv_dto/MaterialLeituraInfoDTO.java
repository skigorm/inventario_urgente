package br.com.devolucao.backend.inv_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialLeituraInfoDTO {
    private Long id;
    private String local;
    private String centro;
    private String deposito;
    private String material;
    private String nome;
    private String lote;
    private String quantidadesConcatenadas;
    private Long totalLeituras;
}
