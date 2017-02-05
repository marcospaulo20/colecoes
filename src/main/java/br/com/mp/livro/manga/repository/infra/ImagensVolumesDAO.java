package br.com.mp.livro.manga.repository.infra;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.mp.livro.manga.model.ImagemVolumes;
import br.com.mp.livro.manga.repository.ImagensVolumes;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class ImagensVolumesDAO implements ImagensVolumes, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public ImagensVolumesDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public ImagemVolumes todosPorVolume(Long id) {
		try {
			TypedQuery<ImagemVolumes> query = this.manager
					.createQuery("SELECT i FROM ImagemVolumes i "						
							+ "JOIN FETCH i.volume "
							+ "WHERE i.volume.id = :id ", ImagemVolumes.class)
					.setParameter("id", id);
			ImagemVolumes imagemVolumes = query.getSingleResult(); 
			return imagemVolumes;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public ImagemVolumes porId(Long id) {
		return this.manager.find(ImagemVolumes.class, id);
	}

	@Override
	@Transactional
	public ImagemVolumes salvar(ImagemVolumes imagemVolumes) throws RegraNegocioException {
		return this.manager.merge(imagemVolumes);
	}

}
