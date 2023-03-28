package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.GeneroDTO;
import br.upf.biblioteca.repository.GeneroRepository;

@Service
public class GeneroService {

	@Autowired
	private GeneroRepository generoRepository;
	
	public GeneroDTO salvar(GeneroDTO dto) {
		return generoRepository.save(dto);
	}
	
	public List<GeneroDTO> listarTodos() {
		return generoRepository.findAll();
	}
	
	public Optional<GeneroDTO> buscaPorCd(Long cdGenero) {
		return generoRepository.findById(cdGenero);
	}
	
	public void removerPorCd(Long cdGenero) {
		generoRepository.deleteById(cdGenero);
	}
	
}
