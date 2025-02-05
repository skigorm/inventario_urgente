package br.com.devolucao.backend.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devolucao.backend.exception.ApplicationServiceException;
import br.com.devolucao.backend.repositories.EnderecoRepository;
import org.apache.commons.lang3.StringUtils;
import javax.transaction.Transactional;

@Service
public class EnderecoService {

	private static final Logger LOGGER = Logger.getLogger(EnderecoService.class.getName());

	private EnderecoRepository enderecoRepository;

	public static final String DESC_OBJETO_PRINCIPAL = "Endereco";

	@Autowired
	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	public List<String> getAllUFs() {
		LOGGER.info("Obter UFs");
		return enderecoRepository.findAllDistinctUfs();
	}

	public List<String> getMunicipiosByUf(String uf) {
		LOGGER.info("Obter municipios no uf:" + uf);

		if (StringUtils.isEmpty(uf)) {
			throw new IllegalArgumentException("uf deve ser fornecido.");
		}

		return enderecoRepository.findMunicipioByUf(uf);
	}

	public List<String> obterBairros(String uf, String cidade) throws ApplicationServiceException {

		LOGGER.info("Obter bairros, params: uf" + uf + cidade);

		if (StringUtils.isEmpty(uf) || StringUtils.isEmpty(cidade)) {
			throw new IllegalArgumentException("UF e cidade devem ser fornecidos.");
		}

		List<String> bairros = enderecoRepository.findBairrosByUfAndMunicipio(uf, cidade);

		if (bairros.isEmpty()) {
			throw new ApplicationServiceException("bairros.naoencontrado");
		}

		return bairros;
	}

	public List<String> obterLojas(String municipio, String bairro) {

		LOGGER.info("Obter lojas no bairro:" + bairro);

		if (StringUtils.isEmpty(bairro)) {
			throw new IllegalArgumentException("Bairro deve ser fornecido.");
		}

		return enderecoRepository.findLojasByBairro(municipio, bairro);
	}

}
