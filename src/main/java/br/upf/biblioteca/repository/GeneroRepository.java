package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.biblioteca.dto.GeneroDTO;

public interface GeneroRepository extends JpaRepository<GeneroDTO, Long> {

}
