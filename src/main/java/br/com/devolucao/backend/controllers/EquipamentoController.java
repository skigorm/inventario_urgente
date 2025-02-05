package br.com.devolucao.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.devolucao.backend.domain.Equipamento;
import br.com.devolucao.backend.domain.Safra;
import br.com.devolucao.backend.dto.CustomResponseDTO;
import br.com.devolucao.backend.dto.EquipamentoResponseDTO;
import br.com.devolucao.backend.enumerated.SituacaoProduto;
import br.com.devolucao.backend.repositories.EquipamentoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @PostMapping("/verificarEquipamentoPorNrSerial")
    public ResponseEntity<CustomResponseDTO> verificarEquipamentoPorNrSerial(@RequestBody List<String> nrSeriais) {
        List<Equipamento> equipamentoEncontrados = nrSeriais.stream()
                .map(equipamentoRepository::findFirstByNumeroSerie)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        SituacaoProduto situacaoEquipamento = equipamentoEncontrados.isEmpty() 
                ? SituacaoProduto.NAO_ENCONTRADO 
                : SituacaoProduto.ENCONTRADO;
        CustomResponseDTO response = new CustomResponseDTO(situacaoEquipamento, null, equipamentoEncontrados);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public Equipamento createSafra(@RequestBody Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }
}
