package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.biblioteca.dto.TipoUsuarioDTO;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioDTO, Long> {

}
