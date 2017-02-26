package br.com.mp.tv.serie.repository.infra;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.mp.tv.serie.model.ImagemSerie;
import br.com.mp.tv.serie.repository.ImagensSeries;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class ImagensSeriesDAO implements ImagensSeries, Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;
	
	@Inject
	public ImagensSeriesDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public ImagemSerie porSerie(Long id) {
		try {
			TypedQuery<ImagemSerie> query = this.manager
					.createQuery("SELECT i FROM ImagemSerie i "						
							+ "JOIN FETCH i.serie "
							+ "WHERE i.serie.id = :id ", ImagemSerie.class)
					.setParameter("id", id);
			ImagemSerie imagemSerie = query.getSingleResult(); 
			return imagemSerie;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public ImagemSerie porId(Long id) {
		return this.manager.find(ImagemSerie.class, id);
	}

	@Override
	public boolean isExistirPorSerie(Long id) {
		try {
			TypedQuery<ImagemSerie> query = this.manager
					.createQuery("SELECT i FROM ImagemSerie i "						
							+ "WHERE i.serie.id = :id ", ImagemSerie.class)
					.setParameter("id", id);
			ImagemSerie imagemSerie = query.getSingleResult();
			if(imagemSerie==null)
				return false;
			return true;
		} catch(NoResultException e) {
			return false;
		}
	}

	@Override
	@Transactional
	public ImagemSerie salvar(ImagemSerie imagemSerie) throws RegraNegocioException {
		return this.manager.merge(imagemSerie);
	}

}
