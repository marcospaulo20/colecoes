package br.com.mp.livro.manga.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.repository.Mangas;
import br.com.mp.livro.model.Tipo;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class MangasDAO implements Mangas, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public MangasDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<Manga> todos() {
		TypedQuery<Manga> query = this.manager
				.createQuery("SELECT m FROM Manga m "
						+ "JOIN FETCH m.licenciador "
						+ "JOIN FETCH m.autor "
						+ "ORDER BY m.nome", Manga.class);
		return query.getResultList();
	}
	
	@Override
	public List<Manga> todosPorTipo(Tipo tipo) {
		TypedQuery<Manga> query = this.manager
				.createQuery("SELECT m FROM Manga m "
						+ "LEFT JOIN FETCH m.licenciador "
						+ "JOIN FETCH m.autor "
						+ "JOIN FETCH m.editora "
						+ "WHERE m.tipo = :tipo ORDER BY m.nome", Manga.class);
		query.setParameter("tipo", tipo);
		return query.getResultList();
	}

	@Override
	public Manga porId(Long id) {
		return this.manager.find(Manga.class, id);
	}

	@Override
	@Transactional
	public Manga salvar(Manga manga) throws RegraNegocioException {
		return this.manager.merge(manga);
	}

	@Override
	@Transactional
	public void remover(Manga manga) throws RegraNegocioException {
		manga = this.porId(manga.getId());
		
		this.manager.remove(manga);
	}

}
