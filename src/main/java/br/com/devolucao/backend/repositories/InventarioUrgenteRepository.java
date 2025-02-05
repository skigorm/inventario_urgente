package br.com.devolucao.backend.repositories;

import br.com.devolucao.backend.domain.InventarioUrgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioUrgenteRepository extends JpaRepository<InventarioUrgente, Long> {
    Optional<InventarioUrgente> findByCodigoSapAndSerial(String codigoSap, String serial);
}
