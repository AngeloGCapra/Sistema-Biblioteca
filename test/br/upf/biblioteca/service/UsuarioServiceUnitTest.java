package br.upf.biblioteca.service;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Testes unitários para a classe UsuarioService
 *
 * @author Angelo G. Capra
 */
public class UsuarioServiceUnitTest extends TestCase {

    UsuarioService usuarioService = new UsuarioService();

    @Test
    public void testTamanhoSenhaDeveRetornarTrueParaTamanhoSeisOuMais() {
        assertTrue(usuarioService.validarTamanhoSenha("teste123"));
    }

    @Test
    public void testTamanhoSenhaDeveRetornarFalseParaTamanhoMenosDeSeis() {
        assertFalse(usuarioService.validarTamanhoSenha("teste"));
    }

    @Test
    public void testAutenticarSenhaDeveRetornarTrueParaSenhaIgual() {
        assertTrue(usuarioService.autenticarUsuarioSenha("teste", "teste"));
    }

    @Test
    public void testAutenticarSenhaDeveRetornarFalseParaSenhaDiferente() {
        assertFalse(usuarioService.autenticarUsuarioSenha("teste", "teste123"));
    }

    @Test
    public void testCriptografarSenhaDeveRetornarValorDiferenteDoOriginal() {
        String senha = "senha";
        String senhaCriptografada;
        
        senhaCriptografada = usuarioService.criptografarValue(senha);
        
        assertNotSame(senha, senhaCriptografada);
    }
}
