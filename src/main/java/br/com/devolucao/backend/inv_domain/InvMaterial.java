package br.com.devolucao.backend.inv_domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inv_materiais")
public class InvMaterial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String local;
    private String centro;
    private String deposito;
    private String material;
    private String textoBreveMaterial;
    private String utilizacaoLivre;
    private String valorUtilizacaoLivre;
    private String umb;
    private String transitoTe;
    private String valorEmTransitoTransferencia;
    private String emControleQualidade;
    private String valorVerificadoQualidade;
    private String bloqueado;
    private String valorEstoqueBloqueado;
    private String nome;
    private String lote;
}
