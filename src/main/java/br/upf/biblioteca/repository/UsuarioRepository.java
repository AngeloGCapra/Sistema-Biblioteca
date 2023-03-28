package br.upf.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.upf.biblioteca.dto.UsuarioDTO;

public interface UsuarioRepository extends JpaRepository<UsuarioDTO, Long> {
	
	@Query(nativeQuery = true, 
			value = "SELECT * FROM tbl_usuario u WHERE u.ds_login LIKE %:dsLogin%")
	public UsuarioDTO findByDsLogin(@Param("dsLogin") String dsLogin); 

}
