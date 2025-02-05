package br.com.devolucao.backend.dto;

import br.com.devolucao.backend.domain.Equipamento;
import br.com.devolucao.backend.enumerated.SituacaoProduto;
import lombok.Data;

@Data
public class EquipamentoResponseDTO {
    private Equipamento equipamento;
    private SituacaoProduto status;

    public EquipamentoResponseDTO(SituacaoProduto status, Equipamento equipamento) {
        this.status = status;
        this.equipamento = equipamento;
    }

    public EquipamentoResponseDTO(SituacaoProduto status) {
        this.status = status;
    }
}
