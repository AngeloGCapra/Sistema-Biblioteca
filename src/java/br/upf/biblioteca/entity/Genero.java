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
@Table(name = "genero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genero.findAll", query = "SELECT t FROM Genero t"),
    @NamedQuery(name = "Genero.findByCdGenero", query = "SELECT t FROM Genero t WHERE t.cdGenero = :cdGenero"),
    @NamedQuery(name = "Genero.findByDsDescricao", query = "SELECT t FROM Genero t WHERE t.dsDescricao = :dsDescricao")})
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_genero")
    private Integer cdGenero;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ds_descricao")
    private String dsDescricao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdGenero")
    private Collection<Livro> livroCollection;

    public Genero() {
    }

    public Genero(Integer cdGenero) {
        this.cdGenero = cdGenero;
    }

    public Genero(Integer cdGenero, String dsDescricao) {
        this.cdGenero = cdGenero;
        this.dsDescricao = dsDescricao;
    }

    public Integer getCdGenero() {
        return cdGenero;
    }

    public void setCdGenero(Integer cdGenero) {
        this.cdGenero = cdGenero;
    }

    public String getDsDescricao() {
        return dsDescricao;
    }

    public void setDsDescricao(String dsDescricao) {
        this.dsDescricao = dsDescricao;
    }

    @XmlTransient
    public Collection<Livro> getLivroCollection() {
        return livroCollection;
    }

    public void setLivroCollection(Collection<Livro> livroCollection) {
        this.livroCollection = livroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdGenero != null ? cdGenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.cdGenero == null && other.cdGenero != null) || (this.cdGenero != null && !this.cdGenero.equals(other.cdGenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.biblioteca.entity.Genero[ cdGenero=" + cdGenero + " ]";
    }
    
}
