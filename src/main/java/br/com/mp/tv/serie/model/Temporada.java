package br.com.mp.tv.serie.model;

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

import br.com.mp.tv.model.TipoIdioma;

@Entity
@Table(name = "tb_temporada", schema = "tv")
public class Temporada implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(name = "ano_lancamento", nullable = false)
	private int anoLancamento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_idioma")
	private TipoIdioma tipoIdioma;
	
	private boolean tem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serie_id", nullable = false)
	private Serie serie;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "temporada_id")
	private List<Episodio> episodios;

	public Temporada() {
		this.episodios = new ArrayList<>();
		this.tem = Boolean.FALSE;
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
	
	public TipoIdioma getTipoIdioma() {
		return tipoIdioma;
	}
	
	public void setTipoIdioma(TipoIdioma tipoIdioma) {
		this.tipoIdioma = tipoIdioma;
	}
	
	public boolean isTem() {
		return tem;
	}
	
	public void setTem(boolean tem) {
		this.tem = tem;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public List<Episodio> getEpisodios() {
		return episodios;
	}

	public void setEpisodios(List<Episodio> listaEpisodios) {
		this.episodios = listaEpisodios;
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
		Temporada other = (Temporada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
