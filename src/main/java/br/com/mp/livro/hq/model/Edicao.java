package br.com.mp.livro.hq.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mp.model.Pessoa;

@Entity
@Table(name = "tb_edicao", schema = "livro")
public class Edicao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_publicacao", nullable = false)
	private Date dataPublicacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "escritor_id", nullable = false)
	private Pessoa escritor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artista_id", nullable = false)
	private Pessoa artista;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "capista_id")
	private Pessoa capista;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "editor_id")
	private Pessoa editor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hq_id", nullable = false)
	private HQ hq;
	
	private double preco;

	private boolean tem;
	private boolean leu;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Pessoa getEscritor() {
		return escritor;
	}

	public void setEscritor(Pessoa escritor) {
		this.escritor = escritor;
	}

	public Pessoa getArtista() {
		return artista;
	}

	public void setArtista(Pessoa artista) {
		this.artista = artista;
	}

	public Pessoa getCapista() {
		return capista;
	}

	public void setCapista(Pessoa capista) {
		this.capista = capista;
	}

	public Pessoa getEditor() {
		return editor;
	}

	public void setEditor(Pessoa editor) {
		this.editor = editor;
	}

	public HQ getHq() {
		return hq;
	}

	public void setHq(HQ hq) {
		this.hq = hq;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}

	public boolean isTem() {
		return tem;
	}

	public void setTem(boolean tem) {
		this.tem = tem;
	}

	public boolean isLeu() {
		return leu;
	}

	public void setLeu(boolean leu) {
		this.leu = leu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edicao other = (Edicao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
