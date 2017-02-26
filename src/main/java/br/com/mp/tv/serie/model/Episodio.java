package br.com.mp.tv.serie.model;

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
@Table(name = "tb_episodio", schema = "tv")
public class Episodio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int numero;
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_exibicao", nullable = false)
	private Date dataExibicao;
	private double duracao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diretor_id")
	private Pessoa diretor;

	private boolean tem;
	private boolean assistir;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "temporada_id", nullable = false)
	private Temporada temporada;

	public Episodio() {
		this.tem = false;
		this.assistir = false;
	}
	
	public Long getId() {
		return id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataExibicao() {
		return dataExibicao;
	}

	public void setDataExibicao(Date dataExibicao) {
		this.dataExibicao = dataExibicao;
	}

	public double getDuracao() {
		return duracao;
	}

	public void setDuracao(double duracao) {
		this.duracao = duracao;
	}

	public Pessoa getDiretor() {
		return diretor;
	}

	public void setDiretor(Pessoa diretor) {
		this.diretor = diretor;
	}

	public boolean isTem() {
		return tem;
	}

	public void setTem(boolean tem) {
		this.tem = tem;
	}

	public boolean isAssistir() {
		return assistir;
	}

	public void setAssistir(boolean assistir) {
		this.assistir = assistir;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
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
		Episodio other = (Episodio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
