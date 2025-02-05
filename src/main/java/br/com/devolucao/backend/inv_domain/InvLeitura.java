package br.com.devolucao.backend.inv_domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inv_leituras")
public class InvLeitura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private InvUsuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "material_id")
    private InvMaterial material;
    
    private Integer quantidade;
    private LocalDateTime dataHora = LocalDateTime.now();

    // Getters e Setters
}
