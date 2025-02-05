package br.com.devolucao.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devolucao.backend.domain.Estabelecimento;
import br.com.devolucao.backend.dto.ChamadoDTO;
import br.com.devolucao.backend.repositories.EstabelecimentoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstabelecimentoService {
	
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository) {
		this.estabelecimentoRepository = estabelecimentoRepository;
	}

	@Transactional(readOnly = true)
	public Estabelecimento obterEstabelecimento(ChamadoDTO chamadoDTO) {
		String uf = chamadoDTO.getUf();
		String municipio = chamadoDTO.getMunicipio();
		String bairro = chamadoDTO.getBairro();
		String loja = chamadoDTO.getLoja();

		return estabelecimentoRepository.obterEstabelecimento(uf, municipio, bairro, loja);
	}

}
