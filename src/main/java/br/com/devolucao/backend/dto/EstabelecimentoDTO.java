package br.com.devolucao.backend.dto;

import br.com.devolucao.backend.domain.Estabelecimento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized @Builder
public class EstabelecimentoDTO {
	
	private String identificacao;
	private String cep;
	private String uf;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	
	public EstabelecimentoDTO(Estabelecimento estabelecimento) {
        this.identificacao = estabelecimento.getIdentificacao();
        this.cep = estabelecimento.getCep();
        this.uf = estabelecimento.getUf();
        this.logradouro = estabelecimento.getLogradouro();
        this.numero = estabelecimento.getNumero();
        this.complemento = estabelecimento.getComplemento();
        this.bairro = estabelecimento.getBairro();
        this.municipio = estabelecimento.getMunicipio();
	}

}