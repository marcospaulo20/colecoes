package br.com.mp.livro.manga.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.livro.manga.repository.Volumes;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class VolumesDAO implements Volumes, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public VolumesDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<Volume> todos() {
		TypedQuery<Volume> query = this.manager.createQuery("SELECT v FROM Volume v ORDER BY v.nome", Volume.class);
		return query.getResultList();
	}
	
	@Override
	public List<Volume> todosPorManga(Manga manga) {
		TypedQuery<Volume> query = this.manager
					.createQuery("SELECT v FROM Volume v "
							+ "JOIN FETCH v.artista "
							+ "JOIN FETCH v.capista "
							+ "LEFT JOIN FETCH v.editor "
							+ "JOIN FETCH v.manga "
							//+ "LEFT OUTER JOIN FETCH v.capitulos "
							+ "WHERE v.manga = :manga "
							+ "ORDER BY v.dataPublicacao", Volume.class);
		query.setParameter("manga", manga);
		return query.getResultList();
	}

	@Override
	public Volume porId(Long id) {
		return this.manager.find(Volume.class, id);
	}

	@Override
	@Transactional
	public Volume salvar(Volume volume) throws RegraNegocioException {
		return this.manager.merge(volume);
	}

	@Override
	@Transactional
	public void remover(Volume volume) throws RegraNegocioException {
		volume = this.porId(volume.getId());
		
		this.manager.remove(volume);
	}

}