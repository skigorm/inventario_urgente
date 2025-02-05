package br.com.devolucao.backend.services;

import br.com.devolucao.backend.domain.DevolucaoAparelho;
import br.com.devolucao.backend.repositories.DevolucaoAparelhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevolucaoAparelhoService {

    private final DevolucaoAparelhoRepository devolucaoAparelhoRepository;

    public DevolucaoAparelhoService(DevolucaoAparelhoRepository devolucaoAparelhoRepository) {
        this.devolucaoAparelhoRepository = devolucaoAparelhoRepository;
    }

    public DevolucaoAparelho obterDevolucaoPorId(Long id) {
        return devolucaoAparelhoRepository.findById(id).orElse(null);
    }

    public List<DevolucaoAparelho> obterDevolucoesPorChamado(Long idChamado) {
        return devolucaoAparelhoRepository.findByChamadoId(idChamado);
    }
}
