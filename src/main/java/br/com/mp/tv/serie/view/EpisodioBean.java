package br.com.mp.tv.serie.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.repository.Pessoas;
import br.com.mp.tv.serie.model.Episodio;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.tv.serie.repository.Episodios;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class EpisodioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Episodios episodios;
	
	@Inject
	private Pessoas pessoas;
	@Inject
	private TemporadaBean temporadaBean;

	private Episodio episodio;
	private Episodio episodioSelecionado;

	private Temporada temporada;

	private List<Episodio> todosEpisodios;
	private List<Pessoa> todosDiretores;

	@PostConstruct
	private void init() {
		this.episodio = new Episodio();
		this.todosEpisodios = new ArrayList<>();		
	}

	public void prepararCadastro() {
		if (isNuloTemporada()) {
			this.episodio.setTemporada(this.temporada);
			this.carregarListas();
		}	
	}
	
	public void carregarListas() {
		this.todosDiretores = this.pessoas.todasPorTipo(TipoPessoa.DIRETOR);
	}

	public boolean isNuloTemporada() {
		if (this.temporada != null)
			return true;
		return false;
	}

	public void salvar() {
		try {
			this.episodios.salvar(this.episodio);

			this.todosEpisodios = episodios.todosPorTemporada(this.temporada);
			this.episodio = new Episodio();
			this.episodio.setTemporada(this.temporada);
			this.quantidadeTotalEpisodiosAssistir();

			Messages.addFlashGlobalInfo("O episodio foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar episodio!");
		}
	}

	public void excluir() {
		try {
			this.episodios.remover(this.episodioSelecionado);
			this.todosEpisodios = episodios.todosPorTemporada(this.temporada);
			this.quantidadeTotalEpisodiosTem();
			this.quantidadeTotalEpisodiosAssistir();

			Messages.addFlashGlobalInfo("Episodio excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir episodio!");
		}
	}

	public void atualizarEpisodioIsTem(Episodio episodio, boolean isTem) {
		if (episodio != null) {
			episodio.setTem(isTem);
			try {
				this.episodios.salvar(episodio);

				Messages.addFlashGlobalInfo("O episodio foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar episodio!");
			}
		}
	}

	public void atualizarEpisodioIsAssistir(Episodio episodio, boolean isAssistir) {
		if (episodio != null) {
			episodio.setAssistir(isAssistir);
			try {
				this.episodios.salvar(episodio);

				Messages.addFlashGlobalInfo("O episodio foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar episodio!");
			}
		}
	}
	
	public Episodio getEpisodio() {
		return episodio;
	}

	public void setEpisodio(Episodio episodio) {
		this.episodio = episodio;
	}

	public Episodio getEpisodioSelecionado() {
		return episodioSelecionado;
	}

	public void setEpisodioSelecionado(Episodio episodioSelecionado) {
		this.episodioSelecionado = episodioSelecionado;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
		if(this.temporada != null) {
			this.todosEpisodios = episodios.todosPorTemporada(this.temporada);
		}
	}

	public List<Episodio> getTodosEpisodios() {
		return todosEpisodios;
	}
	
	public List<Pessoa> getTodosDiretores() {
		return todosDiretores;
	}

	public String quantidadeTotalEpisodiosTem() {
		Serie serie = this.temporadaBean.getSerie();
		List<Episodio> listaEpisodios = this.episodios.todosEpisodiosPorSerie(serie);
		long totalTem = 0, total = 0;

		if (listaEpisodios.size() > 0) {
			total = listaEpisodios.stream().count();
			for (Episodio episodio : listaEpisodios)
				if (episodio.isTem() == true)
					totalTem += 1;
		}

		return totalTem + " / " + total;
	}
	
	public String quantidadeTotalEpisodiosAssistir() {
		Serie serie = this.temporadaBean.getSerie();
		List<Episodio> listaEpisodios = this.episodios.todosEpisodiosPorSerie(serie);
		long totalTem = 0, total = 0;

		if (listaEpisodios.size() > 0) {
			total = listaEpisodios.stream().count();
			for (Episodio episodio : listaEpisodios)
				if (episodio.isAssistir() == true)
					totalTem += 1;
		}

		return totalTem + " / " + total;
	}
}
