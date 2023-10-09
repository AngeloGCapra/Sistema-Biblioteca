package br.upf.biblioteca.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Angelo
 */
@Entity
@Table(name = "tipo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findAllOrderByDescricao", query = "SELECT t FROM TipoUsuario t ORDER BY t.dsDescricao"),
    @NamedQuery(name = "TipoUsuario.findByCdTipoUsuario", query = "SELECT t FROM TipoUsuario t WHERE t.cdTipoUsuario = :cdTipoUsuario"),
    @NamedQuery(name = "TipoUsuario.findByDsDescricao", query = "SELECT t FROM TipoUsuario t WHERE t.dsDescricao = :dsDescricao")})
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_tipo_usuario")
    private Integer cdTipoUsuario;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ds_descricao")
    private String dsDescricao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdTipoUsuario")
    private Collection<Usuario> usuarioCollection;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer cdTipoUsuario) {
        this.cdTipoUsuario = cdTipoUsuario;
    }

    public TipoUsuario(Integer cdTipoUsuario, String dsDescricao) {
        this.cdTipoUsuario = cdTipoUsuario;
        this.dsDescricao = dsDescricao;
    }

    public Integer getCdTipoUsuario() {
        return cdTipoUsuario;
    }

    public void setCdTipoUsuario(Integer cdTipoUsuario) {
        this.cdTipoUsuario = cdTipoUsuario;
    }

    public String getDsDescricao() {
        return dsDescricao;
    }

    public void setDsDescricao(String dsDescricao) {
        this.dsDescricao = dsDescricao;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdTipoUsuario != null ? cdTipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.cdTipoUsuario == null && other.cdTipoUsuario != null) || (this.cdTipoUsuario != null && !this.cdTipoUsuario.equals(other.cdTipoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dsDescricao;
    }
    
}
