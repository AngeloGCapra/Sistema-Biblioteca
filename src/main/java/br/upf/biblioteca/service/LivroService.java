package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.LivroDTO;
import br.upf.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	public LivroDTO salvar(LivroDTO dto) {
		return livroRepository.save(dto);
	}
	
	public List<LivroDTO> listarTodos() {
		return livroRepository.findAll();
	}
	
	public Optional<LivroDTO> buscaPorCd(Long cdLivro) {
		return livroRepository.findById(cdLivro);
	}
	
	public void removerPorCd(Long cdLivro) {
		livroRepository.deleteById(cdLivro);
	}
	
}
