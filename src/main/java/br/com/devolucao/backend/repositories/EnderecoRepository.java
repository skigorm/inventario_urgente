package br.com.devolucao.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.devolucao.backend.domain.Estabelecimento;

@Repository
public interface EnderecoRepository extends JpaRepository<Estabelecimento, Long> {

	@Query("SELECT DISTINCT e.bairro FROM estabelecimento e WHERE e.uf = :uf AND e.municipio = :municipio")
	List<String> findBairrosByUfAndMunicipio(@Param("uf") String uf, @Param("municipio") String municipio);

	@Query("SELECT DISTINCT e.identificacao FROM estabelecimento e WHERE e.municipio = :municipio AND e.bairro = :bairro")
	List<String> findLojasByBairro(@Param("municipio") String municipio, @Param("bairro") String bairro);

	@Query("SELECT DISTINCT e.municipio FROM estabelecimento e WHERE e.uf = :uf")
	List<String> findMunicipioByUf(@Param("uf") String uf);

	@Query("SELECT DISTINCT e.uf FROM estabelecimento e")
	List<String> findAllDistinctUfs();

}
