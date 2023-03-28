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
import br.upf.biblioteca.dto.AutorDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.AutorService;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {

	@Autowired
	private AutorService autorService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public AutorDTO inserir(@RequestBody AutorDTO autor,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);	
		return autorService.salvar(autor);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<AutorDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return autorService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public AutorDTO buscarPorCd(@RequestHeader(value = "cdAutor") Long cdAutor,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return autorService.buscaPorCd(cdAutor)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Autor não encontrado!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader (value = "cdAutor") Long cdAutor,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		autorService.buscaPorCd(cdAutor)
			.map(autor -> {
				autorService.removerPorCd(autor.getCdAutor());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Autor não encontrado!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param autor
	 * @param cdAutor
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody AutorDTO autor, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		autorService.buscaPorCd(autor.getCdAutor()).map(autorBase -> {
			modelMapper.map(autor, autorBase);
			autorService.salvar(autorBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Autor não encontrado!"));
	}
	
}
