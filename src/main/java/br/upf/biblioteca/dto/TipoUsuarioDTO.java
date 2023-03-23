package br.upf.biblioteca.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "TBL_TIPO_USUARIO")
public class TipoUsuarioDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_TIPO_USUARIO")
	private Long cdTipoPessoa;
	
	@Column(name = "DS_DESCRICAO", nullable = false)
	private String dsDescricao;
	
}
