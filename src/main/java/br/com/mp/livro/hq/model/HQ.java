package br.com.mp.livro.hq.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.mp.livro.model.Categoria;
import br.com.mp.livro.model.Editora;
import br.com.mp.livro.model.Status;
import br.com.mp.livro.model.Tipo;

@Entity
@Table(name = "tb_hq", schema = "livro")
public class HQ implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "ano_lancamento", nullable = false)
	private int anoLancamento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Tipo tipo;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "editora_id", nullable = false)
	private Editora editora;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "licenciador_id")
	private Editora licenciador;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "hq_id")
	private List<Edicao> edicoes;
	
	@ManyToMany(mappedBy = "hqs", cascade=CascadeType.ALL)
	private List<TituloHQ> tituloHQs;
	
	public HQ() {
		this.edicoes = new ArrayList<Edicao>();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public Editora getLicenciador() {
		return licenciador;
	}

	public void setLicenciador(Editora licenciador) {
		this.licenciador = licenciador;
	}

	public List<Edicao> getEdicoes() {
		return edicoes;
	}

	public void setEdicoes(List<Edicao> edicoes) {
		this.edicoes = edicoes;
	}
	
	public List<TituloHQ> getTituloHQs() {
		return tituloHQs;
	}
	
	public void setTituloHQs(List<TituloHQ> tituloHQs) {
		this.tituloHQs = tituloHQs;
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
		HQ other = (HQ) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
