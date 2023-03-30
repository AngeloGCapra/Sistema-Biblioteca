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
@Table(name = "TBL_ALUNO")
public class AlunoDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_ALUNO")
	private Long cdAluno;
	
	@Column(name = "NM_NOME", nullable = false, unique = true)
	private String nmNome;
	
	@Column(name = "NR_DEVENDO", nullable = false)
	private Double nrDevendo;
	
	@Column(name = "DS_CONTATO")
	private String dsContato;
	
}
