package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.LocacaoDTO;
import br.upf.biblioteca.repository.LocacaoRepository;

@Service
public class LocacaoService {

	@Autowired
	private LocacaoRepository locacaoRepository;
	
	public LocacaoDTO salvar(LocacaoDTO dto) {
		return locacaoRepository.save(dto);
	}
	
	public List<LocacaoDTO> listarTodos() {
		return locacaoRepository.findAll();
	}
	
	public Optional<LocacaoDTO> buscaPorCd(Long cdLocacao) {
		return locacaoRepository.findById(cdLocacao);
	}
	
	public void removerPorCd(Long cdLocacao) {
		locacaoRepository.deleteById(cdLocacao);
	}
	
}
