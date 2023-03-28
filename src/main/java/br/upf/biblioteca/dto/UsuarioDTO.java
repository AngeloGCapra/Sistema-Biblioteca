package br.upf.biblioteca.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "TBL_USUARIO")
public class UsuarioDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_USUARIO")
	private Long cdUsuario;

	@Column(name = "DS_LOGIN", nullable = false, unique = true)
	private String dsLogin;
	
	@Column(name = "DS_SENHA", nullable = false)
	private String dsSenha;
	
	@Column(name = "NM_NOME", nullable = false)
	private String nmNome;
	
	@Column(name = "DS_EMAIL", nullable = false)
	private String dsEmail;
	
	@ManyToOne
	@JoinColumn(name = "CD_TIPO_USUARIO", nullable = false)
	private TipoUsuarioDTO tipoUsuario;
	
	@Transient
	private String token;
	
}
