package br.com.mp.livro.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.livro.model.Editora;
import br.com.mp.livro.repository.Editoras;
import br.com.mp.util.RegraNegocioException;

@Named(value = "editoraBean")
@ViewScoped
public class EditoraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Editoras editoras;

	private Editora editora;
	private Editora editoraSelecionada;

	private List<Editora> todasEditoras;

	@PostConstruct
	private void init() {
		this.editora = new Editora();
		this.todasEditoras = new ArrayList<Editora>();
		this.todasEditoras = this.editoras.todas();
	}

	public void salvar() {
		try {
			this.editoras.salvar(this.editora);

			this.todasEditoras = this.editoras.todas();
			this.editora = new Editora();

			Messages.addFlashGlobalInfo("A editora foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar editora!");
		}
	}

	public void excluir() {
		try {
			this.editoras.remover(this.editoraSelecionada);
			this.todasEditoras = this.editoras.todas();
			Messages.addFlashGlobalInfo("Editora excluida com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir editora!");
		}
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public Editora getEditoraSelecionada() {
		return editoraSelecionada;
	}

	public void setEditoraSelecionada(Editora editoraSelecionada) {
		this.editoraSelecionada = editoraSelecionada;
	}

	public List<Editora> getTodasEditoras() {
		return todasEditoras;
	}

	public void setTodasEditoras(List<Editora> todasEditoras) {
		this.todasEditoras = todasEditoras;
	}

}
