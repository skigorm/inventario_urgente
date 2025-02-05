package br.com.devolucao.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devolucao.backend.exception.ApplicationServiceException;
import br.com.devolucao.backend.services.EnderecoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/uf")
@Tag(name = "Enderecos", description = "Operações referentes à busca de endereços.")
public class UfMunicipioController {

    private final EnderecoService enderecoService;

    public UfMunicipioController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllUFs() {
        List<String> ufsList = enderecoService.getAllUFs();
        if (ufsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ufsList);
    }

    @GetMapping("/{uf}/municipios")
    public ResponseEntity<List<String>> getMunicipiosByUf(@PathVariable String uf) {
        List<String> municipios = enderecoService.getMunicipiosByUf(uf);
        if (municipios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(municipios);
    }

    @GetMapping("/bairros/{uf}/{cidade}")
    public ResponseEntity<List<String>> obterBairros(@PathVariable String uf, @PathVariable String cidade)
            throws ApplicationServiceException {
        List<String> bairros = enderecoService.obterBairros(uf, cidade);
        if (bairros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bairros);
    }

    @GetMapping("/lojas/{municipio}/{bairro}")
    public ResponseEntity<List<String>> obterLojas(@PathVariable String municipio, @PathVariable String bairro) {
        List<String> lojas = enderecoService.obterLojas(municipio, bairro);
        if (lojas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lojas);
    }

}
