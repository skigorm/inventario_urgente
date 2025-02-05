package br.com.devolucao.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devolucao.backend.domain.Safra;

public interface SafraRepository extends JpaRepository<Safra, Long> {

    Optional<Safra> findFirstByNrSerial(String nrSerial);

}
