package br.upf.biblioteca.controllers;

import static org.mockito.Mockito.any;
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
import br.upf.biblioteca.controller.TipoUsuarioController;
import br.upf.biblioteca.dto.TipoUsuarioDTO;
import br.upf.biblioteca.service.TipoUsuarioService;

@WebMvcTest(TipoUsuarioController.class)
public class TipoUsuarioControllerIntTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TipoUsuarioService tipoUsuarioService;
	
	@Test
	public void salvarTipoUsuario() throws Exception {
		
		TipoUsuarioDTO tipoUsuario = new TipoUsuarioDTO();
		tipoUsuario.setDsDescricao("Teste");
		
		when(tipoUsuarioService.salvar(any(TipoUsuarioDTO.class))).thenReturn(tipoUsuario);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/tipoUsuario/inserir")
				.header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiY2RVc3VhcmlvIjoxLCJpc3MiOiJQYWluZWwgZGUgTW9uaXRvcmFtZW50byBkZSBJbnRlZ3Jhw6fDo28iLCJpYXQiOjE2ODMwNjcwODAsImV4cCI6MTY4NTU4NzA4MH0.H7sW51TXY4Fud-i9R0fH1DoVDcY4c206s4pxMYUlpt0")
				.content(new ObjectMapper().writeValueAsString(tipoUsuario))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	
	}
	
}
