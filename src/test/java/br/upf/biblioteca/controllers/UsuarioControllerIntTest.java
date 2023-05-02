package br.upf.biblioteca.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.upf.biblioteca.controller.UsuarioController;
import br.upf.biblioteca.dto.TipoUsuarioDTO;
import br.upf.biblioteca.dto.UsuarioDTO;
import br.upf.biblioteca.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerIntTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;
	
	@Test
	public void salvarUsuario() throws Exception {
		
		TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
		tipoUsuario.setCdTipoUsuario((long) 1);
		tipoUsuario.setDsDescricao("Professor");
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setDsLogin("Angelo");
		usuario.setDsSenha("123");
		usuario.setNmNome("Nome Teste");
		usuario.setDsEmail("teste@upf.br");
		usuario.setTipoUsuario(tipoUsuario);
		
		when(usuarioService.salvar(any(UsuarioDTO.class))).thenReturn(usuario);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/usuario/inserir")
				.header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiY2RVc3VhcmlvIjoxLCJpc3MiOiJQYWluZWwgZGUgTW9uaXRvcmFtZW50byBkZSBJbnRlZ3Jhw6fDo28iLCJpYXQiOjE2ODMwNjcwODAsImV4cCI6MTY4NTU4NzA4MH0.H7sW51TXY4Fud-i9R0fH1DoVDcY4c206s4pxMYUlpt0")
				.content(new ObjectMapper().writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	
	}
	
}
