package br.upf.biblioteca.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.upf.biblioteca.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "TBL_LOCACAO")
public class LocacaoDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_LOCACAO")
	private Long cdLocacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_LOCACAO", nullable = false)
	@JsonFormat(pattern = Util.FORMATO_RETORNO_DATA)
	private Date dtlocacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_DEVOLUCAO", nullable = false)
	@JsonFormat(pattern = Util.FORMATO_RETORNO_DATA)
	private Date dtDevolucao;
	
	@Column(name = "SN_STATUS", nullable = false)
	private String snStatus;
	
	@ManyToOne
	@JoinColumn(name = "CD_LIVRO", nullable = false)
	private LivroDTO livro;
	
	@ManyToOne
	@JoinColumn(name = "CD_USUARIO", nullable = false)
	private UsuarioDTO usuario;
	
	@ManyToOne
	@JoinColumn(name = "CD_ALUNO", nullable = false)
	private AlunoDTO aluno;
	
}
