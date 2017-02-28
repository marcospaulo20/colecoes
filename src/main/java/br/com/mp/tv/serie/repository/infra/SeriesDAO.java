package br.com.mp.tv.serie.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.tv.model.Genero;
import br.com.mp.tv.model.TipoCategoria;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.repository.Series;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class SeriesDAO implements Series, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public SeriesDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<Serie> todos() {
		TypedQuery<Serie> query = this.manager
				.createQuery("SELECT s FROM Serie s "
						+ "ORDER BY s.nomeOriginal", Serie.class);
		return query.getResultList();
	}

	@Override
	public List<Serie> todosPorCategoria(TipoCategoria tipoCategoria) {
		TypedQuery<Serie> query = this.manager
				.createQuery("SELECT DISTINCT s FROM Serie s "
						+ "LEFT JOIN FETCH s.seriesGeneros "
						+ "WHERE s.tipoCategoria = :tipoCategoria ORDER BY s.nomeOriginal", Serie.class);
		query.setParameter("tipoCategoria", tipoCategoria);
		return query.getResultList();
	}
	
	@Override
	public List<Serie> todosPorGeneroECategoria(Genero genero, TipoCategoria tipoCategoria) {
		TypedQuery<Serie> query = this.manager
				.createQuery("SELECT DISTINCT s FROM Serie s "
						+ "LEFT JOIN FETCH s.seriesGeneros genero "
						+ "WHERE genero = :genero AND s.tipoCategoria = :tipoCategoria "
						+ "ORDER BY s.nomeOriginal", Serie.class);
		query.setParameter("genero", genero.toString());
		query.setParameter("tipoCategoria", tipoCategoria);
		return query.getResultList();
	}

	@Override
	public Serie porId(Long id) {
		return this.manager.find(Serie.class, id);
	}

	@Override
	@Transactional
	public Serie salvar(Serie serie) throws RegraNegocioException {
		return this.manager.merge(serie);
	}

	@Override
	@Transactional
	public void remover(Serie serie) throws RegraNegocioException {
		serie = this.porId(serie.getId());
		
		this.manager.remove(serie);
	}
}
