package br.com.mp.livro.hq.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_titulo_hq", schema = "livro")
public class TituloHQ implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "titulo_hq_pai")
	private TituloHQ tituloHQPai;

	@OneToMany(mappedBy = "tituloHQPai", fetch = FetchType.LAZY)
	List<TituloHQ> titulosHQFilhos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_titulo_hq_hqs", schema = "livro",
		joinColumns = { @JoinColumn(name = "titulo_hq_id", referencedColumnName = "id") }, 
		inverseJoinColumns = { @JoinColumn(name = "hq_id", referencedColumnName = "id") })
	private List<HQ> hqs;

	public TituloHQ() {
		this.hqs = new ArrayList<HQ>();
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

	public TituloHQ getTituloHQPai() {
		return tituloHQPai;
	}

	public void setTituloHQPai(TituloHQ tituloHQPai) {
		this.tituloHQPai = tituloHQPai;
	}

	public List<TituloHQ> getTitulosHQFilhos() {
		return titulosHQFilhos;
	}

	public void setTitulosHQFilhos(List<TituloHQ> titulosHQFilhos) {
		this.titulosHQFilhos = titulosHQFilhos;
	}

	public List<HQ> getHqs() {
		return hqs;
	}

	public void setHqs(List<HQ> hqs) {
		this.hqs = hqs;
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
		TituloHQ other = (TituloHQ) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public void adicionarHQs(List<HQ> listaHQs) {
		this.hqs.addAll(listaHQs);
	}

	public void removeTodosHQs() {
		List<HQ> listaHQRemovido = new ArrayList<>();
		for(HQ hq : this.hqs)
			listaHQRemovido.add(hq);
			
		this.hqs.removeAll(listaHQRemovido);
	}

}
