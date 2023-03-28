package br.upf.biblioteca.controller;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.upf.biblioteca.dto.TipoUsuarioDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.TipoUsuarioService;

@RestController
@RequestMapping(value = "/tipoUsuario")
public class TipoUsuarioController {

	@Autowired
	private TipoUsuarioService tipoUsuarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoUsuarioDTO inserir(@RequestBody TipoUsuarioDTO tipoUsuario,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);	
		return tipoUsuarioService.salvar(tipoUsuario);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoUsuarioDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return tipoUsuarioService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public TipoUsuarioDTO buscarPorCd(@RequestHeader(value = "cdTipoUsuario") Long cdTipoUsuario,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return tipoUsuarioService.buscaPorCd(cdTipoUsuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"TipoUsuario não encontrado!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader (value = "cdTipoUsuario") Long cdTipoUsuario,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		tipoUsuarioService.buscaPorCd(cdTipoUsuario)
			.map(tipoUsuario -> {
				tipoUsuarioService.removerPorCd(tipoUsuario.getCdTipoUsuario());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"TipoUsuario não encontrado!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param tipoUsuario
	 * @param cdTipoUsuario
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody TipoUsuarioDTO tipoUsuario, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		tipoUsuarioService.buscaPorCd(tipoUsuario.getCdTipoUsuario()).map(tipoUsuarioBase -> {
			modelMapper.map(tipoUsuario, tipoUsuarioBase);
			tipoUsuarioService.salvar(tipoUsuarioBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"TipoUsuario não encontrado!"));
	}
	
}
