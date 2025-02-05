package br.com.devolucao.backend.inv_dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialResponseDTO {

    private Long id;
    private String local;
    private String centro;
    private String deposito;
    private String material;
    private String nome;
    private String lote;
    private String textoBreveMaterial;
    private int numeroLeitura;

    public MaterialResponseDTO(Long id, String local, String centro, String deposito, String material, String nome, String lote, String textoBreveMaterial) {
        this.id = id;
        this.local = local;
        this.centro = centro;
        this.deposito = deposito;
        this.material = material;
        this.nome = nome;
        this.lote = lote;
        this.textoBreveMaterial = textoBreveMaterial;
    }
}
