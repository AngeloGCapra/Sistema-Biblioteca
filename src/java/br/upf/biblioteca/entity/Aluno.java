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
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findAllOrderByNome", query = "SELECT a FROM Aluno a ORDER BY a.nmNome ASC"),
    @NamedQuery(name = "Aluno.findByCdAluno", query = "SELECT a FROM Aluno a WHERE a.cdAluno = :cdAluno"),
    @NamedQuery(name = "Aluno.findByNmNome", query = "SELECT a FROM Aluno a WHERE a.nmNome = :nmNome"),
    @NamedQuery(name = "Aluno.findByNrDevendo", query = "SELECT a FROM Aluno a WHERE a.nrDevendo = :nrDevendo"),
    @NamedQuery(name = "Aluno.findByDsContato", query = "SELECT a FROM Aluno a WHERE a.dsContato = :dsContato")})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_aluno")
    private Integer cdAluno;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nm_nome")
    private String nmNome;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nr_devendo")
    private Float nrDevendo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ds_contato")
    private String dsContato;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdAluno")
    private Collection<Locacao> locacaoCollection;

    public Aluno() {
    }

    public Aluno(Integer cdAluno) {
        this.cdAluno = cdAluno;
    }

    public Aluno(Integer cdAluno, String nmNome, String dsContato) {
        this.cdAluno = cdAluno;
        this.nmNome = nmNome;
        this.dsContato = dsContato;
    }

    public Integer getCdAluno() {
        return cdAluno;
    }

    public void setCdAluno(Integer cdAluno) {
        this.cdAluno = cdAluno;
    }

    public String getNmNome() {
        return nmNome;
    }

    public void setNmNome(String nmNome) {
        this.nmNome = nmNome;
    }

    public Float getNrDevendo() {
        return nrDevendo;
    }

    public void setNrDevendo(Float nrDevendo) {
        this.nrDevendo = nrDevendo;
    }

    public String getDsContato() {
        return dsContato;
    }

    public void setDsContato(String dsContato) {
        this.dsContato = dsContato;
    }

    @XmlTransient
    public Collection<Locacao> getLocacaoCollection() {
        return locacaoCollection;
    }

    public void setLocacaoCollection(Collection<Locacao> locacaoCollection) {
        this.locacaoCollection = locacaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdAluno != null ? cdAluno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.cdAluno == null && other.cdAluno != null) || (this.cdAluno != null && !this.cdAluno.equals(other.cdAluno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.biblioteca.entity.Aluno[ cdAluno=" + cdAluno + " ]";
    }
    
}
