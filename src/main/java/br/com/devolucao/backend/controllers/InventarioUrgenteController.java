package br.com.devolucao.backend.controllers;

import br.com.devolucao.backend.domain.InventarioUrgente;
import br.com.devolucao.backend.services.EquipamentoService;
import lombok.RequiredArgsConstructor;
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
}
