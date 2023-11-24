package br.upf.biblioteca.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@NamedNativeQueries({
    /**
     * Método utilizado para buscar um usuário, utilizando e email e data de
     * nascimento.
     */
    @NamedNativeQuery(name = "Usuario.findUsuarioByUsuarioEmailDataNascimento",
            query = "SELECT * FROM Usuario AS usr "
            + "WHERE usr.usr_usuario_email = ? "
            + "AND DATE_TRUNC('day', usr.usr_datanascimento) = DATE_TRUNC('day', ?::date) LIMIT 1",
            resultClass = Usuario.class)

})
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findAllOrderByNome", query = "SELECT u FROM Usuario u ORDER BY u.nmNome"),
    @NamedQuery(name = "Usuario.findByCdUsuario", query = "SELECT u FROM Usuario u WHERE u.cdUsuario = :cdUsuario"),
    @NamedQuery(name = "Usuario.findByDsLogin", query = "SELECT u FROM Usuario u WHERE u.dsLogin = :dsLogin"),
    @NamedQuery(name = "Usuario.findByDsSenha", query = "SELECT u FROM Usuario u WHERE u.dsSenha = :dsSenha"),
    @NamedQuery(name = "Usuario.findByNmNome", query = "SELECT u FROM Usuario u WHERE u.nmNome = :nmNome"),
    @NamedQuery(name = "Usuario.findByDsEmail", query = "SELECT u FROM Usuario u WHERE u.dsEmail = :dsEmail"),
    @NamedQuery(name = "Usuario.findByUsrUsuarioLogin", query = "SELECT u FROM Usuario u WHERE u.usrUsuarioLogin = :usrUsuarioLogin"),
    @NamedQuery(name = "Usuario.findByUsrUsuarioEmail", query = "SELECT u FROM Usuario u WHERE u.usrUsuarioEmail = :usrUsuarioEmail"),
    @NamedQuery(name = "Usuario.findByUsrPermissaoacesso", query = "SELECT u FROM Usuario u WHERE u.usrPermissaoacesso = :usrPermissaoacesso")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_usuario")
    private Integer cdUsuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ds_login")
    private String dsLogin;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ds_senha")
    private String dsSenha;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nm_nome")
    private String nmNome;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ds_email")
    private String dsEmail;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "usr_usuario_login")
    private String usrUsuarioLogin;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "usr_usuario_email")
    private String usrUsuarioEmail;

    @Size(max = 50)
    @NotNull
    @Column(name = "usr_permissaoacesso")
    private String usrPermissaoacesso;

    @Basic(optional = false)
    @NotNull
    @Column(name = "usr_datanascimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usrDatanascimento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdUsuario")
    private Collection<Locacao> locacaoCollection;

    @JoinColumn(name = "cd_tipo_usuario", referencedColumnName = "cd_tipo_usuario")
    @ManyToOne(optional = false)
    private TipoUsuario cdTipoUsuario;

    public Usuario() {
    }

    public Usuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Usuario(Integer cdUsuario, String dsLogin, String dsSenha, String nmNome, String dsEmail) {
        this.cdUsuario = cdUsuario;
        this.dsLogin = dsLogin;
        this.dsSenha = dsSenha;
        this.nmNome = nmNome;
        this.dsEmail = dsEmail;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getDsLogin() {
        return dsLogin;
    }

    public void setDsLogin(String dsLogin) {
        this.dsLogin = dsLogin;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public String getNmNome() {
        return nmNome;
    }

    public void setNmNome(String nmNome) {
        this.nmNome = nmNome;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getUsrUsuarioLogin() {
        return usrUsuarioLogin;
    }

    public void setUsrUsuarioLogin(String usrUsuarioLogin) {
        this.usrUsuarioLogin = usrUsuarioLogin;
    }

    public String getUsrUsuarioEmail() {
        return usrUsuarioEmail;
    }

    public void setUsrUsuarioEmail(String usrUsuarioEmail) {
        this.usrUsuarioEmail = usrUsuarioEmail;
    }

    public String getUsrPermissaoacesso() {
        return usrPermissaoacesso;
    }

    public void setUsrPermissaoacesso(String usrPermissaoacesso) {
        this.usrPermissaoacesso = usrPermissaoacesso;
    }

    public Date getUsrDatanascimento() {
        return usrDatanascimento;
    }

    public void setUsrDatanascimento(Date usrDatanascimento) {
        this.usrDatanascimento = usrDatanascimento;
    }

    @XmlTransient
    public Collection<Locacao> getLocacaoCollection() {
        return locacaoCollection;
    }

    public void setLocacaoCollection(Collection<Locacao> locacaoCollection) {
        this.locacaoCollection = locacaoCollection;
    }

    public TipoUsuario getCdTipoUsuario() {
        return cdTipoUsuario;
    }

    public void setCdTipoUsuario(TipoUsuario cdTipoUsuario) {
        this.cdTipoUsuario = cdTipoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdUsuario != null ? cdUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.cdUsuario == null && other.cdUsuario != null) || (this.cdUsuario != null && !this.cdUsuario.equals(other.cdUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nmNome;
    }

}
