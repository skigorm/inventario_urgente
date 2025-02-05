package br.com.devolucao.backend.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.com.devolucao.backend.enumerated.SituacaoChamado;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tb_chamado")
@Entity(name = "chamado")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chamado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL)
	private List<ChamadoImagem> imagens;

	@OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL)
	private List<DevolucaoAparelho> devolucaoAparelho;

	@Column(name = "dataAgendamento")
	private LocalDateTime dataAgendamento;

	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	@Column(name = "situacao")
	private Integer situacaoCodigo;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public SituacaoChamado getSituacao() {
		if (situacaoCodigo == null) {
			return null;
		}
		return SituacaoChamado.porCodigo(situacaoCodigo);
	}

	public void setSituacao(SituacaoChamado situacao) {
		this.situacaoCodigo = situacao.getCodigo();
	}

	@PrePersist
	public void prePersist() {
		situacaoCodigo = SituacaoChamado.EM_ANDAMENTO.getCodigo();
	}

}