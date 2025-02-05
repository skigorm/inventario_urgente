package br.com.devolucao.backend.domain;

import br.com.devolucao.backend.inv_domain.InvUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "inventario_urgente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventarioUrgente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_sap", nullable = false)
    private String codigoSap;

    @Column(name = "serial", nullable = false)
    private String serial;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private InvUsuario usuario;
}
