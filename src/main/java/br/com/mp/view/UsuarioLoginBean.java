package br.com.mp.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Messages;

import br.com.mp.model.Usuario;
import br.com.mp.repository.Usuarios;

@Named
@SessionScoped
public class UsuarioLoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	private Usuario usuario;
	
	@PostConstruct
	private void init() {
		this.usuario = new Usuario();	
	}
	
	public Usuario getUsuarioSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}
	
	public String logaut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/index.xhtml?faces-redirect=true";
	}

	public String efetuarLogin() {
		if (StringUtils.isEmpty(usuario.getLogin()) || StringUtils.isBlank(usuario.getLogin())) {
			Messages.addGlobalError("Favor informar o login!");
			return null;
		} else if (StringUtils.isEmpty(usuario.getSenha()) || StringUtils.isBlank(usuario.getSenha())) {
			Messages.addGlobalError("Favor informar a senha!");
			return null;
		} else {

			usuario = usuarios.validar(usuario.getLogin(), usuario.getSenha());

			if (usuario != null) {

				usuario.setSenha(null);
				usuario.setId(usuario.getId());

				FacesContext facesContext = FacesContext.getCurrentInstance();

				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuario);

				return "sistema/home?faces-redirect=true";
			} else {
				Messages.addGlobalError("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
