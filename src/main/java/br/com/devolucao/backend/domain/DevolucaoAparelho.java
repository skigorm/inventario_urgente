package br.com.devolucao.backend.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tb_devolucao_aparelho")
@Entity(name = "devolucao_aparelho")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevolucaoAparelho implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_aparelho")
    private String codigoAparelho;

    @Column(name = "aprovado")
    private boolean aprovado;

    @Column(name = "data_devolucao")
    private LocalDateTime dataDevolucao;

    @Column(name = "nr_contrato")
    private String nrContrato;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chamado_id")
    private Chamado chamado;
}
