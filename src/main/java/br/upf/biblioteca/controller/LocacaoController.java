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
import br.upf.biblioteca.dto.LocacaoDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.LocacaoService;

@RestController
@RequestMapping(value = "/locacao")
public class LocacaoController {

	@Autowired
	private LocacaoService locacaoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public LocacaoDTO inserir(@RequestBody LocacaoDTO locacao,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);	
		return locacaoService.salvar(locacao);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<LocacaoDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return locacaoService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public LocacaoDTO buscarPorCd(@RequestHeader(value = "cdLocacao") Long cdLocacao,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return locacaoService.buscaPorCd(cdLocacao)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Locacao não encontrada!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader (value = "cdLocacao") Long cdLocacao,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		locacaoService.buscaPorCd(cdLocacao)
			.map(locacao -> {
				locacaoService.removerPorCd(locacao.getCdLocacao());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Locacao não encontrada!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param locacao
	 * @param cdLocacao
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody LocacaoDTO locacao, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		locacaoService.buscaPorCd(locacao.getCdLocacao()).map(locacaoBase -> {
			modelMapper.map(locacao, locacaoBase);
			locacaoService.salvar(locacaoBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Locacao não encontrada!"));
	}
	
}
