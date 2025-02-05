package br.com.devolucao.backend.inv_repositories;

import br.com.devolucao.backend.inv_domain.InvMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvMaterialRepository extends JpaRepository<InvMaterial, Long> {
    Optional<InvMaterial> findFirstByMaterialAndCentro(String material, String centro);

    Optional<InvMaterial> findByMaterialAndCentroAndDepositoAndLocalAndLote(String material, String centro, String deposito, String local, String lote);

    @Query(value = "SELECT im.* FROM public.inv_materiais im " +
            "LEFT JOIN public.inv_leituras il ON im.id = il.material_id " +
            "WHERE il.material_id IS NULL", nativeQuery = true)
    List<InvMaterial> findAllMaterialsWithoutLeituras();

    @Query(value = "SELECT im.id, im.local, im.centro, im.deposito, im.material, im.nome, im.lote, " +
            "string_agg(CAST(il.quantidade AS TEXT), ', ') AS quantidades_concatenadas, " +
            "COUNT(il.id) AS total_leituras " +
            "FROM public.inv_materiais im " +
            "JOIN inv_leituras il ON im.id = il.material_id " +
            "GROUP BY im.id", nativeQuery = true)
    List<Object[]> findMaterialsWithLeiturasInfo();

}
