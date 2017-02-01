package br.com.mp.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.repository.Pessoas;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class PessoasDAO implements Pessoas, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public PessoasDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Pessoa> todas() {
		TypedQuery<Pessoa> query = this.manager.createQuery("SELECT p FROM Pessoa p ORDER BY p.nome", Pessoa.class);
		return query.getResultList();
	}

	@Override
	public List<Pessoa> todasPorTipo(TipoPessoa tipoPessoa) {
		TypedQuery<Pessoa> query = this.manager.createQuery("SELECT p FROM Pessoa p WHERE p.tipoPessoa = :tipo", Pessoa.class);
		query.setParameter("tipo", tipoPessoa);
		return query.getResultList();
	}
	
	@Override
	public Pessoa porId(Long id) {
		return this.manager.find(Pessoa.class, id);
	}

	@Override
	@Transactional
	public Pessoa salvar(Pessoa pessoa) throws RegraNegocioException {
		return this.manager.merge(pessoa);
	}

	@Override
	@Transactional
	public void remover(Pessoa pessoa) throws RegraNegocioException {
		pessoa = this.porId(pessoa.getId());
		
		this.manager.remove(pessoa);
	}

}
