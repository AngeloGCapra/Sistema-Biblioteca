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
import br.upf.biblioteca.dto.LivroDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.LivroService;

@RestController
@RequestMapping(value = "/livro")
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public LivroDTO inserir(@RequestBody LivroDTO livro,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);	
		return livroService.salvar(livro);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<LivroDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return livroService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public LivroDTO buscarPorCd(@RequestHeader(value = "cdLivro") Long cdLivro,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return livroService.buscaPorCd(cdLivro)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Livro não encontrado!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader (value = "cdLivro") Long cdLivro,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		livroService.buscaPorCd(cdLivro)
			.map(livro -> {
				livroService.removerPorCd(livro.getCdLivro());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Livro não encontrado!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param livro
	 * @param cdLivro
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody LivroDTO livro, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		livroService.buscaPorCd(livro.getCdLivro()).map(livroBase -> {
			modelMapper.map(livro, livroBase);
			livroService.salvar(livroBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Livro não encontrado!"));
	}
	
}
