package br.com.devolucao.backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.devolucao.backend.domain.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    boolean existsByNumeroSerie(String numeroSerie);

    Optional<Equipamento> findFirstByNumeroSerie(String numeroSerie);

}
