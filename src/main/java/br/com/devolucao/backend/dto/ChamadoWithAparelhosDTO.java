package br.com.devolucao.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Jacksonized
@Builder
@NoArgsConstructor
public class ChamadoWithAparelhosDTO {
    private Long id;
    private LocalDateTime dataAgendamento;
    private Integer situacaoCodigo;
    private Long estabelecimentoId;
    private Long userId;
    private List<AparelhoDTO> aparelhos;
    private LocalDateTime createdAt;
}
