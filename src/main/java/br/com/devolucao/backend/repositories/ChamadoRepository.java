package br.com.devolucao.backend.repositories;

import java.util.List;

import br.com.devolucao.backend.enumerated.SituacaoChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devolucao.backend.domain.Chamado;
import br.com.devolucao.backend.domain.User;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

	// Consulta que retorna chamados por usuário
	List<Chamado> findByUser(User user);

	// Consulta que retorna chamados por usuário e situação
	default List<Chamado> findByUserAndSituacao(User user, SituacaoChamado situacao) {
		return findByUserAndSituacaoCodigo(user, situacao.getCodigo());
	}

	// Consulta que retorna chamados filtrados pelo código da situação
	List<Chamado> findByUserAndSituacaoCodigo(User user, Integer situacaoCodigo);

	// Consulta que retorna chamados por código de situação
	List<Chamado> findBySituacaoCodigo(Integer situacaoCodigo);

}
