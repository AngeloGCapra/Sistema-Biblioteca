package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.biblioteca.dto.AutorDTO;

public interface AutorRepository extends JpaRepository<AutorDTO, Long> {

}
