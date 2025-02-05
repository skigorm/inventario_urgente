package br.com.devolucao.backend.dto;

import java.util.List;

import br.com.devolucao.backend.domain.Safra;
import br.com.devolucao.backend.domain.Equipamento;
import br.com.devolucao.backend.enumerated.SituacaoProduto;
import lombok.Data;

@Data
public class CustomResponseDTO {
    private SituacaoProduto status;
    private List<Safra> safra;
    private List<Equipamento> equipamento;

    public CustomResponseDTO(SituacaoProduto status) {
        this.status = status;
    }

    public CustomResponseDTO(SituacaoProduto status, List<Safra> safra, List<Equipamento> equipamento) {
        this.status = status;
        this.safra = safra;
        this.equipamento = equipamento;
    }

    public void setSafra(List<Safra> safra) {
        this.safra = safra;
    }

    public void setEquipamento(List<Equipamento> equipamento) {
        this.equipamento = equipamento;
    }
}
