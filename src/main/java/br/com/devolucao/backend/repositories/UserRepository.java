package br.com.devolucao.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devolucao.backend.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByLogin(String login);
}