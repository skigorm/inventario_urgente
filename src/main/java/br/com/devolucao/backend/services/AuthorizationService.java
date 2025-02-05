package br.com.devolucao.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.repositories.UserRepository;

/**
 * Serviço de autorização que carrega detalhes do usuário.
 */
@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public AuthorizationService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Carrega um usuário pelo seu nome de usuário.
     *
     * @param username o nome de usuário.
     * @return os detalhes do usuário.
     * @throws UsernameNotFoundException se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user;
    }
}
