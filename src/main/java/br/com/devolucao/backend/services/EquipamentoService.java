package br.com.devolucao.backend.services;

import br.com.devolucao.backend.domain.InventarioUrgente;
import br.com.devolucao.backend.inv_domain.InvUsuario;
import br.com.devolucao.backend.inv_repositories.InvUsuarioRepository;
import br.com.devolucao.backend.repositories.InventarioUrgenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipamentoService {

    private final InventarioUrgenteRepository inventarioUrgenteRepository;
    private final InvUsuarioRepository invUsuarioRepository;

    @Transactional
    public ResponseEntity<?> registrarInventario(InventarioUrgente request) {
        // Verifica se o usuário existe
        Optional<InvUsuario> usuarioOpt = invUsuarioRepository.findById(request.getUsuario().getId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        // Criar e salvar o novo registro de inventário
        InventarioUrgente novoInventario = new InventarioUrgente();
        novoInventario.setCodigoSap(request.getCodigoSap());
        novoInventario.setSerial(request.getSerial());
        novoInventario.setUsuario(usuarioOpt.get());

        inventarioUrgenteRepository.save(novoInventario);

        return ResponseEntity.ok("Inventário registrado com sucesso.");
    }

    public String gerarCsvInventario() {

        log.info("Gerando CSV de inventário urgente.");

        List<InventarioUrgente> invetariosRegistrados = inventarioUrgenteRepository.findAll();
        StringWriter writer = new StringWriter();
        writer.append("id,codigo_sap,serial,usuario_id\n");
        for (InventarioUrgente inventario : invetariosRegistrados) {
            writer.append(inventario.getId().toString()).append(';')
                    .append(inventario.getCodigoSap()).append(';')
                    .append(inventario.getSerial()).append(';')
                    .append(inventario.getUsuario().getId().toString()).append('\n');
        }
        return writer.toString();
    }

}

