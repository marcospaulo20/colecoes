package br.com.mp.tv.serie.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.tv.serie.model.Episodio;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.tv.serie.repository.Episodios;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class EpisodiosDAO implements Episodios, Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;
	
	@Inject
	public EpisodiosDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Episodio> todos() {
		TypedQuery<Episodio> query = this.manager.createQuery("SELECT e FROM Episodio e ORDER BY e.numero", Episodio.class);
		return query.getResultList();
	}

	@Override
	public List<Episodio> todosPorTemporada(Temporada temporada) {
		TypedQuery<Episodio> query = this.manager
				.createQuery("SELECT e FROM Episodio e "						
						+ "WHERE e.temporada = :temporada "
						+ "ORDER BY e.numero", Episodio.class);
		query.setParameter("temporada", temporada);
		return query.getResultList();
	}

	@Override
	public List<Episodio> todosEpisodiosPorSerie(Serie serie) {
		TypedQuery<Episodio> query = this.manager
				.createQuery("SELECT e FROM Episodio e "							
						+ "JOIN FETCH e.temporada "
						+ "WHERE e.temporada.serie = :serie "
						+ "ORDER BY e.numero", Episodio.class);
		query.setParameter("serie", serie);
		return query.getResultList();
	}

	@Override
	public Episodio porId(Long id) {
		return this.manager.find(Episodio.class, id);
	}

	@Override
	@Transactional
	public Episodio salvar(Episodio episodio) throws RegraNegocioException {
		return this.manager.merge(episodio);
	}

	@Override
	@Transactional
	public void remover(Episodio episodio) throws RegraNegocioException {
		episodio = this.porId(episodio.getId());
		
		this.manager.remove(episodio);
	}

}