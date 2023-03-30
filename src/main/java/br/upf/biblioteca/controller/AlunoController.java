package br.upf.biblioteca.controller;

import java.text.DecimalFormat;
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
import br.upf.biblioteca.dto.AlunoDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.AlunoService;

@RestController
@RequestMapping(value = "/aluno")
public class AlunoController { 

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private DecimalFormat formato = new DecimalFormat("#.##");  
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public AlunoDTO inserir(@RequestHeader(value = "token") String token, @RequestBody AlunoDTO aluno) {
		TokenJWT.validarToken(token);
		aluno.setNrDevendo(Double.valueOf(formato.format(0)));
		return alunoService.salvar(aluno);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<AlunoDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return alunoService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public AlunoDTO buscarPorCd(@RequestHeader(value = "token") String token, @RequestHeader(value = "cdAluno") Long cdAluno) {
		TokenJWT.validarToken(token);
		return alunoService.buscaPorCd(cdAluno)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Aluno não encontrado!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader(value = "token") String token, @RequestHeader (value = "cdAluno") Long cdAluno) {
		TokenJWT.validarToken(token);
		alunoService.buscaPorCd(cdAluno)
			.map(aluno -> {
				alunoService.removerPorCd(aluno.getCdAluno());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Aluno não encontrado!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param aluno
	 * @param cdAluno
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestHeader(value = "token") String token, @RequestBody AlunoDTO aluno) {
		TokenJWT.validarToken(token);
		alunoService.buscaPorCd(aluno.getCdAluno()).map(alunoBase -> {
			modelMapper.map(aluno, alunoBase);
			alunoService.salvar(alunoBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Aluno não encontrado!"));
	}
	
}