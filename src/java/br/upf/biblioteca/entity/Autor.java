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

/**
 *
 * @author Angelo
 */
@Entity
@Table(name = "autor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autor.findAll", query = "SELECT a FROM Autor a"),
    @NamedQuery(name = "Autor.findAllOrderByNome", query = "SELECT a FROM Autor a ORDER BY a.nmNome"),
    @NamedQuery(name = "Autor.findByCdAutor", query = "SELECT a FROM Autor a WHERE a.cdAutor = :cdAutor"),
    @NamedQuery(name = "Autor.findByNmNome", query = "SELECT a FROM Autor a WHERE a.nmNome = :nmNome"),
    @NamedQuery(name = "Autor.findByDtNascimento", query = "SELECT a FROM Autor a WHERE a.dtNascimento = :dtNascimento")})
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_autor")
    private Integer cdAutor;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nm_nome")
    private String nmNome;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_nascimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtNascimento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdAutor")
    private Collection<Livro> livroCollection;

    public Autor() {
    }

    public Autor(Integer cdAutor) {
        this.cdAutor = cdAutor;
    }

    public Autor(Integer cdAutor, String nmNome, Date dtNascimento) {
        this.cdAutor = cdAutor;
        this.nmNome = nmNome;
        this.dtNascimento = dtNascimento;
    }

    public Integer getCdAutor() {
        return cdAutor;
    }

    public void setCdAutor(Integer cdAutor) {
        this.cdAutor = cdAutor;
    }

    public String getNmNome() {
        return nmNome;
    }

    public void setNmNome(String nmNome) {
        this.nmNome = nmNome;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
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
        hash += (cdAutor != null ? cdAutor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.cdAutor == null && other.cdAutor != null) || (this.cdAutor != null && !this.cdAutor.equals(other.cdAutor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nmNome;
    }
    
}
