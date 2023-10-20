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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "livro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Livro.findAll", query = "SELECT l FROM Livro l"),
    @NamedQuery(name = "Livro.findAllOrderByNomeLivro", query = "SELECT l FROM Livro l ORDER BY l.nmLivro"),
    @NamedQuery(name = "Livro.findByCdLivro", query = "SELECT l FROM Livro l WHERE l.cdLivro = :cdLivro"),
    @NamedQuery(name = "Livro.findByCdRegistro", query = "SELECT l FROM Livro l WHERE l.cdRegistro = :cdRegistro"),
    @NamedQuery(name = "Livro.findByNmLivro", query = "SELECT l FROM Livro l WHERE l.nmLivro = :nmLivro"),
    @NamedQuery(name = "Livro.findByNmEdicao", query = "SELECT l FROM Livro l WHERE l.nmEdicao = :nmEdicao"),
    @NamedQuery(name = "Livro.findByNmEditora", query = "SELECT l FROM Livro l WHERE l.nmEditora = :nmEditora"),
    @NamedQuery(name = "Livro.findByNrFaixaEtaria", query = "SELECT l FROM Livro l WHERE l.nrFaixaEtaria = :nrFaixaEtaria"),
    @NamedQuery(name = "Livro.findByNrCopias", query = "SELECT l FROM Livro l WHERE l.nrCopias = :nrCopias")})
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_livro")
    private Integer cdLivro;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cd_registro")
    private int cdRegistro;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nm_livro")
    private String nmLivro;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nm_edicao")
    private String nmEdicao;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nm_editora")
    private String nmEditora;
    
    @Column(name = "nr_faixa_etaria")
    private Integer nrFaixaEtaria;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "nr_copias")
    private int nrCopias;
    
    @JoinColumn(name = "cd_autor", referencedColumnName = "cd_autor")
    @ManyToOne(optional = false)
    private Autor cdAutor;
    
    @JoinColumn(name = "cd_genero", referencedColumnName = "cd_genero")
    @ManyToOne(optional = false)
    private Genero cdGenero;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdLivro")
    private Collection<Locacao> locacaoCollection;

    public Livro() {
    }

    public Livro(Integer cdLivro) {
        this.cdLivro = cdLivro;
    }

    public Livro(Integer cdLivro, int cdRegistro, String nmLivro, String nmEdicao, String nmEditora, int nrCopias) {
        this.cdLivro = cdLivro;
        this.cdRegistro = cdRegistro;
        this.nmLivro = nmLivro;
        this.nmEdicao = nmEdicao;
        this.nmEditora = nmEditora;
        this.nrCopias = nrCopias;
    }

    public Integer getCdLivro() {
        return cdLivro;
    }

    public void setCdLivro(Integer cdLivro) {
        this.cdLivro = cdLivro;
    }

    public int getCdRegistro() {
        return cdRegistro;
    }

    public void setCdRegistro(int cdRegistro) {
        this.cdRegistro = cdRegistro;
    }

    public String getNmLivro() {
        return nmLivro;
    }

    public void setNmLivro(String nmLivro) {
        this.nmLivro = nmLivro;
    }

    public String getNmEdicao() {
        return nmEdicao;
    }

    public void setNmEdicao(String nmEdicao) {
        this.nmEdicao = nmEdicao;
    }

    public String getNmEditora() {
        return nmEditora;
    }

    public void setNmEditora(String nmEditora) {
        this.nmEditora = nmEditora;
    }

    public Integer getNrFaixaEtaria() {
        return nrFaixaEtaria;
    }

    public void setNrFaixaEtaria(Integer nrFaixaEtaria) {
        this.nrFaixaEtaria = nrFaixaEtaria;
    }

    public int getNrCopias() {
        return nrCopias;
    }

    public void setNrCopias(int nrCopias) {
        this.nrCopias = nrCopias;
    }

    public Autor getCdAutor() {
        return cdAutor;
    }

    public void setCdAutor(Autor cdAutor) {
        this.cdAutor = cdAutor;
    }

    public Genero getCdGenero() {
        return cdGenero;
    }

    public void setCdGenero(Genero cdGenero) {
        this.cdGenero = cdGenero;
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
        hash += (cdLivro != null ? cdLivro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.cdLivro == null && other.cdLivro != null) || (this.cdLivro != null && !this.cdLivro.equals(other.cdLivro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nmLivro;
    }
    
}
