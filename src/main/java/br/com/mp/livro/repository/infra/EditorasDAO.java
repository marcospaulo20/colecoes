package br.com.mp.livro.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.livro.model.Editora;
import br.com.mp.livro.repository.Editoras;
import br.com.mp.util.RegraNegocioException;
import br.com.mp.util.Transactional;

public class EditorasDAO implements Editoras, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public EditorasDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Editora> todas() {
		TypedQuery<Editora> query = this.manager.createQuery("SELECT e FROM Editora e ORDER BY e.nome", Editora.class);
		return query.getResultList();
	}

	@Override
	public Editora porId(Long id) {
		return this.manager.find(Editora.class, id);
	}

	@Override
	@Transactional
	public Editora salvar(Editora editora) throws RegraNegocioException {
		return this.manager.merge(editora);
	}

	@Override
	@Transactional
	public void remover(Editora editora) {
		editora = this.porId(editora.getId());
		
		this.manager.remove(editora);
	}

}
