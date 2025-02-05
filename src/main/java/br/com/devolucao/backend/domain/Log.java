package br.com.devolucao.backend.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_logs")
public class Log {

    public Log(String codigo, String mensagem, LocalDateTime data, User user) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.data = data;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String codigo;
    private String mensagem;
    private LocalDateTime data;

}
