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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.com.mp.tv.model.Classificacao;
import br.com.mp.tv.model.Emissora;
import br.com.mp.tv.model.TipoCategoria;

@Entity
@Table(name = "tb_serie", schema = "tv")
public class Serie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome_original", nullable = false)
	private String nomeOriginal;
	@Column(name = "nome_nacional", nullable = false)
	private String nomeNacional;
	@Column(name = "ano_lancamento", nullable = false)
	private int anoLancamento;
	@Column(name = "pais_origem", nullable = false)
	private String paisOrigem;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Classificacao classificacao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Emissora emissora;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoCategoria tipoCategoria;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String sinopse;

	//private Map<Genero, String> listaGeneros  = new HashMap<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "serie_id")
	private List<Temporada> listaTemporada;

	public Serie() {
		this.listaTemporada = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public String getNomeOriginal() {
		return nomeOriginal;
	}

	public void setNomeOriginal(String nomeOriginal) {
		this.nomeOriginal = nomeOriginal;
	}

	public String getNomeNacional() {
		return nomeNacional;
	}

	public void setNomeNacional(String nomeNacional) {
		this.nomeNacional = nomeNacional;
	}

	public int getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public Classificacao getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}

	public Emissora getEmissora() {
		return emissora;
	}

	public void setEmissora(Emissora emissora) {
		this.emissora = emissora;
	}

	public TipoCategoria getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(TipoCategoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public List<Temporada> getListaTemporada() {
		return listaTemporada;
	}

	public void setListaTemporada(List<Temporada> listaTemporada) {
		this.listaTemporada = listaTemporada;
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
		Serie other = (Serie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
