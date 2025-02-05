package br.com.devolucao.backend.controllers;

import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.dto.LogDTO;
import br.com.devolucao.backend.exception.UserNotFoundException;
import br.com.devolucao.backend.services.LogService;
import br.com.devolucao.backend.services.UserService;
import br.com.devolucao.backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint para registrar um novo log associado ao usuário logado.
     *
     * @param logDTO DTO contendo o código e a mensagem do log.
     * @return Resposta de sucesso ou erro.
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarLog(@RequestBody LogDTO logDTO) {
        try {
            User usuarioLogado = obterUsuarioLogado();
            logService.registrarLog(logDTO, usuarioLogado);
            return ResponseEntity.ok().body("Log registrado com sucesso.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar log: " + e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os logs associados ao usuário logado.
     *
     * @return Lista de logs do usuário.
     */
    @GetMapping("/listar")
    public ResponseEntity<?> listarLogsDoUsuario() {
        try {
            User usuarioLogado = obterUsuarioLogado();
            List<LogDTO> logs = logService.obterLogsPorUsuario(usuarioLogado);
            return ResponseEntity.ok(logs);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar logs: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para obter o usuário logado com base nos detalhes de segurança.
     *
     * @return O usuário logado.
     * @throws UserNotFoundException Caso o usuário não seja encontrado.
     */
    private User obterUsuarioLogado() throws UserNotFoundException {
        String username = SecurityUtils.getCurrentUserDetails().getUsername();
        return userService.obterPorLogin(username);
    }
}
