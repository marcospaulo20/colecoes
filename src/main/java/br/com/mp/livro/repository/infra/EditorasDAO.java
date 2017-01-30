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
		TypedQuery<Editora> query = this.manager.createQuery("SELECT e FROM Editora e", Editora.class);
		return query.getResultList();
	}

	@Override
	public Editora comDadosIguais(Editora editora) {
		return null;
	}

	@Override
	public Editora porId(Long id) {
		return null;
	}

	@Override
	@Transactional
	public Editora salvar(Editora editora) throws RegraNegocioException {
		return null;
	}

	@Override
	public void remover(Editora editora) {

	}

}
