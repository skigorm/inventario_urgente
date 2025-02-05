package br.com.devolucao.backend.inv_repositories;

import br.com.devolucao.backend.inv_domain.InvUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvUsuarioRepository extends JpaRepository<InvUsuario, Long> {
    Optional<InvUsuario> findByEmailAndSenha(String email, String senha);
}
