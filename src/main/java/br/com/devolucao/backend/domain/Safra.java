package br.com.devolucao.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "safra")
@Entity(name = "Safra")
@Data
public class Safra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CD_OPERADORA")
    private int cdOperadora;
    @Column(name = "NR_CONTRATO")
    private int nrContrato;
    @Column(name = "NR_SERIAL")
    private String nrSerial;
    @Column(name = "NM_EQUIPAMENTO_MODELO")
    private String nmEquipamentoModelo;
    @Column(name = "NM_EQUIPAMENTO_TIPO")
    private String nmEquipamentoTipo;

}
