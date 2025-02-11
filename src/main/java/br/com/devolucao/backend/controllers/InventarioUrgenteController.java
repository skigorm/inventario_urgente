package br.com.devolucao.backend.controllers;

import br.com.devolucao.backend.domain.InventarioUrgente;
import br.com.devolucao.backend.services.EquipamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario-urgente")
@RequiredArgsConstructor
public class InventarioUrgenteController {

    private final EquipamentoService equipamentoService;

    @PostMapping
    public ResponseEntity<?> registrarInventario(@RequestBody InventarioUrgente request) {
        return equipamentoService.registrarInventario(request);
    }

    @GetMapping("/export-csv")
    public ResponseEntity<String> exportarCsv() {
        String csvContent = equipamentoService.gerarCsvInventario();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario_urgente.csv");
        return ResponseEntity.ok().headers(headers).body(csvContent);
    }

}
