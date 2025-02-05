package br.com.devolucao.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Table(name = "equipamentos_descricao")
@Entity(name = "equipamentos_descricao")
@Data
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CD_OPERADORA")
    private Long codigoOperadora;

    @Column(name = "NUM_CONTRATO")
    private Long numeroContrato;

    @Column(name = "DATA_ABERTURA_OS")
    private Timestamp dataAberturaOS;

    @Column(name = "NRO_SERIE")
    private String numeroSerie;

    @Column(name = "NM_MODELO")
    private String nomeModelo;

    @Column(name = "NM_TIPO_EQUIPAMENTO")
    private String nomeTipoEquipamento;

}
