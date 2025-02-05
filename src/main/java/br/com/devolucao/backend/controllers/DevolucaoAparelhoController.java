package br.com.devolucao.backend.controllers;

import br.com.devolucao.backend.domain.DevolucaoAparelho;
import br.com.devolucao.backend.dto.DevolucaoAparelhoDTO;
import br.com.devolucao.backend.services.DevolucaoAparelhoService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devolucoes")
public class DevolucaoAparelhoController {

    private final DevolucaoAparelhoService devolucaoAparelhoService;

    public DevolucaoAparelhoController(DevolucaoAparelhoService devolucaoAparelhoService) {
        this.devolucaoAparelhoService = devolucaoAparelhoService;
    }

    @GetMapping("/chamado/{idChamado}")
    public ResponseEntity<List<DevolucaoAparelhoDTO>> obterDevolucoesPorChamado(@PathVariable Long idChamado) {
        List<DevolucaoAparelho> devolucoes = devolucaoAparelhoService.obterDevolucoesPorChamado(idChamado);
        List<DevolucaoAparelhoDTO> aparelhos = new ArrayList<>();
        if (devolucoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (DevolucaoAparelho devolucao : devolucoes) {
            DevolucaoAparelhoDTO devolucaoDTO = new DevolucaoAparelhoDTO(devolucao.getCodigoAparelho(),
                    devolucao.isAprovado(), devolucao.getDataDevolucao(), devolucao.getNrContrato());
            aparelhos.add(devolucaoDTO);
        }
        return ResponseEntity.ok(aparelhos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolucaoAparelho> obterDevolucaoPorId(@PathVariable Long id) {
        DevolucaoAparelho devolucaoAparelho = devolucaoAparelhoService.obterDevolucaoPorId(id);
        if (devolucaoAparelho != null) {
            return ResponseEntity.ok(devolucaoAparelho);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
