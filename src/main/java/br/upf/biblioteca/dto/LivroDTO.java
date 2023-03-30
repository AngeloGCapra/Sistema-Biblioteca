package br.upf.biblioteca.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TBL_LIVRO")
public class LivroDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_LIVRO")
	private Long cdLivro;
	
	@Column(name = "CD_REGISTRO", nullable = false, unique = true)
	private Long cdRegistro;
	
	@Column(name = "NM_LIVRO", nullable = false)
	private String nmLivro;

	@Column(name = "NM_EDICAO", nullable = false)
	private String nmEdicao;
	
	@Column(name = "NM_EDITORA", nullable = false)
	private String nmEditora;
	
	@Column(name = "NR_FAIXA_ETARIA", nullable = false)
	private Long nrFaixaEtaria;
	
	@Column(name = "NR_COPIAS", nullable = false)
	private Long nrCopias;
	
	@ManyToOne
	@JoinColumn(name = "CD_GENERO", nullable = false)
	private GeneroDTO genero;
	
	@ManyToOne
	@JoinColumn(name = "CD_AUTOR", nullable = false)
	private AutorDTO autor;
	
	@ManyToOne
	@JoinColumn(name = "CD_USUARIO", nullable = false)
	private UsuarioDTO usuario;
	
}