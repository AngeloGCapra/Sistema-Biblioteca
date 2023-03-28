package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.biblioteca.dto.AlunoDTO;

public interface AlunoRepository extends JpaRepository<AlunoDTO, Long> {

}
