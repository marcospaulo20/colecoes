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
import br.com.mp.tv.model.TipoCategoria;
import br.com.mp.tv.serie.model.Serie;
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
	
	private List<Serie> todasSeries;
	
	@PostConstruct
	private void init() {
		this.serie = new Serie();
		this.todasSeries = new ArrayList<Serie>();
		this.tipoCategoriaSelecionado = TipoCategoria.SERIE;
		this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
	}
	
	public void prepararCadastro() {
		this.serie = new Serie();
	}	
	
	public void mundarTipoSerie() {
		this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
	}
	
	public void salvar() {
		try {
			this.series.salvar(this.serie);

			this.todasSeries = this.series.todosPorCategoria(this.tipoCategoriaSelecionado);
			this.serie = new Serie();

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
	
	public List<Serie> getTodasSeries() {
		return todasSeries;
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
	
}
