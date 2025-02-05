package br.com.devolucao.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devolucao.backend.domain.DevolucaoAparelho;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucaoAparelhoRepository extends JpaRepository<DevolucaoAparelho, Long> {

    List<DevolucaoAparelho> findByChamadoId(Long idChamado);

    boolean existsByCodigoAparelho(String codigoAparelho);

}
