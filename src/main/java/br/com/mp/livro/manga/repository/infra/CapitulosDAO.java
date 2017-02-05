package br.com.mp.livro.manga.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.livro.manga.model.Capitulo;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.livro.manga.repository.Capitulos;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class CapitulosDAO implements Capitulos, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public CapitulosDAO(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public List<Capitulo> todos() {
		TypedQuery<Capitulo> query = this.manager.createQuery("SELECT c FROM Capitulo c ORDER BY c.numero", Capitulo.class);
		return query.getResultList();
	}

	@Override
	public List<Capitulo> todosPorVolume(Volume volume) {
		TypedQuery<Capitulo> query = this.manager
				.createQuery("SELECT c FROM Capitulo c "						
						+ "JOIN FETCH c.volume "
						+ "WHERE c.volume = :volume "
						+ "ORDER BY c.numero", Capitulo.class);
		query.setParameter("volume", volume);
		return query.getResultList();
	}

	@Override
	public Capitulo porId(Long id) {
		return this.manager.find(Capitulo.class, id);
	}

	@Override
	@Transactional
	public Capitulo salvar(Capitulo capitulo) throws RegraNegocioException {
		return this.manager.merge(capitulo);
	}

	@Override
	@Transactional
	public void remover(Capitulo capitulo) throws RegraNegocioException {
		capitulo = this.porId(capitulo.getId());
		
		this.manager.remove(capitulo);
	}


}
