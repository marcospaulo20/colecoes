package br.com.mp.livro.manga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mp.model.Pessoa;

@Entity
@Table(name = "tb_volume", schema = "livro")
public class Volume implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_publicacao", nullable = false)
	private Date dataPublicacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artista_id", nullable = false)
	private Pessoa artista;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "capista_id")
	private Pessoa capista;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manga_id", nullable = false)
	private Manga manga;

	private double preco;

	@Column(name = "tem")
	private boolean isTem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "editor_id")
	private Pessoa editor;
	
	@OneToMany(fetch =FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="volume_id")
	private List<Capitulo> capitulos;

	public Volume() {
		this.isTem = Boolean.FALSE;
		this.capitulos = new ArrayList<Capitulo>();
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

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
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

	public Manga getManga() {
		return manga;
	}

	public void setManga(Manga manga) {
		this.manga = manga;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public boolean isTem() {
		return isTem;
	}

	public void setTem(boolean isTem) {
		this.isTem = isTem;
	}

	public Pessoa getEditor() {
		return editor;
	}

	public void setEditor(Pessoa editor) {
		this.editor = editor;
	}
	
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}
	
	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
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
		Volume other = (Volume) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
