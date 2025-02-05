package br.com.devolucao.backend.inv_controllers;

import br.com.devolucao.backend.domain.Equipamento;
import br.com.devolucao.backend.inv_domain.InvLeitura;
import br.com.devolucao.backend.inv_domain.InvMaterial;
import br.com.devolucao.backend.inv_domain.InvUsuario;
import br.com.devolucao.backend.inv_dto.LeituraRequest;
import br.com.devolucao.backend.inv_dto.MaterialLeituraInfoDTO;
import br.com.devolucao.backend.inv_dto.MaterialResponseDTO;
import br.com.devolucao.backend.inv_repositories.InvLeituraRepository;
import br.com.devolucao.backend.inv_repositories.InvMaterialRepository;
import br.com.devolucao.backend.inv_repositories.InvUsuarioRepository;
import br.com.devolucao.backend.repositories.EquipamentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller para gerenciar operações com materiais e leituras.
 */
@RestController
@RequestMapping("/inv_material")
public class InvMaterialController {

    @Autowired
    private InvMaterialRepository materialRepository;
    
    @Autowired
    private InvUsuarioRepository usuarioRepository;
    
    @Autowired
    private InvLeituraRepository leituraRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;
//
//    @Autowired
//    private final EquipamentoService equipamentoService;

    /**
     * Consulta informações de um material com base no material e centro.
     * 
     * @param material Código do material
     * @param centro Centro associado ao material
     * @return Detalhes do material consultado
     */
    @Operation(summary = "Consulta material no inventário", description = "Retorna informações sobre o material no inventário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Material não encontrado",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/consultar/{material}/{centro}/{deposito}/{local}/{lote}/{usuarioId}")
    public ResponseEntity<?> consultarMaterial(@PathVariable String material, @PathVariable String centro, @PathVariable String deposito, @PathVariable String local, @PathVariable String lote, @PathVariable Long usuarioId) {
        Optional<InvUsuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<InvMaterial> materialOpt = materialRepository.findByMaterialAndCentroAndDepositoAndLocalAndLote(material, centro, deposito, local, lote);

        if (materialOpt.isPresent() && usuarioOpt.isPresent()) {
            InvUsuario usuario = usuarioOpt.get();
            InvMaterial mat = materialOpt.get();
            Integer numeroLeitura = leituraRepository.countByUsuarioAndMaterial(usuario, materialOpt.get());
            return ResponseEntity.ok(new MaterialResponseDTO(mat.getId(), mat.getLocal(), mat.getCentro(), mat.getDeposito(), mat.getMaterial(), mat.getNome(), mat.getLote(), mat.getTextoBreveMaterial(), (numeroLeitura)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material não encontrado");
        }
    }

    @Operation(summary = "Lista materiais sem leituras", description = "Retorna todos os materiais que ainda não possuem leituras registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materiais encontrados",
                    content = @Content(mediaType = "application/json", schema =     @Schema(implementation = MaterialResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum material sem   leituras encontrado",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/materiais/sem-leitura")
    public ResponseEntity<?> listarMateriaisSemLeituras() {
        List<InvMaterial> materiais = materialRepository.findAllMaterialsWithoutLeituras();
    
        if (materiais.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum material sem leituras encontrado.");
        }
    
        // Transformar a lista de InvMaterial em uma lista de DTOs, se necessário
        List<MaterialResponseDTO> response = materiais.stream()
                .map(mat -> new MaterialResponseDTO(mat.getId(), mat.getLocal(), mat.   getCentro(), mat.getDeposito(), mat.getMaterial(), mat.getNome(),  mat.getLote(), mat.getTextoBreveMaterial()))
                .collect(Collectors.toList());
    
        return ResponseEntity.ok(response);
    }

    /**
     * Registra uma nova leitura para um material, validando o número de leituras.
     * 
     * @param usuarioId ID do usuário que faz a leitura
     * @param material Código do material lido
     * @param centro Centro associado ao material
     * @param quantidade Quantidade lida
     * @return Mensagem de sucesso ou erro
     */
    @Operation(summary = "Registra uma leitura de material", description = "Salva uma nova leitura para o material e verifica o limite de leituras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leitura registrada com sucesso", 
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Erro ao registrar leitura (limite de leituras excedido)", 
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Usuário ou material não encontrado", 
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/leitura")
    public ResponseEntity<?> registrarLeitura(@RequestBody LeituraRequest   leituraRequest) {
        Optional<InvUsuario> usuarioOpt = usuarioRepository.findById(leituraRequest.getUsuarioId());
        Optional<InvMaterial> materialOpt = materialRepository.findByMaterialAndCentroAndDepositoAndLocalAndLote(leituraRequest.getMaterial(), leituraRequest.getCentro(), leituraRequest.getDeposito(), leituraRequest.getLocal(), leituraRequest.getLote());

        if (usuarioOpt.isPresent() && materialOpt.isPresent()) {
            InvUsuario usuario = usuarioOpt.get();
            InvMaterial mat = materialOpt.get();

            int count = leituraRepository.countByUsuarioAndMaterial (usuario, mat);
            if (count >= 3) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body ("Limite de leituras excedido para este material.");
            }

            InvLeitura leitura = new InvLeitura();
            leitura.setUsuario(usuario);
            leitura.setMaterial(mat);
            leitura.setQuantidade(leituraRequest.getQuantidade());
            leituraRepository.save(leitura);

            return ResponseEntity.ok("Leitura registrada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário ou material não encontrado.");
        }
    }

    @GetMapping("/materiais-com-leituras")
    public ResponseEntity<?> listarMateriaisComLeiturasInfo() {
        List<Object[]> materiaisComLeituras = materialRepository.findMaterialsWithLeiturasInfo();

        if (materiaisComLeituras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum material com leituras encontrado.");
        }

        List<MaterialLeituraInfoDTO> response = materiaisComLeituras.stream()
                .map(result -> new MaterialLeituraInfoDTO(
                        ((Number) result[0]).longValue(),  // Conversão correta para Long
                        result[1] != null ? result[1].toString() : null,
                        result[2] != null ? result[2].toString() : null,
                        result[3] != null ? result[3].toString() : null,
                        result[4] != null ? result[4].toString() : null,
                        result[5] != null ? result[5].toString() : null,
                        result[6] != null ? result[6].toString() : null,
                        result[7] != null ? result[7].toString() : null,
                        result[8] != null ? ((Number) result[8]).longValue() : null 
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

//    @PostMapping
//    public ResponseEntity<?> registrarEquipamento(@RequestBody EquipamentoRequest request) {
//        return equipamentoService.registrarEquipamento(request);
//    }

}
