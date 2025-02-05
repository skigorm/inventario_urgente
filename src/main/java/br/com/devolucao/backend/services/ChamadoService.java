package br.com.devolucao.backend.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import br.com.devolucao.backend.enumerated.SituacaoChamado;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devolucao.backend.domain.Chamado;
import br.com.devolucao.backend.domain.ChamadoImagem;
import br.com.devolucao.backend.domain.DevolucaoAparelho;
import br.com.devolucao.backend.domain.Estabelecimento;
import br.com.devolucao.backend.domain.User;
import br.com.devolucao.backend.dto.ChamadoDTO;
import br.com.devolucao.backend.dto.ChamadoDTOView;
import br.com.devolucao.backend.dto.ChamadoWithAparelhosDTO;
import br.com.devolucao.backend.dto.AparelhoDTO;
import br.com.devolucao.backend.repositories.ChamadoRepository;

@Service
public class ChamadoService {

	private final ChamadoRepository chamadoRepository;
	private final EstabelecimentoService estabelecimentoService;

	@Autowired
	public ChamadoService(ChamadoRepository chamadoRepository, EstabelecimentoService estabelecimentoService) {
		this.chamadoRepository = chamadoRepository;
		this.estabelecimentoService = estabelecimentoService;
	}

	@Transactional
	public void incluir(ChamadoDTO chamadoDTO, User usuarioLogado) {
		Chamado objeto = new Chamado();
		Estabelecimento estabObjeto = estabelecimentoService.obterEstabelecimento(chamadoDTO);

		objeto.setEstabelecimento(estabObjeto);
		objeto.setDataAgendamento(LocalDateTime.now());
		objeto.setUser(usuarioLogado);

		ImageUtils imageUtils = new ImageUtils();

		try {
			List<ChamadoImagem> imagens = chamadoDTO.getImagens().stream().map(imagemBase64 -> {
				ChamadoImagem imagem = new ChamadoImagem();
				try {
					String resizedImageBase64 = imageUtils.resizeImage(imagemBase64, 800, 600);
					imagem.setImageBase64(resizedImageBase64);
				} catch (IOException e) {
					throw new RuntimeException("Erro ao redimensionar imagem", e);
				}
				imagem.setChamado(objeto);
				return imagem;
			}).collect(Collectors.toList());

			List<DevolucaoAparelho> devolucoes = chamadoDTO.getDevolucaoAparelho().stream().map(devolucaoDTO -> {
				DevolucaoAparelho dev = new DevolucaoAparelho();
				dev.setChamado(objeto);
				dev.setDataDevolucao(LocalDateTime.now());
				dev.setCodigoAparelho(devolucaoDTO.getCodigoAparelho());
				dev.setAprovado(devolucaoDTO.isAprovado());
				dev.setNrContrato(devolucaoDTO.getNrContrato());
				return dev;
			}).collect(Collectors.toList());

			objeto.setDevolucaoAparelho(devolucoes);
			objeto.setImagens(imagens);

			chamadoRepository.saveAndFlush(objeto);

			System.out.println("Atualizado sistema");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e; // Re-throwing the exception to ensure transaction rollback
		}
	}

	@Transactional(readOnly = true)
	public List<ChamadoDTOView> obterChamados(User usuarioLogado) {
		List<Chamado> listChamados = chamadoRepository.findByUserAndSituacao(usuarioLogado, SituacaoChamado.porCodigo(2));
		List<ChamadoDTOView> listDto = new ArrayList<>();

		listChamados.stream().forEach(chamado -> {
			ChamadoDTOView dto = new ChamadoDTOView();

			dto.setId(chamado.getId());
			dto.setDataAgendamento(chamado.getDataAgendamento());
			dto.setSituacaoCodigo(chamado.getSituacao().getCodigo());
			dto.setSituacao(chamado.getSituacao().name());
			dto.setAparelhos(chamado.getDevolucaoAparelho().stream().map(devolucao -> {
				AparelhoDTO dev = new AparelhoDTO();
				dev.setCodigoAparelho(devolucao.getCodigoAparelho());
				dev.setAprovado(devolucao.isAprovado());
				dev.setNrContrato(devolucao.getNrContrato());
				return dev;
			}).collect(Collectors.toList()));

			listDto.add(dto);
		});

		return listDto;
	}

	@Transactional(readOnly = true)
	public List<ChamadoWithAparelhosDTO> findAllWithDevolucaoAparelho() {
		return chamadoRepository.findBySituacaoCodigo(2).stream().map(chamado -> {
			ChamadoWithAparelhosDTO dto = new ChamadoWithAparelhosDTO();
			dto.setId(chamado.getId());
			dto.setDataAgendamento(chamado.getDataAgendamento());
			dto.setSituacaoCodigo(chamado.getSituacao().getCodigo());
			dto.setEstabelecimentoId(chamado.getEstabelecimento().getId());
			dto.setUserId(chamado.getUser().getId());
			dto.setAparelhos(chamado.getDevolucaoAparelho().stream().map(devolucao -> {
				AparelhoDTO dev = new AparelhoDTO();
				dev.setCodigoAparelho(devolucao.getCodigoAparelho());
				dev.setAprovado(devolucao.isAprovado());
				dev.setNrContrato(devolucao.getNrContrato());
				return dev;
			}).collect(Collectors.toList()));
			dto.setCreatedAt(chamado.getDataAgendamento());
			return dto;
		}).collect(Collectors.toList());
	}

	// ImageUtils pode ser movida para fora se for reutilizada em outros lugares
	public class ImageUtils {
		public String resizeImage(String imageBase64, int width, int height) throws IOException {
			byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

			try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
				 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

				Thumbnails.of(inputStream)
						.size(width, height)
						.outputFormat("jpg")
						.toOutputStream(outputStream);

				byte[] resizedImageBytes = outputStream.toByteArray();
				return Base64.getEncoder().encodeToString(resizedImageBytes);
			}
		}
	}
}
