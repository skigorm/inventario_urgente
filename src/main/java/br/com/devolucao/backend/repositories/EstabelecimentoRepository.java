package br.com.devolucao.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.devolucao.backend.domain.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>{

	@Query("SELECT e from estabelecimento e WHERE e.uf = :uf AND e.municipio = :municipio AND e.bairro = :bairro AND e.identificacao = :loja")
	Estabelecimento obterEstabelecimento(@Param("uf") String uf, @Param("municipio") String municipio,@Param("bairro")String bairro, @Param("loja") String loja);

}
