package br.upf.biblioteca.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Angelo
 */
@Entity
@Table(name = "locacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locacao.findAll", query = "SELECT l FROM Locacao l"),
    @NamedQuery(name = "Locacao.findAllOrderByDataDevolucao", query = "SELECT l FROM Locacao l ORDER BY l.dtDevolucao DESC"),
    @NamedQuery(name = "Locacao.findByCdLocacao", query = "SELECT l FROM Locacao l WHERE l.cdLocacao = :cdLocacao"),
    @NamedQuery(name = "Locacao.findByDtDevolucao", query = "SELECT l FROM Locacao l WHERE l.dtDevolucao = :dtDevolucao"),
    @NamedQuery(name = "Locacao.findBySnStatus", query = "SELECT l FROM Locacao l WHERE l.snStatus = :snStatus"),
    @NamedQuery(name = "Locacao.findByDtLocacao", query = "SELECT l FROM Locacao l WHERE l.dtLocacao = :dtLocacao")})
public class Locacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_locacao")
    private Integer cdLocacao;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_devolucao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtDevolucao;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "sn_status")
    private String snStatus;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_locacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtLocacao;
    
    @JoinColumn(name = "cd_aluno", referencedColumnName = "cd_aluno")
    @ManyToOne(optional = false)
    private Aluno cdAluno;
    
    @JoinColumn(name = "cd_livro", referencedColumnName = "cd_livro")
    @ManyToOne(optional = false)
    private Livro cdLivro;
    
    @JoinColumn(name = "cd_usuario", referencedColumnName = "cd_usuario")
    @ManyToOne(optional = false)
    private Usuario cdUsuario;

    public Locacao() {
    }

    public Locacao(Integer cdLocacao) {
        this.cdLocacao = cdLocacao;
    }

    public Locacao(Integer cdLocacao, Date dtDevolucao, String snStatus, Date dtLocacao) {
        this.cdLocacao = cdLocacao;
        this.dtDevolucao = dtDevolucao;
        this.snStatus = snStatus;
        this.dtLocacao = dtLocacao;
    }

    public Integer getCdLocacao() {
        return cdLocacao;
    }

    public void setCdLocacao(Integer cdLocacao) {
        this.cdLocacao = cdLocacao;
    }

    public Date getDtDevolucao() {
        return dtDevolucao;
    }

    public void setDtDevolucao(Date dtDevolucao) {
        this.dtDevolucao = dtDevolucao;
    }

    public String getSnStatus() {
        return snStatus;
    }

    public void setSnStatus(String snStatus) {
        this.snStatus = snStatus;
    }

    public Date getDtLocacao() {
        return dtLocacao;
    }

    public void setDtLocacao(Date dtLocacao) {
        this.dtLocacao = dtLocacao;
    }

    public Aluno getCdAluno() {
        return cdAluno;
    }

    public void setCdAluno(Aluno cdAluno) {
        this.cdAluno = cdAluno;
    }

    public Livro getCdLivro() {
        return cdLivro;
    }

    public void setCdLivro(Livro cdLivro) {
        this.cdLivro = cdLivro;
    }

    public Usuario getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Usuario cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdLocacao != null ? cdLocacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locacao)) {
            return false;
        }
        Locacao other = (Locacao) object;
        if ((this.cdLocacao == null && other.cdLocacao != null) || (this.cdLocacao != null && !this.cdLocacao.equals(other.cdLocacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Código: " + cdLocacao;
    }
    
}
