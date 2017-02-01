package br.com.mp.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.model.Usuario;
import br.com.mp.repository.Usuarios;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;

	private Usuario usuario;
	
	private List<Usuario> todosUsuarios;
	
	@PostConstruct
	private void init() {
		this.usuario = new Usuario();
		this.todosUsuarios = new ArrayList<Usuario>();
		this.todosUsuarios = this.usuarios.todos();
	}
	
	public void salvar() {
		try {
			this.usuarios.salvar(this.usuario);

			this.todosUsuarios = this.usuarios.todos();
			this.usuario = new Usuario();

			Messages.addFlashGlobalInfo("O usuário foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar usuário!");
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}
}
