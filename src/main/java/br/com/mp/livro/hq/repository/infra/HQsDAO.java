package br.com.mp.livro.hq.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.repository.HQs;
import br.com.mp.livro.model.Tipo;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class HQsDAO implements HQs, Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	@Inject
	public HQsDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<HQ> todos() {
		TypedQuery<HQ> query = this.manager
				.createQuery("SELECT h FROM HQ h " 
						+ "JOIN FETCH h.licenciador " 
						+ "ORDER BY h.nome", HQ.class);
		return query.getResultList();
	}
	
	@Override
	public List<HQ> todosPorTipoVirtual() {
		TypedQuery<HQ> query = this.manager
				.createQuery("SELECT h FROM HQ h "
						+ "WHERE h.tipo = :tipo ORDER BY h.nome", HQ.class);
		query.setParameter("tipo", Tipo.VIRTUAL);
		return query.getResultList();
	}

	@Override
	public List<HQ> todosPorTipo(Tipo tipo) {
		TypedQuery<HQ> query = this.manager
				.createQuery("SELECT h FROM HQ h " 
						+ "LEFT JOIN FETCH h.licenciador "
						+ "JOIN FETCH h.editora " 
						+ "WHERE h.tipo = :tipo ORDER BY h.nome", HQ.class);
		query.setParameter("tipo", tipo);
		return query.getResultList();
	}

	@Override
	public HQ porId(Long id) {
		return this.manager.find(HQ.class, id);
	}

	@Override
	@Transactional
	public HQ salvar(HQ hq) throws RegraNegocioException {
		return this.manager.merge(hq);
	}

	@Override
	@Transactional
	public void remover(HQ hq) throws RegraNegocioException {
		hq = this.porId(hq.getId());

		this.manager.remove(hq);
	}

}