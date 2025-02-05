package br.com.devolucao.backend.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.devolucao.backend.domain.Chamado;
import br.com.devolucao.backend.domain.ChamadoImagem;
import br.com.devolucao.backend.enumerated.SituacaoChamado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Jacksonized
@Builder
public class ChamadoDTOView {

	private Long id;

	//private List<String> imagensBase64;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAgendamento;

	//private EstabelecimentoDTO estabelecimentoDTO;

	private int situacaoCodigo;

	private String situacao;

	private List<AparelhoDTO> aparelhos;

	public ChamadoDTOView(Chamado chamado) {
//		if (chamado.getImagens() != null && !chamado.getImagens().isEmpty()) {
//			this.imagensBase64 = chamado.getImagens().stream().map(ChamadoImagem::getImageBase64)
//					.collect(Collectors.toList());
//		} else {
//			this.imagensBase64 = Collections.emptyList();
//		}
		this.dataAgendamento = chamado.getDataAgendamento();
		this.id = chamado.getId();
		//this.estabelecimentoDTO = new EstabelecimentoDTO(chamado.getEstabelecimento());
		this.situacaoCodigo = SituacaoChamado.porCodigo(chamado.getSituacaoCodigo()).getCodigo();
		this.situacao = SituacaoChamado.porCodigo(situacaoCodigo).name();
	}
}