package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.upf.biblioteca.dto.LocacaoDTO;

public interface LocacaoRepository extends JpaRepository<LocacaoDTO, Long> {

}
