package br.com.mp.livro.manga.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_capitulo", schema = "livro")
public class Capitulo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int numero;

	private String nome;

	private boolean tem;
	private boolean leu;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volume_id", nullable = false)
	private Volume volume;

	public Capitulo() {
		this.tem = Boolean.FALSE;
		this.leu = Boolean.FALSE;
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

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
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
		Capitulo other = (Capitulo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
