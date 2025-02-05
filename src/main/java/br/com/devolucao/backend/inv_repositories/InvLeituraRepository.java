package br.com.devolucao.backend.inv_repositories;

import br.com.devolucao.backend.inv_domain.InvLeitura;
import br.com.devolucao.backend.inv_domain.InvUsuario;
import br.com.devolucao.backend.inv_domain.InvMaterial;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvLeituraRepository extends JpaRepository<InvLeitura, Long> {
    int countByUsuarioAndMaterial(InvUsuario usuario, InvMaterial material);
}
