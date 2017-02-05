package br.com.mp.livro.manga.model;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.mp.livro.model.Categoria;
import br.com.mp.livro.model.Editora;
import br.com.mp.livro.model.Status;
import br.com.mp.livro.model.Tipo;
import br.com.mp.model.Pessoa;

@Entity
@Table(name = "tb_manga", schema = "livro")
public class Manga implements Serializable {

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

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "editora_id", nullable = false)
	private Editora editora;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "licenciador_id")
	private Editora licenciador;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "autor_id", nullable = false)
	private Pessoa autor;
	
	@OneToMany(fetch =FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="manga_id")
	private List<Volume> volumes;
	
	public Manga() {
		this.volumes = new ArrayList<Volume>();
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
	
	public Pessoa getAutor() {
		return autor;
	}
	
	public void setAutor(Pessoa autor) {
		this.autor = autor;
	}
	
	public List<Volume> getVolumes() {
		return volumes;
	}
	
	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
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
		Manga other = (Manga) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
