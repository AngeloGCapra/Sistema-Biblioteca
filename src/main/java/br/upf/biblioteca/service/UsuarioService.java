package br.upf.biblioteca.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.biblioteca.dto.UsuarioDTO;
import br.upf.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public UsuarioDTO salvar(UsuarioDTO dto) {
		return usuarioRepository.save(dto);
	}
	
	public List<UsuarioDTO> listarTodos() {
		return usuarioRepository.findAll();
	}
	
	public Optional<UsuarioDTO> buscaPorCd(Long cdUsuario) {
		return usuarioRepository.findById(cdUsuario);
	}
	
	public void removerPorCd(Long cdUsuario) {
		usuarioRepository.deleteById(cdUsuario);
	}
	
	public UsuarioDTO buscarPorLogin(String dsLogin) {
		return usuarioRepository.findByDsLogin(dsLogin);
	}
	
}
