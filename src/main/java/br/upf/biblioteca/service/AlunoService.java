package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.AlunoDTO;
import br.upf.biblioteca.repository.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	public AlunoDTO salvar(AlunoDTO dto) {
		return alunoRepository.save(dto);
	}
	
	public List<AlunoDTO> listarTodos() {
		return alunoRepository.findAll();
	}
	
	public Optional<AlunoDTO> buscaPorCd(Long cdAluno) {
		return alunoRepository.findById(cdAluno);
	}
	
	public void removerPorCd(Long cdAluno) {
		alunoRepository.deleteById(cdAluno);
	}
	
}
