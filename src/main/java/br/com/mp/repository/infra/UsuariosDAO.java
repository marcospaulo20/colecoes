package br.com.mp.repository.infra;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mp.model.Usuario;
import br.com.mp.repository.Usuarios;
import br.com.mp.util.RegraNegocioException;

public class UsuariosDAO implements Usuarios, Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	@Inject
	public UsuariosDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public Usuario validar(String login, String senha) {
		TypedQuery<Usuario> query = this.manager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", Usuario.class);
		query.setParameter("login", login);
		query.setParameter("senha", senha);
		return query.getSingleResult();
	}

	@Override
	public List<Usuario> todos() {
		TypedQuery<Usuario> query = this.manager.createQuery("SELECT u FROM Usuario u", Usuario.class);
		return query.getResultList();
	}

	@Override
	public Usuario salvar(Usuario usuario) throws RegraNegocioException {
		return this.manager.merge(usuario);
	}

}
