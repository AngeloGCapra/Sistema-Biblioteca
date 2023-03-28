package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.biblioteca.dto.LivroDTO;

public interface LivroRepository extends JpaRepository<LivroDTO, Long> {

}
