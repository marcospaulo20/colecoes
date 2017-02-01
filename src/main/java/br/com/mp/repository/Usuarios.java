package br.com.mp.repository;

import java.util.List;

import br.com.mp.model.Usuario;
import br.com.mp.util.RegraNegocioException;

public interface Usuarios {

	Usuario validar(String login, String senha);
	List<Usuario> todos();
	
	Usuario salvar(Usuario usuario) throws RegraNegocioException;
}
