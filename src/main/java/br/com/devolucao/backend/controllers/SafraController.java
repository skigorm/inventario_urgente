package br.com.devolucao.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.devolucao.backend.domain.Safra;
import br.com.devolucao.backend.dto.CustomResponseDTO;
import br.com.devolucao.backend.enumerated.SituacaoProduto;
import br.com.devolucao.backend.repositories.SafraRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/safra")
public class SafraController {

    @Autowired
    private SafraRepository safraRepository;

    @PostMapping("/verificarSafraPorNrSerial")
    public ResponseEntity<CustomResponseDTO> verificarSafrasPorNrSerial(@RequestBody List<String> nrSeriais) {
        List<Safra> safraEncontradas = nrSeriais.stream()
                .map(safraRepository::findFirstByNrSerial)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        SituacaoProduto situacaoSafra = safraEncontradas.isEmpty() 
                ? SituacaoProduto.NAO_ENCONTRADO 
                : SituacaoProduto.ENCONTRADO;
        CustomResponseDTO response = new CustomResponseDTO(situacaoSafra, safraEncontradas, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public Safra createSafra(@RequestBody Safra safra) {
        return safraRepository.save(safra);
    }
}
