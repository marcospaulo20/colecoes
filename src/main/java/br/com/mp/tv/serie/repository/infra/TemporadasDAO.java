package br.com.mp.tv.serie.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.tv.serie.repository.Temporadas;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class TemporadasDAO implements Temporadas, Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;
	
	@Inject
	public TemporadasDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Temporada> todos() {
		TypedQuery<Temporada> query = this.manager.createQuery("SELECT t FROM Temporada t ORDER BY t.nome", Temporada.class);
		return query.getResultList();
	}

	@Override
	public List<Temporada> todosPorSerie(Serie serie) {
		TypedQuery<Temporada> query = this.manager
				.createQuery("SELECT t FROM Temporada t "
						+ "JOIN FETCH t.serie "
						+ "WHERE t.serie = :serie "
						+ "ORDER BY t.anoLancamento", Temporada.class);
		query.setParameter("serie", serie);
		return query.getResultList();
	}

	@Override
	public Temporada porId(Long id) {
		return this.manager.find(Temporada.class, id);
	}

	@Override
	@Transactional
	public Temporada salvar(Temporada temporada) throws RegraNegocioException {
		return this.manager.merge(temporada);
	}

	@Override
	@Transactional
	public void remover(Temporada temporada) throws RegraNegocioException {
		temporada = this.porId(temporada.getId());
		
		this.manager.remove(temporada);
		
	}

}
