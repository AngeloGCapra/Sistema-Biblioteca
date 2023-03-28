package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.AutorDTO;
import br.upf.biblioteca.repository.AutorRepository;

@Service
public class AutorService {

	@Autowired
	private AutorRepository autorRepository;
	
	public AutorDTO salvar(AutorDTO dto) {
		return autorRepository.save(dto);
	}
	
	public List<AutorDTO> listarTodos() {
		return autorRepository.findAll();
	}
	
	public Optional<AutorDTO> buscaPorCd(Long cdAutor) {
		return autorRepository.findById(cdAutor);
	}
	
	public void removerPorCd(Long cdAutor) {
		autorRepository.deleteById(cdAutor);
	}
	
}
