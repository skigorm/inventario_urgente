package br.com.devolucao.backend.services;

import br.com.devolucao.backend.domain.Log;
import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.dto.LogDTO;
import br.com.devolucao.backend.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    /**
     * Registra um novo log associado ao usuário.
     *
     * @param logDTO   Informações do log.
     * @param user     Usuário associado ao log.
     */
    public void registrarLog(LogDTO logDTO, User user) {
        Log log = new Log(logDTO.getCodigo(), logDTO.getMensagem(), LocalDateTime.now(), user);
        logRepository.save(log);
    }

    /**
     * Obtém todos os logs associados a um determinado usuário.
     *
     * @param user Usuário para o qual os logs serão recuperados.
     * @return Lista de DTOs de logs.
     */
    public List<LogDTO> obterLogsPorUsuario(User user) {
        return logRepository.findByUser(user).stream()
                .map(log -> new LogDTO(log.getCodigo(), log.getMensagem()))
                .collect(Collectors.toList());
    }
}
