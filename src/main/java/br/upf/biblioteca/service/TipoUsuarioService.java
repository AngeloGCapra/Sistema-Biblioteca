package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.TipoUsuarioDTO;
import br.upf.biblioteca.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	public TipoUsuarioDTO salvar(TipoUsuarioDTO dto) {
		return tipoUsuarioRepository.save(dto);
	}
	
	public List<TipoUsuarioDTO> listarTodos() {
		return tipoUsuarioRepository.findAll();
	}
	
	public Optional<TipoUsuarioDTO> buscaPorCd(Long cdTipoUsuario) {
		return tipoUsuarioRepository.findById(cdTipoUsuario);
	}
	
	public void removerPorCd(Long cdTipoUsuario) {
		tipoUsuarioRepository.deleteById(cdTipoUsuario);
	}
	
}
