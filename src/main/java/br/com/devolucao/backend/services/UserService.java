package br.com.devolucao.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    @Transactional(readOnly = true)
    public User obterPorLogin(String username) {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username);
        }
        return user;
    }
	
}
