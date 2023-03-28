package br.upf.biblioteca.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import br.upf.biblioteca.dto.UsuarioDTO;
import br.upf.biblioteca.login.TokenJWT;
import br.upf.biblioteca.service.UsuarioService;	

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	private static final Logger logger = LogManager.getLogger(UsuarioDTO.class);

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO inserir(@RequestBody UsuarioDTO usuario,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);	
		return usuarioService.salvar(usuario);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<UsuarioDTO> listarTodos(@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return usuarioService.listarTodos();
	}

	@GetMapping(value = "/buscarPorCd")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDTO buscarPorCd(@RequestHeader(value = "cdUsuario") Long cdUsuario,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		return usuarioService.buscaPorCd(cdUsuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Usuario não encontrado!"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@RequestHeader (value = "cdUsuario") Long cdUsuario,
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		usuarioService.buscaPorCd(cdUsuario)
			.map(usuario -> {
				usuarioService.removerPorCd(usuario.getCdUsuario());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Usuario não encontrado!"));
	}
	
	/**
	 * Método de atualizar é necessário a implementação do método Bean modelMapper()
	 * na classe BibliotecaApplication.java
	 * 
	 * @param usuario
	 * @param cdUsuario
	 */
	@PutMapping(value = "/editar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody UsuarioDTO usuario, 
			@RequestHeader(value = "token") String token) {
		TokenJWT.validarToken(token);
		usuarioService.buscaPorCd(usuario.getCdUsuario()).map(usuarioBase -> {
			modelMapper.map(usuario, usuarioBase);
			usuarioService.salvar(usuarioBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Usuario não encontrado!"));
	}
	
	@PostMapping(value = "/login")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDTO autorizar(@RequestHeader(value = "dsLogin") String dsLogin,
			@RequestHeader(value = "dsSenha") String dsSenha) {
		UsuarioDTO usuario;
		
		if(dsLogin != null && !dsLogin.isEmpty() && dsSenha != null && !dsSenha.isEmpty()) {
			usuario = usuarioService.buscarPorLogin(dsLogin);
			
			if(usuario == null) { // se o login não estiver correto...
				logger.error("Usuario não encontrado: " + dsLogin);
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login e/ou Senha incorretos!");
				
			} else if (usuario.getCdUsuario() != null) {
				if(usuario.getDsSenha().equals(dsSenha) && usuario.getDsLogin().equals(dsLogin)) {
					usuario.setToken(TokenJWT.processarTokenJWT(usuario.getTipoUsuario().getCdTipoUsuario(), usuario.getCdUsuario()));
					return usuario;
					
				} else { // se a senha ou o login não estiverem corretos...
					logger.error("Login: " + dsLogin + " ou Senha: " + dsSenha + " incorretos.");
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login e/ou Senha incorretos!");
				}
			} else { // se NÃO encontrar o usuario no banco...
				logger.error("Usuário " + usuario.getCdUsuario() + " não encontrado.");
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado!");
			}
		} else { // se não enviou login e senha corretamente...
			logger.error("Login: " + dsLogin + " ou Senha: " + dsSenha + " incorretos.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login e/ou Senha não são válidos");
		}
	}
	
}
