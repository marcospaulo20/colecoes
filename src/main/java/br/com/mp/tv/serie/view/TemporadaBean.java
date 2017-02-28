package br.com.mp.tv.serie.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;

import br.com.mp.tv.serie.model.Serie;
import br.com.mp.tv.serie.model.Temporada;
import br.com.mp.tv.serie.repository.Temporadas;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class TemporadaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Temporadas temporadas;
	
	@Inject
	private EpisodioBean episodioBean;
	
	private Temporada temporada;
	private Temporada temporadaSelecionada;
	private Serie serie;
	
	private List<Temporada> todasTemporadas;
	
	@PostConstruct
	public void init() {
		this.temporada = new Temporada();
		this.todasTemporadas = new ArrayList<Temporada>();
		this.carregarListaTemporadas();
	}
	
	public void prepararCadastro() {
		this.temporada = new Temporada();
		this.temporada.setSerie(this.serie);
	}
	
	private void carregarListaTemporadas() {
		if(this.isNuloSerie())
			this.todasTemporadas = temporadas.todosPorSerie(this.serie);
	}
	
	private boolean isNuloSerie() {
		if(this.serie != null)
			return true;
		return false;
	}
	
	public void salvar() {
		try {
			this.temporadas.salvar(this.temporada);

			this.carregarListaTemporadas();
			this.temporada = new Temporada();
			this.temporada.setSerie(this.serie);

			Messages.addFlashGlobalInfo("A temporada foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar temporada!");
		}
	}
	
	public void excluir() {
		try {
			this.temporadas.remover(this.temporadaSelecionada);
			this.carregarListaTemporadas();
			Messages.addFlashGlobalInfo("Temporada excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir temporada!");
		}
	}
	
	public void atualizarTemporada(Temporada temporada, boolean selecionado) {
		if(temporada != null) {
			temporada.setTem(selecionado);
			try {
				this.temporadas.salvarTemporadaEpisodios(temporada);
				
				Messages.addFlashGlobalInfo("A temporada e episodios foram atualizados com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar temporada ou episodio!");
			}
		}
	}
	
	public void onRowSelect(SelectEvent event) {
		Temporada temporada = (Temporada) event.getObject();
        this.episodioBean.setTemporada(temporada);
    }
	
	public Temporada getTemporada() {
		return temporada;
	}
	
	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	
	public Temporada getTemporadaSelecionada() {
		return temporadaSelecionada;
	}
	
	public void setTemporadaSelecionada(Temporada temporadaSelecionada) {
		this.temporadaSelecionada = temporadaSelecionada;
	}
	
	public Serie getSerie() {
		return serie;
	}
	
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	
	public List<Temporada> getTodasTemporadas() {
		return todasTemporadas;
	}

	public String quantidadeTotalTemporadas() {
		long total = 0, totalTem = 0;
		this.carregarListaTemporadas();
		
		if(this.todasTemporadas.size() > 0) {
			total = this.todasTemporadas.stream().count();
			
			for(Temporada temporada : this.todasTemporadas)
				if(temporada.isTem() == true)
					totalTem += 1;
		}
		
		return totalTem + " / " + total;
	}
}
