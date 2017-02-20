package br.com.mp.livro.hq.repository.infra;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.mp.livro.hq.model.ImagemEdicoes;
import br.com.mp.livro.hq.repository.ImagensEdicoes;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class ImagensEdicoesDAO implements ImagensEdicoes, Serializable {

private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public ImagensEdicoesDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public ImagemEdicoes porEdicao(Long id) {
		try {
			TypedQuery<ImagemEdicoes> query = this.manager
					.createQuery("SELECT i FROM ImagemEdicoes i "						
							+ "JOIN FETCH i.edicao "
							+ "WHERE i.edicao.id = :id ", ImagemEdicoes.class)
					.setParameter("id", id);
			ImagemEdicoes ImagemEdicoes = query.getSingleResult(); 
			return ImagemEdicoes;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public ImagemEdicoes porId(Long id) {
		return this.manager.find(ImagemEdicoes.class, id);
	}

	@Override
	@Transactional
	public ImagemEdicoes salvar(ImagemEdicoes ImagemEdicoes) throws RegraNegocioException {
		return this.manager.merge(ImagemEdicoes);
	}

	@Override
	public boolean isExistirPorEdicao(Long id) {
		try {
			TypedQuery<ImagemEdicoes> query = this.manager
					.createQuery("SELECT i FROM ImagemEdicoes i "						
							+ "WHERE i.edicao.id = :id ", ImagemEdicoes.class)
					.setParameter("id", id);
			ImagemEdicoes ImagemEdicoes = query.getSingleResult();
			if(ImagemEdicoes==null)
				return false;
			return true;
		} catch(NoResultException e) {
			return false;
		}
	}
}
