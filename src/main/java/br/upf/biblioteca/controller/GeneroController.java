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
import br.upf.biblioteca.dto.GeneroDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.GeneroService;

@RestController
@RequestMapping(value = "/genero")
public class GeneroController {

	@Autowired
	private GeneroService generoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public GeneroDTO inserir(@RequestBody GeneroDTO genero,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);	
		return generoService.salvar(genero);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<GeneroDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return generoService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public GeneroDTO buscarPorCd(@RequestHeader(value = "cdGenero") Long cdGenero,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return generoService.buscaPorCd(cdGenero)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Gênero não encontrado!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader (value = "cdGenero") Long cdGenero,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		generoService.buscaPorCd(cdGenero)
			.map(genero -> {
				generoService.removerPorCd(genero.getCdGenero());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Gênero não encontrado!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param genero
	 * @param cdGenero
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody GeneroDTO genero, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		generoService.buscaPorCd(genero.getCdGenero()).map(generoBase -> {
			modelMapper.map(genero, generoBase);
			generoService.salvar(generoBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Gênero não encontrado!"));
	}
	
}
