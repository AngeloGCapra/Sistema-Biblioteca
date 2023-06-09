package br.upf.biblioteca.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "TBL_AUTOR")
public class AutorDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_AUTOR")
	private Long cdAutor;
	
	@Column(name = "NM_NOME", nullable = false)
	private String nmNome;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_NASCIMENTO", nullable = false)
	@JsonFormat(pattern = Util.FORMATO_RETORNO_DATA)
	private Date dtNascimento;
	
}