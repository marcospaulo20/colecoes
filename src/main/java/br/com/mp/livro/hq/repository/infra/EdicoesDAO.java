package br.com.mp.livro.hq.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.livro.hq.model.Edicao;
import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.repository.Edicoes;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class EdicoesDAO implements Edicoes, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public EdicoesDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Edicao> todos() {
		TypedQuery<Edicao> query = this.manager.createQuery("SELECT e FROM Edicao e ORDER BY e.nome", Edicao.class);
		return query.getResultList();
	}
	
	@Override
	public List<Edicao> todosPorHQ(HQ hq) {
		TypedQuery<Edicao> query = this.manager
					.createQuery("SELECT e FROM Edicao e "
							+ "JOIN FETCH e.escritor "
							+ "JOIN FETCH e.artista "
							+ "JOIN FETCH e.capista "
							+ "LEFT JOIN FETCH e.editor "
							+ "JOIN FETCH e.hq "
							+ "WHERE e.hq = :hq "
							+ "ORDER BY e.dataPublicacao", Edicao.class);
		query.setParameter("hq", hq);
		return query.getResultList();
	}

	@Override
	public Edicao porId(Long id) {
		return this.manager.find(Edicao.class, id);
	}

	@Override
	@Transactional
	public Edicao salvar(Edicao edicao) throws RegraNegocioException {
		return this.manager.merge(edicao);
	}

	@Override
	@Transactional
	public void remover(Edicao edicao) throws RegraNegocioException {
		edicao = this.porId(edicao.getId());
		
		this.manager.remove(edicao);
	}
}
