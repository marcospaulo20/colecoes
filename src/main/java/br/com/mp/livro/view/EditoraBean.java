package br.com.mp.livro.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.mp.livro.model.Editora;
import br.com.mp.livro.repository.Editoras;

@Named(value="editoraBean")
@ViewScoped
public class EditoraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Editoras editoras;
	
	private List<Editora> todasEditoras;
	
	@PostConstruct
	private void init() {
		this.todasEditoras = new ArrayList<Editora>();
		this.todasEditoras = this.editoras.todas();
	}
	
	public List<Editora> getTodasEditoras() {
		return todasEditoras;
	}
	
	public void setTodasEditoras(List<Editora> todasEditoras) {
		this.todasEditoras = todasEditoras;
	}
	
}
