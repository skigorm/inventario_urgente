package br.com.devolucao.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import br.com.devolucao.backend.domain.Chamado;
import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.dto.ChamadoDTO;
import br.com.devolucao.backend.dto.ChamadoDTOView;
import br.com.devolucao.backend.dto.ChamadoWithAparelhosDTO;
import br.com.devolucao.backend.exception.ChamadoException;
import br.com.devolucao.backend.exception.UserNotFoundException;
import br.com.devolucao.backend.services.ChamadoService;
import br.com.devolucao.backend.services.UserService;
import br.com.devolucao.backend.util.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/chamado")
@Tag(name = "Chamados", description = "Operações referentes à inclusão de chamados.")
public class ChamadoController {

	private final ChamadoService chamadoService;
	private final UserService userService;

	@Autowired
	public ChamadoController(ChamadoService chamadoService, UserService userService) {
		this.chamadoService = chamadoService;
		this.userService = userService;
	}

	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody ChamadoDTO chamadoDTO) {
	    try {
			if (chamadoDTO.getDevolucaoAparelho().isEmpty() || chamadoDTO.getDevolucaoAparelho().size() == 0 || chamadoDTO.getDevolucaoAparelho() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao incluir o chamado, tente novamente!");
			}
	        User usuarioLogado = obterUsuarioLogado();
	        chamadoService.incluir(chamadoDTO, usuarioLogado);
	        return ResponseEntity.ok().build();
	    } catch (UserNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não 	encontrado: " + e.getMessage());
	    } catch (ChamadoException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao incluir 	o chamado: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Erro interno: " + e.getMessage());
	    }
}


	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		try {
			User usuarioLogado = obterUsuarioLogado();

			List<ChamadoDTOView> lista = chamadoService.obterChamados(usuarioLogado);
			return ResponseEntity.ok(lista);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao listar os chamados: " + e.getMessage());
		}
	}

	@GetMapping("/listar-todos")
	public ResponseEntity<?> listarTodosComDevolucaoAparelho() {
		User usuarioLogado = obterUsuarioLogado();
		if (!usuarioLogado.getUsername().isEmpty()) {
			try {
				List<ChamadoWithAparelhosDTO> lista = chamadoService.findAllWithDevolucaoAparelho();
				return ResponseEntity.ok(lista);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Erro ao listar os chamados: " + e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
		}
	}

	@GetMapping("/status")
	public ResponseEntity<String> status() {
		return ResponseEntity.ok("OK");
	}

	private User obterUsuarioLogado() throws UserNotFoundException {
		UserDetails userDetails = SecurityUtils.getCurrentUserDetails();
		String username = userDetails.getUsername();
		return userService.obterPorLogin(username);
	}
}
