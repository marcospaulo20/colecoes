package br.com.mp.livro.manga.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.repository.Mangas;
import br.com.mp.livro.model.Categoria;
import br.com.mp.livro.model.Editora;
import br.com.mp.livro.model.Status;
import br.com.mp.livro.model.Tipo;
import br.com.mp.livro.repository.Editoras;
import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.repository.Pessoas;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class MangaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mangas mangas;
	@Inject
	private Editoras editoras;
	@Inject
	private Pessoas pessoas;
	
	private Manga manga;
	private Manga mangaSelecionado;
	
	private Tipo tipoSelecionado;
	
	private boolean isFisico;
	
	private List<Manga> todosMangas;
	private List<Editora> todasEditoras;
	private List<Pessoa> todosAutores;
	
	@PostConstruct
	private void init() {
		this.manga = new Manga();
		this.todosMangas = new ArrayList<Manga>();
		this.tipoSelecionado = Tipo.FISICO;
		this.todosMangas = this.mangas.todosPorTipo(tipoSelecionado);
		this.isFisico = false;
	}
	
	public void prepararCadastro() {
		this.manga = new Manga();
		this.carregarListas();
		this.isFisico = false;
	}
	
	public void carregarListas() {
		this.todasEditoras = this.editoras.todas();
		this.todosAutores = this.pessoas.todasPorTipo(TipoPessoa.AUTOR);
	}
	
	public void mundarTipoManga() {
		this.todosMangas = this.mangas.todosPorTipo(this.tipoSelecionado);
	}
	
	public void tipoModificadao(AjaxBehaviorEvent event) {		
		if(this.manga.getTipo().equals(Tipo.FISICO)) {
			isFisico = true;
		} else {
			isFisico = false;
		}
	}
	
	public void salvar() {
		try {
			this.mangas.salvar(this.manga);

			this.todosMangas = this.mangas.todosPorTipo(this.tipoSelecionado);
			this.manga = new Manga();

			Messages.addFlashGlobalInfo("O titulo foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar titulo!");
		}
	}
	
	public void excluir() {
		try {
			this.mangas.remover(this.mangaSelecionado);
			this.todosMangas = this.mangas.todosPorTipo(this.tipoSelecionado);
			Messages.addFlashGlobalInfo("Titulo excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir titulo!");
		}
	}
	
	public Manga getManga() {
		return manga;
	}
	
	public void setManga(Manga manga) {
		this.manga = manga;
		if(this.manga.getTipo().equals(Tipo.FISICO))
			this.isFisico = true;
		else
			this.isFisico = false;
	}
	
	public Manga getMangaSelecionado() {
		return mangaSelecionado;
	}
	
	public void setMangaSelecionado(Manga mangaSelecionado) {
		this.mangaSelecionado = mangaSelecionado;		
	}
	
	public Tipo getTipoSelecionado() {
		return tipoSelecionado;
	}
	
	public void setTipoSelecionado(Tipo tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}
	
	public boolean isFisico() {
		return isFisico;
	}
 	
	public List<Manga> getTodosMangas() {
		return todosMangas;
	}
	
	public List<Editora> getTodasEditoras() {
		return todasEditoras;
	}
	
	public List<Pessoa> getTodosAutores() {
		return todosAutores;
	}
	
	public Tipo[] getTodosTipos() {
		return Tipo.values();
	}
	
	public Status[] getTodosStatus() {
		return Status.values();
	}
	
	public Categoria[] getTodasCategorias() {
		return Categoria.values();
	}
}
