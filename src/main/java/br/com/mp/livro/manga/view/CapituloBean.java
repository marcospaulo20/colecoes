package br.com.mp.livro.manga.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.mp.livro.manga.model.Capitulo;
import br.com.mp.livro.manga.model.Manga;
import br.com.mp.livro.manga.model.Volume;
import br.com.mp.livro.manga.repository.Capitulos;
import br.com.mp.util.RegraNegocioException;

@Named
@ViewScoped
public class CapituloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Capitulos capitulos;
	
	@Inject
	private VolumeBean volumeBean;

	private Capitulo capitulo;
	private Capitulo capituloSelecionado;
	private Volume volume;

	private List<Capitulo> todosCapitulos;

	@PostConstruct
	private void init() {
		this.capitulo = new Capitulo();
		this.todosCapitulos = new ArrayList<Capitulo>();				
	}
	
	public void prepararCadastro() {
		if(isNuloVolume())
			this.capitulo.setVolume(this.volume);
	}
	
	public boolean isNuloVolume() {
		if (this.volume != null)			
			return true;		
		return false;
	}

	public void salvar() {
		try {
			this.capitulos.salvar(this.capitulo);

			this.todosCapitulos = capitulos.todosPorVolume(this.volume);
			this.capitulo = new Capitulo();
			this.capitulo.setVolume(this.volume);
			this.quantidadeTotalCapitulosTem();
			this.quantidadeTotalCapitulosLeu();

			Messages.addFlashGlobalInfo("O capitulo foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar capitulo!");
		}
	}

	public void excluir() {
		try {
			this.capitulos.remover(this.capituloSelecionado);
			this.todosCapitulos = capitulos.todosPorVolume(this.volume);
			this.quantidadeTotalCapitulosTem();
			this.quantidadeTotalCapitulosLeu();
			
			Messages.addFlashGlobalInfo("Capitulo excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir capitulo!");
		}
	}

	public void atualizarCapituloIsTem(Capitulo capitulo, boolean isTem) {
		if (capitulo != null) {
			capitulo.setTem(isTem);
			try {
				this.capitulos.salvar(capitulo);

				Messages.addFlashGlobalInfo("O capitulo foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar capitulo!");
			}
		}
	}

	public void atualizarCapituloIsLeu(Capitulo capitulo, boolean isLeu) {
		if (capitulo != null) {
			capitulo.setLeu(isLeu);
			try {
				this.capitulos.salvar(capitulo);

				Messages.addFlashGlobalInfo("O capitulo foi atualizado com sucesso!");
			} catch (RegraNegocioException e) {
				e.printStackTrace();
				Messages.addGlobalError("Não foi possivel atualizar capitulo!");
			}
		}
	}

	public Capitulo getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}

	public Capitulo getCapituloSelecionado() {
		return capituloSelecionado;
	}

	public void setCapituloSelecionado(Capitulo capituloSelecionado) {
		this.capituloSelecionado = capituloSelecionado;
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
		if(this.volume != null) {
			this.todosCapitulos = capitulos.todosPorVolume(this.volume);
		}
	}

	public List<Capitulo> getTodosCapitulos() {
		return todosCapitulos;
	}
	
	public String quantidadeTotalCapitulosTem() {
		Manga manga = this.volumeBean.getManga();
		List<Capitulo> listaCapitulos = this.capitulos.todosCapitulosPorManga(manga);
		long totalTem = 0, total = 0;
		
		if(listaCapitulos.size() > 0) { 
			total = listaCapitulos.stream().count();
			for(Capitulo capitulo : listaCapitulos)
				if(capitulo.isTem() == true)
					totalTem += 1;
		}
		
		return totalTem + " / " + total;
	}
	
	public String quantidadeTotalCapitulosLeu() {
		Manga manga = this.volumeBean.getManga();
		List<Capitulo> listaCapitulos = this.capitulos.todosCapitulosPorManga(manga);
		long totalTem = 0, total = 0;
		
		if(listaCapitulos.size() > 0) { 
			total = listaCapitulos.stream().count();
			for(Capitulo capitulo : listaCapitulos)
				if(capitulo.isLeu() == true)
					totalTem += 1;
		}
		
		return totalTem + " / " + total;
	}
}
