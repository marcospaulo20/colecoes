package br.com.mp.livro.hq.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.mp.livro.hq.model.TituloHQ;
import br.com.mp.livro.hq.repository.TitulosHQs;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class TitulosHQsDAO implements TitulosHQs, Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	@Inject
	public TitulosHQsDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<TituloHQ> raizes() {
		return manager
				.createQuery("SELECT t FROM TituloHQ t "
						+ "WHERE t.tituloHQPai IS NULL "
						+ "ORDER BY t.nome", TituloHQ.class)
				.getResultList();
	}
	
	@Override
	public TituloHQ todosHQsPorTituloHQ(TituloHQ tituloHQ) {
		try{
			TypedQuery<TituloHQ> query = this.manager
					.createQuery("SELECT t "
							+ "FROM TituloHQ t "
							+ "LEFT JOIN FETCH t.hqs "
							+ "WHERE t = :tituloHQ "
							+ "ORDER BY t.nome", TituloHQ.class);		
			query.setParameter("tituloHQ", tituloHQ);
			TituloHQ tituloHQRetorno = query.getSingleResult();
			
			return tituloHQRetorno;
		} catch (NoResultException  e) {
			e.printStackTrace(); 
			return null;
		}
	}

	@Override
	public TituloHQ porId(Long id) {
		return this.manager.find(TituloHQ.class, id);
	}
	
	@Override
	@Transactional
	public TituloHQ salvar(TituloHQ tituloHQ) throws RegraNegocioException {
		return this.manager.merge(tituloHQ);
	}

	@Override
	@Transactional
	public void remover(TituloHQ titulosHQ) throws RegraNegocioException {
		titulosHQ = this.porId(titulosHQ.getId());

		this.manager.remove(titulosHQ);
	}

}
