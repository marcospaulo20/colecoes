package br.com.mp.livro.manga.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;

import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.livro.manga.repository.Volumes;
import br.com.mp.livro.model.Tipo;
import br.com.mp.model.Pessoa;
import br.com.mp.model.TipoPessoa;
import br.com.mp.repository.Pessoas;
import br.com.mp.util.RegraNegocioException;

@Named(value="volumeBean")
@ViewScoped
public class VolumeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Volumes volumes;
	@Inject
	private Pessoas pessoas;
	
	@Inject
	private CapituloBean capituloBean;
	
	private Volume volume;
	private Volume volumeSelecionado;
	private Manga manga;
	
	private List<Volume> todosVolumes;
	private List<Pessoa> todosArtista;
	private List<Pessoa> todosCapista;
	private List<Pessoa> todosEditores;
	
	@PostConstruct
	public void init() {
		this.volume = new Volume();
		this.todosVolumes = new ArrayList<Volume>();
		this.carregarListaVolumes();
	}
	
	public void prepararCadastro() {
		this.volume = new Volume();
		this.volume.setManga(this.manga);
		carregarListas();
	}

	public void carregarListas() {
		this.todosArtista = pessoas.todasPorTipo(TipoPessoa.ARTISTA);
		this.todosCapista = pessoas.todasPorTipo(TipoPessoa.CAPISTA);
		if(this.isMangaFisico()) {
			this.todosEditores = pessoas.todasPorTipo(TipoPessoa.EDITOR);
		}
	}
	
	private void carregarListaVolumes() {
		if(this.isNuloManga())
			this.todosVolumes = volumes.todosPorManga(this.manga);
	}
	
	private boolean isNuloManga() {
		if(this.manga != null)
			return true;
		return false;
	}
	
	public boolean isMangaFisico() {
		if(this.manga != null)
			return this.manga.getTipo().equals(Tipo.FISICO);		
		return false;			
	}
	
	public void salvar() {
		try {
			this.volumes.salvar(this.volume);

			this.carregarListaVolumes();
			this.volume = new Volume();
			this.volume.setManga(this.manga);

			Messages.addFlashGlobalInfo("O volume foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar volume!");
		}
	}
	
	public void excluir() {
		try {
			this.volumes.remover(this.volumeSelecionado);
			this.carregarListaVolumes();
			Messages.addFlashGlobalInfo("Volume excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir volume!");
		}
	}
	
	public void atualizarVolume(Volume volume, boolean selecionado) {
		if(volume != null) {
			volume.setTem(selecionado);
			try {
				this.volumes.salvar(volume);
				
				Messages.addFlashGlobalInfo("O volume foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar volume!");
			}
		}
	}
	
	public void onRowSelect(SelectEvent event) {
        Volume volume = (Volume) event.getObject();
        this.capituloBean.setVolume(volume);
    }
	
	public Volume getVolume() {
		return volume;
	}
	
	public void setVolume(Volume volume) {
		this.volume = volume;
	}
	
	public Volume getVolumeSelecionado() {
		return volumeSelecionado;
	}
	
	public void setVolumeSelecionado(Volume volumeSelecionado) {
		this.volumeSelecionado = volumeSelecionado;
	}
	
	public Manga getManga() {
		return manga;
	}
	
	public void setManga(Manga manga) {
		this.manga = manga;
	}
	
	public List<Volume> getTodosVolumes() {
		return todosVolumes;
	}
	
	public List<Pessoa> getTodosArtista() {
		return todosArtista;
	}
	
	public List<Pessoa> getTodosCapista() {
		return todosCapista;
	}
	
	public List<Pessoa> getTodosEditores() {
		return todosEditores;
	}
	
	public double precoTotal() {
		if(this.isMangaFisico()) {
			if(this.todosVolumes.size() > 0) {
				double somaTotal = 0.0;
				for(Volume v : this.todosVolumes)
					somaTotal += v.getPreco();
				return somaTotal;
			}
		}
		return 0.0;
	}
	
	public double precoTotalFatam() {
		if(this.isMangaFisico()) {
			if(this.todosVolumes.size() > 0) {
				double somaTotal = 0.0;
				for(Volume v : this.todosVolumes)
					if(v.isTem() == false)
						somaTotal += v.getPreco();
				return somaTotal;
			}
		}
		return 0.0;
	}
	
	public String quantidadeTotalVolumes() {
		long total = 0, totalTem = 0;
		this.carregarListaVolumes();
		
		if(this.todosVolumes.size() > 0) {
			total = this.todosVolumes.stream().count();
			
			for(Volume volume : this.todosVolumes)
				if(volume.isTem() == true)
					totalTem += 1;
		}
		
		return totalTem + " / " + total;
	}
	
}
