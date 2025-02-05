package br.com.devolucao.backend.repositories;

import br.com.devolucao.backend.domain.Log;
import br.com.devolucao.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUser(User user);
}
