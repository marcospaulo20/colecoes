package br.com.mp.tv.serie.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.tv.model.Classificacao;
import br.com.mp.tv.model.Emissora;
import br.com.mp.tv.model.Genero;
import br.com.mp.tv.model.TipoCategoria;
import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.tv.serie.repository.Series;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class SerieBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Series series;
	
	private Serie serie;
	private Serie serieSelecionado;
	private TipoCategoria tipoCategoriaSelecionado;
	private Genero generoSelecionado;
	
	private List<Serie> todasSeries;
	
	private List<String> todosGenerosSelecionado;
	
	@PostConstruct
	private void init() {
		this.serie = new Serie();
		this.todasSeries = new ArrayList<Serie>();
		this.tipoCategoriaSelecionado = TipoCategoria.SERIE;
		this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
		
		this.todosGenerosSelecionado = new ArrayList<>();
	}
	
	public void prepararCadastro() {
		this.serie = new Serie();
	}	
	
	public void mundarTipoSerie() {
		this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
	}
	
	public void mundarGeneroSerie() {
		this.todasSeries = this.series.todosPorGeneroECategoria(this.generoSelecionado, this.tipoCategoriaSelecionado);
	}
	
	public void atualizarSerie() {
		this.todosGenerosSelecionado = this.serie.getSeriesGeneros();
	}
	
	public void salvar() {
		try {
			if(!this.todosGenerosSelecionado.isEmpty())
				this.serie.setSeriesGeneros(this.todosGenerosSelecionado);
			
			this.series.salvar(this.serie);

			this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
			this.serie = new Serie();
			this.todosGenerosSelecionado = new ArrayList<>();

			Messages.addFlashGlobalInfo("A serie foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar serie!");
		}
	}
	
	public void excluir() {
		try {
			this.series.remover(this.serieSelecionado);
			this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
			Messages.addFlashGlobalInfo("Serie excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir serie!");
		}
	}
	
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	
	public Serie getSerieSelecionado() {
		return serieSelecionado;
	}
	
	public void setSerieSelecionado(Serie serieSelecionado) {
		this.serieSelecionado = serieSelecionado;
	}
	
	public TipoCategoria getTipoCategoriaSelecionado() {
		return tipoCategoriaSelecionado;
	}
	
	public void setTipoCategoriaSelecionado(TipoCategoria tipoCategoriaSelecionado) {
		this.tipoCategoriaSelecionado = tipoCategoriaSelecionado;
	}
	
	public Genero getGeneroSelecionado() {
		return generoSelecionado;
	}
	
	public void setGeneroSelecionado(Genero generoSelecionado) {
		this.generoSelecionado = generoSelecionado;
	}
	
	public List<Serie> getTodasSeries() {
		return todasSeries;
	}
	
	public List<String> getTodosGenerosSelecionado() {
		return todosGenerosSelecionado;
	}
	
	public void setTodosGenerosSelecionado(List<String> todosGenerosSelecionado) {
		this.todosGenerosSelecionado = todosGenerosSelecionado;
	}
	
	public TipoCategoria[] getTodosTiposCategorias() {
		return TipoCategoria.values();
	}
	
	public Classificacao[] getTodasClassificacoes() {
		return Classificacao.values(); 
	}
	
	public Emissora[] getTodasEmissoras() {
		return Emissora.values();
	}
	
	public Genero[] getTodosGeneros() {
		return Genero.values();
	}
	
	public String quantidadeTotalTemporadas(List<Temporada> listaTemporadas) {
		long total = 0, totalTem = 0;
		
		if(listaTemporadas.size() > 0) {
			total = listaTemporadas.stream().count();
			
			for(Temporada temporada : listaTemporadas)
				if(temporada.isTem() == true)
					totalTem += 1;
		}
		
		return totalTem + " / " + total;
	}
}
