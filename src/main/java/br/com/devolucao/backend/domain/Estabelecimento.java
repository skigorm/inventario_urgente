package br.com.devolucao.backend.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_estabelecimento")
@Entity(name = "estabelecimento")
@EqualsAndHashCode(of = "id")
public class Estabelecimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String identificacao;

	// endereco

	private String cep;

	private String uf;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String municipio;

}