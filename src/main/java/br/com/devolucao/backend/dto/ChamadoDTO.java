package br.com.devolucao.backend.dto;

import java.util.List;
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
public class ChamadoDTO {

	private List<String> imagens;
	private List<DevolucaoAparelhoDTO> devolucaoAparelho;
	private String uf;
	private String municipio;
	private String bairro;
	private String loja;

	public ChamadoDTO(List<String> imagens, List<DevolucaoAparelhoDTO> devolucaoAparelho) {
		this.imagens = imagens;
		this.devolucaoAparelho = devolucaoAparelho;
	}

}